package net.pixeldream.mythicmobs.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.AutomatonEntity;
import net.pixeldream.mythicmobs.entity.KoboldEntity;
import net.pixeldream.mythicmobs.entity.TrollEntity;
import net.pixeldream.mythicmobs.entity.WendigoEntity;

public class EntityRegistry {
    public EntityRegistry() {
        FabricDefaultAttributeRegistry.register(TROLL_ENTITY, TrollEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(KOBOLD_ENTITY, KoboldEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(WENDIGO_ENTITY, WendigoEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(AUTOMATON_ENTITY, AutomatonEntity.setAttributes());
    }
    public static final EntityType<TrollEntity> TROLL_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(MythicMobs.MOD_ID, "troll"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, TrollEntity::new).dimensions(EntityDimensions.fixed(3.0f,5.0f)).build()
    );
    public static final EntityType<KoboldEntity> KOBOLD_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(MythicMobs.MOD_ID, "kobold"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, KoboldEntity::new).dimensions(EntityDimensions.fixed(0.75f,1.75f)).build()
    );

    public static final EntityType<WendigoEntity> WENDIGO_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(MythicMobs.MOD_ID, "wendigo"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, WendigoEntity::new).dimensions(EntityDimensions.fixed(3.5f,4.0f)).build()
    );

    public static final EntityType<AutomatonEntity> AUTOMATON_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(MythicMobs.MOD_ID, "automaton"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, AutomatonEntity::new).dimensions(EntityDimensions.fixed(1.5f,3.0f)).build()
    );
}
