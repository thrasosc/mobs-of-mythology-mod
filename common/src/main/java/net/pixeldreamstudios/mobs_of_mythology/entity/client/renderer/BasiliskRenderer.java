package net.pixeldreamstudios.mobs_of_mythology.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.azure.azurelib.common.api.client.renderer.GeoEntityRenderer;
import mod.azure.azurelib.common.api.client.renderer.layer.AutoGlowingGeoLayer;
import mod.azure.azurelib.common.api.client.renderer.layer.BlockAndItemGeoLayer;
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

        // Add some held item rendering
        addRenderLayer(new BlockAndItemGeoLayer<>(this) {
            @Nullable
            @Override
            protected ItemStack getStackForBone(GeoBone bone, BasiliskEntity animatable) {
                // Retrieve the items in the entity's hands for the relevant bone

                if (animatable.hasChest() && (bone.getName().equals("chest_left") || bone.getName().equals("chest_right"))) {
                    return new ItemStack(Items.CHEST);
                }
                return null;
//                return switch (bone.getName()) {
//                    case "chest_left", "chest_right" -> new ItemStack(Items.CHEST);
//                    default -> null;
//                };
            }

            @Override
            protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, BasiliskEntity animatable) {
                // Apply the camera transform for the given hand
                if (animatable.hasChest()) {
                    return ItemDisplayContext.FIXED;
                }
                return ItemDisplayContext.NONE;
            }

            // Do some quick render modifications depending on what the item is
            @Override
            protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, BasiliskEntity animatable, MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {

                if (bone.getName().equals("chest_left")) {
                    poseStack.mulPose(Axis.YP.rotationDegrees(90));
                } else if (bone.getName().equals("chest_right")) {
                    poseStack.mulPose(Axis.YP.rotationDegrees(-90));
                }
//                if (stack == BasiliskRenderer.this.mainHandItem) {
//                    poseStack.mulPose(Axis.XP.rotationDegrees(-90f));
//
//                    if (stack.getItem() instanceof ShieldItem)
//                        poseStack.translate(0, 0.125, -0.25);
//                } else if (stack == KoboldRenderer.this.offHandItem) {
//                    poseStack.mulPose(Axis.XP.rotationDegrees(-90f));
//
//                    if (stack.getItem() instanceof ShieldItem) {
//                        poseStack.translate(0, 0.125, 0.25);
//                        poseStack.mulPose(Axis.YP.rotationDegrees(180));
//                    }
//                }

                super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
            }
        });
    }

    @Override
    public ResourceLocation getTextureLocation(BasiliskEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "textures/entity/basilisk.png");
    }
}