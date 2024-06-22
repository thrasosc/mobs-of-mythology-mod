package net.pixeldreamstudios.mobs_of_mythology.registry;

import dev.architectury.platform.Platform;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class TagRegistry {
    public static final TagKey<Block> BRONZE_BLOCKS = TagKey.create(Registries.BLOCK, new ResourceLocation(Platform.isFabric() ? "c" : "forge", Platform.isFabric() ? "bronze_blocks" : "bronze"));

    public static final TagKey<Item> PICKAXES = TagKey.create(Registries.ITEM, new ResourceLocation(Platform.isFabric() ? "c" : "forge", "pickaxes"));
    public static final TagKey<Item> BRONZE_INGOTS = TagKey.create(Registries.ITEM, new ResourceLocation(Platform.isFabric() ? "c" : "forge", Platform.isFabric() ? "bronze_ingots" : "bronze"));

    public static TagKey<Biome> DESERT_BIOMES = TagKey.create(Registries.BIOME, new ResourceLocation(Platform.isFabric() ? "c" : "forge", Platform.isFabric() ? "desert" : "is_desert"));
}
