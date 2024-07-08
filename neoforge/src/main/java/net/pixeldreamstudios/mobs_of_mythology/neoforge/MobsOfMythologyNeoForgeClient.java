package net.pixeldreamstudios.mobs_of_mythology.neoforge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;

@Mod(value = MobsOfMythology.MOD_ID, dist = Dist.CLIENT)
public class MobsOfMythologyNeoForgeClient {
    public MobsOfMythologyNeoForgeClient(IEventBus modBus) {
        MobsOfMythology.initClient();
    }
}
