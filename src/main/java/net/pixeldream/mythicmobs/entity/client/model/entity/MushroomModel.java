package net.pixeldream.mythicmobs.entity.client.model.entity;

import mod.azure.azurelib.model.GeoModel;
import net.minecraft.resources.ResourceLocation;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.client.renderer.entity.MushroomRenderer;
import net.pixeldream.mythicmobs.entity.mobs.MushroomEntity;

public class MushroomModel extends GeoModel<MushroomEntity> {

    @Override
    public ResourceLocation getModelResource(MushroomEntity object) {
        return new ResourceLocation(MythicMobs.MOD_ID, "geo/entity/mushroom_thing.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MushroomEntity object) {
        return MushroomRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public ResourceLocation getAnimationResource(MushroomEntity animatable) {
        return new ResourceLocation(MythicMobs.MOD_ID, "animations/entity/mushroom_thing.animation.json");
    }
}