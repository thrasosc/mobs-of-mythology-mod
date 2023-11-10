package net.pixeldream.mythicmobs.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.pixeldream.mythicmobs.MythicMobs;

import static net.pixeldream.mythicmobs.MythicMobs.MOD_ID;

public class SoundRegistry {
    public static SoundEvent DRAKE_ROAR = registerSoundEvent("drake_roar");
    public static SoundEvent DRAKE_DEATH = registerSoundEvent("drake_death");
    public static SoundEvent WENDIGO_ROAR = registerSoundEvent("wendigo_roar");
    public static SoundEvent ROBOTIC_VOICE = registerSoundEvent("robotic_voice");

    private static SoundEvent registerSoundEvent(String name) {
        ResourceLocation id = new ResourceLocation(MOD_ID, name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    public static void initialize() {
        MythicMobs.LOGGER.info("Registering sounds for " + MythicMobs.MOD_NAME);
    }
}
