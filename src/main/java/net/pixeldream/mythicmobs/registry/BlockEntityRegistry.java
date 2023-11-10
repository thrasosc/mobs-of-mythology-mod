package net.pixeldream.mythicmobs.registry;

import net.pixeldream.mythicmobs.MythicMobs;

public class BlockEntityRegistry {
//    public static BlockEntityType<RitualStoneBlockEntity> RITUAL_STONE_BLOCK_ENTITY;
//    public static BlockEntityType<DrakeEggBlockEntity> DRAKE_EGG_BLOCK_ENTITY;
//
//    private static BlockEntity registerBlockEntity(String name, BlockEntity blockEntity) {
//        return Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MythicMobs.MOD_ID, name), new BlockEntity(blockEntity, new FabricItemSettings()));
//    }

    public static void initialize() {
        MythicMobs.LOGGER.info("Registering block entities for " + MythicMobs.MOD_NAME);
    }
}
