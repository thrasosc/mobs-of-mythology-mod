package net.pixeldream.mythicmobs;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.pixeldream.mythicmobs.entity.client.renderer.entity.*;
import net.pixeldream.mythicmobs.registry.EntityRegistry;

@Environment(EnvType.CLIENT)
public class MythicMobsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(EntityRegistry.KOBOLD_ENTITY, KoboldRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.KOBOLD_WARRIOR_ENTITY, KoboldWarriorRenderer::new);
//        EntityRendererRegistry.register(EntityRegistry.WENDIGO_ENTITY, WendigoRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.AUTOMATON_ENTITY, AutomatonRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.CHUPACABRA_ENTITY, ChupacabraRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.DRAKE_ENTITY, DrakeRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.MUSHROOM_ENTITY, MushroomRenderer::new);
//        EntityRendererRegistry.register(EntityRegistry.BASILISK_ENTITY, BasiliskRenderer::new);

//        BlockEntityRendererRegistry.register(BlockEntityRegistry.RITUAL_STONE_BLOCK_ENTITY, RitualStoneRenderer::new);
//        BlockEntityRendererRegistry.register(BlockEntityRegistry.DRAKE_EGG_BLOCK_ENTITY, DrakeEggRenderer::new);
    }
}
