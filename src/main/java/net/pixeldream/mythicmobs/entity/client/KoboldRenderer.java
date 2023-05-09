package net.pixeldream.mythicmobs.entity.client;

import com.google.common.collect.Maps;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.KoboldEntity;
import net.pixeldream.mythicmobs.entity.KoboldVariant;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

public class KoboldRenderer extends GeoEntityRenderer<KoboldEntity> {
    public static final Map<KoboldVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(KoboldVariant.class), (map) -> {
                map.put(KoboldVariant.KOBOLD,
                        new Identifier(MythicMobs.MOD_ID, "textures/entity/kobold.png"));
                map.put(KoboldVariant.KOBOLD_CLOTHED,
                        new Identifier(MythicMobs.MOD_ID, "textures/entity/kobold_cloth.png"));
            });
    public KoboldRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new KoboldModel());
        this.shadowRadius = 0.4f;
    }

    @Override
    public Identifier getTextureResource(KoboldEntity instance) {
        return LOCATION_BY_VARIANT.get(instance.getVariant());
    }

    @Override
    public RenderLayer getRenderType(KoboldEntity animatable, float partialTicks, MatrixStack stack,
                                     VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder,
                                     int packedLightIn, Identifier textureLocation) {
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
