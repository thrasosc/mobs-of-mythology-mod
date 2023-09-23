package net.pixeldream.mythicmobs.entity.constant;

import mod.azure.azurelib.core.animation.RawAnimation;

public final class DefaultAnimations {
    public static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    public static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
    public static final RawAnimation RUN = RawAnimation.begin().thenLoop("run");
    public static final RawAnimation ATTACK = RawAnimation.begin().thenLoop("attack");
    public static final RawAnimation SIT = RawAnimation.begin().thenLoop("sit");
    public static final RawAnimation IDLE_RIDING = RawAnimation.begin().thenLoop("idle_riding");
    public static final RawAnimation WALK_RIDING = RawAnimation.begin().thenLoop("walk_riding");
    public static final RawAnimation RUN_RIDING = RawAnimation.begin().thenLoop("run_riding");
}
