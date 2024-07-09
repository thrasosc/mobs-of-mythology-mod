package net.pixeldreamstudios.mobs_of_mythology.entity;

import net.minecraft.Util;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.abstraction.AbstractKoboldEntity;
import net.pixeldreamstudios.mobs_of_mythology.entity.variant.KoboldWarriorVariant;
import net.pixeldreamstudios.mobs_of_mythology.registry.ItemRegistry;
import org.jetbrains.annotations.Nullable;

public class KoboldWarriorEntity extends AbstractKoboldEntity {
    public KoboldWarriorEntity(EntityType<? extends AbstractKoboldEntity> entityType, Level world) {
        super(entityType, world, Monster.XP_REWARD_MEDIUM);
        this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ItemRegistry.KOBOLD_SPEAR, 1));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
        KoboldWarriorVariant variant = Util.getRandom(KoboldWarriorVariant.values(), this.random);
        setVariant(variant);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, MobsOfMythology.config.koboldWarriorHealth)
                .add(Attributes.ARMOR, MobsOfMythology.config.koboldWarriorArmor)
                .add(Attributes.ATTACK_DAMAGE, MobsOfMythology.config.koboldWarriorAttackDamage)
                .add(Attributes.ATTACK_SPEED, 2)
                .add(Attributes.ATTACK_KNOCKBACK, 1)
                .add(Attributes.MOVEMENT_SPEED, 0.3);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.25f, false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.75f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Villager.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Player.class, true));
        this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal(this, true));
    }

    @Override
    public <T> T getVariant() {
        return (T) KoboldWarriorVariant.byId(this.getTypeVariant() & 255);
    }

    private void setVariant(KoboldWarriorVariant variant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }
}
