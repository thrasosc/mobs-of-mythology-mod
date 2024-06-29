package net.pixeldreamstudios.mobs_of_mythology.entity.abstraction;

import mod.azure.azurelib.common.api.common.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animatable.instance.SingletonAnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.pixeldreamstudios.mobs_of_mythology.entity.constant.DefaultAnimations;

public abstract class MythicEntity extends PathfinderMob implements GeoEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    protected MythicEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "livingController", 3, state -> {
            if (state.isMoving() && !swinging) {
                if (isAggressive() && !swinging) {
                    state.getController().setAnimation(DefaultAnimations.RUN);
                    return PlayState.CONTINUE;
                }
                state.getController().setAnimation(DefaultAnimations.WALK);
                return PlayState.CONTINUE;
            } else if (swinging) {
                state.getController().setAnimation(DefaultAnimations.ATTACK);
                return PlayState.CONTINUE;
            }
            state.getController().setAnimation(DefaultAnimations.IDLE);
            return PlayState.CONTINUE;
        }));
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
}