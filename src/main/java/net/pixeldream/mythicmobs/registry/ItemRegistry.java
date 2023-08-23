package net.pixeldream.mythicmobs.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.item.KoboldSpearItem;

public class ItemRegistry {
    public static final Item AUTOMATON_SPAWN_EGG = registerItem("automaton_spawn_egg", new SpawnEggItem(EntityRegistry.AUTOMATON_ENTITY, 0xcf9611, 0xa87807, new FabricItemSettings().maxCount(64)));
    public static final Item KOBOLD_SPAWN_EGG = registerItem("kobold_spawn_egg", new SpawnEggItem(EntityRegistry.KOBOLD_ENTITY, 0xd5f07d, 0x637036, new FabricItemSettings().maxCount(64)));
    public static final Item KOBOLD_WARRIOR_SPAWN_EGG = registerItem("kobold_warrior_spawn_egg", new SpawnEggItem(EntityRegistry.KOBOLD_WARRIOR_ENTITY, 0xd5f07d, 0xe6b800, new FabricItemSettings().maxCount(64)));
    public static final Item CHUPACABRA_SPAWN_EGG = registerItem("chupacabra_spawn_egg", new SpawnEggItem(EntityRegistry.CHUPACABRA_ENTITY, 0x8E7870, 0xDA5126, new FabricItemSettings().maxCount(64)));
    public static final Item DRAKE_SPAWN_EGG = registerItem("drake_spawn_egg", new SpawnEggItem(EntityRegistry.DRAKE_ENTITY, 0xFE6F42, 0xE54B1A, new FabricItemSettings().maxCount(64)));
    public static final Item MUSHROOM_SPAWN_EGG = registerItem("mushroom_spawn_egg", new SpawnEggItem(EntityRegistry.MUSHROOM_ENTITY, 0xE53935, 0xFEFEFE, new FabricItemSettings().maxCount(64)));
//    public static final Item WENDIGO_SPAWN_EGG = registerItem("wendigo_spawn_egg", new SpawnEggItem(EntityRegistry.WENDIGO_ENTITY, 0x9f906f, 0x473a1f, new FabricItemSettings().maxCount(64)));
//    public static final Item BASILISK_SPAWN_EGG = registerItem("basilisk_spawn_egg", new SpawnEggItem(EntityRegistry.BASILISK_ENTITY, 0x399E6F, 0x1B6E47, new FabricItemSettings().maxCount(64)));
    public static final Item KOBOLD_SPEAR = registerItem("kobold_spear", new KoboldSpearItem(ToolMaterials.IRON, 3, -2.0f, new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON)));
//    public static final Item AUTOMATON_AXE = registerItem("automaton_axe", new AutomatonAxeItem(ToolMaterials.GOLD, 8, -3.25f, new Item.Settings().maxCount(1).rarity(Rarity.EPIC)));
    public static final Item BRONZE_INGOT = registerItem("bronze_ingot", new Item(new FabricItemSettings().maxCount(64)));
    public static final Item AUTOMATON_HEAD = registerItem("automaton_head", new Item(new FabricItemSettings().maxCount(1)));
//    public static final Item DRAKE_EGG_BLOCK = registerItem("drake_egg", new BlockItem(BlockRegistry.DRAKE_EGG_BLOCK, new FabricItemSettings().maxCount(16)));
    public static final Item CHUPACABRA_RAW_MEAT = registerItem("chupacabra_raw_meat", new Item(new FabricItemSettings().maxCount(64).food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1F).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.8F).meat().build())));
    public static final Item CHUPACABRA_COOKED_MEAT_SKEWER = registerItem("chupacabra_cooked_meat_skewer", new Item(new FabricItemSettings().maxCount(64).food(new FoodComponent.Builder().hunger(6).saturationModifier(0.8F).meat().build())));
    public static final Item GEAR = registerItem("gear", new Item(new FabricItemSettings().maxCount(64)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MythicMobs.MOD_ID, name), item);
    }

    public static void initialize() {
        MythicMobs.LOGGER.info("Registering items for " + MythicMobs.MOD_NAME);
    }
}
