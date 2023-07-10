package net.pixeldream.mythicmobs.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.world.Heightmap;
import net.pixeldream.mythicmobs.config.MythicMobsConfigs;
import net.pixeldream.mythicmobs.registry.EntityRegistry;

public class EntitySpawn {
    public static void setEntitySpawn() {
        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), SpawnGroup.CREATURE,
                EntityRegistry.KOBOLD_ENTITY, MythicMobsConfigs.koboldSpawnWeight, 3, 6);
        SpawnRestriction.register(EntityRegistry.KOBOLD_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                PathAwareEntity::canMobSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), SpawnGroup.CREATURE,
                EntityRegistry.KOBOLD_WARRIOR_ENTITY, MythicMobsConfigs.koboldWarriorSpawnWeight, 1, 3);
        SpawnRestriction.register(EntityRegistry.KOBOLD_WARRIOR_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                PathAwareEntity::canMobSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), SpawnGroup.MONSTER,
                EntityRegistry.CHUPACABRA_ENTITY, MythicMobsConfigs.chupacabraSpawnWeight, 1, 1);
        SpawnRestriction.register(EntityRegistry.CHUPACABRA_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                HostileEntity::canSpawnInDark);

        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), SpawnGroup.CREATURE,
                EntityRegistry.DRAKE_ENTITY, MythicMobsConfigs.drakeSpawnWeight, 1, 4);
        SpawnRestriction.register(EntityRegistry.DRAKE_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                TameableEntity::canMobSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), SpawnGroup.CREATURE,
                EntityRegistry.MUSHROOM_ENTITY, MythicMobsConfigs.mushroomSpawnWeight, 3, 5);
        SpawnRestriction.register(EntityRegistry.MUSHROOM_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                PathAwareEntity::canMobSpawn);
    }
}
