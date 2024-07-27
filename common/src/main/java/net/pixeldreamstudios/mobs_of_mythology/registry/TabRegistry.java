package net.pixeldreamstudios.mobs_of_mythology.registry;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;

public class TabRegistry {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(MobsOfMythology.MOD_ID, Registries.CREATIVE_MODE_TAB);
    public static final RegistrySupplier<CreativeModeTab> MOBS_OF_MYTHOLOGY_TAB = TABS.register(
            "mobs_of_mythology_tab", // Tab ID
            () -> CreativeTabRegistry.create(
                    Component.translatable("category.mobs_of_mythology"), // Tab Name
                    () -> new ItemStack(ItemRegistry.AUTOMATON_HEAD.get()) // Icon
            )
    );

    public static void init() {
        TABS.register();
    }
}
