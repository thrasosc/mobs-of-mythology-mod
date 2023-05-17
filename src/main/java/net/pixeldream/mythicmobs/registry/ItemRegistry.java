package net.pixeldream.mythicmobs.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.pixeldream.mythicmobs.item.KoboldSpearItem;

import static net.pixeldream.mythicmobs.MythicMobs.MOD_ID;
import static net.pixeldream.mythicmobs.MythicMobs.ITEM_GROUP;

public class ItemRegistry {
    public static final KoboldSpearItem KOBOLD_SPEAR = new KoboldSpearItem(ToolMaterials.IRON, 4, 1, new Item.Settings().group(ITEM_GROUP));
    public static final Item BRONZE_ITEM = new Item(new FabricItemSettings());
    public ItemRegistry() {
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "kobold_spear"), KOBOLD_SPEAR);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "bronze_block"), new BlockItem(BlockRegistry.BRONZE_BLOCK, new FabricItemSettings().group(ITEM_GROUP)));
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "bronze_item"), BRONZE_ITEM);
    }
}
