package net.pixeldream.mythicmobs.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.pixeldream.mythicmobs.config.MythicMobsConfigs;
import net.pixeldream.mythicmobs.registry.EntityRegistry;

public class EntitySpawn {
    public static void setEntitySpawn() {
        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), MobCategory.CREATURE, EntityRegistry.KOBOLD_ENTITY, MythicMobsConfigs.koboldSpawnWeight, 3, 6);
        SpawnPlacements.register(EntityRegistry.KOBOLD_ENTITY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PathfinderMob::checkMobSpawnRules);

        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), MobCategory.CREATURE, EntityRegistry.KOBOLD_WARRIOR_ENTITY, MythicMobsConfigs.koboldWarriorSpawnWeight, 1, 3);
        SpawnPlacements.register(EntityRegistry.KOBOLD_WARRIOR_ENTITY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PathfinderMob::checkMobSpawnRules);

        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), MobCategory.MONSTER, EntityRegistry.CHUPACABRA_ENTITY, MythicMobsConfigs.chupacabraSpawnWeight, 1, 1);
        SpawnPlacements.register(EntityRegistry.CHUPACABRA_ENTITY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);

        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), MobCategory.CREATURE, EntityRegistry.DRAKE_ENTITY, MythicMobsConfigs.drakeSpawnWeight, 1, 4);
        SpawnPlacements.register(EntityRegistry.DRAKE_ENTITY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TamableAnimal::checkMobSpawnRules);

        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), MobCategory.CREATURE, EntityRegistry.MUSHROOM_ENTITY, MythicMobsConfigs.mushroomSpawnWeight, 3, 5);
        SpawnPlacements.register(EntityRegistry.MUSHROOM_ENTITY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PathfinderMob::checkMobSpawnRules);

//        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), MobCategory.CREATURE, EntityRegistry.BASILISK_ENTITY, MythicMobsConfigs.basiliskSpawnWeight, 1, 1);
//        SpawnPlacements.register(EntityRegistry.BASILISK_ENTITY, SpawnPlacements.Type.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PathfiderMob::canMobSpawn);
    }
}
