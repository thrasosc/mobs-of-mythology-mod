package net.pixeldream.mythicmobs.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.block.BronzeBlock;
import net.pixeldream.mythicmobs.block.CutBronzeBlock;

public class BlockRegistry {
    public static final Block BRONZE_BLOCK = registerBlock("bronze_block", new BronzeBlock(FabricBlockSettings.copyOf(Blocks.COPPER_BLOCK).sounds(BlockSoundGroup.METAL)));
    public static final Block CUT_BRONZE_BLOCK = registerBlock("bronze_block_cut", new CutBronzeBlock(FabricBlockSettings.copyOf(Blocks.COPPER_BLOCK).sounds(BlockSoundGroup.METAL)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(MythicMobs.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, new Identifier(MythicMobs.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
    }

    public static void initialize() {
        MythicMobs.LOGGER.info("Registering blocks for " + MythicMobs.MOD_NAME);
    }
}
