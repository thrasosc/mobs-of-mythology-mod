package net.pixeldreamstudios.mobs_of_mythology.neoforge;

import dev.architectury.utils.EnvExecutor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;

@Mod(MobsOfMythology.MOD_ID)
public final class MobsOfMythologyNeoForge {
    public MobsOfMythologyNeoForge() {
        MobsOfMythology.init();
        EnvExecutor.runInEnv(Dist.CLIENT, () -> MobsOfMythology::initClient);
    }
}

