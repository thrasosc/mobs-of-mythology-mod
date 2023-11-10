package net.pixeldream.mythicmobs.entity.mobs;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.pixeldream.mythicmobs.entity.MythicEntity;

public abstract class AbstractKoboldEntity extends MythicEntity implements Enemy {
    protected static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(KoboldWarriorEntity.class, EntityDataSerializers.INT);
    protected int placeTorchCooldown = 0;
    protected AbstractKoboldEntity(EntityType<? extends PathfinderMob> entityType, Level world, int XP) {
        super(entityType, world);
        this.xpReward = XP;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
    }

    protected int getTypeVariant() {
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }

    protected abstract <T> T getVariant();

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.entityData.set(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));
    }

    @Override
    public void tick() {
        super.tick();
//        if (getEntityWorld().isNight()) {
//            if (!getStackInHand(Hand.OFF_HAND).isEmpty()) {
//                if (placeTorchCooldown == 30) {
//                    MythicMobs.LOGGER.info("PLACING TORCH");
//                    placeTorchCooldown = 0;
//                }
//            }
//            else {
//                MythicMobs.LOGGER.info("EQUIPPED TORCH");
//                setStackInHand(Hand.OFF_HAND, new ItemStack(Items.TORCH, 64));
//            }
//        }
//        else if (getEntityWorld().isDay() && this.getStackInHand(Hand.OFF_HAND).isOf(Items.TORCH)) {
//            setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);
//        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        this.playSound(SoundEvents.PILLAGER_AMBIENT, 1.0f, 3.0f);
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        this.playSound(SoundEvents.PILLAGER_HURT, 1.0f, 3.0f);
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        this.playSound(SoundEvents.PILLAGER_DEATH, 1.0f, 3.0f);
        return null;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.WOLF_STEP, 0.5f, 1.0f);
    }
}
