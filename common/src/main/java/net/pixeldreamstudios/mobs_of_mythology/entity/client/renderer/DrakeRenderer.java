package net.pixeldreamstudios.mobs_of_mythology.entity.client.renderer;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.azure.azurelib.common.api.client.renderer.GeoEntityRenderer;
import mod.azure.azurelib.common.api.client.renderer.layer.AutoGlowingGeoLayer;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.creature.DrakeEntity;
import net.pixeldreamstudios.mobs_of_mythology.entity.client.model.DrakeModel;
import net.pixeldreamstudios.mobs_of_mythology.entity.variant.DrakeVariant;

import java.util.Map;

public class DrakeRenderer extends GeoEntityRenderer<DrakeEntity> {
    public static final Map<DrakeVariant, ResourceLocation> LOCATION_BY_VARIANT = Util.make(Maps.newEnumMap(DrakeVariant.class), (map) -> {
        map.put(DrakeVariant.DRAKE_1, ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "textures/entity/drake/drake_1.png"));
        map.put(DrakeVariant.DRAKE_2, ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "textures/entity/drake/drake_2.png"));
        map.put(DrakeVariant.DRAKE_3, ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "textures/entity/drake/drake_3.png"));
        map.put(DrakeVariant.DRAKE_4, ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "textures/entity/drake/drake_4.png"));
        map.put(DrakeVariant.DRAKE_5, ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "textures/entity/drake/drake_5.png"));
        map.put(DrakeVariant.DRAKE_6, ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "textures/entity/drake/drake_6.png"));
        map.put(DrakeVariant.DRAKE_7, ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "textures/entity/drake/drake_7.png"));
    });

    public DrakeRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new DrakeModel());
        this.shadowRadius = 0.75f;
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(DrakeEntity animatable) {
        return LOCATION_BY_VARIANT.get(animatable.getVariant());
    }

    @Override
    public void render(DrakeEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        if (entity.isBaby()) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            poseStack.scale(1f, 1f, 1f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);

    }
}