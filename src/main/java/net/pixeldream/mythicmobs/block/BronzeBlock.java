package net.pixeldream.mythicmobs.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.pixeldream.mythicmobs.registry.BlockRegistry;
import net.pixeldream.mythicmobs.registry.TagRegistry;

public class BronzeBlock extends Block {
    public BronzeBlock(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (item.builtInRegistryHolder().is(TagRegistry.Items.PICKAXES)) {
            item.use(world, player, hand);
            world.playLocalSound(pos, SoundEvents.COPPER_BREAK, SoundSource.BLOCKS, 1, 1, true);
            world.addDestroyBlockEffect(pos, this.defaultBlockState());
            world.setBlockAndUpdate(pos, BlockRegistry.CUT_BRONZE_BLOCK.defaultBlockState());
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
