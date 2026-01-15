package net.pixeldreamstudios.mobs_of_mythology.entity.client.animator;

import mod.azure.azurelib.animation.AzAnimatorConfig;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.impl.AzEntityAnimator;
import net.minecraft.resources.ResourceLocation;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.mobs.AutomatonEntity;
import org.jetbrains.annotations.NotNull;

public class AutomatonAnimator extends AzEntityAnimator<AutomatonEntity> {

    private static final ResourceLocation ANIMATIONS = MobsOfMythology.modResource(
            "animations/entity/automaton.animation.json"
    );
    public AutomatonAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<AutomatonEntity> animationControllerContainer) {
        animationControllerContainer.add(
                AzAnimationController.builder(this, "base_controller")
                        .build()
        );
        animationControllerContainer.add(
                AzAnimationController.builder(this, "attack_controller")
                        .setTransitionLength(5)
                        .build()
        );
    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(AutomatonEntity animatable) {
        return ANIMATIONS;
    }
}
