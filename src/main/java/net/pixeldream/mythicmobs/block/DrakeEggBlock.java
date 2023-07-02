//package net.pixeldream.mythicmobs.block;
//
//import net.minecraft.block.*;
//import net.minecraft.block.entity.BlockEntity;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.server.world.ServerWorld;
//import net.minecraft.sound.SoundCategory;
//import net.minecraft.sound.SoundEvents;
//import net.minecraft.state.StateManager;
//import net.minecraft.state.property.IntProperty;
//import net.minecraft.state.property.Properties;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.Hand;
//import net.minecraft.util.function.BooleanBiFunction;
//import net.minecraft.util.hit.BlockHitResult;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.random.Random;
//import net.minecraft.util.shape.VoxelShape;
//import net.minecraft.util.shape.VoxelShapes;
//import net.minecraft.world.BlockView;
//import net.minecraft.world.World;
//import net.pixeldream.mythicmobs.block.entity.DrakeEggBlockEntity;
//import net.pixeldream.mythicmobs.entity.DrakeEntity;
//import net.pixeldream.mythicmobs.registry.EntityRegistry;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.stream.Stream;
//
//public class DrakeEggBlock extends BlockWithEntity {
//    public static final IntProperty HATCH_TIME;
//    private static final VoxelShape SHAPE_N = Stream.of(Block.createCuboidShape(5, 0, 5, 11, 8, 11), Block.createCuboidShape(4, 1, 6, 5, 2, 10), Block.createCuboidShape(11, 1, 6, 12, 2, 10), Block.createCuboidShape(6, 1, 4, 10, 2, 5), Block.createCuboidShape(5, 2, 4, 11, 7, 5), Block.createCuboidShape(5, 2, 11, 11, 7, 12), Block.createCuboidShape(6, 3, 12, 10, 6, 12.5), Block.createCuboidShape(6, 3, 3.5, 10, 6, 4), Block.createCuboidShape(6, 7, 4, 10, 8, 5), Block.createCuboidShape(6, 7, 11, 10, 8, 12), Block.createCuboidShape(6, 1, 11, 10, 2, 12), Block.createCuboidShape(4, 2, 5, 5, 7, 11), Block.createCuboidShape(12, 3, 6, 12.5, 6, 10), Block.createCuboidShape(3.5, 3, 6, 4, 6, 10), Block.createCuboidShape(11, 2, 5, 12, 7, 11), Block.createCuboidShape(4, 7, 6, 5, 8, 10), Block.createCuboidShape(11, 7, 6, 12, 8, 10), Block.createCuboidShape(5, 8, 5, 11, 9.2, 11), Block.createCuboidShape(6, 9, 6, 10, 10.5, 10), Block.createCuboidShape(7, 10, 7, 9, 11.7, 9)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
//
//    public DrakeEggBlock(Settings settings) {
//        super(settings);
//        this.setDefaultState((BlockState) ((BlockState) ((BlockState) this.stateManager.getDefaultState()).with(HATCH_TIME, 0)));
//    }
//
//    static {
//        HATCH_TIME = Properties.HATCH;
//    }
//
//    @Override
//    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
//        return SHAPE_N;
//    }
//
//    @Nullable
//    @Override
//    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
//        return new DrakeEggBlockEntity(pos, state);
//    }
//
//    @Override
//    public BlockRenderType getRenderType(BlockState state) {
//        return BlockRenderType.ENTITYBLOCK_ANIMATED;
//    }
//
//    @Override
//    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
//        world.playSound((PlayerEntity) null, pos, SoundEvents.ENTITY_TURTLE_EGG_HATCH, SoundCategory.BLOCKS, 0.7F, 0.9F);
//        world.removeBlock(pos, false);
////        world.syncWorldEvent(2001, pos, Block.getRawIdFromState(state));
//        DrakeEntity drakeEntity = EntityRegistry.DRAKE_ENTITY.create(world);
//        drakeEntity.setBreedingAge(-24000);
//        drakeEntity.refreshPositionAndAngles((double) pos.getX() + 0.3, (double) pos.getY(), (double) pos.getZ() + 0.3, 0.0F, 0.0F);
//        world.spawnEntity(drakeEntity);
//        return ActionResult.CONSUME;
//    }
//
//    private boolean shouldHatchProgress(World world) {
//        float f = world.getSkyAngle(1.0F);
//        if ((double) f < 0.69 && (double) f > 0.65) {
//            return true;
//        } else {
//            return world.random.nextInt(500) == 0;
//        }
//    }
//
//    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
//        builder.add(HATCH_TIME);
//    }
//
//    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
//        if (this.shouldHatchProgress(world)) {
//            int i = (Integer) state.get(HATCH_TIME);
//            world.playSound((PlayerEntity) null, pos, SoundEvents.ENTITY_TURTLE_EGG_HATCH, SoundCategory.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
//            world.removeBlock(pos, false);
//            world.syncWorldEvent(2001, pos, Block.getRawIdFromState(state));
//            DrakeEntity drakeEntity = (DrakeEntity) EntityRegistry.DRAKE_ENTITY.create(world);
//            drakeEntity.setBreedingAge(-24000);
//            drakeEntity.refreshPositionAndAngles((double) pos.getX() + 0.3, (double) pos.getY(), (double) pos.getZ() + 0.3, 0.0F, 0.0F);
//            world.spawnEntity(drakeEntity);
//        }
//
//    }
//}