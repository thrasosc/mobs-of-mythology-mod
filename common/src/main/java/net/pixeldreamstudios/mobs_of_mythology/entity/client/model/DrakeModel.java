package net.pixeldreamstudios.mobs_of_mythology.entity.client.model;

import mod.azure.azurelib.common.api.client.model.GeoModel;
import net.minecraft.resources.ResourceLocation;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.mobs.DrakeEntity;
import net.pixeldreamstudios.mobs_of_mythology.entity.client.renderer.DrakeRenderer;

public class DrakeModel extends GeoModel<DrakeEntity> {

    @Override
    public ResourceLocation getModelResource(DrakeEntity object) {
        return ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "geo/entity/drake.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DrakeEntity object) {
        return DrakeRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public ResourceLocation getAnimationResource(DrakeEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "animations/entity/drake.animation.json");
    }
}
