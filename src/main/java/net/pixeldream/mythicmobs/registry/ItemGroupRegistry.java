package net.pixeldream.mythicmobs.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.pixeldream.mythicmobs.MythicMobs;

public class ItemGroupRegistry {
    public static final CreativeModeTab ITEM_GROUP = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, new ResourceLocation(MythicMobs.MOD_ID, MythicMobs.MOD_ID), FabricItemGroup.builder().title(Component.translatable("itemgroup." + MythicMobs.MOD_ID)).icon(() -> new ItemStack(ItemRegistry.AUTOMATON_HEAD)).displayItems((displayContext, entries) -> {
        entries.accept(ItemRegistry.KOBOLD_SPEAR);
        entries.accept(ItemRegistry.CHUPACABRA_RAW_MEAT);
        entries.accept(ItemRegistry.CHUPACABRA_COOKED_MEAT_SKEWER);
        entries.accept(ItemRegistry.GEAR);
        entries.accept(ItemRegistry.BRONZE_INGOT);
        entries.accept(BlockRegistry.BRONZE_BLOCK);
        entries.accept(BlockRegistry.CUT_BRONZE_BLOCK);
        entries.accept(ItemRegistry.AUTOMATON_SPAWN_EGG);
        entries.accept(ItemRegistry.KOBOLD_SPAWN_EGG);
        entries.accept(ItemRegistry.KOBOLD_WARRIOR_SPAWN_EGG);
        entries.accept(ItemRegistry.CHUPACABRA_SPAWN_EGG);
        entries.accept(ItemRegistry.DRAKE_SPAWN_EGG);
        entries.accept(ItemRegistry.MUSHROOM_SPAWN_EGG);
//        entries.accept(ItemRegistry.WENDIGO_SPAWN_EGG);
//        entries.accept(ItemRegistry.BASILISK_SPAWN_EGG);
    }).build());

    public static void initialize() {
        MythicMobs.LOGGER.info("Registering item group for " + MythicMobs.MOD_NAME);
    }
}