package net.pixeldreamstudios.mobs_of_mythology.entity.mobs;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animatable.instance.SingletonAnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.constant.DefaultMythAnimations;
import net.pixeldreamstudios.mobs_of_mythology.entity.variant.DrakeVariant;
import net.pixeldreamstudios.mobs_of_mythology.registry.ItemRegistry;
import net.pixeldreamstudios.mobs_of_mythology.registry.SoundRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class DrakeEntity extends TamableAnimal implements GeoEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    protected static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(DrakeEntity.class, EntityDataSerializers.INT);
    public static final Predicate<LivingEntity> PREY_SELECTOR;

    public DrakeEntity(EntityType<? extends TamableAnimal> entityType, Level world) {
        super(entityType, world);
        this.xpReward = Monster.XP_REWARD_MEDIUM;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        DrakeVariant variant = Util.getRandom(DrakeVariant.values(), this.random);
        setVariant(variant);
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public void setTame(boolean bl) {
        super.setTame(bl);
        if (bl) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(MobsOfMythology.config.drakeHealth * 2);
            this.setHealth((float) (MobsOfMythology.config.drakeHealth * 2));
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(MobsOfMythology.config.drakeHealth);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
    }

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
    public boolean isFood(ItemStack itemStack) {
        Item item = itemStack.getItem();
        return item.isEdible() && item.getFoodProperties().isMeat();
    }

    public DrakeVariant getVariant() {
        return DrakeVariant.byId(this.getTypeVariant() & 255);
    }

    private void setVariant(DrakeVariant variant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    private int getTypeVariant() {
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, MobsOfMythology.config.drakeHealth)
                .add(Attributes.ATTACK_DAMAGE, MobsOfMythology.config.drakeAttackDamage)
                .add(Attributes.ATTACK_SPEED, 2)
                .add(Attributes.ATTACK_KNOCKBACK, 1)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
                .add(Attributes.MOVEMENT_SPEED, 0.3);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(3, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this, new Class[0])).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(4, new NonTameRandomTargetGoal(this, Animal.class, false, PREY_SELECTOR));
        this.targetSelector.addGoal(5, new ResetUniversalAngerTargetGoal(this, true));
    }

    static {
        PREY_SELECTOR = (livingEntity) -> {
            EntityType<?> entityType = livingEntity.getType();
            return entityType == EntityType.VILLAGER || entityType == EntityType.WANDERING_TRADER || entityType == EntityType.WOLF;
        };
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "livingController", 3, state -> {
            if (isInSittingPose()) {
                state.getController().setAnimation(DefaultMythAnimations.SIT);
                return PlayState.CONTINUE;
            } else if (state.isMoving() && !swinging) {
                if (isAggressive() && !swinging) {
                    state.getController().setAnimation(DefaultMythAnimations.RUN);
                    return PlayState.CONTINUE;
                }
                else {
                    state.getController().setAnimation(DefaultMythAnimations.WALK);
                    return PlayState.CONTINUE;
                }
            }
            state.getController().setAnimation(DefaultMythAnimations.IDLE);
            return PlayState.CONTINUE;
        })).add(new AnimationController<>(this, "attackController", 3, event -> {
            swinging = false;
            return PlayState.STOP;
        }).triggerableAnim("attack", DefaultMythAnimations.ATTACK));
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        this.triggerAnim("attackController", "attack");
        return super.doHurtTarget(entity);
    }

        @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        Item item = itemStack.getItem();
        if (((Level)this.level()).isClientSide) {
            boolean bl = this.isOwnedBy(player) || this.isTame() || itemStack.is(ItemRegistry.COOKED_CHUPACABRA_MEAT.get()) && !this.isTame();
            return bl ? InteractionResult.CONSUME : InteractionResult.PASS;
        }
        if (this.isTame()) {
            InteractionResult interactionResult;
            if (this.isFood(itemStack) && this.getHealth() < this.getMaxHealth()) {
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                this.heal(2.0f * item.getFoodProperties().getNutrition());
                return InteractionResult.SUCCESS;
            }
            if ((interactionResult = super.mobInteract(player, interactionHand)).consumesAction() && !this.isBaby() || !this.isOwnedBy(player)) return interactionResult;
            this.setOrderedToSit(!this.isOrderedToSit());
            this.jumping = false;
            this.navigation.stop();
            this.setTarget(null);
            return InteractionResult.SUCCESS;
        }
        if (!itemStack.is(ItemRegistry.COOKED_CHUPACABRA_MEAT.get())) return super.mobInteract(player, interactionHand);
        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
        }
        if (this.random.nextInt(3) == 0) {
            this.tame(player);
            this.navigation.stop();
            this.setTarget(null);
            this.setOrderedToSit(true);
            ((Level)this.level()).broadcastEntityEvent(this, (byte)7);
            return InteractionResult.SUCCESS;
        } else {
            ((Level)this.level()).broadcastEntityEvent(this, (byte)6);
        }
        return InteractionResult.SUCCESS;
    }

    private void tryToTame(Player player) {
        if (this.random.nextInt(3) == 0) {
            this.tame(player);
            this.navigation.stop();
            this.setTarget((LivingEntity)null);
            this.setOrderedToSit(true);
            this.level().broadcastEntityEvent(this, (byte)7);
        } else {
            this.level().broadcastEntityEvent(this, (byte)6);
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        this.playSound(SoundRegistry.DRAKE_ROAR.get(), 1.0f, 1.0f);
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        this.playSound(SoundRegistry.DRAKE_ROAR.get(), 1.0f, 2.0f);
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        this.playSound(SoundRegistry.DRAKE_DEATH.get(), 1.0f, 1.0f);
        return null;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.WOLF_STEP, 0.75f, 1.0f);
    }
}
