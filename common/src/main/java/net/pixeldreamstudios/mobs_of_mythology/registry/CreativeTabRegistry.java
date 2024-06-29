package net.pixeldreamstudios.mobs_of_mythology.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;

public class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(MobsOfMythology.MOD_ID, Registries.CREATIVE_MODE_TAB);
    public static final RegistrySupplier<CreativeModeTab> MOBS_OF_MYTHOLOGY_TAB = TABS.register("mobs_of_mythology_tab", () ->
            dev.architectury.registry.CreativeTabRegistry.create(Component.translatable("itemGroup." + MobsOfMythology.MOD_ID + ".mobs_of_mythology_tab"),
                    () -> new ItemStack(ItemRegistry.GEAR.get())));

    public static void init() {
        TABS.register();
    }
}
