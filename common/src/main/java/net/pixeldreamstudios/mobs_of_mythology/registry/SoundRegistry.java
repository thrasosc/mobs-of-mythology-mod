package net.pixeldreamstudios.mobs_of_mythology.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(MobsOfMythology.MOD_ID, Registries.SOUND_EVENT);
    public static final RegistrySupplier<SoundEvent> DRAKE_ROAR = SOUND_EVENTS.register("drake_roar", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "drake_roar")));
    public static final RegistrySupplier<SoundEvent> DRAKE_DEATH = SOUND_EVENTS.register("drake_death", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "drake_death")));
    public static final RegistrySupplier<SoundEvent> ROBOTIC_VOICE = SOUND_EVENTS.register("robotic_voice", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "robotic_voice")));

    public static void init() {
        SOUND_EVENTS.register();
    }
}
