package net.pixeldream.mythicmobs.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.*;

public class EntityRegistry {

    public static final EntityType<KoboldEntity> KOBOLD_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MythicMobs.MOD_ID, "kobold"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, KoboldEntity::new).dimensions(EntityDimensions.fixed(0.75f,1.75f)).build()
    );
    public static final EntityType<KoboldWarriorEntity> KOBOLD_WARRIOR_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MythicMobs.MOD_ID, "kobold_warrior"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, KoboldWarriorEntity::new).dimensions(EntityDimensions.fixed(0.75f,1.75f)).build()
    );
//    public static final EntityType<WendigoEntity> WENDIGO_ENTITY = Registry.register(
//            Registries.ENTITY_TYPE,
//            new Identifier(MythicMobs.MOD_ID, "wendigo"),
//            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, WendigoEntity::new).dimensions(EntityDimensions.fixed(3.5f,4.0f)).build()
//    );
    public static final EntityType<AutomatonEntity> AUTOMATON_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MythicMobs.MOD_ID, "automaton"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, AutomatonEntity::new).dimensions(EntityDimensions.fixed(1.5f,3.0f)).build()
    );
    public static final EntityType<ChupacabraEntity> CHUPACABRA_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MythicMobs.MOD_ID, "chupacabra"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ChupacabraEntity::new).dimensions(EntityDimensions.fixed(1.25f,1.0f)).build()
    );
    public static final EntityType<DrakeEntity> DRAKE_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MythicMobs.MOD_ID, "drake"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DrakeEntity::new).dimensions(EntityDimensions.fixed(1.25f,1.0f)).build()
    );
    public static final EntityType<MushroomEntity> MUSHROOM_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MythicMobs.MOD_ID, "mushroom_thing"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MushroomEntity::new).dimensions(EntityDimensions.fixed(1.0f,0.8f)).build()
    );
    public static final EntityType<BasiliskEntity> BASILISK_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MythicMobs.MOD_ID, "basilisk"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, BasiliskEntity::new).dimensions(EntityDimensions.fixed(1.75f,1.2f)).build()
    );

    public static void initialize() {
        MythicMobs.LOGGER.info("Registering entities for " + MythicMobs.MOD_NAME);
        FabricDefaultAttributeRegistry.register(KOBOLD_ENTITY, KoboldEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(KOBOLD_WARRIOR_ENTITY, KoboldWarriorEntity.setAttributes());
//        FabricDefaultAttributeRegistry.register(WENDIGO_ENTITY, WendigoEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(AUTOMATON_ENTITY, AutomatonEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(CHUPACABRA_ENTITY, ChupacabraEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(DRAKE_ENTITY, DrakeEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(MUSHROOM_ENTITY, MushroomEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(BASILISK_ENTITY, BasiliskEntity.setAttributes());
    }
}
