package net.pixeldream.mythicmobs.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.pixeldream.mythicmobs.MythicMobs;

public class ParticleRegistry {
    public static final DefaultParticleType BLOOD_PARTICLE = FabricParticleTypes.simple();

    public ParticleRegistry() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(MythicMobs.MOD_ID, "blood_particle"),
                BLOOD_PARTICLE);
    }
}
