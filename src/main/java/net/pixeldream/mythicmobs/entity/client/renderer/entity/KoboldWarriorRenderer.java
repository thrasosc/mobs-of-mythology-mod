package net.pixeldream.mythicmobs.entity.client.renderer.entity;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mod.azure.azurelib.cache.object.BakedGeoModel;
import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.DynamicGeoEntityRenderer;
import mod.azure.azurelib.renderer.layer.BlockAndItemGeoLayer;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.client.model.entity.KoboldWarriorModel;
import net.pixeldream.mythicmobs.entity.mobs.KoboldWarriorEntity;
import net.pixeldream.mythicmobs.entity.mobs.KoboldWarriorVariant;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class KoboldWarriorRenderer extends DynamicGeoEntityRenderer<KoboldWarriorEntity> {
    private static final String RIGHT_HAND = "item";
    private static final String LEFT_HAND = "item2";
    protected ItemStack mainHandItem;
    protected ItemStack offHandItem;
    public static final Map<KoboldWarriorVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(KoboldWarriorVariant.class), (map) -> {
                map.put(KoboldWarriorVariant.KOBOLD_WARRIOR_1,
                        new ResourceLocation(MythicMobs.MOD_ID, "textures/entity/kobold_warrior/kobold_warrior_1.png"));
                map.put(KoboldWarriorVariant.KOBOLD_WARRIOR_2,
                        new ResourceLocation(MythicMobs.MOD_ID, "textures/entity/kobold_warrior/kobold_warrior_2.png"));
                map.put(KoboldWarriorVariant.KOBOLD_WARRIOR_3,
                        new ResourceLocation(MythicMobs.MOD_ID, "textures/entity/kobold_warrior/kobold_warrior_3.png"));
            });
    public KoboldWarriorRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new KoboldWarriorModel());
        this.shadowRadius = 0.4f;

        //TODO Add glow
//        addRenderLayer(new AutoGlowingGeoLayer<>(this));

        // Add some held item rendering
        addRenderLayer(new BlockAndItemGeoLayer<>(this) {
            @Nullable
            @Override
            protected ItemStack getStackForBone(GeoBone bone, KoboldWarriorEntity animatable) {
                // Retrieve the items in the entity's hands for the relevant bone
                return switch (bone.getName()) {
                    case LEFT_HAND -> animatable.isLeftHanded() ?
                            KoboldWarriorRenderer.this.mainHandItem : KoboldWarriorRenderer.this.offHandItem;
                    case RIGHT_HAND -> animatable.isLeftHanded() ?
                            KoboldWarriorRenderer.this.offHandItem : KoboldWarriorRenderer.this.mainHandItem;
                    default -> null;
                };
            }

            @Override
            protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, KoboldWarriorEntity animatable) {
                // Apply the camera transform for the given hand
                return switch (bone.getName()) {
                    case LEFT_HAND, RIGHT_HAND -> ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
                    default -> ItemDisplayContext.NONE;
                };
            }

            // Do some quick render modifications depending on what the item is
            @Override
            protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, KoboldWarriorEntity animatable, MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
                if (stack == KoboldWarriorRenderer.this.mainHandItem) {
                    poseStack.mulPose(Axis.XP.rotationDegrees(-90f));

                    if (stack.getItem() instanceof ShieldItem)
                        poseStack.translate(0, 0.125, -0.25);
                } else if (stack == KoboldWarriorRenderer.this.offHandItem) {
                    poseStack.mulPose(Axis.XP.rotationDegrees(-90f));

                    if (stack.getItem() instanceof ShieldItem) {
                        poseStack.translate(0, 0.125, 0.25);
                        poseStack.mulPose(Axis.YP.rotationDegrees(180));
                    }
                }

                super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
            }
        });
    }

    @Override
    public ResourceLocation getTextureLocation(KoboldWarriorEntity animatable) {
        return LOCATION_BY_VARIANT.get(animatable.getVariant());
    }

    @Override
    public void preRender(PoseStack poseStack, KoboldWarriorEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);

        this.mainHandItem = animatable.getMainHandItem();
        this.offHandItem = animatable.getOffhandItem();
    }
}
