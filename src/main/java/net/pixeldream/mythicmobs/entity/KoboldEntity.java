package net.pixeldream.mythicmobs.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.pixeldream.mythicmobs.MythicMobs;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class KoboldEntity extends PathAwareEntity implements IAnimatable, Monster {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP);
    public static final AnimationBuilder WALK = new AnimationBuilder().addAnimation("walk", ILoopType.EDefaultLoopTypes.LOOP);
    public static final AnimationBuilder RUN = new AnimationBuilder().addAnimation("run", ILoopType.EDefaultLoopTypes.LOOP);
    public static final AnimationBuilder ATTACK = new AnimationBuilder().addAnimation("attack", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
    private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT = DataTracker.registerData(KoboldEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public KoboldEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = STRONG_MONSTER_XP;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        KoboldVariant variant = Util.getRandom(KoboldVariant.values(), this.random);
        setVariant(variant);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    public KoboldVariant getVariant() {
        return KoboldVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
    }

    private void setVariant(KoboldVariant variant) {
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    @Override
    public int getHandSwingDuration() {
        return (int) (20*0.44);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return HostileEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.5, false));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.75));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, CowEntity.class, true));
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller", 1, animationEvent -> {
            if (animationEvent.isMoving() && !handSwinging) {
                if (isAttacking() && !handSwinging) {
                    animationEvent.getController().setAnimation(RUN);
                    return PlayState.CONTINUE;
                }
                animationEvent.getController().setAnimation(WALK);
                return PlayState.CONTINUE;
            }
            else if (handSwinging) {
                MythicMobs.LOGGER.info("ATTACKING ANIMATION");
                this.speed = 0;
                animationEvent.getController().setAnimation(ATTACK);
                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                executor.schedule(() -> setHandSwinging(false), 440, TimeUnit.MILLISECONDS);
//                executor.schedule(() -> resetSpeed(), 840, TimeUnit.MILLISECONDS);
                return PlayState.CONTINUE;
            }
            animationEvent.getController().setAnimation(IDLE);
            return PlayState.CONTINUE;
        }));
    }

    private void setHandSwinging(boolean value) {
        handSwinging = value;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        this.playSound(SoundEvents.ENTITY_PILLAGER_AMBIENT, 1.0f, 3.0f);
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        this.playSound(SoundEvents.ENTITY_RAVAGER_HURT, 1.0f, 3.0f);
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        this.playSound(SoundEvents.ENTITY_PILLAGER_DEATH, 1.0f, 3.0f);
        return null;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_PIGLIN_STEP, 0.5f, 1.0f);
    }
}
