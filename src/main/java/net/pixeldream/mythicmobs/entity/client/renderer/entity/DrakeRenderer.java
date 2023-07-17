package net.pixeldream.mythicmobs.entity.client.renderer.entity;

import com.google.common.collect.Maps;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.DrakeEntity;
import net.pixeldream.mythicmobs.entity.DrakeVariant;
import net.pixeldream.mythicmobs.entity.client.model.entity.DrakeModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class DrakeRenderer extends GeoEntityRenderer<DrakeEntity> {
    public static final Map<DrakeVariant, Identifier> LOCATION_BY_VARIANT = Util.make(Maps.newEnumMap(DrakeVariant.class), (map) -> {
        map.put(DrakeVariant.DRAKE_1, new Identifier(MythicMobs.MOD_ID, "textures/entity/drake/drake_1.png"));
        map.put(DrakeVariant.DRAKE_2, new Identifier(MythicMobs.MOD_ID, "textures/entity/drake/drake_2.png"));
        map.put(DrakeVariant.DRAKE_3, new Identifier(MythicMobs.MOD_ID, "textures/entity/drake/drake_3.png"));
        map.put(DrakeVariant.DRAKE_4, new Identifier(MythicMobs.MOD_ID, "textures/entity/drake/drake_4.png"));
        map.put(DrakeVariant.DRAKE_5, new Identifier(MythicMobs.MOD_ID, "textures/entity/drake/drake_5.png"));
        map.put(DrakeVariant.DRAKE_6, new Identifier(MythicMobs.MOD_ID, "textures/entity/drake/drake_6.png"));
        map.put(DrakeVariant.DRAKE_7, new Identifier(MythicMobs.MOD_ID, "textures/entity/drake/drake_7.png"));
    });
    public static final Map<DrakeVariant, Identifier> EYES_LOCATION_BY_VARIANT = Util.make(Maps.newEnumMap(DrakeVariant.class), (map) -> {
        map.put(DrakeVariant.DRAKE_1, new Identifier(MythicMobs.MOD_ID, "textures/entity/drake/drake_1_eyes.png"));
        map.put(DrakeVariant.DRAKE_2, new Identifier(MythicMobs.MOD_ID, "textures/entity/drake/drake_2_eyes.png"));
        map.put(DrakeVariant.DRAKE_3, new Identifier(MythicMobs.MOD_ID, "textures/entity/drake/drake_3_eyes.png"));
        map.put(DrakeVariant.DRAKE_4, new Identifier(MythicMobs.MOD_ID, "textures/entity/drake/drake_4_eyes.png"));
        map.put(DrakeVariant.DRAKE_5, new Identifier(MythicMobs.MOD_ID, "textures/entity/drake/drake_5_eyes.png"));
        map.put(DrakeVariant.DRAKE_6, new Identifier(MythicMobs.MOD_ID, "textures/entity/drake/drake_6_eyes.png"));
        map.put(DrakeVariant.DRAKE_7, new Identifier(MythicMobs.MOD_ID, "textures/entity/drake/drake_7_eyes.png"));
    });

    public DrakeRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new DrakeModel());
        this.shadowRadius = 0.75f;
        //TODO Glow layer
    }

    @Override
    public Identifier getTextureLocation(DrakeEntity animatable) {
        return LOCATION_BY_VARIANT.get(animatable.getVariant());
    }

    @Override
    public void render(DrakeEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        if (entity.isBaby()) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            poseStack.scale(1f, 1f, 1f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);

    }
}