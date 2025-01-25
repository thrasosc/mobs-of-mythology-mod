package net.pixeldreamstudios.mobs_of_mythology.forge;

import dev.architectury.platform.forge.EventBuses;
import dev.architectury.utils.EnvExecutor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;

@Mod(MobsOfMythology.MOD_ID)
public class MobsOfMythologyForge {
        public MobsOfMythologyForge() {
                EventBuses.registerModEventBus(MobsOfMythology.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
                // Initialize the common mod
                MobsOfMythology.init();
                EnvExecutor.runInEnv(Dist.CLIENT, () -> MobsOfMythology::initClient);
        }
}
