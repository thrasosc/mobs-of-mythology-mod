package net.pixeldreamstudios.mobs_of_mythology;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import mod.azure.azurelib.common.internal.common.AzureLib;
import mod.azure.azurelib.common.internal.common.AzureLibMod;
import mod.azure.azurelib.common.internal.common.config.format.ConfigFormats;
import net.pixeldreamstudios.mobs_of_mythology.entity.client.renderer.*;
import net.pixeldreamstudios.mobs_of_mythology.registry.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MobsOfMythology {
    public static final String MOD_ID = "mobs_of_mythology";
    public static MobsOfMythologyConfig config;
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        AzureLib.initialize();
        config = AzureLibMod.registerConfig(MobsOfMythologyConfig.class, ConfigFormats.properties()).getConfigInstance();
        SoundRegistry.init();
        EntityRegistry.init();
        ItemRegistry.init();
        BlockRegistry.init();
        TabRegistry.init();
    }

    public static void initClient() {
        EntityRendererRegistry.register(EntityRegistry.AUTOMATON, AutomatonRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.CHUPACABRA, ChupacabraRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.KOBOLD, KoboldRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.KOBOLD_WARRIOR, KoboldWarriorRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.DRAKE, DrakeRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.SPORELING, SporelingRenderer::new);
    }
}
