package net.pixeldreamstudios.mobs_of_mythology.registry;

import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MobsOfMythology.MOD_ID, Registries.ITEM);

    // SPAWN EGGS
    public static final RegistrySupplier<Item> AUTOMATON_SPAWN_EGG = ITEMS.register("automaton_spawn_egg", () -> new ArchitecturySpawnEggItem(EntityRegistry.AUTOMATON, 0xcf9611, 0xa87807, new Item.Properties().arch$tab(CreativeTabRegistry.MOBS_OF_MYTHOLOGY_TAB)));
    public static final RegistrySupplier<Item> CHUPACABRA_SPAWN_EGG = ITEMS.register("chupacabra_spawn_egg", () -> new ArchitecturySpawnEggItem(EntityRegistry.CHUPACABRA, 0x8E7870, 0xDA5126, new Item.Properties().arch$tab(CreativeTabRegistry.MOBS_OF_MYTHOLOGY_TAB)));
    public static final RegistrySupplier<Item> KOBOLD_SPAWN_EGG = ITEMS.register("kobold_spawn_egg", () -> new ArchitecturySpawnEggItem(EntityRegistry.KOBOLD, 0xd5f07d, 0x637036, new Item.Properties().arch$tab(CreativeTabRegistry.MOBS_OF_MYTHOLOGY_TAB)));
    public static final RegistrySupplier<Item> KOBOLD_WARRIOR_SPAWN_EGG = ITEMS.register("kobold_warrior_spawn_egg", () -> new ArchitecturySpawnEggItem(EntityRegistry.KOBOLD_WARRIOR, 0xd5f07d, 0xe6b800, new Item.Properties().arch$tab(CreativeTabRegistry.MOBS_OF_MYTHOLOGY_TAB)));

    // FOODS
    public static final RegistrySupplier<Item> CHUPACABRA_RAW_MEAT = ITEMS.register("chupacabra_raw_meat", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1F).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.8F).build()).arch$tab(CreativeTabRegistry.MOBS_OF_MYTHOLOGY_TAB)));
    public static final RegistrySupplier<Item> CHUPACABRA_COOKED_MEAT_SKEWER = ITEMS.register("chupacabra_cooked_meat_skewer", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.8F).build()).arch$tab(CreativeTabRegistry.MOBS_OF_MYTHOLOGY_TAB)));

    // WEAPONS
    public static final RegistrySupplier<Item> KOBOLD_SPEAR = ITEMS.register("kobold_spear", () -> new SwordItem(Tiers.IRON, new Item.Properties().attributes(SwordItem.createAttributes(Tiers.IRON, 3, -2.0F)).rarity(Rarity.UNCOMMON).arch$tab(CreativeTabRegistry.MOBS_OF_MYTHOLOGY_TAB)));

    // MISCELLANEOUS
    public static final RegistrySupplier<Item> BRONZE_INGOT = ITEMS.register("bronze_ingot", () -> new Item(new Item.Properties().stacksTo(64).arch$tab(CreativeTabRegistry.MOBS_OF_MYTHOLOGY_TAB)));
    public static final RegistrySupplier<Item> GEAR = ITEMS.register("gear", () -> new Item(new Item.Properties().stacksTo(64).arch$tab(CreativeTabRegistry.MOBS_OF_MYTHOLOGY_TAB)));

    public static void init() {
        ITEMS.register();
    }
}
