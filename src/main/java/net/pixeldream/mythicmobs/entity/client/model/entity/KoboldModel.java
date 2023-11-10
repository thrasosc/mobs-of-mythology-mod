package net.pixeldream.mythicmobs.entity.client.model.entity;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.azurelib.model.data.EntityModelData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.client.renderer.entity.KoboldRenderer;
import net.pixeldream.mythicmobs.entity.mobs.KoboldEntity;

public class KoboldModel extends GeoModel<KoboldEntity> {
    @Override
    public ResourceLocation getModelResource(KoboldEntity object) {
        return new ResourceLocation(MythicMobs.MOD_ID, "geo/entity/kobold.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(KoboldEntity object) {
        return KoboldRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public ResourceLocation getAnimationResource(KoboldEntity animatable) {
        return new ResourceLocation(MythicMobs.MOD_ID, "animations/entity/kobold.animation.json");
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
