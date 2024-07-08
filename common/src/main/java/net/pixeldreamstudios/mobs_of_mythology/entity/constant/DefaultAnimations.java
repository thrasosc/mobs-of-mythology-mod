package net.pixeldreamstudios.mobs_of_mythology.entity.constant;

import mod.azure.azurelib.core.animation.Animation;
import mod.azure.azurelib.core.animation.RawAnimation;

public final class DefaultAnimations {
    public static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    public static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
    public static final RawAnimation RUN = RawAnimation.begin().thenLoop("run");
    public static final RawAnimation DEATH = RawAnimation.begin().thenPlayAndHold("death");
    public static final RawAnimation ATTACK = RawAnimation.begin().then("attack", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation ATTACK2 = RawAnimation.begin().then("attack2", Animation.LoopType.PLAY_ONCE);
    public static final RawAnimation SIT = RawAnimation.begin().thenLoop("sit");
    public static final RawAnimation IDLE_RIDING = RawAnimation.begin().thenLoop("idle_riding");
    public static final RawAnimation WALK_RIDING = RawAnimation.begin().thenLoop("walk_riding");
    public static final RawAnimation RUN_RIDING = RawAnimation.begin().thenLoop("run_riding");

}
