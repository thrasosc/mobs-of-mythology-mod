package net.pixeldreamstudios.mobs_of_mythology.entity.constant;


import mod.azure.azurelib.common.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.common.animation.play_behavior.AzPlayBehaviors;
import net.minecraft.world.entity.Entity;

public class DefaultMythAnimations {

    public static final String BASE_CONTROLLER = "base_controller";
    public static final String ATTACK_CONTROLLER = "attack_controller";

    private static final AzCommand IDLE_COMMAND = AzCommand.create(
            BASE_CONTROLLER, "idle", AzPlayBehaviors.LOOP
    );

    private static final AzCommand WALK_COMMAND = AzCommand.create(
            BASE_CONTROLLER, "walk", AzPlayBehaviors.LOOP
    );

    private static final AzCommand RUN_COMMAND = AzCommand.create(
            BASE_CONTROLLER, "run", AzPlayBehaviors.LOOP
    );

    private static final AzCommand SIT_COMMAND = AzCommand.create(
            BASE_CONTROLLER, "sit", AzPlayBehaviors.LOOP
    );

    private static final AzCommand ATTACK_COMMAND = AzCommand.create(
            ATTACK_CONTROLLER, "attack", AzPlayBehaviors.PLAY_ONCE
    );

    private static final AzCommand ATTACK2_COMMAND = AzCommand.create(
            ATTACK_CONTROLLER, "attack2", AzPlayBehaviors.PLAY_ONCE
    );
    private static final AzCommand BOUNCE_COMMAND = AzCommand.create(
            "bounce_controller", "bounce", AzPlayBehaviors.PLAY_ONCE
    );

    private final Entity animatedEntity;

    public DefaultMythAnimations(Entity animatable) {
        this.animatedEntity = animatable;
    }

    public void idle()  { IDLE_COMMAND.sendForEntity(animatedEntity); }
    public void walk()  { WALK_COMMAND.sendForEntity(animatedEntity); }
    public void run()   { RUN_COMMAND.sendForEntity(animatedEntity); }
    public void sit()   { SIT_COMMAND.sendForEntity(animatedEntity); }
    public void bounce() { BOUNCE_COMMAND.sendForEntity(animatedEntity); }

    public void attack()  { ATTACK_COMMAND.sendForEntity(animatedEntity); }
    public void attack2() { ATTACK2_COMMAND.sendForEntity(animatedEntity); }
}
