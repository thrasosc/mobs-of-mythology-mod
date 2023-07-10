package net.pixeldream.mythicmobs.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.IronGolemEntity;
import net.pixeldream.mythicmobs.config.MythicMobsConfigs;
import net.pixeldream.mythicmobs.entity.AutomatonEntity;
import net.pixeldream.mythicmobs.registry.EntityRegistry;
import net.pixeldream.mythicmobs.util.IMobRememberSpawnReason;

import java.util.LinkedList;
import java.util.Queue;

public class EntityEvents {
    private static final Queue<AutomatonEntity> AUTOMATON_ENTITIES = new LinkedList<>();
    public static void replaceNaturallySpawningIronGolemsWithAutomata() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {
            if(!MythicMobsConfigs.replaceIronGolems) return;
            if(entity instanceof IronGolemEntity ironGolem && entity.getClass() == IronGolemEntity.class && ((IMobRememberSpawnReason)ironGolem).getMobSpawnType() != SpawnReason.COMMAND && !ironGolem.isPlayerCreated()) {
                AutomatonEntity automatonEntity = EntityRegistry.AUTOMATON_ENTITY.create(level);
                if(automatonEntity == null) return;
                automatonEntity.refreshPositionAfterTeleport(ironGolem.getPos());
                AUTOMATON_ENTITIES.add(automatonEntity);
                ironGolem.discard();
            }
        });
    }

    public static void checkForUnSpawnedGolem() {
        ServerTickEvents.END_WORLD_TICK.register(level -> {
            if(!MythicMobsConfigs.replaceIronGolems) return;
            Queue<AutomatonEntity> automatonEntityQueue = new LinkedList<>();
            for(AutomatonEntity automatonEntity : AUTOMATON_ENTITIES) {
                level.spawnEntity(automatonEntity);
                automatonEntityQueue.add(automatonEntity);
            }
            AUTOMATON_ENTITIES.removeAll(automatonEntityQueue);
        });
    }
}
