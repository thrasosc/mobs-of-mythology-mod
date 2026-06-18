package net.pixeldreamstudios.mobs_of_mythology.entity.client.renderer;


import mod.azure.azurelib.common.render.entity.AzEntityRenderer;
import mod.azure.azurelib.common.render.entity.AzEntityRendererConfig;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.client.animator.ChupacabraAnimator;
import net.pixeldreamstudios.mobs_of_mythology.entity.mobs.ChupacabraEntity;

public class ChupacabraRenderer extends AzEntityRenderer<ChupacabraEntity> {

    private static final ResourceLocation MODEL = MobsOfMythology.modResource(
            "geo/entity/chupacabra.geo.json"
    );

    private static final ResourceLocation TEXTURE = MobsOfMythology.modResource(
            "textures/entity/chupacabra.png"
    );

    public ChupacabraRenderer(EntityRendererProvider.Context context) {
        super(
                AzEntityRendererConfig.<ChupacabraEntity>builder(MODEL, TEXTURE)
                        .setAnimatorProvider(ChupacabraAnimator::new)
                        .setShadowRadius(0.65F)
                        .build(),
                context
        );
    }

    @Override
    public ResourceLocation getTextureLocation(ChupacabraEntity animatable) {
        return TEXTURE;
    }
}
