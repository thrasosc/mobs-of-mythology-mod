package net.pixeldream.mythicmobs.entity.client.renderer.entity;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.AutomatonEntity;
import net.pixeldream.mythicmobs.entity.client.model.entity.AutomatonModel;

public class AutomatonRenderer extends GeoEntityRenderer<AutomatonEntity> {
    public AutomatonRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new AutomatonModel());
        this.shadowRadius = 0.85f;
    }

    @Override
    public Identifier getTextureLocation(AutomatonEntity animatable) {
        return new Identifier(MythicMobs.MOD_ID, "textures/entity/automaton.png");
    }
}