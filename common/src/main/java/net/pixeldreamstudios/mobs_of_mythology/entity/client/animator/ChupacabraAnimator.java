package net.pixeldreamstudios.mobs_of_mythology.entity.client.animator;

import mod.azure.azurelib.common.animation.AzAnimatorConfig;
import mod.azure.azurelib.common.animation.controller.AzAnimationController;
import mod.azure.azurelib.common.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.common.animation.impl.AzEntityAnimator;
import net.minecraft.resources.ResourceLocation;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.mobs.ChupacabraEntity;
import org.jetbrains.annotations.NotNull;

public class ChupacabraAnimator extends AzEntityAnimator<ChupacabraEntity> {

    private static final ResourceLocation ANIMATIONS =
            MobsOfMythology.modResource("animations/entity/chupacabra.animation.json");

    public ChupacabraAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<ChupacabraEntity> controllers) {
        controllers.add(
                AzAnimationController.builder(this, "base_controller")
                        .build()
        );

        controllers.add(
                AzAnimationController.builder(this, "attack_controller")
                        .setTransitionLength(5)
                        .build()
        );
    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(ChupacabraEntity animatable) {
        return ANIMATIONS;
    }
}
