package net.pixeldreamstudios.mobs_of_mythology.entity.client.renderer;

import mod.azure.azurelib.common.api.client.renderer.GeoEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.AutomatonEntity;
import net.pixeldreamstudios.mobs_of_mythology.entity.client.model.AutomatonModel;

public class AutomatonRenderer extends GeoEntityRenderer<AutomatonEntity> {
    public AutomatonRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new AutomatonModel());
        this.shadowRadius = 0.85f;
    }

    @Override
    public ResourceLocation getTextureLocation(AutomatonEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "textures/entity/automaton.png");
    }
}