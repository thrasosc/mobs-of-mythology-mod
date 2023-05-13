package net.pixeldream.mythicmobs.goal;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.Vec3d;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.WendigoEntity;

import java.util.EnumSet;

public class RoarGoal extends Goal {
    private final WendigoEntity wendigoEntity;
    private int pauseTime = 0;

    public RoarGoal(WendigoEntity wendigoEntity) {
        this.wendigoEntity = wendigoEntity;
//        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        // Checks if the entity should currently be performing actions
        return this.pauseTime <= 0 && wendigoEntity.isAttacking() && wendigoEntity.getRoaring() && !wendigoEntity.handSwinging;
    }

    @Override
    public void start() {
        MythicMobs.LOGGER.info("ROAR GOAL");
        // Performs the entity's action
        wendigoEntity.setRoaring(true);
        // Sets the pauseTime variable to pause the AI
        this.pauseTime = 17;
    }

    @Override
    public void tick() {
        MythicMobs.LOGGER.info(String.valueOf(pauseTime));
        pauseTime--;
//        Vec3d vec3d2 = wendigoEntity.getVelocity().add(wendigoEntity.getVelocity().multiply(-1));
        wendigoEntity.setVelocity(wendigoEntity.getVelocity().multiply(0));
    }

    @Override
    public boolean shouldContinue() {
        // Returns true while the goal is still active
        return this.pauseTime > 0;
    }

    @Override
    public void stop() {
        // Resets the pauseTime variable to resume the AI
        wendigoEntity.setRoaring(false);
        this.pauseTime = 0;
    }
}
