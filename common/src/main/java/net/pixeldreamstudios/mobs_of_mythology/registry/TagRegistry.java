package net.pixeldreamstudios.mobs_of_mythology.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;

public record TagRegistry() {
    // BLOCK TAGS
    public static final TagKey<Block> BRONZE_BLOCKS = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "bronze_blocks"));

    // ITEM TAGS
    public static final TagKey<Item> PICKAXES = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "pickaxes"));
    public static final TagKey<Item> BRONZE_INGOTS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "bronze_ingots"));
    public static final TagKey<Item> DRAKE_FOOD = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "drake_food"));

    // BIOME TAGS
    public static TagKey<Biome> HOT_BIOMES = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("c", "is_hot"));
    public static TagKey<Biome> WET_BIOMES = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("c", "is_wet"));
    public static TagKey<Biome> DRY_BIOMES = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("c", "is_dry"));
    public static TagKey<Biome> MUSHROOM_BIOMES = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("c", "is_mushroom"));
    public static TagKey<Biome> OVERWORLD_BIOMES = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("c", "is_overworld"));
}
