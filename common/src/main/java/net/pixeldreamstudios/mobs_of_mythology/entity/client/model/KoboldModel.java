package net.pixeldreamstudios.mobs_of_mythology.entity.client.model;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.azurelib.model.data.EntityModelData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.client.renderer.KoboldRenderer;
import net.pixeldreamstudios.mobs_of_mythology.entity.mobs.KoboldEntity;

public class KoboldModel extends GeoModel<KoboldEntity> {
    @Override
    public ResourceLocation getModelResource(KoboldEntity object) {
        return new ResourceLocation(MobsOfMythology.MOD_ID, "geo/entity/kobold.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(KoboldEntity object) {
        return KoboldRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public ResourceLocation getAnimationResource(KoboldEntity animatable) {
        return new ResourceLocation(MobsOfMythology.MOD_ID, "animations/entity/kobold.animation.json");
    }

    @Override
    public void setCustomAnimations(KoboldEntity animatable, long instanceId, AnimationState<KoboldEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
