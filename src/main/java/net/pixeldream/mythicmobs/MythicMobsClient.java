package net.pixeldream.mythicmobs;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.pixeldream.mythicmobs.registry.EntityRegistry;
import net.pixeldream.mythicmobs.entity.client.*;
public class MythicMobsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(EntityRegistry.TROLL_ENTITY, TrollRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.KOBOLD_ENTITY, KoboldRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.WENDIGO_ENTITY, WendigoRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.AUTOMATON_ENTITY, AutomatonRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.WYVERN_ENTITY, WyvernRenderer::new);
    }
}
