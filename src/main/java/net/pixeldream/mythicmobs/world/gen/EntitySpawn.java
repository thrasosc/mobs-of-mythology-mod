package net.pixeldream.mythicmobs.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;
import net.pixeldream.mythicmobs.entity.AbstractKoboldEntity;
import net.pixeldream.mythicmobs.entity.KoboldEntity;
import net.pixeldream.mythicmobs.entity.KoboldWarriorEntity;
import net.pixeldream.mythicmobs.registry.EntityRegistry;

public class EntitySpawn {
    public static void setEntitySpawn() {
        BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.CREATURE,
                EntityRegistry.KOBOLD_ENTITY, 50, 3, 6);
        SpawnRestriction.register(EntityRegistry.KOBOLD_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                KoboldEntity::canMobSpawn);
        BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.CREATURE,
                EntityRegistry.KOBOLD_WARRIOR_ENTITY, 50, 1, 3);
        SpawnRestriction.register(EntityRegistry.KOBOLD_WARRIOR_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                KoboldWarriorEntity::canMobSpawn);
    }
}
