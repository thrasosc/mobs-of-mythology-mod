package net.pixeldreamstudios.mobs_of_mythology.entity.client.renderer;

import mod.azure.azurelib.common.render.entity.AzEntityRenderer;
import mod.azure.azurelib.common.render.entity.AzEntityRendererConfig;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.client.animator.BasiliskAnimator;
import net.pixeldreamstudios.mobs_of_mythology.entity.mobs.BasiliskEntity;

public class BasiliskRenderer extends AzEntityRenderer<BasiliskEntity> {

    private static final ResourceLocation MODEL =
            MobsOfMythology.modResource("geo/entity/basilisk.geo.json");

    private static final ResourceLocation TEXTURE =
            MobsOfMythology.modResource("textures/entity/basilisk.png");

    public BasiliskRenderer(EntityRendererProvider.Context context) {
        super(
                AzEntityRendererConfig.<BasiliskEntity>builder(
                                entity -> MODEL,
                                entity -> TEXTURE
                        )
                        .setAnimatorProvider(BasiliskAnimator::new)
                        .setShadowRadius(0.75F)
                        .build(),
                context
        );
    }

    @Override
    public ResourceLocation getTextureLocation(BasiliskEntity animatable) {
        return TEXTURE;
    }
}
