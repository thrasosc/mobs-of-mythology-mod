package net.pixeldream.mythicmobs.entity.client.renderer.entity;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.renderer.layer.AutoGlowingGeoLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.ChupacabraEntity;
import net.pixeldream.mythicmobs.entity.client.model.entity.ChupacabraModel;

public class ChupacabraRenderer extends GeoEntityRenderer<ChupacabraEntity> {
    public ChupacabraRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ChupacabraModel());
        this.shadowRadius = 0.65f;
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public Identifier getTextureLocation(ChupacabraEntity animatable) {
        return new Identifier(MythicMobs.MOD_ID, "textures/entity/chupacabra.png");
    }
}