package net.pixeldream.mythicmobs.registry;

import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.pixeldream.mythicmobs.item.AxeCaliburItem;
import net.pixeldream.mythicmobs.item.SpearItem;

import static net.pixeldream.mythicmobs.MythicMobs.MOD_ID;
import static net.pixeldream.mythicmobs.MythicMobs.ITEM_GROUP;

public class ItemRegistry {
    public static final SpearItem SPEAR = new SpearItem(ToolMaterials.IRON, 4, 1, new Item.Settings().group(ITEM_GROUP));
    public static final AxeItem AXE_CALIBUR = new AxeCaliburItem(ToolMaterials.IRON, 4, 1, new Item.Settings().group(ITEM_GROUP));
    public ItemRegistry() {
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "spear"), SPEAR);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "axe-calibur"), AXE_CALIBUR);
    }
}
