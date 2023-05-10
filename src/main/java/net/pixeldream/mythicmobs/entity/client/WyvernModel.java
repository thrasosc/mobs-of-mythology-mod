package net.pixeldream.mythicmobs.entity.client;

import net.minecraft.util.Identifier;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.WyvernEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class WyvernModel extends AnimatedGeoModel<WyvernEntity> {

    @Override
    public Identifier getModelResource(WyvernEntity object) {
        return new Identifier(MythicMobs.MOD_ID, "geo/entity/wyvern.geo.json");
    }

    @Override
    public Identifier getTextureResource(WyvernEntity object) {
        return new Identifier(MythicMobs.MOD_ID, "textures/entity/wyvern.png");
    }

    @Override
    public Identifier getAnimationResource(WyvernEntity animatable) {
        return new Identifier(MythicMobs.MOD_ID, "animations/entity/wyvern.animation.json");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setLivingAnimations(WyvernEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}
