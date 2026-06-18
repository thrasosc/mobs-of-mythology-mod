package net.pixeldreamstudios.mobs_of_mythology.entity.client.renderer.layor;

import com.mojang.math.Axis;
import mod.azure.azurelib.common.model.AzBone;
import mod.azure.azurelib.common.render.AzRendererPipelineContext;
import mod.azure.azurelib.common.render.layer.AzBlockAndItemLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.pixeldreamstudios.mobs_of_mythology.entity.mobs.KoboldEntity;

import java.util.UUID;

public class KoboldItemAndHeadLayer extends AzBlockAndItemLayer<UUID, KoboldEntity> {

    private static final String BONE_HEAD = "head";
    private static final String BONE_RIGHT_HAND = "hand";
    private static final String BONE_LEFT_HAND  = "hand2";

    @Override
    public void renderForBone(AzRendererPipelineContext<UUID, KoboldEntity> context, AzBone bone) {
        final KoboldEntity entity = context.animatable();

        if (BONE_HEAD.equals(bone.getName())) {
            LivingEntity living = entity;

            float partial = context.partialTick();
            float headYaw = Mth.rotLerp(partial, living.yHeadRotO, living.yHeadRot);
            float bodyYaw = Mth.rotLerp(partial, living.yBodyRotO, living.yBodyRot);
            float netYaw  = Mth.wrapDegrees(headYaw - bodyYaw);
            float pitch   = Mth.lerp(partial, living.xRotO, living.getXRot());

            bone.setRotY(netYaw * Mth.DEG_TO_RAD);
            bone.setRotX(pitch * Mth.DEG_TO_RAD);
            return;
        }

        super.renderForBone(context, bone);
    }

    @Override
    public ItemStack itemStackForBone(AzBone bone, KoboldEntity animatable) {
        String name = bone.getName();
        boolean isLeftBone = BONE_LEFT_HAND.equals(name);
        boolean isRightBone = BONE_RIGHT_HAND.equals(name);

        if (!isLeftBone && !isRightBone)
            return null;

        ItemStack main = animatable.getMainHandItem();
        ItemStack off  = animatable.getOffhandItem();

        boolean leftHanded = animatable.isLeftHanded();

        ItemStack stack = isLeftBone
                ? (leftHanded ? main : off)
                : (leftHanded ? off : main);

        return stack.isEmpty() ? null : stack;
    }

    @Override
    protected ItemDisplayContext getTransformTypeForStack(AzBone bone, ItemStack stack, KoboldEntity animatable) {
        return BONE_LEFT_HAND.equals(bone.getName())
                ? ItemDisplayContext.THIRD_PERSON_LEFT_HAND
                : ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
    }

    @Override
    protected void renderItemForBone(
            AzRendererPipelineContext<UUID, KoboldEntity> context,
            AzBone bone,
            ItemStack itemStack,
            KoboldEntity animatable
    ) {
        boolean isLeftBone = BONE_LEFT_HAND.equals(bone.getName());

        context.poseStack().mulPose(Axis.XP.rotationDegrees(270));

        double up = 0.08D;
        double forward = -0.04D;
        double side = isLeftBone ? -0.02D : 0.02D;
        context.poseStack().translate(side, up, forward);

        if (itemStack.getItem() instanceof ShieldItem) {
            if (!isLeftBone) {
                context.poseStack().translate(0, 0.125, -0.25);
            } else {
                context.poseStack().translate(0, 0.125, 0.25);
                context.poseStack().mulPose(Axis.YP.rotationDegrees(180));
            }
        }

        super.renderItemForBone(context, bone, itemStack, animatable);
    }
}
