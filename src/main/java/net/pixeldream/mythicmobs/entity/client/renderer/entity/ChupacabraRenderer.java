package net.pixeldream.mythicmobs.entity.client.renderer.entity;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.renderer.layer.AutoGlowingGeoLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.client.model.entity.ChupacabraModel;
import net.pixeldream.mythicmobs.entity.mobs.ChupacabraEntity;

public class ChupacabraRenderer extends GeoEntityRenderer<ChupacabraEntity> {
    public ChupacabraRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new ChupacabraModel());
        this.shadowRadius = 0.65f;
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(ChupacabraEntity animatable) {
        return new ResourceLocation(MythicMobs.MOD_ID, "textures/entity/chupacabra.png");
    }
}