//package net.pixeldream.mythicmobs.block.client;
//
//import net.minecraft.client.render.RenderLayer;
//import net.minecraft.client.render.VertexConsumer;
//import net.minecraft.client.render.VertexConsumerProvider;
//import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
//import net.minecraft.client.util.math.MatrixStack;
//import net.minecraft.util.Identifier;
//import net.pixeldream.mythicmobs.block.entity.RitualStoneBlockEntity;
//import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
//
//public class RitualStoneRenderer extends GeoBlockRenderer<RitualStoneBlockEntity> {
//    public RitualStoneRenderer(BlockEntityRendererFactory.Context context) {
//        super(new RitualStoneModel());
//    }
//
//    @Override
//    public RenderLayer getRenderType(RitualStoneBlockEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
//        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
//    }
//}