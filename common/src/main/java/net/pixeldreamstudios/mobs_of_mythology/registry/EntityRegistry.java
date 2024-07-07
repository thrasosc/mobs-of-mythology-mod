package net.pixeldreamstudios.mobs_of_mythology.registry;

import dev.architectury.registry.level.biome.BiomeModifications;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.*;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(MobsOfMythology.MOD_ID, Registries.ENTITY_TYPE);
    public static final RegistrySupplier<EntityType<AutomatonEntity>> AUTOMATON = ENTITIES.register("automaton", () -> EntityType.Builder.of(AutomatonEntity::new, MobCategory.MISC)
            .sized(1.5f,3.0f)
            .build(new ResourceLocation(MobsOfMythology.MOD_ID, "automaton").toString()));
    public static final RegistrySupplier<EntityType<ChupacabraEntity>> CHUPACABRA = ENTITIES.register("chupacabra", () -> EntityType.Builder.of(ChupacabraEntity::new, MobCategory.MISC)
            .sized(1.25f,1.0f)
            .build(new ResourceLocation(MobsOfMythology.MOD_ID, "chupacabra").toString()));
    public static final RegistrySupplier<EntityType<KoboldEntity>> KOBOLD = ENTITIES.register("kobold", () -> EntityType.Builder.of(KoboldEntity::new, MobCategory.MISC)
            .sized(0.75f,1.75f)
            .build(new ResourceLocation(MobsOfMythology.MOD_ID, "kobold").toString()));
    public static final RegistrySupplier<EntityType<KoboldWarriorEntity>> KOBOLD_WARRIOR = ENTITIES.register("kobold_warrior", () -> EntityType.Builder.of(KoboldWarriorEntity::new, MobCategory.MISC)
            .sized(0.75f,1.75f)
            .build(new ResourceLocation(MobsOfMythology.MOD_ID, "kobold_warrior").toString()));
    public static final RegistrySupplier<EntityType<DrakeEntity>> DRAKE = ENTITIES.register("drake", () -> EntityType.Builder.of(DrakeEntity::new, MobCategory.MISC)
            .sized(1.25f,1.0f)
            .build(new ResourceLocation(MobsOfMythology.MOD_ID, "drake").toString()));

    private static void initSpawns() {
        BiomeModifications.addProperties(b -> b.hasTag(TagRegistry.DESERT_BIOMES), (ctx, b) -> b.getSpawnProperties().addSpawn(MobCategory.MISC, new MobSpawnSettings.SpawnerData(AUTOMATON.get(), 80, 1, 1)));
    }

    private static void initAttributes() {
        EntityAttributeRegistry.register(AUTOMATON, AutomatonEntity::createAttributes);
        EntityAttributeRegistry.register(CHUPACABRA, ChupacabraEntity::createAttributes);
        EntityAttributeRegistry.register(CHUPACABRA, ChupacabraEntity::createAttributes);
        EntityAttributeRegistry.register(KOBOLD, KoboldEntity::createAttributes);
        EntityAttributeRegistry.register(KOBOLD_WARRIOR, KoboldWarriorEntity::createAttributes);
        EntityAttributeRegistry.register(DRAKE, DrakeEntity::createAttributes);
    }

    public static void init() {
        ENTITIES.register();
        initAttributes();
        initSpawns();
    }

}
