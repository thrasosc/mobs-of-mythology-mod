package net.pixeldream.mythicmobs.entity.client.renderer.entity;

import com.google.common.collect.Maps;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.RotationAxis;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.KoboldWarriorEntity;
import net.pixeldream.mythicmobs.entity.KoboldWarriorVariant;
import net.pixeldream.mythicmobs.entity.client.model.entity.KoboldWarriorModel;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.DynamicGeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

import java.util.Map;

public class KoboldWarriorRenderer extends DynamicGeoEntityRenderer<KoboldWarriorEntity> {
    private static final String LEFT_HAND = "item";
    private static final String RIGHT_HAND = "item2";
    protected ItemStack mainHandItem;
    protected ItemStack offHandItem;
    public static final Map<KoboldWarriorVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(KoboldWarriorVariant.class), (map) -> {
                map.put(KoboldWarriorVariant.KOBOLD_WARRIOR_1,
                        new Identifier(MythicMobs.MOD_ID, "textures/entity/kobold_warrior/kobold_warrior_1.png"));
                map.put(KoboldWarriorVariant.KOBOLD_WARRIOR_2,
                        new Identifier(MythicMobs.MOD_ID, "textures/entity/kobold_warrior/kobold_warrior_2.png"));
                map.put(KoboldWarriorVariant.KOBOLD_WARRIOR_3,
                        new Identifier(MythicMobs.MOD_ID, "textures/entity/kobold_warrior/kobold_warrior_3.png"));
            });
    public KoboldWarriorRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new KoboldWarriorModel());
        this.shadowRadius = 0.4f;

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
            protected ModelTransformationMode getTransformTypeForStack(GeoBone bone, ItemStack stack, KoboldWarriorEntity animatable) {
                // Apply the camera transform for the given hand
                return switch (bone.getName()) {
                    case LEFT_HAND, RIGHT_HAND -> ModelTransformationMode.THIRD_PERSON_RIGHT_HAND;
                    default -> ModelTransformationMode.NONE;
                };
            }

            // Do some quick render modifications depending on what the item is
            @Override
            protected void renderStackForBone(MatrixStack poseStack, GeoBone bone, ItemStack stack, KoboldWarriorEntity animatable, VertexConsumerProvider bufferSource, float partialTick, int packedLight, int packedOverlay) {
                if (stack == KoboldWarriorRenderer.this.mainHandItem) {
                    poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90f));

                    if (stack.getItem() instanceof ShieldItem)
                        poseStack.translate(0, 0.125, -0.25);
                } else if (stack == KoboldWarriorRenderer.this.offHandItem) {
                    poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90f));

                    if (stack.getItem() instanceof ShieldItem) {
                        poseStack.translate(0, 0.125, 0.25);
                        poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
                    }
                }

                super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
            }
        });
    }

    @Override
    public Identifier getTextureLocation(KoboldWarriorEntity animatable) {
        return LOCATION_BY_VARIANT.get(animatable.getVariant());
    }
}
