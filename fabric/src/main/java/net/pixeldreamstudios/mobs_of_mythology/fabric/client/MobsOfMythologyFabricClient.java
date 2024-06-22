package net.pixeldreamstudios.mobs_of_mythology.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;

public final class MobsOfMythologyFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MobsOfMythology.initClient();
    }
}
