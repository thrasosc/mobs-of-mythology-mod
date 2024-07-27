package net.pixeldreamstudios.mobs_of_mythology.fabric;

import net.fabricmc.api.ModInitializer;

import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;

public final class MobsOfMythologyFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        MobsOfMythology.init();
    }
}
