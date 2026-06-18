package net.pixeldreamstudios.mobs_of_mythology.entity.client.animator;


import mod.azure.azurelib.common.animation.AzAnimatorConfig;
import mod.azure.azurelib.common.animation.controller.AzAnimationController;
import mod.azure.azurelib.common.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.common.animation.impl.AzEntityAnimator;
import net.minecraft.resources.ResourceLocation;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.mobs.BasiliskEntity;
import org.jetbrains.annotations.NotNull;

public class BasiliskAnimator extends AzEntityAnimator<BasiliskEntity> {

    private static final ResourceLocation ANIMATIONS = MobsOfMythology.modResource(
            "animations/entity/basilisk.animation.json"
    );
    public BasiliskAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<BasiliskEntity> animationControllerContainer) {
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
    public @NotNull ResourceLocation getAnimationLocation(BasiliskEntity animatable) {
        return ANIMATIONS;
    }
}
