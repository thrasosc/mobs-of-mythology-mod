package net.pixeldream.mythicmobs.entity;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import net.pixeldream.mythicmobs.registry.BlockRegistry;
import net.pixeldream.mythicmobs.registry.EntityRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.function.Predicate;

public class DrakeEntity extends TameableEntity implements IAnimatable {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public static final AnimationBuilder SIT = new AnimationBuilder().addAnimation("sit", ILoopType.EDefaultLoopTypes.LOOP);
    public static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP);
    public static final AnimationBuilder WALK = new AnimationBuilder().addAnimation("walk", ILoopType.EDefaultLoopTypes.LOOP);
    public static final AnimationBuilder RUN = new AnimationBuilder().addAnimation("run", ILoopType.EDefaultLoopTypes.LOOP);
    public static final AnimationBuilder ATTACK = new AnimationBuilder().addAnimation("attack", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
    protected static final TrackedData<Integer> DATA_ID_TYPE_VARIANT = DataTracker.registerData(DrakeEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> SITTING = DataTracker.registerData(DrakeEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> HAS_EGG;
    private static final Predicate<LivingEntity> UNTAMED_DIET;
    private long ticksUntilAttackFinish = 0;

    public DrakeEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        DrakeVariant variant = Util.getRandom(DrakeVariant.values(), this.random);
        setVariant(variant);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
//        DrakeEntity baby = EntityRegistry.DRAKE_ENTITY.create(world);

//        world.setBlockState(this.getBlockPos(), BlockRegistry.DRAKE_EGG_BLOCK.getDefaultState());
//        this.getPos().add(1, 0, 0);
//        entity.getPos().add(-1, 0, 0);

//        DrakeVariant variant = Util.getRandom(DrakeVariant.values(), this.random);
//        baby.setVariant(variant);
        DrakeEntity baby = EntityRegistry.DRAKE_ENTITY.create(world);
        baby.setVariant(Util.getRandom(DrakeVariant.values(), this.random));
        return baby;
    }

    @Override
    public boolean isBreedingItem(@NotNull ItemStack stack) {
        return stack.getItem() == Items.GOLDEN_APPLE;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SITTING, false);
        this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
        this.dataTracker.startTracking(HAS_EGG, false);
    }

    public DrakeVariant getVariant() {
        return DrakeVariant.byId(this.getTypeVariant() & 255);
    }

    private void setVariant(DrakeVariant variant) {
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("isSitting", this.dataTracker.get(SITTING));
        nbt.putInt("Variant", this.getTypeVariant());
        nbt.putBoolean("HasEgg", this.hasEgg());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(SITTING, nbt.getBoolean("isSitting"));
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));
        this.setHasEgg(nbt.getBoolean("HasEgg"));
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return HostileEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 30).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5).add(EntityAttributes.GENERIC_ATTACK_SPEED, 2).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new SitGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.25f, true));
        this.goalSelector.add(3, new FollowOwnerGoal(this, 1.2f, 10.0F, 2.0F, false));
        this.goalSelector.add(4, new MateGoal(this, 1.0));
        this.goalSelector.add(4, new LayEggGoal(this, 1.0));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.75f));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new AttackWithOwnerGoal(this));
        this.targetSelector.add(3, (new RevengeGoal(this, new Class[0])).setGroupRevenge(new Class[0]));
        this.targetSelector.add(4, new UntamedActiveTargetGoal(this, AnimalEntity.class, false, UNTAMED_DIET));
        this.targetSelector.add(5, new ActiveTargetGoal(this, ChickenEntity.class, false));
        this.targetSelector.add(6, new UniversalAngerGoal(this, true));
    }

    static {
        UNTAMED_DIET = (entity) -> {
            EntityType<?> entityType = entity.getType();
            return entityType == EntityType.SHEEP || entityType == EntityType.COW || entityType == EntityType.HORSE || entityType == EntityType.DONKEY || entityType == EntityType.PIG || entityType == EntityType.CHICKEN || entityType == EntityType.RABBIT;
        };
        HAS_EGG = DataTracker.registerData(DrakeEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller", 2.5f, animationEvent -> {
            if (isSitting()) {
                animationEvent.getController().setAnimation(SIT);
                return PlayState.CONTINUE;
            } else if (animationEvent.isMoving() && !handSwinging) {
                if (isAttacking() && !handSwinging) {
                    animationEvent.getController().setAnimation(RUN);
                    return PlayState.CONTINUE;
                }
                animationEvent.getController().setAnimation(WALK);
                return PlayState.CONTINUE;
            } else if (handSwinging) {
                animationEvent.getController().setAnimation(ATTACK);
                ticksUntilAttackFinish++;
                if (ticksUntilAttackFinish > 25 * 2) {
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
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getStackInHand(hand);
        Item item = itemstack.getItem();
        Item tameItem = Items.ROTTEN_FLESH;
        if (isBreedingItem(itemstack)) {
            return super.interactMob(player, hand);
        }
        if (item == tameItem && !isTamed()) {
            if (this.world.isClient()) {
                return ActionResult.CONSUME;
            } else {
                if (!player.getAbilities().creativeMode) {
                    itemstack.decrement(1);
                }
                if (!this.world.isClient()) {
                    super.setOwner(player);
                    this.navigation.recalculatePath();
                    this.setTarget(null);
                    this.world.sendEntityStatus(this, (byte) 7);
                    setSit(true);
                }
                return ActionResult.SUCCESS;
            }
        }
        if (isTamed() && !this.world.isClient() && hand == Hand.MAIN_HAND) {
            setSit(!isSitting());
            return ActionResult.SUCCESS;
        }
        if (itemstack.getItem() == tameItem) {
            return ActionResult.PASS;
        }
        return super.interactMob(player, hand);
    }

    public void setSit(boolean sitting) {
        this.dataTracker.set(SITTING, sitting);
        super.setSitting(sitting);
    }

    public boolean isSitting() {
        return this.dataTracker.get(SITTING);
    }

    @Override
    public void setTamed(boolean tamed) {
        double health = EntityAttributes.GENERIC_MAX_HEALTH.getDefaultValue();
        double damage = EntityAttributes.GENERIC_ATTACK_DAMAGE.getDefaultValue();
        double speed = EntityAttributes.GENERIC_MOVEMENT_SPEED.getDefaultValue();
        super.setTamed(tamed);
        if (tamed) {
            getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(Math.ceil(health + health * 0.25));
            getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(Math.ceil(damage + damage * 0.25));
        } else {
            getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(health);
            getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(damage);
        }
    }

    protected void produceParticles(ParticleEffect parameters) {
        for (int i = 0; i < 5; ++i) {
            double d = this.random.nextGaussian() * 0.02;
            double e = this.random.nextGaussian() * 0.02;
            double f = this.random.nextGaussian() * 0.02;
            this.world.addParticle(parameters, this.getParticleX(1.0), this.getRandomBodyY() + 1.0, this.getParticleZ(1.0), d, e, f);
        }
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
    }

    @Override
    public void updatePostDeath() {
        ++deathTime;
        if (deathTime == 30) {
            produceParticles(ParticleTypes.POOF);
            this.remove(Entity.RemovalReason.KILLED);
            this.dropXp();
        }
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        this.playSound(SoundEvents.ENTITY_ENDER_DRAGON_AMBIENT, 1.0f, 50.0f);
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        this.playSound(SoundEvents.ENTITY_ENDER_DRAGON_GROWL, 1.0f, 50.0f);
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        this.playSound(SoundEvents.ENTITY_ENDER_DRAGON_GROWL, 1.0f, 10.0f);
        return null;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.5f, 1.0f);
    }

    public boolean hasEgg() {
        return (Boolean) this.dataTracker.get(HAS_EGG);
    }

    void setHasEgg(boolean hasEgg) {
        this.dataTracker.set(HAS_EGG, hasEgg);
    }

    private static class LayEggGoal extends MoveToTargetPosGoal {
        private final DrakeEntity drake;

        LayEggGoal(DrakeEntity drake, double speed) {
            super(drake, speed, 16);
            this.drake = drake;
        }

        public boolean canStart() {
            return this.drake.hasEgg() ? super.canStart() : false;
        }

        public boolean shouldContinue() {
            return super.shouldContinue() && this.drake.hasEgg();
        }

        public void tick() {
            super.tick();
            BlockPos blockPos = this.drake.getBlockPos();
            if (!this.drake.isTouchingWater() && this.hasReached()) {
                World world = this.drake.world;
                world.playSound((PlayerEntity) null, blockPos, SoundEvents.ENTITY_TURTLE_LAY_EGG, SoundCategory.BLOCKS, 0.3F, 0.9F + world.random.nextFloat() * 0.2F);
                world.setBlockState(this.targetPos.up(), (BlockState) BlockRegistry.DRAKE_EGG_BLOCK.getDefaultState());
                this.drake.setHasEgg(false);
                this.drake.setLoveTicks(600);
            }

        }

        protected boolean isTargetPos(WorldView world, BlockPos pos) {
            return !world.isAir(pos.up());
        }
    }

    private static class MateGoal extends AnimalMateGoal {
        private final DrakeEntity drake;

        MateGoal(DrakeEntity drake, double speed) {
            super(drake, speed);
            this.drake = drake;
        }

        public boolean canStart() {
            return super.canStart() && !this.drake.hasEgg();
        }

        protected void breed() {
            ServerPlayerEntity serverPlayerEntity = this.animal.getLovingPlayer();
            if (serverPlayerEntity == null && this.mate.getLovingPlayer() != null) {
                serverPlayerEntity = this.mate.getLovingPlayer();
            }

            if (serverPlayerEntity != null) {
                serverPlayerEntity.incrementStat(Stats.ANIMALS_BRED);
                Criteria.BRED_ANIMALS.trigger(serverPlayerEntity, this.animal, this.mate, (PassiveEntity)null);
            }

            this.drake.setHasEgg(true);
            this.animal.resetLoveTicks();
            this.mate.resetLoveTicks();
            Random random = this.animal.getRandom();
            if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
                this.world.spawnEntity(new ExperienceOrbEntity(this.world, this.animal.getX(), this.animal.getY(), this.animal.getZ(), random.nextInt(7) + 1));
            }

        }
    }
}
