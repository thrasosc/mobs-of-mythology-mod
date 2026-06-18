package net.pixeldreamstudios.mobs_of_mythology.entity.client.renderer;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.azure.azurelib.common.render.entity.AzEntityRenderer;
import mod.azure.azurelib.common.render.entity.AzEntityRendererConfig;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.client.animator.DrakeAnimator;
import net.pixeldreamstudios.mobs_of_mythology.entity.mobs.DrakeEntity;
import net.pixeldreamstudios.mobs_of_mythology.entity.variant.DrakeVariant;

import java.util.Map;

public class DrakeRenderer extends AzEntityRenderer<DrakeEntity> {

    private static final ResourceLocation MODEL = MobsOfMythology.modResource(
            "geo/entity/drake.geo.json"
    );

    public static final Map<DrakeVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(DrakeVariant.class), (map) -> {
                map.put(DrakeVariant.DRAKE_1, ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "textures/entity/drake/drake_1.png"));
                map.put(DrakeVariant.DRAKE_2, ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "textures/entity/drake/drake_2.png"));
                map.put(DrakeVariant.DRAKE_3, ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "textures/entity/drake/drake_3.png"));
                map.put(DrakeVariant.DRAKE_4, ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "textures/entity/drake/drake_4.png"));
                map.put(DrakeVariant.DRAKE_5, ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "textures/entity/drake/drake_5.png"));
                map.put(DrakeVariant.DRAKE_6, ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "textures/entity/drake/drake_6.png"));
                map.put(DrakeVariant.DRAKE_7, ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "textures/entity/drake/drake_7.png"));
            });

    public DrakeRenderer(EntityRendererProvider.Context context) {
        super(
                AzEntityRendererConfig.<DrakeEntity>builder(
                                MODEL,
                                // “base texture” required by config; we override per-entity below
                                MobsOfMythology.modResource("textures/entity/drake/drake_1.png")
                        )
                        .setAnimatorProvider(DrakeAnimator::new)
                        .setShadowRadius(0.75F)
                        .build(),
                context
        );
    }

    @Override
    public ResourceLocation getTextureLocation(DrakeEntity entity) {
        return LOCATION_BY_VARIANT.getOrDefault(
                entity.getVariant(),
                ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "textures/entity/drake/drake_1.png")
        );
    }

    @Override
    public void render(DrakeEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        if (entity.isBaby()) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
