package net.pixeldreamstudios.mobs_of_mythology.forge;

import dev.architectury.platform.forge.EventBuses;
import dev.architectury.utils.EnvExecutor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;

@Mod(MobsOfMythology.MOD_ID)
public class MobsOfMythologyForge {
        public MobsOfMythologyForge() {
                IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
                EventBuses.registerModEventBus(MobsOfMythology.MOD_ID, modEventBus);
                modEventBus.register(this);
                modEventBus.addListener(this::onClientSetup);
                MobsOfMythology.init();
        }
        private void onClientSetup(final FMLClientSetupEvent event) {
                MobsOfMythology.initClient();
        }
}
