package net.pixeldreamstudios.mobs_of_mythology.entity.client.renderer;

import mod.azure.azurelib.render.entity.AzEntityRenderer;
import mod.azure.azurelib.render.entity.AzEntityRendererConfig;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.client.animator.AutomatonAnimator;
import net.pixeldreamstudios.mobs_of_mythology.entity.mobs.AutomatonEntity;

public class AutomatonRenderer extends AzEntityRenderer<AutomatonEntity> {

    private static final ResourceLocation MODEL = MobsOfMythology.modResource(
            "geo/entity/automaton.geo.json"
    );

    private static final ResourceLocation TEXTURE = MobsOfMythology.modResource(
            "textures/entity/automaton.png"
    );

    public AutomatonRenderer(EntityRendererProvider.Context context) {
        super( AzEntityRendererConfig.<AutomatonEntity>builder(MODEL, TEXTURE)
                .setAnimatorProvider(AutomatonAnimator::new)
                .setShadowRadius(0.85F)
                .build(),
                context
        );
    }

    @Override
    public ResourceLocation getTextureLocation(AutomatonEntity animatable) {
        return new ResourceLocation(MobsOfMythology.MOD_ID, "textures/entity/automaton.png");
    }
}