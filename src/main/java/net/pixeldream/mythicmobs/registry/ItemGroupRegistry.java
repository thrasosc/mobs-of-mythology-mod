package net.pixeldream.mythicmobs.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.pixeldream.mythicmobs.MythicMobs;

public class ItemGroupRegistry {
    public static final ItemGroup ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(MythicMobs.MOD_ID, MythicMobs.MOD_ID), FabricItemGroup.builder().displayName(Text.translatable("itemgroup." + MythicMobs.MOD_ID)).icon(() -> new ItemStack(ItemRegistry.AUTOMATON_HEAD)).entries((displayContext, entries) -> {
        entries.add(ItemRegistry.KOBOLD_SPEAR);
        entries.add(ItemRegistry.CHUPACABRA_RAW_MEAT);
        entries.add(ItemRegistry.CHUPACABRA_COOKED_MEAT_SKEWER);
        entries.add(ItemRegistry.GEAR);
        entries.add(ItemRegistry.BRONZE_INGOT);
        entries.add(BlockRegistry.BRONZE_BLOCK);
        entries.add(BlockRegistry.CUT_BRONZE_BLOCK);
        entries.add(ItemRegistry.AUTOMATON_SPAWN_EGG);
        entries.add(ItemRegistry.KOBOLD_SPAWN_EGG);
        entries.add(ItemRegistry.KOBOLD_WARRIOR_SPAWN_EGG);
        entries.add(ItemRegistry.CHUPACABRA_SPAWN_EGG);
        entries.add(ItemRegistry.DRAKE_SPAWN_EGG);
        entries.add(ItemRegistry.MUSHROOM_SPAWN_EGG);
//        entries.add(ItemRegistry.WENDIGO_SPAWN_EGG);
        entries.add(ItemRegistry.BASILISK_SPAWN_EGG);
    }).build());

    public static void initialize() {
        MythicMobs.LOGGER.info("Registering item group for " + MythicMobs.MOD_NAME);
    }
}