package net.pixeldreamstudios.mobs_of_mythology.entity.client.renderer;

import mod.azure.azurelib.common.api.client.renderer.GeoEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.client.model.PegasusModel;
import net.pixeldreamstudios.mobs_of_mythology.entity.mobs.PegasusEntity;

public class PegasusRenderer extends GeoEntityRenderer<PegasusEntity> {
    public PegasusRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new PegasusModel());
        this.shadowRadius = 0.75f;
    }

    @Override
    public ResourceLocation getTextureLocation(PegasusEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "textures/entity/pegasus.png");
    }
}