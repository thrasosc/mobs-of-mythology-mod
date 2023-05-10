package net.pixeldream.mythicmobs.goal;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.pixeldream.mythicmobs.entity.WyvernEntity;

import java.util.EnumSet;

public class WyvernFlyRandomGoal extends Goal {
    private final WyvernEntity wyvernEntity;
    private int flyTimer;
    private int waitTimer;
    private double d;
    private double e;
    private double f;

    public WyvernFlyRandomGoal(WyvernEntity wyvernEntity) {
        this.wyvernEntity = wyvernEntity;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        waitTimer++;
        if (waitTimer > 100 && flyTimer <= 0 && wyvernEntity.sidewaysSpeed == 0.0F && wyvernEntity.forwardSpeed == 0.0F && wyvernEntity.isFlying && !this.wyvernEntity.hasPassengers()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void tick() {
        super.tick();
        flyTimer--;
        Vec3d vec3d2 = new Vec3d(d, e, f);
        Vec3d vec3d = this.wyvernEntity.getVelocity().add(vec3d2);
        this.wyvernEntity.setVelocity(vec3d);
        this.wyvernEntity.headYaw = MathHelper.wrapDegrees((float) (MathHelper.atan2(f, d) * 57.2957763671875D) - 90.0F);
    }

    @Override
    public boolean shouldContinue() {
        if (flyTimer > 0) {
            return true;
        } else
            return false;
    }

    @Override
    public void stop() {
        flyTimer = 0;
        waitTimer = 0;
    }

    @Override
    public void start() {
        Random random = this.wyvernEntity.getRandom();
        flyTimer = 100 + random.nextInt(60);
        Math.sin(Math.PI * random.nextDouble());
        d = Math.sin(Math.PI * random.nextDouble() * 2.0D) * 0.007D;
        e = Math.sin(Math.PI * random.nextDouble() * 2.0D) * 0.001D;
        f = Math.sin(Math.PI * random.nextDouble() * 2.0D) * 0.007D;
    }
}
