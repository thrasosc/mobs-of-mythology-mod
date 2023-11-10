package net.pixeldream.mythicmobs.entity.mobs;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animatable.instance.SingletonAnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.core.animation.RawAnimation;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.pixeldream.mythicmobs.config.MythicMobsConfigs;
import net.pixeldream.mythicmobs.registry.ItemRegistry;
import net.pixeldream.mythicmobs.registry.SoundRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class DrakeEntity extends TamableAnimal implements GeoEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    public static final RawAnimation FIRE = RawAnimation.begin().thenLoop("fire");
    private boolean firing = false;
    protected static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(DrakeEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(DrakeEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_EGG;
    private static final Predicate<LivingEntity> UNTAMED_DIET;
    private long ticksUntilAttackFinish = 0;

    public DrakeEntity(EntityType<? extends TamableAnimal> entityType, Level world) {
        super(entityType, world);
        this.xpReward = Monster.XP_REWARD_MEDIUM;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag entityNbt) {
        DrakeVariant variant = Util.getRandom(DrakeVariant.values(), this.random);
        setVariant(variant);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

//    @Override
//    public boolean isBreedingItem(@NotNull ItemStack stack) {
//        return stack.getItem() == Items.GOLDEN_APPLE;
//    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SITTING, false);
        this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
        this.entityData.define(HAS_EGG, false);
    }

    public DrakeVariant getVariant() {
        return DrakeVariant.byId(this.getTypeVariant() & 255);
    }

    private void setVariant(DrakeVariant variant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    private int getTypeVariant() {
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("IsSitting", this.entityData.get(SITTING));
        nbt.putInt("Variant", this.getTypeVariant());
        nbt.putBoolean("HasEgg", this.hasEgg());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.entityData.set(SITTING, nbt.getBoolean("IsSitting"));
        this.entityData.set(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));
        this.setHasEgg(nbt.getBoolean("HasEgg"));
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, MythicMobsConfigs.drakeHealth)
                .add(Attributes.ATTACK_DAMAGE, MythicMobsConfigs.drakeAttackDamage)
                .add(Attributes.ATTACK_SPEED, 2)
                .add(Attributes.ATTACK_KNOCKBACK, 1)
                .add(Attributes.MOVEMENT_SPEED, 0.3);
    }

//    @Override
//    protected void initGoals() {
//        this.goalSelector.add(0, new SwimGoal(this));
//        this.goalSelector.add(1, new SitGoal(this));
//        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.25f, true));
////        this.goalSelector.add(2, new ProjectileAttackGoal(this, 0.0f, 40, 100, 20f));
//        this.goalSelector.add(3, new FollowOwnerGoal(this, 1.2f, 3.0F, 16.0F, false));
////        this.goalSelector.add(4, new MateGoal(this, 1.0));
////        this.goalSelector.add(4, new LayEggGoal(this, 1.0));
//        this.goalSelector.add(5, new WanderAroundGoal(this, 0.75f));
//        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
//        this.goalSelector.add(7, new LookAroundGoal(this));
//        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
//        this.targetSelector.add(2, new AttackWithOwnerGoal(this));
//        this.targetSelector.add(3, (new RevengeGoal(this, new Class[0])).setGroupRevenge(new Class[0]));
//        this.targetSelector.add(4, new ActiveTargetGoal(this, ChupacabraEntity.class, false));
//        this.targetSelector.add(5, new ActiveTargetGoal(this, ChickenEntity.class, false));
//        this.targetSelector.add(6, new UntamedActiveTargetGoal(this, AnimalEntity.class, false, UNTAMED_DIET));
//        this.targetSelector.add(7, new UniversalAngerGoal(this, true));
//    }

    static {
        UNTAMED_DIET = (entity) -> {
            EntityType<?> entityType = entity.getType();
            return entityType == EntityType.SHEEP || entityType == EntityType.COW || entityType == EntityType.HORSE || entityType == EntityType.DONKEY || entityType == EntityType.PIG || entityType == EntityType.CHICKEN || entityType == EntityType.RABBIT;
        };
        HAS_EGG = SynchedEntityData.defineId(DrakeEntity.class, EntityDataSerializers.BOOLEAN);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        Item tameItem = ItemRegistry.CHUPACABRA_COOKED_MEAT_SKEWER;
        boolean b1 = random.nextInt(3) == 2;
        if (isFood(itemstack)) {
            return super.mobInteract(player, hand);
        }
        if (item == tameItem && !isTame()) {
            if (this.level().isClientSide()) {
                if (!b1) {
                    produceParticles(ParticleTypes.SMOKE);
                }
                return InteractionResult.CONSUME;
            } else {
                if (!player.isCreative()) {
                    itemstack.shrink(1);
                }
                if (!this.level().isClientSide() && b1) {
                    super.tame(player);
                    this.navigation.recomputePath();
                    this.setTarget(null);
                    this.level().broadcastEntityEvent(this, (byte) 7);
                    setSit(true);
                }
                return InteractionResult.SUCCESS;
            }
        }
        if (isTame() && !this.level().isClientSide() && hand == InteractionHand.MAIN_HAND) {
            setSit(!isSitting());
            return InteractionResult.SUCCESS;
        }
        if (itemstack.getItem() == tameItem) {
            return InteractionResult.PASS;
        }
        return super.mobInteract(player, hand);
    }

    public void setSit(boolean sitting) {
        this.entityData.set(SITTING, sitting);
        super.setOrderedToSit(sitting);
    }

    public boolean isSitting() {
        return this.entityData.get(SITTING);
    }

    @Override
    public void setTame(boolean tamed) {
        double health = Attributes.MAX_HEALTH.getDefaultValue();
        double damage = Attributes.ATTACK_DAMAGE.getDefaultValue();
        double speed = Attributes.MOVEMENT_SPEED.getDefaultValue();
        super.setTame(tamed);
        if (tamed) {
            getAttribute(Attributes.MAX_HEALTH).setBaseValue(Math.ceil(health + health * 0.25));
            getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(Math.ceil(damage + damage * 0.25));
        } else {
            getAttribute(Attributes.MAX_HEALTH).setBaseValue(health);
            getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(damage);
        }
    }

    protected void produceParticles(ParticleOptions parameters) {
        for (int i = 0; i < 5; ++i) {
            double d = this.random.nextGaussian() * 0.02;
            double e = this.random.nextGaussian() * 0.02;
            double f = this.random.nextGaussian() * 0.02;
            this.level().addParticle(parameters, this.getRandomX(1.0), this.getRandomY() + 1.0, this.getRandomZ(1.0), d, e, f);
        }
    }

    @Override
    public void die(DamageSource damageSource) {
        produceParticles(ParticleTypes.POOF);
        super.die(damageSource);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        this.playSound(SoundRegistry.DRAKE_ROAR, 0.5f, 1.0f);
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        this.playSound(SoundRegistry.DRAKE_ROAR, 0.75f, 1.0f);
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        this.playSound(SoundRegistry.DRAKE_DEATH, 0.75f, 1.0f);
        return null;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.WOLF_STEP, 0.5f, 1.0f);
    }

    public boolean hasEgg() {
        return (Boolean) this.entityData.get(HAS_EGG);
    }

    void setHasEgg(boolean hasEgg) {
        this.entityData.set(HAS_EGG, hasEgg);
    }

//    private static class LayEggGoal extends MoveToTargetPosGoal {
//        private final DrakeEntity drake;
//
//        LayEggGoal(DrakeEntity drake, double speed) {
//            super(drake, speed, 16);
//            this.drake = drake;
//        }
//
//        public boolean canStart() {
//            return this.drake.hasEgg() ? super.canStart() : false;
//        }
//
//        public boolean shouldContinue() {
//            return super.shouldContinue() && this.drake.hasEgg();
//        }
//
//        public void tick() {
//            super.tick();
//            BlockPos blockPos = this.drake.getBlockPos();
//            if (!this.drake.isTouchingWater() && this.hasReached()) {
//                World world = this.drake.world;
//                world.playSound((PlayerEntity) null, blockPos, SoundEvents.ENTITY_TURTLE_LAY_EGG, SoundCategory.BLOCKS, 1.0f, 1.25f);
//                world.setBlockState(this.targetPos.up(), (BlockState) BlockRegistry.DRAKE_EGG_BLOCK.getDefaultState());
//                this.drake.setHasEgg(false);
//                this.drake.setLoveTicks(600);
//            }
//
//        }
//
//        protected boolean isTargetPos(WorldView world, BlockPos pos) {
//            return !world.isAir(pos.up());
//        }
//    }

//    private static class MateGoal extends AnimalMateGoal {
//        private final DrakeEntity drake;
//
//        MateGoal(DrakeEntity drake, double speed) {
//            super(drake, speed);
//            this.drake = drake;
//        }
//
//        public boolean canStart() {
//            return super.canStart() && !this.drake.hasEgg();
//        }
//
//        protected void breed() {
//            ServerPlayerEntity serverPlayerEntity = this.animal.getLovingPlayer();
//            if (serverPlayerEntity == null && this.mate.getLovingPlayer() != null) {
//                serverPlayerEntity = this.mate.getLovingPlayer();
//            }
//
//            if (serverPlayerEntity != null) {
//                serverPlayerEntity.incrementStat(Stats.ANIMALS_BRED);
//                Criteria.BRED_ANIMALS.trigger(serverPlayerEntity, this.animal, this.mate, (PassiveEntity) null);
//            }
//
//            this.drake.setHasEgg(true);
//            this.animal.resetLoveTicks();
//            this.mate.resetLoveTicks();
//            Random random = this.animal.getRandom();
//            if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
//                this.world.spawnEntity(new ExperienceOrbEntity(this.world, this.animal.getX(), this.animal.getY(), this.animal.getZ(), random.nextInt(7) + 1));
//            }
//
//        }
//    }
}
