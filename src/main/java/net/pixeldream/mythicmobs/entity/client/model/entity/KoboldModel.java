package net.pixeldream.mythicmobs.entity.client.model.entity;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.KoboldEntity;
import net.pixeldream.mythicmobs.entity.client.renderer.entity.KoboldRenderer;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

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
