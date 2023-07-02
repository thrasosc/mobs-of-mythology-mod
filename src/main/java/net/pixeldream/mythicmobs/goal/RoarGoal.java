package net.pixeldream.mythicmobs.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.WendigoEntity;

public class RoarGoal extends Goal {
    private final WendigoEntity wendigoEntity;
    private Path path;
    private final double speed = 0;
    private LivingEntity livingEntity;
    private int pauseTime = 0;

    public RoarGoal(WendigoEntity wendigoEntity) {
        this.wendigoEntity = wendigoEntity;
    }

    @Override
    public boolean canStart() {
        if (this.pauseTime <= 0 && wendigoEntity.isAttacking() && wendigoEntity.getRoaring() && !wendigoEntity.handSwinging) {
            livingEntity = this.wendigoEntity.getTarget();
            this.path = this.wendigoEntity.getNavigation().findPathTo(livingEntity, 0);
            return true;
        }
        return false;
    }

    @Override
    public void start() {
        this.wendigoEntity.getNavigation().startMovingAlong(this.path, this.speed);
        wendigoEntity.setRoaring(true);
    }

    @Override
    public void tick() {
        MythicMobs.LOGGER.info(String.valueOf(pauseTime));
        pauseTime--;
    }

    @Override
    public boolean shouldContinue() {
        return this.pauseTime > 0;
    }

    @Override
    public void stop() {
        wendigoEntity.setRoaring(false);
        this.pauseTime = 0;
    }
}
