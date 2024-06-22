package net.pixeldreamstudios.mobs_of_mythology;

import mod.azure.azurelib.common.api.common.config.Config;
import mod.azure.azurelib.common.internal.common.config.Configurable;

@Config(id = MobsOfMythology.MOD_ID)
public class MobsOfMythologyConfig {
    @Configurable.Comment("Automaton")
    public double automatonHealth = 100.0;
    @Configurable
    public double automatonAttackDamage = 16.0;
}
