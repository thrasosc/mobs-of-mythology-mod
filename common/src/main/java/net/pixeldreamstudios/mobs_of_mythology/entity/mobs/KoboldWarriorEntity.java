package net.pixeldreamstudios.mobs_of_mythology.entity.mobs;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.AbstractMythMonsterEntity;
import net.pixeldreamstudios.mobs_of_mythology.entity.constant.DefaultMythAnimations;
import net.pixeldreamstudios.mobs_of_mythology.entity.variant.KoboldWarriorVariant;
import net.pixeldreamstudios.mobs_of_mythology.registry.ItemRegistry;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class KoboldWarriorEntity extends AbstractKoboldEntity {

    public final DefaultMythAnimations dispatcher;

    public KoboldWarriorEntity(EntityType<? extends AbstractKoboldEntity> entityType, Level world) {
        super(entityType, world, Monster.XP_REWARD_MEDIUM);
        this.dispatcher = new DefaultMythAnimations(this);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason,
                                        @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
        SpawnGroupData out = super.finalizeSpawn(level, difficulty, reason, data, tag);

        KoboldWarriorVariant variant = Util.getRandom(KoboldWarriorVariant.values(), this.random);
        setVariant(variant);

        this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ItemRegistry.KOBOLD_SPEAR.get()));

        return out;
    }
    @Override
    protected void populateDefaultEquipmentSlots(net.minecraft.util.RandomSource random, DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(random, difficulty);
        this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ItemRegistry.KOBOLD_SPEAR.get()));
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
    }
    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (this.level().isClientSide()) return InteractionResult.SUCCESS;
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ItemRegistry.KOBOLD_SPEAR.get()));
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        return InteractionResult.SUCCESS;
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
    public List<ExtendedSensor<AbstractMythMonsterEntity>> getSensors() {
        return ObjectArrayList.of(
                new NearbyLivingEntitySensor<AbstractMythMonsterEntity>()
                        .setPredicate((target, entity) -> {
                            return target instanceof Player
                                    || target instanceof Villager
                                    || target instanceof IronGolem
                                    || target instanceof AutomatonEntity;
                        }),
                new HurtBySensor<>()
        );
    }

    @Override
    public BrainActivityGroup<AbstractMythMonsterEntity> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>()
                        .invalidateIf((target, entity) -> !target.isAlive() || !entity.hasLineOfSight(target)),
                new SetWalkTargetToAttackTarget<>()
                        .speedMod((mob, livingEntity) -> 1.25f),
                new AnimatableMeleeAttack<>(6)
                        .whenStarting(mob -> this.dispatcher.attack())
        );
    }
    @Override
    public boolean checkSpawnRules(LevelAccessor level, MobSpawnType spawnType) {
        if (level.getDifficulty() == Difficulty.PEACEFUL) {
            return false;
        }
        BlockPos pos = this.blockPosition();
        int skyLight = level.getBrightness(LightLayer.SKY, pos);
        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);

        if (skyLight > 7 || blockLight > 7) {
            return false;
        }
        return super.checkSpawnRules(level, spawnType);
    }


    @Override
    public <T> T getVariant() {
        return (T) KoboldWarriorVariant.byId(this.getTypeVariant() & 255);
    }

    private void setVariant(KoboldWarriorVariant variant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }
}
