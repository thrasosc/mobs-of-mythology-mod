package net.pixeldream.mythicmobs.entity.client.model.entity;

import mod.azure.azurelib.model.GeoModel;
import net.minecraft.util.Identifier;
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
}
