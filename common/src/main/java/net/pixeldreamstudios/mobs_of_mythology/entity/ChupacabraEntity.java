package net.pixeldreamstudios.mobs_of_mythology.entity;

import mod.azure.azurelib.common.api.common.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animatable.instance.SingletonAnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;

import java.util.function.Predicate;

public class ChupacabraEntity extends Monster implements GeoEntity, Enemy {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private static final Predicate<LivingEntity> DIET;
    private long ticksUntilAttackFinish = 0;

    public ChupacabraEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.xpReward = Enemy.XP_REWARD_MEDIUM;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes().add(Attributes.MAX_HEALTH, MobsOfMythology.config.chupacabraHealth)
                .add(Attributes.ATTACK_DAMAGE, MobsOfMythology.config.chupacabraAttackDamage)
                .add(Attributes.ATTACK_SPEED, 1.25f)
                .add(Attributes.ATTACK_KNOCKBACK, 1)
                .add(Attributes.MOVEMENT_SPEED, 0.3);
    }

//    @Override
//    protected void initGoals() {
//        this.goalSelector.add(0, new SwimGoal(this));
//        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.5f, true));
//        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.75f));
//        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
//        this.goalSelector.add(4, new LookAroundGoal(this));
//        this.targetSelector.add(1, new RevengeGoal(this));
//        this.targetSelector.add(4, new ActiveTargetGoal(this, AnimalEntity.class, false, DIET));
//        this.targetSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
//    }

    static {
        DIET = (entity) -> {
            EntityType<?> entityType = entity.getType();
            return entityType == EntityType.SHEEP || entityType == EntityType.COW || entityType == EntityType.PIG || entityType == EntityType.CHICKEN || entityType == EntityType.RABBIT;
        };
    }

//    @Override
//    public void tick() {
//        super.tick();
//        if (handSwinging) {
//            this.getWorld().addParticle(ParticleRegistry.BLOOD_PARTICLE, getX(), getY(), getZ(), 0.0, 0.0, 0.0);
//            this.getWorld().addParticle(ParticleRegistry.BLOOD_PARTICLE, getX(), getY(), getZ(), 0.0, 0.0, 0.0);
//            this.getWorld().addParticle(ParticleRegistry.BLOOD_PARTICLE, getX(), getY(), getZ(), 0.0, 0.0, 0.0);
//            if (getHealth() < getMaxHealth()) {
//                this.heal(2.5f);
//                produceParticles(ParticleTypes.HAPPY_VILLAGER);
//            }
//        }
//    }

    protected void produceParticles(ParticleOptions parameters) {
        for (int i = 0; i < 5; ++i) {
            double d = this.random.nextGaussian() * 0.02;
            double e = this.random.nextGaussian() * 0.02;
            double f = this.random.nextGaussian() * 0.02;
            this.level().addParticle(parameters, this.getRandomX(1.0), this.getRandomY() + 1.0, this.getRandomZ(1.0), d, e, f);
        }
    }

//    @Override
//    public void die(DamageSource damageSource) {
//        produceParticles(ParticleTypes.POOF);
//        this.spawnAtLocation(new ItemStack(ItemRegistry.CHUPACABRA_RAW_MEAT, random.nextInt(2)));
//        super.die(damageSource);
//    }

    @Override
    protected SoundEvent getAmbientSound() {
        this.playSound(SoundEvents.WOLF_AMBIENT, 1.0f, 0.25f);
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        this.playSound(SoundEvents.WOLF_HURT, 1.0f, 0.25f);
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        this.playSound(SoundEvents.WOLF_DEATH, 1.0f, 0.25f);
        return null;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.WOLF_STEP, 0.5f, 1.0f);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}