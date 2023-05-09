package net.pixeldream.mythicmobs.entity.client;

import net.minecraft.util.Identifier;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.TrollEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class TrollModel extends AnimatedGeoModel<TrollEntity> {

    @Override
    public Identifier getModelResource(TrollEntity object) {
        return new Identifier(MythicMobs.MOD_ID, "geo/entity/troll.geo.json");
    }

    @Override
    public Identifier getTextureResource(TrollEntity object) {
        return new Identifier(MythicMobs.MOD_ID, "textures/entity/troll.png");
    }

    @Override
    public Identifier getAnimationResource(TrollEntity animatable) {
        return new Identifier(MythicMobs.MOD_ID, "animations/entity/troll.animation.json");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setLivingAnimations(TrollEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}
