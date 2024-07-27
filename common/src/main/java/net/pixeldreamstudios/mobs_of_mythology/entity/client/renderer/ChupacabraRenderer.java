package net.pixeldreamstudios.mobs_of_mythology.entity.client.renderer;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.renderer.layer.AutoGlowingGeoLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.client.model.ChupacabraModel;
import net.pixeldreamstudios.mobs_of_mythology.entity.mobs.ChupacabraEntity;

public class ChupacabraRenderer extends GeoEntityRenderer<ChupacabraEntity> {
    public ChupacabraRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new ChupacabraModel());
        this.shadowRadius = 0.65f;
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(ChupacabraEntity animatable) {
        return new ResourceLocation(MobsOfMythology.MOD_ID, "textures/entity/chupacabra.png");
    }
}