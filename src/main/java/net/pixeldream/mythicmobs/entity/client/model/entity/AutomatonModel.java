package net.pixeldream.mythicmobs.entity.client.model.entity;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.azurelib.model.data.EntityModelData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.mobs.AutomatonEntity;

public class AutomatonModel extends GeoModel<AutomatonEntity> {

    @Override
    public ResourceLocation getModelResource(AutomatonEntity object) {
        return new ResourceLocation(MythicMobs.MOD_ID, "geo/entity/automaton.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AutomatonEntity object) {
        return new ResourceLocation(MythicMobs.MOD_ID, "textures/entity/automaton.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AutomatonEntity animatable) {
        return new ResourceLocation(MythicMobs.MOD_ID, "animations/entity/automaton.animation.json");
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
