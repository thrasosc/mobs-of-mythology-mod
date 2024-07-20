package net.pixeldreamstudios.mobs_of_mythology.entity.client.model;

import mod.azure.azurelib.common.api.client.model.GeoModel;
import net.minecraft.resources.ResourceLocation;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.mobs.ChupacabraEntity;

public class ChupacabraModel extends GeoModel<ChupacabraEntity> {

    @Override
    public ResourceLocation getModelResource(ChupacabraEntity object) {
        return ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "geo/entity/chupacabra.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ChupacabraEntity object) {
        return ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "textures/entity/chupacabra.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ChupacabraEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "animations/entity/chupacabra.animation.json");
    }
}