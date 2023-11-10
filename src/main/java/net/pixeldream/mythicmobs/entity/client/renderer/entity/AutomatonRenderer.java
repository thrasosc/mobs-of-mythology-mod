package net.pixeldream.mythicmobs.entity.client.renderer.entity;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.client.model.entity.AutomatonModel;
import net.pixeldream.mythicmobs.entity.mobs.AutomatonEntity;

public class AutomatonRenderer extends GeoEntityRenderer<AutomatonEntity> {
    public AutomatonRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new AutomatonModel());
        this.shadowRadius = 0.85f;
    }

    @Override
    public ResourceLocation getTextureLocation(AutomatonEntity animatable) {
        return new ResourceLocation(MythicMobs.MOD_ID, "textures/entity/automaton.png");
    }
}