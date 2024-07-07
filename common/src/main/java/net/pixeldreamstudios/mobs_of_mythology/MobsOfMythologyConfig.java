package net.pixeldreamstudios.mobs_of_mythology;

import mod.azure.azurelib.common.api.common.config.Config;
import mod.azure.azurelib.common.internal.common.config.Configurable;

@Config(id = MobsOfMythology.MOD_ID)
public class MobsOfMythologyConfig {
    @Configurable.Comment("Automaton")
    public double automatonHealth = 100.0;
    @Configurable
    public double automatonAttackDamage = 16.0;

    @Configurable.Comment("Chupacabra")
    public double chupacabraHealth = 16.0;
    @Configurable
    public double chupacabraAttackDamage = 6.0;

    @Configurable.Comment("Kobold")
    public double koboldHealth = 20.0;
    @Configurable
    public double koboldAttackDamage = 2.5;

    @Configurable.Comment("Kobold Warrior")
    public double koboldWarriorHealth = 20.0;
    @Configurable
    public double koboldWarriorArmor = 6.0;
    @Configurable
    public double koboldWarriorAttackDamage = 5.5;
}
