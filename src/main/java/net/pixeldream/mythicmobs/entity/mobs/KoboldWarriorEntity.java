package net.pixeldream.mythicmobs.entity.mobs;

import net.minecraft.Util;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.pixeldream.mythicmobs.config.MythicMobsConfigs;
import net.pixeldream.mythicmobs.registry.ItemRegistry;
import org.jetbrains.annotations.Nullable;

public class KoboldWarriorEntity extends AbstractKoboldEntity {
    public KoboldWarriorEntity(EntityType<? extends AbstractKoboldEntity> entityType, Level world) {
        super(entityType, world, Monster.XP_REWARD_MEDIUM);
        this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ItemRegistry.KOBOLD_SPEAR, 1));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag entityNbt) {
        KoboldWarriorVariant variant = Util.getRandom(KoboldWarriorVariant.values(), this.random);
        setVariant(variant);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData, entityNbt);
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, MythicMobsConfigs.koboldWarriorHealth)
                .add(Attributes.ARMOR, MythicMobsConfigs.koboldWarriorArmor)
                .add(Attributes.ATTACK_DAMAGE, MythicMobsConfigs.koboldWarriorAttackDamage)
                .add(Attributes.ATTACK_SPEED, 2)
                .add(Attributes.ATTACK_KNOCKBACK, 1)
                .add(Attributes.MOVEMENT_SPEED, 0.3);
    }

//    @Override
//    protected void initGoals() {
//        this.goalSelector.add(0, new SwimGoal(this));
//        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.25f, false));
//        this.goalSelector.add(2, new WanderAroundGoal(this, 0.75f));
//        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
//        this.goalSelector.add(4, new LookAroundGoal(this));
//        this.targetSelector.add(1, new ActiveTargetGoal<>(this, VillagerEntity.class, true));
//        this.targetSelector.add(2, (new KoboldRevengeGoal(this, new Class[0])).setGroupRevenge(new Class[0]));
//        this.targetSelector.add(3, new UniversalAngerGoal(this, true));
//    }

    @Override
    public <T> T getVariant() {
        return (T) KoboldWarriorVariant.byId(this.getTypeVariant() & 255);
    }

    private void setVariant(KoboldWarriorVariant variant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    @Override
    public void die(DamageSource damageSource) {
        produceParticles(ParticleTypes.POOF);
        this.spawnAtLocation(new ItemStack(ItemRegistry.KOBOLD_SPEAR));
        super.die(damageSource);
    }
}
