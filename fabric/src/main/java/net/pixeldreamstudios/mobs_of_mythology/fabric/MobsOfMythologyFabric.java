package net.pixeldreamstudios.mobs_of_mythology.fabric;

import net.fabricmc.api.ModInitializer;

import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;

public final class MobsOfMythologyFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        MobsOfMythology.init();
    }
}
