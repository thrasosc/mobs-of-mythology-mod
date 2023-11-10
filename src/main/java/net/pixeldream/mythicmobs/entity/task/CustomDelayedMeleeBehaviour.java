package net.pixeldream.mythicmobs.entity.task;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.PathfinderMob;
import net.pixeldream.mythicmobs.entity.mobs.AutomatonEntity;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

import java.util.function.Consumer;

public abstract class CustomDelayedMeleeBehaviour<E extends PathfinderMob> extends ExtendedBehaviour<E> {
    protected final int delayTime;
    protected long delayFinishedAt = 0;
    protected Consumer<E> delayedCallback = entity -> {
    };

    public CustomDelayedMeleeBehaviour(int delayTicks) {
        this.delayTime = delayTicks;
        runFor(entity -> Math.max(delayTicks, 60));
    }

    /**
     * A callback for when the delayed action is called.
     *
     * @param callback The callback
     * @return this
     */
    public final CustomDelayedMeleeBehaviour<E> whenActivating(Consumer<E> callback) {
        this.delayedCallback = callback;
        return this;
    }

    @Override
    protected final void start(ServerLevel level, E entity, long gameTime) {
        if (this.delayTime > 0) {
            this.delayFinishedAt = gameTime + this.delayTime;
            super.start(level, entity, gameTime);
        } else {
            super.start(level, entity, gameTime);
            doDelayedAction(entity);
        }
        entity.triggerAnim("attackController", "melee");
        if (entity instanceof AutomatonEntity executionerEntity) {
            executionerEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 100, false, false));
        }
    }

    @Override
    protected final void stop(ServerLevel level, E entity, long gameTime) {
        super.stop(level, entity, gameTime);
        this.delayFinishedAt = 0;
        entity.setAttackingState(0);
    }

    @Override
    protected boolean shouldKeepRunning(E entity) {
        return this.delayFinishedAt >= entity.level().getGameTime();
    }

    @Override
    protected final void tick(ServerLevel level, E entity, long gameTime) {
        super.tick(level, entity, gameTime);
        if (this.delayFinishedAt <= gameTime) {
            doDelayedAction(entity);
            this.delayedCallback.accept(entity);
        }
    }

    /**
     * The action to take once the delay period has elapsed.
     *
     * @param entity The owner of the brain
     */
    protected void doDelayedAction(E entity) {}
}
