package net.pixeldreamstudios.mobs_of_mythology.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public record TagRegistry() {
    // BLOCK TAGS
    public static final TagKey<Block> BRONZE_BLOCKS = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "bronze_blocks"));

    // ITEM TAGS
    public static final TagKey<Item> PICKAXES = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "pickaxes"));
    public static final TagKey<Item> BRONZE_INGOTS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "bronze_ingots"));

    // MOB BIOME SPAWN TAGS
    public static TagKey<Biome> KOBOLD_BIOMES = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("mobs_of_mythology", "kobolds_spawn_in"));
    public static TagKey<Biome> DRAKE_BIOMES = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("mobs_of_mythology", "drakes_spawn_in"));
    public static TagKey<Biome> CHUPACABRA_BIOMES = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("mobs_of_mythology", "chupacabras_spawn_in"));
    public static TagKey<Biome> SPORELING_BIOMES = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("mobs_of_mythology", "sporelings_spawn_in"));
}
