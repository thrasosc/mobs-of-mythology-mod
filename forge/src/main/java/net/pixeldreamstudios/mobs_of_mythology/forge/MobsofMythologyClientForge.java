package net.pixeldreamstudios.mobs_of_mythology.forge;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythologyConfig;
import net.pixeldreamstudios.mobs_of_mythology.entity.client.renderer.*;
import net.pixeldreamstudios.mobs_of_mythology.registry.EntityRegistry;

@Mod.EventBusSubscriber(modid = MobsOfMythology.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MobsofMythologyClientForge {


    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        MobsOfMythology.initClient();
    }
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.AUTOMATON.get(), AutomatonRenderer::new);
        event.registerEntityRenderer(EntityRegistry.KOBOLD.get(), KoboldRenderer::new);
        event.registerEntityRenderer(EntityRegistry.CHUPACABRA.get(), ChupacabraRenderer::new);
        event.registerEntityRenderer(EntityRegistry.DRAKE.get(), DrakeRenderer::new);
        event.registerEntityRenderer(EntityRegistry.SPORELING.get(), SporelingRenderer::new);
        event.registerEntityRenderer(EntityRegistry.KOBOLD_WARRIOR.get(), KoboldWarriorRenderer::new);
    }

}
