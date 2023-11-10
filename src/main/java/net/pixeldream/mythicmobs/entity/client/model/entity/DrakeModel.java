package net.pixeldream.mythicmobs.entity.client.model.entity;

import mod.azure.azurelib.model.GeoModel;
import net.minecraft.resources.ResourceLocation;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.client.renderer.entity.DrakeRenderer;
import net.pixeldream.mythicmobs.entity.mobs.DrakeEntity;

public class DrakeModel extends GeoModel<DrakeEntity> {

    @Override
    public ResourceLocation getModelResource(DrakeEntity object) {
        return new ResourceLocation(MythicMobs.MOD_ID, "geo/entity/drake.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DrakeEntity object) {
        return DrakeRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public ResourceLocation getAnimationResource(DrakeEntity animatable) {
        return new ResourceLocation(MythicMobs.MOD_ID, "animations/entity/drake.animation.json");
    }
}
