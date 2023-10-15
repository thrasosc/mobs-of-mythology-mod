package net.pixeldream.mythicmobs.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.pixeldream.mythicmobs.MythicMobs;

import static net.pixeldream.mythicmobs.MythicMobs.MOD_ID;

public class SoundRegistry {
    public static SoundEvent DRAKE_ROAR = registerSoundEvent("drake_roar");
    public static SoundEvent DRAKE_DEATH = registerSoundEvent("drake_death");
    public static SoundEvent WENDIGO_ROAR = registerSoundEvent("wendigo_roar");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void initialize() {
        MythicMobs.LOGGER.info("Registering sounds for " + MythicMobs.MOD_NAME);
    }
}
