package net.pixeldream.mythicmobs.entity;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.util.AzureLibUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.pixeldream.mythicmobs.config.MythicMobsConfigs;
import net.pixeldream.mythicmobs.entity.constant.DefaultAnimations;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import org.jetbrains.annotations.Nullable;

public class WendigoEntity extends BossEntity implements SmartBrainOwner<WendigoEntity>, GeoEntity, Monster {
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
    public RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    public RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
    private long ticksUntilAttackFinish = 0;
    private int roarTimer = 0;
    private long age = 0;
    private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT = DataTracker.registerData(WendigoEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> HAS_ROARED = DataTracker.registerData(WendigoEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> ROARING = DataTracker.registerData(WendigoEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> HAS_ATTACKED = DataTracker.registerData(WendigoEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> WAS_STANDING = DataTracker.registerData(WendigoEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


    public WendigoEntity(EntityType<? extends WendigoEntity> entityType, World world) {
        super(entityType, world, BossBar.Color.PURPLE);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
        this.dataTracker.startTracking(HAS_ROARED, Boolean.FALSE);
        this.dataTracker.startTracking(ROARING, Boolean.TRUE);
        this.dataTracker.startTracking(HAS_ATTACKED, Boolean.FALSE);
        this.dataTracker.startTracking(WAS_STANDING, Boolean.FALSE);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        WendigoVariant variant = Util.getRandom(WendigoVariant.values(), this.random);
        setVariant(variant);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));
    }

    public WendigoVariant getVariant() {
        return WendigoVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
    }

    private void setVariant(WendigoVariant variant) {
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    private void setAnim() {
        if (getVariant().equals(WendigoVariant.WENDIGO)) {
            IDLE = RawAnimation.begin().thenLoop("idle");
            WALK = RawAnimation.begin().thenLoop("walk");
        } else {
            this.dataTracker.set(WAS_STANDING, Boolean.TRUE);
            IDLE = RawAnimation.begin().thenLoop("idle_stand_up");
            WALK = RawAnimation.begin().thenLoop("two_legs_walk");
        }
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return HostileEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, MythicMobsConfigs.wendigoHealth).add(EntityAttributes.GENERIC_ARMOR, MythicMobsConfigs.wendigoArmor).add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 10).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 5).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, MythicMobsConfigs.wendigoAttackDamage).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.5f).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3);
    }

    public boolean getRoaring() {
        return this.dataTracker.get(ROARING);
    }

    public void setRoaring(boolean roaring) {
        this.dataTracker.set(ROARING, roaring);
    }

    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);
    }

//    @Override
//    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
//        controllerRegistrar.add(new AnimationController<>(this, "controller", 3, state -> {
//            if (getRoaring()) {
////                animationEvent.getController().setAnimation(ROAR);
////                MythicMobs.LOGGER.info("ROARING CURRENTLY");
////                return PlayState.CONTINUE;
//            } else
//                if (state.isMoving() && !handSwinging) {
//                    if (isAttacking() && !handSwinging) {
//                        state.getController().setAnimation(DefaultAnimations.RUN);
//                        return PlayState.CONTINUE;
//                    }
//                    state.getController().setAnimation(WALK);
//                    return PlayState.CONTINUE;
//                } else if (isAttacking()) {
//                    state.getController().setAnimation(DefaultAnimations.ATTACK);
//                    ticksUntilAttackFinish++;
//                    if (ticksUntilAttackFinish > 17 * 6) {
//                        handSwinging = false;
//                        ticksUntilAttackFinish = 0;
//                    }
//                    return PlayState.CONTINUE;
//                }
//            state.getController().setAnimation(IDLE);
//                return PlayState.CONTINUE;
//            }));
//    }

    @Override
    public List<? extends ExtendedSensor<? extends WendigoEntity>> getSensors() {
        return null;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, event ->
        {
            return event.setAndContinue(
                    // If moving, play the walking animation
                    event.isMoving() ? DefaultAnimations.WALK : DefaultAnimations.IDLE);
        }).triggerableAnim("attack", DefaultAnimations.ATTACK));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void tick() {
        super.tick();
        age++;
        if (!getWorld().isClient) {
            if (age == 1) {
                setAnim();
            }

            if (!getRoaring()) {
                roarTimer++;
                if (roarTimer > 6000) {
                    setRoaring(true);
                    roarTimer = 0;
                }
            }

            if (this.getVariant().equals(WendigoVariant.WENDIGO_STANDING) && this.isAttacking()) {
                this.setVariant(WendigoVariant.WENDIGO);
            }

            if (this.dataTracker.get(WAS_STANDING).equals(Boolean.TRUE) && !this.isAttacking() && getVariant().equals(WendigoVariant.WENDIGO)) {
                this.setVariant(WendigoVariant.WENDIGO_STANDING);
            }
        }
    }

    protected void produceParticles(ParticleEffect parameters) {
        for (int i = 0; i < 5; ++i) {
            double d = this.random.nextGaussian() * 0.02;
            double e = this.random.nextGaussian() * 0.02;
            double f = this.random.nextGaussian() * 0.02;
            this.getWorld().addParticle(parameters, this.getParticleX(1.0), this.getRandomBodyY() + 1.0, this.getParticleZ(1.0), d, e, f);
        }
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        produceParticles(ParticleTypes.POOF);
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
    protected SoundEvent getAmbientSound() {
        playSound(SoundEvents.ENTITY_HUSK_AMBIENT, 1.0f, 3.0f);
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        playSound(SoundEvents.ENTITY_HUSK_HURT, 1.0f, 3.0f);
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        playSound(SoundEvents.ENTITY_HUSK_DEATH, 1.0f, 3.0f);
        return null;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        playSound(SoundEvents.ENTITY_HUSK_STEP, 0.5f, 1.0f);
    }
}