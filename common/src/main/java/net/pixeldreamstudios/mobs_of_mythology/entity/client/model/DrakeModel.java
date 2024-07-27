package net.pixeldreamstudios.mobs_of_mythology.entity.client.model;

import mod.azure.azurelib.model.GeoModel;
import net.minecraft.resources.ResourceLocation;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.client.renderer.DrakeRenderer;
import net.pixeldreamstudios.mobs_of_mythology.entity.mobs.DrakeEntity;

public class DrakeModel extends GeoModel<DrakeEntity> {

    @Override
    public ResourceLocation getModelResource(DrakeEntity object) {
        return new ResourceLocation(MobsOfMythology.MOD_ID, "geo/entity/drake.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DrakeEntity object) {
        return DrakeRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public ResourceLocation getAnimationResource(DrakeEntity animatable) {
        return new ResourceLocation(MobsOfMythology.MOD_ID, "animations/entity/drake.animation.json");
    }
}
