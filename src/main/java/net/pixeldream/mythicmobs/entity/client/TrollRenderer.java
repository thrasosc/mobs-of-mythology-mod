package net.pixeldream.mythicmobs.entity.client;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.TrollEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class TrollRenderer extends GeoEntityRenderer<TrollEntity> {
    public TrollRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new TrollModel());
        this.shadowRadius = 0.75f;
    }

    @Override
    public Identifier getTextureResource(TrollEntity instance) {
        return new Identifier(MythicMobs.MOD_ID, "textures/entity/troll.png");
    }

    @Override
    public RenderLayer getRenderType(TrollEntity animatable, float partialTicks, MatrixStack stack,
                                     VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder,
                                     int packedLightIn, Identifier textureLocation) {
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
