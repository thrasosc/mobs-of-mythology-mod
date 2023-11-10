package net.pixeldream.mythicmobs.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.block.BronzeBlock;
import net.pixeldream.mythicmobs.block.CutBronzeBlock;

public class BlockRegistry {
    public static final Block BRONZE_BLOCK = registerBlock("bronze_block", new BronzeBlock(FabricBlockSettings.copyOf(Blocks.COPPER_BLOCK).sounds(SoundType.METAL)));
    public static final Block CUT_BRONZE_BLOCK = registerBlock("bronze_block_cut", new CutBronzeBlock(FabricBlockSettings.copyOf(Blocks.COPPER_BLOCK).sounds(SoundType.METAL)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(MythicMobs.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MythicMobs.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
    }

    public static void initialize() {
        MythicMobs.LOGGER.info("Registering blocks for " + MythicMobs.MOD_NAME);
    }
}
