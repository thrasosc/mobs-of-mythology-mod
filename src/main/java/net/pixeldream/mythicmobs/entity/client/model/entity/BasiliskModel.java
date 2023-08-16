package net.pixeldream.mythicmobs.entity.client.model.entity;

import net.minecraft.util.Identifier;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.BasiliskEntity;
import software.bernie.geckolib.model.GeoModel;

public class BasiliskModel extends GeoModel<BasiliskEntity> {

    @Override
    public Identifier getModelResource(BasiliskEntity object) {
        return new Identifier(MythicMobs.MOD_ID, "geo/entity/basilisk.geo.json");
    }

    @Override
    public Identifier getTextureResource(BasiliskEntity object) {
        return new Identifier(MythicMobs.MOD_ID, "textures/entity/basilisk.png");
    }

    @Override
    public Identifier getAnimationResource(BasiliskEntity animatable) {
        return new Identifier(MythicMobs.MOD_ID, "animations/entity/basilisk.animation.json");
    }
}