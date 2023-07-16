package net.pixeldream.mythicmobs.registry;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;
import net.pixeldream.mythicmobs.MythicMobs;

public class ItemGroupRegistry {
    public static final ItemGroup ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(MythicMobs.MOD_ID, MythicMobs.MOD_ID), FabricItemGroup.builder().displayName(Text.translatable("itemgroup." + MythicMobs.MOD_ID)).icon(() -> new ItemStack(Items.RED_MUSHROOM)).entries((displayContext, entries) -> {
        entries.add(ItemRegistry.FIRST_DUNGEON_KEY
        );
    }).build());



    public static void initialize() {
        Valor.LOGGER.info("registering item group for " + Valor.MOD_ID);
    }
}