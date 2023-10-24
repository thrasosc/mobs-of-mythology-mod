package net.pixeldream.mythicmobs.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class TagRegistry {

    public static class Blocks {
        public static final TagKey<Block> BRONZE_BLOCKS = createTag("bronze_blocks");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier("c", name));
        }
    }

    public static class Items {
        public static final TagKey<Item> PICKAXES = createTag("pickaxes");
        public static final TagKey<Item> BRONZE_INGOTS = createTag("bronze_ingots");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier("c", name));
        }
    }
}
