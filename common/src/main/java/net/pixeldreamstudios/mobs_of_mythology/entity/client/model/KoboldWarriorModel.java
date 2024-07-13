package net.pixeldreamstudios.mobs_of_mythology.entity.client.model;

import mod.azure.azurelib.common.api.client.model.GeoModel;
import mod.azure.azurelib.common.internal.client.model.data.EntityModelData;
import mod.azure.azurelib.common.internal.common.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.monster.KoboldWarriorEntity;
import net.pixeldreamstudios.mobs_of_mythology.entity.client.renderer.KoboldWarriorRenderer;

public class KoboldWarriorModel extends GeoModel<KoboldWarriorEntity> {
    @Override
    public ResourceLocation getModelResource(KoboldWarriorEntity object) {
        return ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "geo/entity/kobold_warrior.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(KoboldWarriorEntity object) {
        return KoboldWarriorRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public ResourceLocation getAnimationResource(KoboldWarriorEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "animations/entity/kobold_warrior.animation.json");
    }

    @Override
    public void setCustomAnimations(KoboldWarriorEntity animatable, long instanceId, AnimationState<KoboldWarriorEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}