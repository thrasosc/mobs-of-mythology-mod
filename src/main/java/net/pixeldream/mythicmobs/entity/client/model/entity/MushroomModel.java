package net.pixeldream.mythicmobs.entity.client.model.entity;

import mod.azure.azurelib.model.GeoModel;
import net.minecraft.util.Identifier;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.MushroomEntity;
import net.pixeldream.mythicmobs.entity.client.renderer.entity.MushroomRenderer;

public class MushroomModel extends GeoModel<MushroomEntity> {

    @Override
    public Identifier getModelResource(MushroomEntity object) {
        return new Identifier(MythicMobs.MOD_ID, "geo/entity/mushroom_thing.geo.json");
    }

    @Override
    public Identifier getTextureResource(MushroomEntity object) {
        return MushroomRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getAnimationResource(MushroomEntity animatable) {
        return new Identifier(MythicMobs.MOD_ID, "animations/entity/mushroom_thing.animation.json");
    }
}