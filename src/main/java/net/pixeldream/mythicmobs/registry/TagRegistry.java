package net.pixeldream.mythicmobs.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TagRegistry {

    public static class Blocks {
        public static final TagKey<Block> BRONZE_BLOCKS = createTag("bronze_blocks");

        private static TagKey<Block> createTag(String name) {
            return TagKey.create(Registries.BLOCK, new ResourceLocation("c", name));
        }
    }

    public static class Items {
        public static final TagKey<Item> PICKAXES = createTag("pickaxes");
        public static final TagKey<Item> BRONZE_INGOTS = createTag("bronze_ingots");

        private static TagKey<Item> createTag(String name) {
            return TagKey.create(Registries.ITEM, new ResourceLocation("c", name));
        }
    }
}
