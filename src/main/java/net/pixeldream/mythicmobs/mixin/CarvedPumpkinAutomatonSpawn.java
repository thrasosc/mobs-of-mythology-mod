package net.pixeldream.mythicmobs.mixin;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.function.MaterialPredicate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pixeldream.mythicmobs.entity.AutomatonEntity;
import net.pixeldream.mythicmobs.registry.EntityRegistry;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(CarvedPumpkinBlock.class)
public abstract class CarvedPumpkinAutomatonSpawn {
//    @Nullable
//    private BlockPattern AutomatonDispenserPattern;
//    @Nullable
//    private BlockPattern AutomatonPattern;
//    private BlockPattern getAutomatonDispenserPattern() {
//        if (this.AutomatonDispenserPattern == null) {
//            this.AutomatonDispenserPattern = BlockPatternBuilder.start().aisle(new String[]{"~ ~", "###", "~#~"}).where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.COPPER_BLOCK))).where('~', CachedBlockPosition.matchesBlockState(MaterialPredicate.create(Material.AIR))).build();
//        }
//
//        return this.AutomatonDispenserPattern;
//    }
//
//    private BlockPattern getAutomatonPattern() {
//        if (this.AutomatonPattern == null) {
//            this.AutomatonPattern = BlockPatternBuilder.start().aisle(new String[]{"~^~", "###", "~#~"}).where('^', CachedBlockPosition.matchesBlockState(IS_GOLEM_HEAD_PREDICATE)).where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.COPPER_BLOCK))).where('~', CachedBlockPosition.matchesBlockState(MaterialPredicate.create(Material.AIR))).build();
//        }
//
//        return this.AutomatonPattern;
//    }
//    @Inject(
//            method = "trySpawnEntity(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
//            at = @At(value = "HEAD")
//    )
//    private void spawnAutomaton(World world, BlockPos pos) {
//        BlockPattern.Result result = this.getAutomatonDispenserPattern().searchAround(world, pos);
//        if (result != null) {
//            for(i = 0; i < this.getAutomatonPattern().getWidth(); ++i) {
//                for(int k = 0; k < this.getAutomatonPattern().getHeight(); ++k) {
//                    CachedBlockPosition cachedBlockPosition3 = result.translate(i, k, 0);
//                    world.setBlockState(cachedBlockPosition3.getBlockPos(), Blocks.AIR.getDefaultState(), 2);
//                    world.syncWorldEvent(2001, cachedBlockPosition3.getBlockPos(), Block.getRawIdFromState(cachedBlockPosition3.getBlockState()));
//                }
//            }
//
//            BlockPos blockPos2 = result.translate(1, 2, 0).getBlockPos();
//            AutomatonEntity automatonEntity = (AutomatonEntity) EntityRegistry.AUTOMATON_ENTITY.create(world);
//            automatonEntity.setPlayerCreated(true);
//            automatonEntity.refreshPositionAndAngles((double)blockPos2.getX() + 0.5, (double)blockPos2.getY() + 0.05, (double)blockPos2.getZ() + 0.5, 0.0F, 0.0F);
//            world.spawnEntity(automatonEntity);
//            var6 = world.getNonSpectatingEntities(ServerPlayerEntity.class, automatonEntity.getBoundingBox().expand(5.0)).iterator();
//
//            while(var6.hasNext()) {
//                serverPlayerEntity = (ServerPlayerEntity)var6.next();
//                Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity, automatonEntity);
//            }
//
//            for(j = 0; j < this.getAutomatonPattern().getWidth(); ++j) {
//                for(int l = 0; l < this.getAutomatonPattern().getHeight(); ++l) {
//                    CachedBlockPosition cachedBlockPosition4 = result.translate(j, l, 0);
//                    world.updateNeighbors(cachedBlockPosition4.getBlockPos(), Blocks.AIR);
//                }
//            }
//        }
//    }
}
