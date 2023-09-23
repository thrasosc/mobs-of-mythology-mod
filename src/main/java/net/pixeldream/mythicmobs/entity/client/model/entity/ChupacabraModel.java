package net.pixeldream.mythicmobs.entity.client.model.entity;

import mod.azure.azurelib.model.GeoModel;
import net.minecraft.util.Identifier;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.ChupacabraEntity;

public class ChupacabraModel extends GeoModel<ChupacabraEntity> {

    @Override
    public Identifier getModelResource(ChupacabraEntity object) {
        return new Identifier(MythicMobs.MOD_ID, "geo/entity/chupacabra.geo.json");
    }

    @Override
    public Identifier getTextureResource(ChupacabraEntity object) {
        return new Identifier(MythicMobs.MOD_ID, "textures/entity/chupacabra.png");
    }

    @Override
    public Identifier getAnimationResource(ChupacabraEntity animatable) {
        return new Identifier(MythicMobs.MOD_ID, "animations/entity/chupacabra.animation.json");
    }
}