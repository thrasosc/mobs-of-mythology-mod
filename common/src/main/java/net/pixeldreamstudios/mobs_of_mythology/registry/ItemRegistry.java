package net.pixeldreamstudios.mobs_of_mythology.registry;

import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MobsOfMythology.MOD_ID, Registries.ITEM);

    // SPAWN EGGS
    public static final RegistrySupplier<Item> AUTOMATON_SPAWN_EGG = ITEMS.register("automaton_spawn_egg", () -> new ArchitecturySpawnEggItem(EntityRegistry.AUTOMATON, 0xcf9611, 0xa87807, new Item.Properties().arch$tab(CreativeTabRegistry.MOBS_OF_MYTHOLOGY_TAB)));
    public static final RegistrySupplier<Item> CHUPACABRA_SPAWN_EGG = ITEMS.register("chupacabra_spawn_egg", () -> new ArchitecturySpawnEggItem(EntityRegistry.CHUPACABRA, 0x8E7870, 0xDA5126, new Item.Properties().arch$tab(CreativeTabRegistry.MOBS_OF_MYTHOLOGY_TAB)));

    // FOODS
    public static final RegistrySupplier<Item> CHUPACABRA_RAW_MEAT = ITEMS.register("chupacabra_raw_meat", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1F).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.8F).build()).arch$tab(CreativeTabRegistry.MOBS_OF_MYTHOLOGY_TAB)));
    public static final RegistrySupplier<Item> CHUPACABRA_COOKED_MEAT_SKEWER = ITEMS.register("chupacabra_cooked_meat_skewer", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.8F).build()).arch$tab(CreativeTabRegistry.MOBS_OF_MYTHOLOGY_TAB)));

    // MISCELLANEOUS
    public static final RegistrySupplier<Item> BRONZE_INGOT = ITEMS.register("bronze_ingot", () -> new Item(new Item.Properties().stacksTo(64).arch$tab(CreativeTabRegistry.MOBS_OF_MYTHOLOGY_TAB)));
    public static final RegistrySupplier<Item> GEAR = ITEMS.register("gear", () -> new Item(new Item.Properties().stacksTo(64).arch$tab(CreativeTabRegistry.MOBS_OF_MYTHOLOGY_TAB)));

    public static void init() {
        ITEMS.register();
    }
}
