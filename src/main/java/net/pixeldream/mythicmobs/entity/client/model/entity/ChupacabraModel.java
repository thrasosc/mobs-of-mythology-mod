package net.pixeldream.mythicmobs.entity.client.model.entity;

import mod.azure.azurelib.model.GeoModel;
import net.minecraft.resources.ResourceLocation;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.mobs.ChupacabraEntity;

public class ChupacabraModel extends GeoModel<ChupacabraEntity> {

    @Override
    public ResourceLocation getModelResource(ChupacabraEntity object) {
        return new ResourceLocation(MythicMobs.MOD_ID, "geo/entity/chupacabra.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ChupacabraEntity object) {
        return new ResourceLocation(MythicMobs.MOD_ID, "textures/entity/chupacabra.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ChupacabraEntity animatable) {
        return new ResourceLocation(MythicMobs.MOD_ID, "animations/entity/chupacabra.animation.json");
    }
}