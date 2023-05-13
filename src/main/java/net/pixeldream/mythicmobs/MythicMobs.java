package net.pixeldream.mythicmobs;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.pixeldream.mythicmobs.config.CommonConfigs;
import net.pixeldream.mythicmobs.event.EntityEvents;
import net.pixeldream.mythicmobs.registry.EntityRegistry;
import net.pixeldream.mythicmobs.registry.ItemRegistry;
import net.pixeldream.mythicmobs.registry.ParticleRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;

import static net.pixeldream.mythicmobs.registry.ItemRegistry.SPEAR;

public class MythicMobs implements ModInitializer {
	public static final String MOD_ID = "mythicmobs";
	public static final String MOD_NAME = "Mythic Mobs";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(MOD_ID, "item_group"), () -> new ItemStack(SPEAR));

	@Override
	public void onInitialize() {
		LOGGER.info("Hello from " + MOD_NAME + "!");
		LOGGER.info("Initializing GeckoLib for " + MOD_NAME);
		GeckoLib.initialize();
		LOGGER.info("Initializing MidnightConfig for " + MOD_NAME);
		MidnightConfig.init(MOD_ID, CommonConfigs.class);
		LOGGER.info("Registering entities for " + MOD_NAME);
		new EntityRegistry();
		LOGGER.info("Registering particles for " + MOD_NAME);
		new ParticleRegistry();
		LOGGER.info("Registering items for " + MOD_NAME);
		new ItemRegistry();
		LOGGER.info("Replacing Iron Golems with Automata from " + MOD_NAME);
		EntityEvents.replaceNaturallySpawningIronGolemsWithClayGolems();
		EntityEvents.checkForUnSpawnedGolem();

	}
}