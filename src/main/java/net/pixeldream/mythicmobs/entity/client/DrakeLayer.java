package net.pixeldream.mythicmobs.entity.client;

import com.google.common.collect.Maps;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.DrakeEntity;
import net.pixeldream.mythicmobs.entity.DrakeVariant;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

import java.util.Map;

public class DrakeLayer<T extends DrakeEntity> extends GeoLayerRenderer<T> {
    protected final DrakeEntity instance;
    protected static final Identifier DRAKE_GEO = new Identifier(MythicMobs.MOD_ID, "geo/entity/drake.geo.json");
    public static final Map<DrakeVariant, Identifier> EYES_LOCATION_BY_VARIANT = Util.make(Maps.newEnumMap(DrakeVariant.class), (map) -> {
        map.put(DrakeVariant.DRAKE_1, new Identifier(MythicMobs.MOD_ID, "textures/entity/drake/drake_1_eyes.png"));
        map.put(DrakeVariant.DRAKE_2, new Identifier(MythicMobs.MOD_ID, "textures/entity/drake/drake_2_eyes.png"));
        map.put(DrakeVariant.DRAKE_3, new Identifier(MythicMobs.MOD_ID, "textures/entity/drake/drake_3_eyes.png"));
        map.put(DrakeVariant.DRAKE_4, new Identifier(MythicMobs.MOD_ID, "textures/entity/drake/drake_4_eyes.png"));
        map.put(DrakeVariant.DRAKE_5, new Identifier(MythicMobs.MOD_ID, "textures/entity/drake/drake_5_eyes.png"));
        map.put(DrakeVariant.DRAKE_6, new Identifier(MythicMobs.MOD_ID, "textures/entity/drake/drake_6_eyes.png"));
        map.put(DrakeVariant.DRAKE_7, new Identifier(MythicMobs.MOD_ID, "textures/entity/drake/drake_7_eyes.png"));
    });

    public DrakeLayer(IGeoRenderer<T> entityRendererIn, DrakeEntity instance) {
        super(entityRendererIn);
        this.instance = instance;
    }

    @Override
    public void render(MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, T entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        RenderLayer glow = RenderLayer.getEntityTranslucentEmissive(EYES_LOCATION_BY_VARIANT.get(instance.getVariant()));
        this.getRenderer().render(this.getEntityModel().getModel(DRAKE_GEO), entityLivingBaseIn, partialTicks, glow, matrixStackIn, bufferIn, bufferIn.getBuffer(glow), packedLightIn, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);
    }
}
