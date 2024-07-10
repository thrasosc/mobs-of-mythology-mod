package net.pixeldreamstudios.mobs_of_mythology.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(MobsOfMythology.MOD_ID, Registries.BLOCK);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(MobsOfMythology.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Block> BRONZE_BLOCK = BLOCKS.register("bronze_block", () ->
            new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.BASS)
                    .requiresCorrectToolForDrops()
                    .strength(6.0F, 6.0F)
                    .sound(SoundType.METAL)));
    public static final RegistrySupplier<Item> BRONZE_BLOCK_ITEM = BLOCK_ITEMS.register(BRONZE_BLOCK.getId(), () -> new BlockItem(BRONZE_BLOCK.get(), new Item.Properties().arch$tab(TabRegistry.MOBS_OF_MYTHOLOGY_TAB)));

    public static final RegistrySupplier<Block> CUT_BRONZE_BLOCK = BLOCKS.register("cut_bronze_block", () ->
            new RotatedPillarBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .instrument(NoteBlockInstrument.BASS)
                    .requiresCorrectToolForDrops()
                    .strength(6.0F, 6.0F)
                    .sound(SoundType.METAL)));
    public static final RegistrySupplier<Item> CUT_BRONZE_BLOCK_ITEM = BLOCK_ITEMS.register(CUT_BRONZE_BLOCK.getId(), () -> new BlockItem(CUT_BRONZE_BLOCK.get(), new Item.Properties().arch$tab(TabRegistry.MOBS_OF_MYTHOLOGY_TAB)));

    public static void init() {
        BLOCKS.register();
        BLOCK_ITEMS.register();
    }
}
