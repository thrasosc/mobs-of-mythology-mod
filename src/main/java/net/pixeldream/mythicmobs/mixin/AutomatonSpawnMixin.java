package net.pixeldream.mythicmobs.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.pixeldream.mythicmobs.entity.mobs.AutomatonEntity;
import net.pixeldream.mythicmobs.registry.BlockRegistry;
import net.pixeldream.mythicmobs.registry.EntityRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(CarvedPumpkinBlock.class)
public class AutomatonSpawnMixin {
    @Shadow
    @Final
    private static Predicate<BlockState> PUMPKINS_PREDICATE;
    @Shadow
    private static void spawnGolemInWorld(Level level, BlockPattern.BlockPatternMatch blockPatternMatch, Entity entity, BlockPos blockPos) {}
    private BlockPattern automatonFull;

    private BlockPattern getOrCreateAutomatonFull() {
        if (this.automatonFull == null) {
            this.automatonFull = BlockPatternBuilder.start().aisle(new String[]{"~^~", "###", "~#~"}).where('^', BlockInWorld.hasState(PUMPKINS_PREDICATE)).where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(BlockRegistry.BRONZE_BLOCK))).where('~', (blockInWorld) -> {
                return blockInWorld.getState().isAir();
            }).build();
        }
        return this.automatonFull;
    }

    @Inject(at = @At("TAIL"), method = "Lnet/minecraft/world/level/block/CarvedPumpkinBlock;trySpawnGolem(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V")
    private void checkAutomatonSpawn(Level level, BlockPos blockPos, CallbackInfo ci) {
        AutomatonEntity automatonEntity = (AutomatonEntity) EntityRegistry.AUTOMATON_ENTITY.create(level);
        BlockPattern.BlockPatternMatch blockPatternMatch3 = this.getOrCreateAutomatonFull().find(level, blockPos);
        if (blockPatternMatch3 != null) {
            spawnGolemInWorld(level, blockPatternMatch3, automatonEntity, blockPatternMatch3.getBlock(1, 2, 0).getPos());
        }
    }
}