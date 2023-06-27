package net.pixeldream.mythicmobs.block.client;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.pixeldream.mythicmobs.block.entity.DrakeEggBlockEntity;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class DrakeEggRenderer extends GeoBlockRenderer<DrakeEggBlockEntity> {
    public DrakeEggRenderer(BlockEntityRendererFactory.Context context) {
        super(new DrakeEggModel());
    }


    @Override
    public RenderLayer getRenderType(DrakeEggBlockEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}
