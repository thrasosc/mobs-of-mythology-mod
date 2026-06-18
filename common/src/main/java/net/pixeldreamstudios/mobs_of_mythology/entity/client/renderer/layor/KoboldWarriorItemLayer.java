package net.pixeldreamstudios.mobs_of_mythology.entity.client.renderer.layor;

import com.mojang.math.Axis;
import mod.azure.azurelib.common.model.AzBone;
import mod.azure.azurelib.common.render.AzRendererPipelineContext;
import mod.azure.azurelib.common.render.layer.AzBlockAndItemLayer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.pixeldreamstudios.mobs_of_mythology.entity.mobs.KoboldWarriorEntity;

import java.util.UUID;

public class KoboldWarriorItemLayer extends AzBlockAndItemLayer<UUID, KoboldWarriorEntity> {

    private static final String BONE_RIGHT_HAND = "left_arm";
    private static final String BONE_LEFT_HAND  = "left_arm2";

    @Override
    public ItemStack itemStackForBone(AzBone bone, KoboldWarriorEntity animatable) {
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
    protected ItemDisplayContext getTransformTypeForStack(AzBone bone, ItemStack stack, KoboldWarriorEntity animatable) {
        return BONE_LEFT_HAND.equals(bone.getName())
                ? ItemDisplayContext.THIRD_PERSON_LEFT_HAND
                : ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
    }

    @Override
    protected void renderItemForBone(
            AzRendererPipelineContext<UUID, KoboldWarriorEntity> context,
            AzBone bone,
            ItemStack itemStack,
            KoboldWarriorEntity animatable
    ) {

        context.poseStack().mulPose(Axis.XP.rotationDegrees(270));
        double up = -0.14D;
        double forward = -0.50D;
        double side = 0.0D;


        context.poseStack().translate(side, up, forward);


        super.renderItemForBone(context, bone, itemStack, animatable);
    }
}
