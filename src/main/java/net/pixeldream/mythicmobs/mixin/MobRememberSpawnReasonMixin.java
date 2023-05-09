package net.pixeldream.mythicmobs.mixin;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.pixeldream.mythicmobs.util.IMobRememberSpawnReason;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class MobRememberSpawnReasonMixin implements IMobRememberSpawnReason {
    @Unique
    private SpawnReason mythicmobs_mobSpawnType;

    @Override
    public SpawnReason getMobSpawnType(){
        return mythicmobs_mobSpawnType;
    }

    @Inject(
            method = "initialize(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/world/LocalDifficulty;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/EntityData;Lnet/minecraft/nbt/NbtCompound;)Lnet/minecraft/entity/EntityData;",
            at = @At(value = "HEAD")
    )
    private void mythicmobs_finalizeSpawn(ServerWorldAccess serverLevelAccessor, LocalDifficulty difficulty, SpawnReason spawnType, EntityData spawnGroupData, NbtCompound tag, CallbackInfoReturnable<EntityData> cir){
        this.mythicmobs_mobSpawnType = spawnType;
    }
}
