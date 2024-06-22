package net.pixeldreamstudios.mobs_of_mythology.neoforge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.DistExecutor;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;

@Mod(MobsOfMythology.MOD_ID)
public final class MobsOfMythologyNeoForge {
    public MobsOfMythologyNeoForge() {
        // Run our common setup.
        MobsOfMythology.init();
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> MobsOfMythology::initClient);
    }

//    @EventBusSubscriber(modid = MobsOfMythology.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
//    public static class ClientModEvents
//    {
//        @SubscribeEvent
//        public static void onClientSetup(FMLClientSetupEvent event)
//        {
//            MobsOfMythology.initClient();
//        }
//    }

}
