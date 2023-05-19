package net.pixeldream.mythicmobs.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.pixeldream.mythicmobs.MythicMobs;

import static net.pixeldream.mythicmobs.MythicMobs.ITEM_GROUP;
import static net.pixeldream.mythicmobs.MythicMobs.MOD_ID;

public class BlockRegistry {
    public static final Block BRONZE_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(4.0f).requiresTool());
    public static final Block BRONZE_BLOCK_CUT = new Block(FabricBlockSettings.of(Material.METAL).strength(4.0f).requiresTool());

    public BlockRegistry() {
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "bronze_block"), BRONZE_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "bronze_block_cut"), BRONZE_BLOCK_CUT);
    }
}
