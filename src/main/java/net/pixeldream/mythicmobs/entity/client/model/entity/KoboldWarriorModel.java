package net.pixeldream.mythicmobs.entity.client.model.entity;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.KoboldWarriorEntity;
import net.pixeldream.mythicmobs.entity.client.renderer.entity.KoboldWarriorRenderer;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class KoboldWarriorModel extends GeoModel<KoboldWarriorEntity> {
    @Override
    public Identifier getModelResource(KoboldWarriorEntity object) {
        return new Identifier(MythicMobs.MOD_ID, "geo/entity/kobold_warrior.geo.json");
    }

    @Override
    public Identifier getTextureResource(KoboldWarriorEntity object) {
        return KoboldWarriorRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getAnimationResource(KoboldWarriorEntity animatable) {
        return new Identifier(MythicMobs.MOD_ID, "animations/entity/kobold_warrior.animation.json");
    }

    @Override
    public void setCustomAnimations(KoboldWarriorEntity animatable, long instanceId, AnimationState<KoboldWarriorEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}