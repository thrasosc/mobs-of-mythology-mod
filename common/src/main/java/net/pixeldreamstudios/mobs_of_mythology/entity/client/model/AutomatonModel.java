package net.pixeldreamstudios.mobs_of_mythology.entity.client.model;

import mod.azure.azurelib.common.api.client.model.GeoModel;
import mod.azure.azurelib.common.internal.client.model.data.EntityModelData;
import mod.azure.azurelib.common.internal.common.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.animal.AutomatonEntity;

public class AutomatonModel extends GeoModel<AutomatonEntity> {

    @Override
    public ResourceLocation getModelResource(AutomatonEntity object) {
        return ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "geo/entity/automaton.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AutomatonEntity object) {
        return ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "textures/entity/automaton.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AutomatonEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "animations/entity/automaton.animation.json");
    }

    @Override
    public void setCustomAnimations(AutomatonEntity animatable, long instanceId, AnimationState<AutomatonEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
