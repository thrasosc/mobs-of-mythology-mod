package net.pixeldream.mythicmobs.entity;

import io.netty.buffer.Unpooled;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.pixeldream.mythicmobs.goal.WyvernFlyRandomGoal;
import net.pixeldream.mythicmobs.mixin.accessor.LivingEntityAccessor;
import net.pixeldream.mythicmobs.network.GeneralPacket;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Optional;
import java.util.UUID;

public class WyvernEntity extends PathAwareEntity implements IAnimatable, Tameable {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP);
    public static final AnimationBuilder WALK = new AnimationBuilder().addAnimation("walk", ILoopType.EDefaultLoopTypes.LOOP);
    public static final AnimationBuilder RUN = new AnimationBuilder().addAnimation("run", ILoopType.EDefaultLoopTypes.LOOP);
    public static final AnimationBuilder ATTACK = new AnimationBuilder().addAnimation("attack", ILoopType.EDefaultLoopTypes.PLAY_ONCE);

    public static final TrackedData<Boolean> IS_FLYING = DataTracker.registerData(WyvernEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> IS_START_FLYING = DataTracker.registerData(WyvernEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> CLIENT_START_FLYING = DataTracker.registerData(WyvernEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> CLIENT_END_FLYING = DataTracker.registerData(WyvernEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> HAS_SADDLE = DataTracker.registerData(WyvernEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(WyvernEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    public static final TrackedData<Byte> TAMEABLE_FLAGS = DataTracker.registerData(WyvernEntity.class, TrackedDataHandlerRegistry.BYTE);

    private long ticksUntilAttackFinish = 0;
    public boolean isFlying;
    private int startFlyingTime = 0;
    private float turningFloat;
    public int keyBind = 342;
    private float wyvernSideSpeed = 0.0F;
    private float wyvernForwardSpeed = 0.0F;
    private int startFlyingTimer = 0;
    private int onGroundTicker;
    private int healingFood;
    private boolean hasSaddle;
    private boolean sitting;

    public WyvernEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return HostileEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0f, false));
        this.goalSelector.add(2, new WyvernFlyRandomGoal(this));
        this.goalSelector.add(3, new WanderAroundGoal(this, 0.75f));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(5, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, CowEntity.class, true));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(IS_FLYING, false);
        this.dataTracker.startTracking(IS_START_FLYING, false);
        this.dataTracker.startTracking(CLIENT_START_FLYING, false);
        this.dataTracker.startTracking(CLIENT_END_FLYING, false);
        this.dataTracker.startTracking(HAS_SADDLE, false);
        this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
        this.dataTracker.startTracking(TAMEABLE_FLAGS, (byte) 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        if (this.getOwnerUuid() != null) {
            tag.putUuid("WyvernOwner", this.getOwnerUuid());

        }
        tag.putBoolean("IsFlying", this.isFlying);
        tag.putBoolean("SittingDragon", this.sitting);
        tag.putBoolean("HasSaddle", this.hasSaddle);
        tag.putInt("StartFlyingTime", this.startFlyingTime);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        UUID uUID2;
        if (tag.containsUuid("WyvernOwner")) {
            uUID2 = tag.getUuid("WyvernOwner");
        }
        else {
            String string = tag.getString("WyvernOwner");
            uUID2 = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string);
        }
        if (uUID2 != null) {
            try {
                this.setOwnerUuid(uUID2);
                this.setTamed(true);
            } catch (Throwable var4) {
                this.setTamed(false);
            }
        }
        this.isFlying = tag.getBoolean("IsFlying");
        this.dataTracker.set(IS_FLYING, this.isFlying);
        this.sitting = tag.getBoolean("SittingDragon");
        this.setSitting(this.sitting);
        this.hasSaddle = tag.getBoolean("HasSaddle");
        this.dataTracker.set(HAS_SADDLE, this.hasSaddle);
        if (this.isFlying) {
            this.startFlyingTime = tag.getInt("StartFlyingTime");
        }
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller", 2.5f, animationEvent -> {
            if (animationEvent.isMoving() && !handSwinging) {
                if (isAttacking() && !handSwinging) {
                    animationEvent.getController().setAnimation(RUN);
                    return PlayState.CONTINUE;
                }
                animationEvent.getController().setAnimation(WALK);
                return PlayState.CONTINUE;
            } else if (handSwinging) {
                animationEvent.getController().setAnimation(ATTACK);
                ticksUntilAttackFinish++;
                if (ticksUntilAttackFinish > 20 * 3) {
                    handSwinging = false;
                    ticksUntilAttackFinish = 0;
                }
                return PlayState.CONTINUE;
            }
            animationEvent.getController().setAnimation(IDLE);
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.isAlive()) {
            if (this.hasPassengers() && this.canBeControlledByRider()) {
                LivingEntity livingEntity = (LivingEntity) this.getPrimaryPassenger();
                double wrapper = MathHelper.wrapDegrees(this.bodyYaw - (double) this.getYaw());
                this.setYaw((float) ((double) this.getYaw() + wrapper));
                this.prevYaw = this.getYaw();
                this.setPitch(livingEntity.getPitch() * 0.5F);
                this.setRotation(this.getYaw(), this.getPitch());
                this.headYaw = livingEntity.headYaw;
                boolean shouldFlyUp = false;
                boolean shouldFlyDown = false;
                shouldFlyUp = ((LivingEntityAccessor) livingEntity).jumping(); // Pressing jump button for going upwards
                if (this.world.isClient && livingEntity instanceof ClientPlayerEntity) {
                    ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity) livingEntity;
                    shouldFlyDown = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), this.keyBind);
                    if (clientPlayerEntity.input.pressingLeft) {
                        turningFloat -= 0.05F;
                    }
                    if (!clientPlayerEntity.input.pressingLeft && turningFloat < 0.0F) {
                        turningFloat = 0.0F;
                    }
                    if (clientPlayerEntity.input.pressingRight) {
                        turningFloat += 0.05F;
                    }
                    if (!clientPlayerEntity.input.pressingRight && turningFloat > 0.0F) {
                        turningFloat = 0.0F;
                    }
                }

                this.setYaw(livingEntity.getYaw());

                float f = livingEntity.sidewaysSpeed * 0.1F;
                float g = livingEntity.forwardSpeed * 0.5F;
                float flySpeed = 0.0F;
                float maxForwardSpeed = 0.6F;
                float maxSidewaysSpeed = 0.15F;

                if ((this.wyvernForwardSpeed < maxForwardSpeed && livingEntity.forwardSpeed > 0.0F) || (this.wyvernForwardSpeed > maxForwardSpeed * -0.3F && livingEntity.forwardSpeed < 0.0F)) {
                    this.wyvernForwardSpeed += g * 0.04F;
                }
                if ((this.wyvernSideSpeed < maxSidewaysSpeed && livingEntity.sidewaysSpeed > 0.0F) || (this.wyvernSideSpeed > maxSidewaysSpeed * -1 && livingEntity.sidewaysSpeed < 0.0F)) {
                    this.wyvernSideSpeed += f * 0.03F;
                }
                if (livingEntity.sidewaysSpeed == 0.0F) {
                    this.wyvernSideSpeed *= 0.7F;
                }
                if (livingEntity.forwardSpeed == 0.0F) {
                    this.wyvernForwardSpeed *= 0.7F;
                }

                // To do, set proper sit position
                if (shouldFlyUp && !this.isFlying && this.startFlyingTimer < 10) {
                    if (this.isTouchingWater() && this.getFluidHeight(FluidTags.WATER) > 0.5D) {
                        if (!this.world.isClient && this.getFirstPassenger() instanceof ServerPlayerEntity) {
                            CustomPayloadS2CPacket packet = new CustomPayloadS2CPacket(GeneralPacket.VELOCITY_PACKET, new PacketByteBuf(Unpooled.buffer().writeInt(this.getId()).writeFloat(0.05F)));
                            ((ServerPlayerEntity) this.getFirstPassenger()).networkHandler.sendPacket(packet);
                        }
                    } else {
                        this.startFlyingTimer++;
                        this.getDataTracker().set(CLIENT_START_FLYING, true);
                        this.getDataTracker().set(IS_START_FLYING, true);
                    }
                }
                if (!shouldFlyUp && !this.isFlying && this.startFlyingTimer > 0) {
                    this.startFlyingTimer--;
                    this.getDataTracker().set(IS_START_FLYING, false);
                }
                if ((this.startFlyingTimer <= 0 && !this.isFlying) || this.isFlying || this.getDataTracker().get(IS_FLYING)) {
                    this.getDataTracker().set(CLIENT_START_FLYING, false);
                }

                if (this.startFlyingTimer >= 10 && (!this.isFlying || !this.getDataTracker().get(IS_FLYING))) {
                    this.isFlying = true;
                    this.getDataTracker().set(IS_FLYING, true);
                    this.startFlyingTimer = 0;
                    this.startFlyingTime = (int) this.world.getTime();
                }
                if (this.isFlying && this.onGround) {// && shouldFlyDown only client: bad
                    this.onGroundTicker++;
                    if (this.onGroundTicker > 3) {
                        this.onGroundTicker = 0;
                        this.isFlying = false;
                        this.getDataTracker().set(IS_FLYING, false);
                        this.getDataTracker().set(CLIENT_END_FLYING, true);
                    }

                }
                if ((this.isFlying || this.getDataTracker().get(IS_FLYING)) && shouldFlyUp) {
                    flySpeed = 0.15F;
                }
                if ((this.isFlying || this.getDataTracker().get(IS_FLYING)) && shouldFlyDown) {
                    flySpeed = -0.2F;
                }
                if ((this.isFlying || this.getDataTracker().get(IS_FLYING)) && !shouldFlyDown && !shouldFlyUp) {
                    flySpeed *= 0.4F;
                }

                if (this.isLogicalSideForUpdatingMovement()) {
                    this.setMovementSpeed((float) this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
                    if ((!this.getDataTracker().get(IS_FLYING) && !this.isFlying) || (this.isTouchingWater() && this.getFluidHeight(FluidTags.WATER) > 0.2D)) {
                        super.travel(new Vec3d((double) f, movementInput.y, (double) g));
                    } else {
                        Vec3d vec3d = new Vec3d(livingEntity.sidewaysSpeed * 0.7D, movementInput.y + flySpeed, livingEntity.forwardSpeed * 0.7D);
                        this.updateVelocity(0.05F, vec3d);
                        this.move(MovementType.SELF, this.getVelocity());
                        this.setVelocity(this.getVelocity());
                    }
                } else if (livingEntity instanceof PlayerEntity) {
                    this.setVelocity(Vec3d.ZERO);
                }
                this.updateLimbs(this, false);
            } else {
                if (this.isFlying || this.getDataTracker().get(IS_FLYING)) {
                    this.updateVelocity(0.02F, movementInput);
                    this.move(MovementType.SELF, this.getVelocity());
                    this.setVelocity(this.getVelocity().multiply((0.91F)));
                    double wrapper = MathHelper.wrapDegrees(this.headYaw - (double) this.getYaw());
                    this.setYaw((float) ((double) this.getYaw() + wrapper));
                    BlockPos blockPos = this.getBlockPos().down(2);
                    if (this.world.getBlockState(blockPos).isSolidBlock(world, blockPos)) {
                        this.setVelocity(this.getVelocity().add(0.0D, -0.005D, 0.0D));
                    }
                    if (this.onGround) {
                        this.getDataTracker().set(CLIENT_END_FLYING, true);
                        this.isFlying = false;
                        this.getDataTracker().set(IS_FLYING, false);
                    }
                } else {
                    this.airStrafingSpeed = 0.02F;
                    super.travel(movementInput);
                }
            }
        }
//        if (this.isFlying && this.startFlyingTime != 0 && (int) (this.world.getTime() - this.startFlyingTime) % 25 == 0)
//            this.playWingFlapSound();
    }

    private boolean canBeControlledByRider() {
        return this.getPrimaryPassenger() instanceof LivingEntity;
    }

    public boolean isOwner(LivingEntity entity) {
        return entity == this.getOwner();
    }

    private boolean wyvernFood(Item item) {
        if (item == Items.PORKCHOP || item == Items.BEEF) {
            healingFood = 5;
            return true;
        } else if (item == Items.MUTTON || item == Items.CHICKEN) {
            healingFood = 4;
            return true;
        } else if (item == Items.RABBIT) {
            healingFood = 3;
            return true;
        }
        return false;
    }

    public void setSitting(boolean sitting) {
        this.sitting = sitting;
        setInSittingPose(sitting);
    }

    public boolean isInSittingPose() {
        return ((Byte) this.dataTracker.get(TAMEABLE_FLAGS) & 1) != 0;
    }

    private void setInSittingPose(boolean inSittingPose) {
        this.sitting = inSittingPose;
        byte b = (Byte) this.dataTracker.get(TAMEABLE_FLAGS);
        if (inSittingPose) {
            this.dataTracker.set(TAMEABLE_FLAGS, (byte) (b | 1));
        } else {
            this.dataTracker.set(TAMEABLE_FLAGS, (byte) (b & 0xFFFFFFFE));
        }

    }

    private void putPlayerOnBack(PlayerEntity player) {
        if (!this.world.isClient) {
            player.setYaw(this.getYaw());
            player.setPitch(this.getPitch());
            player.startRiding(this);
        }
    }

    public void setOwner(PlayerEntity player) {
        this.setTamed(true);
        this.setOwnerUuid(player.getUuid());
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if (!this.isBaby() && this.hasPassengers())
            return super.interactMob(player, hand);

        if (this.world.isClient) {
            boolean bl = this.isOwner(player) || this.isTamed() || this.wyvernFood(item) && !this.isTamed();
            return bl ? ActionResult.CONSUME : ActionResult.PASS;
        } else {
            // Check for owner
            if (this.isTamed() && this.isOwner(player)) {
                if (this.wyvernFood(item) && this.getHealth() < this.getMaxHealth() && player.isSneaking()) {
                    if (!player.isCreative()) {
                        itemStack.decrement(1);
                    }
                    this.heal((float) this.healingFood);
                    return ActionResult.SUCCESS;
                } else if (!player.isSneaking()) {
                    if (!this.hasSaddle) {
                        if (item == Items.SADDLE) {
                            this.world.playSoundFromEntity((PlayerEntity) null, this, SoundEvents.ENTITY_HORSE_SADDLE, SoundCategory.NEUTRAL, 0.8F, 1.0F);
                            if (!player.isCreative()) {
                                itemStack.decrement(1);
                            }
                            this.hasSaddle = true;
                            this.getDataTracker().set(HAS_SADDLE, true);
                            return ActionResult.SUCCESS;
                        }
                        return ActionResult.FAIL;
                    }
                    this.setSitting(false);
                    this.putPlayerOnBack(player);
                    return ActionResult.SUCCESS;
                } else if (this.isInSittingPose()) {
                    this.setSitting(false);
                    return ActionResult.SUCCESS;
                } else if (this.onGround) {
                    this.setSitting(true);
                    return ActionResult.SUCCESS;
                } else
                    return ActionResult.PASS;
            } else if (this.wyvernFood(item)) {
                if (!this.isTamed()) {
                    if (!player.isCreative()) {
                        itemStack.decrement(1);
                    }
                    int tamer;
                    if (item == Items.GOLDEN_APPLE) {
                        tamer = 1;
                    } else {
                        tamer = healingFood;
                    }
                    if (this.random.nextInt(tamer) == 0) {
                        this.setOwner(player);
                        this.navigation.stop();
                        this.setTarget((LivingEntity) null);
                        this.world.sendEntityStatus(this, (byte) 7);
                    } else {
                        this.world.sendEntityStatus(this, (byte) 6);
                    }

                    return ActionResult.SUCCESS;
                } else {
                    if (!player.isCreative()) {
                        itemStack.decrement(1);
                    }
                    return ActionResult.SUCCESS;
                }
            }

            return super.interactMob(player, hand);
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        this.playSound(SoundEvents.ENTITY_RAVAGER_AMBIENT, 1.0f, 0.75f);
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        this.playSound(SoundEvents.ENTITY_RAVAGER_HURT, 1.0f, 0.75f);
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        this.playSound(SoundEvents.ENTITY_RAVAGER_DEATH, 1.0f, 0.75f);
        return null;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_RAVAGER_STEP, 0.15f, 1.0f);
    }

    public boolean isTamed() {
        return ((Byte) this.dataTracker.get(TAMEABLE_FLAGS) & 4) != 0;
    }

    public void setTamed(boolean tamed) {
        byte b = (Byte) this.dataTracker.get(TAMEABLE_FLAGS);
        if (tamed) {
            this.dataTracker.set(TAMEABLE_FLAGS, (byte) (b | 4));
        } else {
            this.dataTracker.set(TAMEABLE_FLAGS, (byte) (b & -5));
        }
    }

    public void setOwnerUuid(@Nullable UUID uuid) {
        this.dataTracker.set(OWNER_UUID, Optional.ofNullable(uuid));
    }

    @Nullable
    @Override
    public UUID getOwnerUuid() {
        return (UUID) ((Optional<UUID>) this.dataTracker.get(OWNER_UUID)).orElse((UUID) (Object) null);
    }

    @Nullable
    @Override
    public Entity getOwner() {
        return null;
    }
}