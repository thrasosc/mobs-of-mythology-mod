package net.pixeldream.mythicmobs.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.pixeldream.mythicmobs.item.KoboldSpearItem;

import static net.pixeldream.mythicmobs.MythicMobs.MOD_ID;
import static net.pixeldream.mythicmobs.MythicMobs.ITEM_GROUP;

public class ItemRegistry {
    public static final Item AUTOMATON_SPAWN_EGG = new SpawnEggItem(EntityRegistry.AUTOMATON_ENTITY,0xcf9611, 0xa87807, new FabricItemSettings().group(ITEM_GROUP).maxCount(64));
    public static final Item KOBOLD_SPAWN_EGG = new SpawnEggItem(EntityRegistry.KOBOLD_ENTITY,0xd5f07d, 0x637036, new FabricItemSettings().group(ITEM_GROUP).maxCount(64));
    public static final Item KOBOLD_WARRIOR_SPAWN_EGG = new SpawnEggItem(EntityRegistry.KOBOLD_WARRIOR_ENTITY,0xd5f07d, 0x637036, new FabricItemSettings().group(ITEM_GROUP).maxCount(64));
    public static final Item CHUPACABRA_SPAWN_EGG = new SpawnEggItem(EntityRegistry.CHUPACABRA_ENTITY,0x444541, 0x82261e, new FabricItemSettings().group(ITEM_GROUP).maxCount(64));
    public static final KoboldSpearItem KOBOLD_SPEAR = new KoboldSpearItem(ToolMaterials.IRON, 4, 1, new Item.Settings().group(ITEM_GROUP).maxCount(1));
    public static final Item BRONZE_ITEM = new Item(new FabricItemSettings().group(ITEM_GROUP).maxCount(64));
    public static final BlockItem BRONZE_BLOCK = new BlockItem(BlockRegistry.BRONZE_BLOCK, new FabricItemSettings().group(ITEM_GROUP).maxCount(64));
    public ItemRegistry() {
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "kobold_spear"), KOBOLD_SPEAR);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "bronze_item"), BRONZE_ITEM);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "bronze_block"),BRONZE_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "automaton_spawn_egg"), AUTOMATON_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "kobold_spawn_egg"), KOBOLD_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "kobold_warrior_spawn_egg"), KOBOLD_WARRIOR_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "chupacabra_spawn_egg"), CHUPACABRA_SPAWN_EGG);
    }
}
