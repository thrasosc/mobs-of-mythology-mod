package net.pixeldreamstudios.mobs_of_mythology.entity.client.model;

import mod.azure.azurelib.model.GeoModel;
import net.minecraft.resources.ResourceLocation;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.client.renderer.SporelingRenderer;
import net.pixeldreamstudios.mobs_of_mythology.entity.mobs.SporelingEntity;

public class SporelingModel extends GeoModel<SporelingEntity> {

    @Override
    public ResourceLocation getModelResource(SporelingEntity object) {
        return new ResourceLocation(MobsOfMythology.MOD_ID, "geo/entity/sporeling.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SporelingEntity object) {
        return SporelingRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public ResourceLocation getAnimationResource(SporelingEntity animatable) {
        return new ResourceLocation(MobsOfMythology.MOD_ID, "animations/entity/sporeling.animation.json");
    }
}