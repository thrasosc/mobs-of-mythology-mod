package net.pixeldream.mythicmobs.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.block.entity.RitualStoneBlockEntity;

public class BlockEntityRegistry {
    public static BlockEntityType<RitualStoneBlockEntity> RITUAL_STONE_BLOCK_ENTITY;

    public BlockEntityRegistry() {
        RITUAL_STONE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MythicMobs.MOD_ID, "ritual_stone_block_entity"), FabricBlockEntityTypeBuilder.create(RitualStoneBlockEntity::new, BlockRegistry.RITUAL_STONE_BLOCK).build(null));
    }
}
