package net.pixeldream.mythicmobs.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.pixeldream.mythicmobs.MythicMobs;

public class ParticleRegistry {
    public static final DefaultParticleType BLOOD_PARTICLE = FabricParticleTypes.simple();

    public static void initialize() {
        MythicMobs.LOGGER.info("Registering particles for " + MythicMobs.MOD_NAME);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(MythicMobs.MOD_ID, "blood_particle"), BLOOD_PARTICLE);
    }
}
