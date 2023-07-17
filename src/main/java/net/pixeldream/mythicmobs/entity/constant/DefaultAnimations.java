package net.pixeldream.mythicmobs.entity.constant;

import software.bernie.geckolib.core.animation.RawAnimation;

public final class DefaultAnimations {
    public static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    public static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
    public static final RawAnimation RUN = RawAnimation.begin().thenLoop("run");
    public static final RawAnimation ATTACK = RawAnimation.begin().thenLoop("attack");
    public static final RawAnimation SIT = RawAnimation.begin().thenLoop("sit");
}
