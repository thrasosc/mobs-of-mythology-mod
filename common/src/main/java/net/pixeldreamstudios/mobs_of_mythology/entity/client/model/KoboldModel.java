package net.pixeldreamstudios.mobs_of_mythology.entity.client.model;

import mod.azure.azurelib.common.api.client.model.GeoModel;
import mod.azure.azurelib.common.internal.client.model.data.EntityModelData;
import mod.azure.azurelib.common.internal.common.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.monster.KoboldEntity;
import net.pixeldreamstudios.mobs_of_mythology.entity.client.renderer.KoboldRenderer;

public class KoboldModel extends GeoModel<KoboldEntity> {
    @Override
    public ResourceLocation getModelResource(KoboldEntity object) {
        return ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "geo/entity/kobold.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(KoboldEntity object) {
        return KoboldRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public ResourceLocation getAnimationResource(KoboldEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "animations/entity/kobold.animation.json");
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
