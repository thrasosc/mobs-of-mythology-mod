package net.pixeldreamstudios.mobs_of_mythology.registry;

import dev.architectury.registry.level.biome.BiomeModifications;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.level.entity.SpawnPlacementsRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.mobs.*;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(MobsOfMythology.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<AutomatonEntity>> AUTOMATON = ENTITIES.register("automaton", () ->
            EntityType.Builder.of(AutomatonEntity::new, MobCategory.CREATURE)
                    .sized(1.5f, 2.9f)
                    .build(new ResourceLocation(MobsOfMythology.MOD_ID, "automaton").toString()));

    public static final RegistrySupplier<EntityType<ChupacabraEntity>> CHUPACABRA = ENTITIES.register("chupacabra", () ->
            EntityType.Builder.of(ChupacabraEntity::new, MobCategory.MONSTER)
                    .sized(1.25f, 1.0f)
                    .build(new ResourceLocation(MobsOfMythology.MOD_ID, "chupacabra").toString()));

    public static final RegistrySupplier<EntityType<KoboldEntity>> KOBOLD = ENTITIES.register("kobold", () ->
            EntityType.Builder.of(KoboldEntity::new, MobCategory.MONSTER)
                    .sized(0.75f, 1.75f)
                    .build(new ResourceLocation(MobsOfMythology.MOD_ID, "kobold").toString()));

    public static final RegistrySupplier<EntityType<KoboldWarriorEntity>> KOBOLD_WARRIOR = ENTITIES.register("kobold_warrior", () ->
            EntityType.Builder.of(KoboldWarriorEntity::new, MobCategory.MONSTER)
                    .sized(0.75f, 1.75f)
                    .build(new ResourceLocation(MobsOfMythology.MOD_ID, "kobold_warrior").toString()));

    public static final RegistrySupplier<EntityType<DrakeEntity>> DRAKE = ENTITIES.register("drake", () ->
            EntityType.Builder.of(DrakeEntity::new, MobCategory.CREATURE)
                    .sized(1.25f, 1.0f)
                    .build(new ResourceLocation(MobsOfMythology.MOD_ID, "drake").toString()));

    public static final RegistrySupplier<EntityType<SporelingEntity>> SPORELING = ENTITIES.register("sporeling", () ->
            EntityType.Builder.of(SporelingEntity::new, MobCategory.CREATURE)
                    .sized(1.0f, 0.8f)
                    .build(new ResourceLocation(MobsOfMythology.MOD_ID, "sporeling").toString()));

    private static void initSpawns() {
        SpawnPlacementsRegistry.register(EntityRegistry.KOBOLD, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, KoboldEntity::checkAnyLightMonsterSpawnRules);
        BiomeModifications.addProperties(b -> b.hasTag(TagRegistry.WET_BIOMES), (ctx, b) -> b.getSpawnProperties().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(KOBOLD.get(), MobsOfMythology.config.koboldSpawnWeight, 2, 4)));

        SpawnPlacementsRegistry.register(EntityRegistry.KOBOLD_WARRIOR, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, KoboldWarriorEntity::checkAnyLightMonsterSpawnRules);
        BiomeModifications.addProperties(b -> b.hasTag(TagRegistry.WET_BIOMES), (ctx, b) -> b.getSpawnProperties().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(KOBOLD_WARRIOR.get(), MobsOfMythology.config.koboldWarriorSpawnWeight, 2, 3)));

        SpawnPlacementsRegistry.register(EntityRegistry.DRAKE, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DrakeEntity::checkMobSpawnRules);
        BiomeModifications.addProperties(b -> b.hasTag(TagRegistry.BADLANDS_BIOMES), (ctx, b) -> b.getSpawnProperties().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(DRAKE.get(), MobsOfMythology.config.drakeSpawnWeight, 1, 1)));

        SpawnPlacementsRegistry.register(EntityRegistry.CHUPACABRA, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ChupacabraEntity::checkAnyLightMonsterSpawnRules);
        BiomeModifications.addProperties(b -> b.hasTag(TagRegistry.TEMPERATE_BIOMES), (ctx, b) -> b.getSpawnProperties().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(CHUPACABRA.get(), MobsOfMythology.config.chupacabraSpawnWeight, 1, 1)));

        SpawnPlacementsRegistry.register(EntityRegistry.SPORELING, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SporelingEntity::checkMobSpawnRules);
        BiomeModifications.addProperties(b -> b.hasTag(TagRegistry.MUSHROOM_BIOMES), (ctx, b) -> b.getSpawnProperties().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(SPORELING.get(), MobsOfMythology.config.sporelingSpawnWeight, 4, 6)));
    }

    private static void initAttributes() {
        EntityAttributeRegistry.register(AUTOMATON, AutomatonEntity::createAttributes);
        EntityAttributeRegistry.register(CHUPACABRA, ChupacabraEntity::createAttributes);
        EntityAttributeRegistry.register(CHUPACABRA, ChupacabraEntity::createAttributes);
        EntityAttributeRegistry.register(KOBOLD, KoboldEntity::createAttributes);
        EntityAttributeRegistry.register(KOBOLD_WARRIOR, KoboldWarriorEntity::createAttributes);
        EntityAttributeRegistry.register(DRAKE, DrakeEntity::createAttributes);
        EntityAttributeRegistry.register(SPORELING, SporelingEntity::createAttributes);
    }

    public static void init() {
        ENTITIES.register();
        initAttributes();
        initSpawns();
    }

}
