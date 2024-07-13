package net.pixeldreamstudios.mobs_of_mythology.entity.creature;

import mod.azure.azurelib.common.api.common.animatable.GeoEntity;
import mod.azure.azurelib.common.internal.common.util.AzureLibUtil;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.constant.DefaultAnimations;
import net.pixeldreamstudios.mobs_of_mythology.registry.ItemRegistry;
import net.pixeldreamstudios.mobs_of_mythology.registry.SoundRegistry;
import net.tslat.smartbrainlib.api.core.navigation.SmoothGroundNavigation;
import org.jetbrains.annotations.Nullable;

public class AutomatonEntity extends TamableAnimal implements GeoEntity {
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
    private static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(AutomatonEntity.class, EntityDataSerializers.INT);

    public AutomatonEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.navigation = new SmoothGroundNavigation(this, level);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    public int getAttckingState() {
        return entityData.get(STATE);
    }

    public void setAttackingState(int time) {
        entityData.set(STATE, time);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(STATE, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("state", getAttckingState());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setAttackingState(nbt.getInt("state"));
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ItemRegistry.BRONZE_INGOT.get());
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this, new Class[0])).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, Monster.class, false));
        this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal(this, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, MobsOfMythology.config.automatonHealth)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
                .add(Attributes.ATTACK_DAMAGE, MobsOfMythology.config.automatonAttackDamage);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    protected void produceParticles(ParticleOptions parameters) {
        if (this.level().isClientSide) {
            for (int i = 0; i < 2; ++i) {
                double d = this.random.nextGaussian() * 0.02;
                double e = this.random.nextGaussian() * 0.02;
                double f = this.random.nextGaussian() * 0.02;
                this.level().addParticle(parameters, this.getRandomX(1.0), this.getRandomY() + 1.0, this.getRandomZ(1.0), d, e, f);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (getHealth() < (double) 50) {
            if (getHealth() < (double) 25) {
                produceParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE);
                return;
            }
            produceParticles(ParticleTypes.SMOKE);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        Item item = itemStack.getItem();
        if (this.level().isClientSide && (!this.isBaby() || !this.isFood(itemStack))) {
            boolean bl = this.isOwnedBy(player) || this.isTame() || itemStack.is(ItemRegistry.GEAR.get()) && !this.isTame();
            return bl ? InteractionResult.CONSUME : InteractionResult.PASS;
        } else if (this.isTame()) {
            if (this.isFood(itemStack) && this.getHealth() < this.getMaxHealth()) {
                itemStack.consume(1, player);
                FoodProperties foodProperties = (FoodProperties)itemStack.get(DataComponents.FOOD);
                float f = foodProperties != null ? (float)foodProperties.nutrition() : 1.0F;
                this.heal(2.0F * f);
                return InteractionResult.sidedSuccess(this.level().isClientSide());
            }
            InteractionResult interactionResult = super.mobInteract(player, interactionHand);
            if (!interactionResult.consumesAction() && this.isOwnedBy(player)) {
                this.setOrderedToSit(!this.isOrderedToSit());
                this.playSound(SoundRegistry.ROBOTIC_VOICE.get(), 1.0f, 1.0f);
                MinecraftServer server = player.getServer();
                if (server != null) {
                    server.tell(new TickTask(0, () -> player.displayClientMessage(Component.literal(isInSittingPose() ? "I will follow you." : "I will wait for you."), true)));
                }
                this.jumping = false;
                this.navigation.stop();
                this.setTarget((LivingEntity)null);
                return InteractionResult.SUCCESS_NO_ITEM_USED;
            } else {
                return interactionResult;
            }
        } else if (itemStack.is(ItemRegistry.GEAR.get())) {
            itemStack.consume(1, player);
            this.tryToTame(player);
            return InteractionResult.SUCCESS;
        } else {
            return super.mobInteract(player, interactionHand);
        }
    }

    private void tryToTame(Player player) {
        if (this.random.nextInt(3) == 0) {
            this.tame(player);
            this.navigation.stop();
            this.setTarget((LivingEntity)null);
            this.setOrderedToSit(true);
            this.playSound(SoundRegistry.ROBOTIC_VOICE.get(), 1.0f, 1.0f);
            MinecraftServer server = player.getServer();
            if (server != null) {
                server.tell(new TickTask(0, () -> player.displayClientMessage(Component.literal("I will protect you at all costs, " + player.getScoreboardName() + "."), true)));
            }
            this.level().broadcastEntityEvent(this, (byte)7);
        } else {
            this.level().broadcastEntityEvent(this, (byte)6);
        }
    }

    @Override
    public Vec3 getLeashOffset() {
        return new Vec3(0.0, 0.845f * this.getEyeHeight(), this.getBbWidth() * 0.4f);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "livingController", 3, event -> {
            if (event.isMoving() && !swinging) {
                if (isAggressive()) {
                    return event.setAndContinue(DefaultAnimations.RUN);
                }
                return event.setAndContinue(DefaultAnimations.WALK);
            }
            return event.setAndContinue(DefaultAnimations.IDLE);
        })).add(new AnimationController<>(this, "attackController", 0, event -> {
            swinging = false;
            return PlayState.STOP;
        }).triggerableAnim("attack", DefaultAnimations.ATTACK).triggerableAnim("attack2", DefaultAnimations.ATTACK2));
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        this.triggerAnim("attackController", "attack");
//        this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 255));
        this.getNavigation().stop();
        return super.doHurtTarget(entity);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.IRON_GOLEM_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundRegistry.ROBOTIC_VOICE.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, 1.0f, 1.0f);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}