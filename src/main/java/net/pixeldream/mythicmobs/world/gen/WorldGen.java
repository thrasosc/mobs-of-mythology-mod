package net.pixeldream.mythicmobs.world.gen;

import net.pixeldream.mythicmobs.MythicMobs;

public class WorldGen {
    public static void generateWorldGen() {
        MythicMobs.LOGGER.info("Generating world-gen for " + MythicMobs.MOD_NAME);
        EntitySpawn.setEntitySpawn();
    }
}
