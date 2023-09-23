package net.pixeldream.mythicmobs.entity.client.model.entity;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.azurelib.model.data.EntityModelData;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.KoboldEntity;
import net.pixeldream.mythicmobs.entity.client.renderer.entity.KoboldRenderer;

public class KoboldModel extends GeoModel<KoboldEntity> {
    @Override
    public Identifier getModelResource(KoboldEntity object) {
        return new Identifier(MythicMobs.MOD_ID, "geo/entity/kobold.geo.json");
    }

    @Override
    public Identifier getTextureResource(KoboldEntity object) {
        return KoboldRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getAnimationResource(KoboldEntity animatable) {
        return new Identifier(MythicMobs.MOD_ID, "animations/entity/kobold.animation.json");
    }

    @Override
    public void setCustomAnimations(KoboldEntity animatable, long instanceId, AnimationState<KoboldEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
