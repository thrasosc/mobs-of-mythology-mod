package net.pixeldream.mythicmobs.entity.client.model.entity;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.azurelib.model.data.EntityModelData;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.AutomatonEntity;

public class AutomatonModel extends GeoModel<AutomatonEntity> {

    @Override
    public Identifier getModelResource(AutomatonEntity object) {
        return new Identifier(MythicMobs.MOD_ID, "geo/entity/automaton.geo.json");
    }

    @Override
    public Identifier getTextureResource(AutomatonEntity object) {
        return new Identifier(MythicMobs.MOD_ID, "textures/entity/automaton.png");
    }

    @Override
    public Identifier getAnimationResource(AutomatonEntity animatable) {
        return new Identifier(MythicMobs.MOD_ID, "animations/entity/automaton.animation.json");
    }

    @Override
    public void setCustomAnimations(AutomatonEntity animatable, long instanceId, AnimationState<AutomatonEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
