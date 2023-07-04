package net.pixeldream.mythicmobs.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.registry.EntityRegistry;

public class CutBronzeBlock extends Block {
    public CutBronzeBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient() && hand == Hand.MAIN_HAND) {
            world.spawnEntity(EntityRegistry.AUTOMATON_ENTITY.create(world));
            MythicMobs.LOGGER.info("TOUCHING");
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }
}
