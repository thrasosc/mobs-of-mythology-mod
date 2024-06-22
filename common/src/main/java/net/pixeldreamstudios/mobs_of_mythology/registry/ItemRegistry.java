package net.pixeldreamstudios.mobs_of_mythology.registry;

import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MobsOfMythology.MOD_ID, Registries.ITEM);
    public static final RegistrySupplier<Item> AUTOMATON_SPAWN_EGG = ITEMS.register("automaton_spawn_egg", () -> new ArchitecturySpawnEggItem(EntityRegistry.AUTOMATON, 0xcf9611, 0xa87807, new Item.Properties().arch$tab(CreativeTabRegistry.AUTOMATONS_TAB)));
    public static final RegistrySupplier<Item> BRONZE_INGOT = ITEMS.register("bronze_ingot", () -> new Item(new Item.Properties().stacksTo(64).arch$tab(CreativeTabRegistry.AUTOMATONS_TAB)));
    public static final RegistrySupplier<Item> GEAR = ITEMS.register("gear", () -> new Item(new Item.Properties().stacksTo(64).arch$tab(CreativeTabRegistry.AUTOMATONS_TAB)));

    public static void init() {
        ITEMS.register();
    }
}
