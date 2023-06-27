package net.pixeldream.mythicmobs.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.block.CutBronzeBlock;
import net.pixeldream.mythicmobs.block.RitualStoneBlock;

import static net.pixeldream.mythicmobs.MythicMobs.MOD_ID;

public class BlockRegistry {
    public static final Block BRONZE_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(4.0f).requiresTool());
    public static final Block CUT_BRONZE_BLOCK = new CutBronzeBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f).requiresTool());
    public static final Block RITUAL_STONE_BLOCK = registerBlockWithoutItem("ritual_stone_block", new RitualStoneBlock(FabricBlockSettings.of(Material.STONE).nonOpaque()));

    public BlockRegistry() {
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "bronze_block"), BRONZE_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "cut_bronze_block"), CUT_BRONZE_BLOCK);
    }

    private static Block registerBlockWithoutItem(String name, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(MythicMobs.MOD_ID, name), block);
    }
}
