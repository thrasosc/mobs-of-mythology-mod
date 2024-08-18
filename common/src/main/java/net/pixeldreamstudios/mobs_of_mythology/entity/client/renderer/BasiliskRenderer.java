package net.pixeldreamstudios.mobs_of_mythology.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mod.azure.azurelib.common.api.client.renderer.GeoEntityRenderer;
import mod.azure.azurelib.common.api.client.renderer.layer.AutoGlowingGeoLayer;
import mod.azure.azurelib.common.api.client.renderer.layer.BlockAndItemGeoLayer;
import mod.azure.azurelib.common.internal.common.cache.object.BakedGeoModel;
import mod.azure.azurelib.common.internal.common.cache.object.GeoBone;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.client.model.BasiliskModel;
import net.pixeldreamstudios.mobs_of_mythology.entity.mobs.BasiliskEntity;
import org.jetbrains.annotations.Nullable;

public class BasiliskRenderer extends GeoEntityRenderer<BasiliskEntity> {
    public BasiliskRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new BasiliskModel());
        this.shadowRadius = 0.75f;
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
        addRenderLayer(new BlockAndItemGeoLayer<>(this) {
            @Nullable
            @Override
            protected ItemStack getStackForBone(GeoBone bone, BasiliskEntity animatable) {
                if (animatable.hasChest() && (bone.getName().equals("chest_left") || bone.getName().equals("chest_right"))) {
                    return new ItemStack(Items.CHEST);
                }
                return null;
            }

            @Override
            protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, BasiliskEntity animatable) {
                if (animatable.hasChest()) {
                    return ItemDisplayContext.FIXED;
                }
                return ItemDisplayContext.NONE;
            }

            @Override
            protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, BasiliskEntity animatable, MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
                if (bone.getName().equals("chest_left")) {
                    poseStack.mulPose(Axis.YP.rotationDegrees(90));
                } else if (bone.getName().equals("chest_right")) {
                    poseStack.mulPose(Axis.YP.rotationDegrees(-90));
                }
                super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
            }
        });
    }

    @Override
    public void preRender(PoseStack poseStack, BasiliskEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        if (animatable.isSaddled()) {
            model.getBone("saddle").get().setHidden(false);
        } else {
            model.getBone("saddle").get().setHidden(true);
        }
    }

        @Override
    public ResourceLocation getTextureLocation(BasiliskEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "textures/entity/basilisk.png");
    }
}