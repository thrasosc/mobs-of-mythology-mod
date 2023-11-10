package net.pixeldream.mythicmobs.entity.mobs;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animatable.instance.SingletonAnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.pixeldream.mythicmobs.config.MythicMobsConfigs;
import net.pixeldream.mythicmobs.registry.ItemRegistry;
import net.pixeldream.mythicmobs.registry.SoundRegistry;
import net.pixeldream.mythicmobs.registry.TagRegistry;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AutomatonEntity extends TamableAnimal implements GeoEntity, SmartBrainOwner<AutomatonEntity> {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private long ticksUntilAttackFinish = 0;
        private static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(DrakeEntity.class, EntityDataSerializers.BOOLEAN);

    public AutomatonEntity(EntityType<? extends TamableAnimal> entityType, Level world) {
        super(entityType, world);
    }

//    @Override
//    protected void initGoals() {
//        this.goalSelector.add(0, new SwimGoal(this));
//        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, true));
//        this.goalSelector.add(2, new SitGoal(this));
//        this.goalSelector.add(3, new FollowOwnerGoal(this, 1.0, 3.0F, 16.0F, false));
//        this.goalSelector.add(5, new WanderAroundGoal(this, 0.6));
//        this.goalSelector.add(6, new LookAtEntityGoal(this, Player.class, 6.0f));
//        this.goalSelector.add(7, new LookAroundGoal(this));
//        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
//        this.targetSelector.add(2, new AttackWithOwnerGoal(this));
//        this.targetSelector.add(3, (new RevengeGoal(this, new Class[0])).setGroupRevenge(new Class[0]));
//        this.targetSelector.add(4, new ActiveTargetGoal<MobEntity>(this, MobEntity.class, 5, false, false, entity -> entity instanceof Monster));
//        this.targetSelector.add(6, new UntamedActiveTargetGoal<MobEntity>(this, MobEntity.class, false, entity -> entity instanceof Monster));
//        this.targetSelector.add(7, new UniversalAngerGoal(this, true));
//    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SITTING, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("IsSitting", this.entityData.get(SITTING));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.entityData.set(SITTING, nbt.getBoolean("IsSitting"));
    }

    public static AttributeSupplier.Builder setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, MythicMobsConfigs.automatonHealth)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
                .add(Attributes.ATTACK_DAMAGE, MythicMobsConfigs.automatonAttackDamage);
    }

    protected void produceParticles(ParticleOptions parameters) {
        for (int i = 0; i < 5; ++i) {
            double d = this.random.nextGaussian() * 0.02;
            double e = this.random.nextGaussian() * 0.02;
            double f = this.random.nextGaussian() * 0.02;
            this.level().addParticle(parameters, this.getRandomX(1.0), this.getRandomY() + 1.0, this.getRandomZ(1.0), d, e, f);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (getHealth() < (double) 50) {
            if (getHealth() < (double) 25) {
                produceParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE);
                return;
            }
            produceParticles(ParticleTypes.SMOKE);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean bl = super.hurt(source, amount);
        if (bl) {
            this.playSound(SoundEvents.IRON_GOLEM_DAMAGE, 1.0f, 1.0f);
        }
        return bl;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.IRON_GOLEM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (hand == InteractionHand.MAIN_HAND && !isTame()) {
            if (!this.level().isClientSide()) {
                super.tame(player);
                this.navigation.recomputePath();
                this.setTarget(null);
                this.level().broadcastEntityEvent(this, (byte) 7);
                setSit(false);
                MinecraftServer server = player.getServer();
                if (server != null) {
                    server.tell(new TickTask(0, () -> player.displayClientMessage(Component.literal("I will protect you at all costs, " + player.getScoreboardName() + "."), true)));
                }
            }
            return InteractionResult.SUCCESS;
        } else if (itemStack.getItemHolder().is(TagRegistry.Items.BRONZE_INGOTS)) {
            float f = this.getHealth();
            this.heal(25.0f);
            if (this.getHealth() == f) {
                return InteractionResult.PASS;
            }
            float g = 1.0f + (this.random.nextFloat() - this.random.nextFloat()) * 0.2f;
            this.playSound(SoundEvents.IRON_GOLEM_REPAIR, 1.0f, g);
            if (!player.isCreative()) {
                itemStack.shrink(1);
            }
            return InteractionResult.PASS;
        } else if (this.isOwnedBy(player) && !this.level().isClientSide() && hand == InteractionHand.MAIN_HAND) {
            MinecraftServer server = player.getServer();
            setSit(!isSitting());
            if (server != null) {
                this.playSound(SoundRegistry.ROBOTIC_VOICE, 1.0f, 1.0f);
                server.tell(new TickTask(0, () -> player.displayClientMessage(Component.literal(!isSitting() ? "I will follow you." : "I will wait for you."), true)));
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.sidedSuccess(this.level().isClientSide());
    }

    public void setSit(boolean sitting) {
        this.entityData.set(SITTING, sitting);
        super.setOrderedToSit(sitting);
    }

    public boolean isSitting() {
        return this.entityData.get(SITTING);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, 1.0f, 1.0f);
    }

    @Override
    public void die(DamageSource damageSource) {
        produceParticles(ParticleTypes.POOF);
        spawnAtLocation(new ItemStack(ItemRegistry.BRONZE_INGOT, random.nextInt(1, 4)));
        spawnAtLocation(new ItemStack(ItemRegistry.GEAR, random.nextInt(1, 3)));
        super.die(damageSource);
    }

    @Override
    public Vec3 getLeashOffset() {
        return new Vec3(0.0, 0.845f * this.getEyeHeight(), this.getBbWidth() * 0.4f);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);
    }

    @Override
    public List<ExtendedSensor<AutomatonEntity>> getSensors() {
        return ObjectArrayList.of(
                new NearbyLivingEntitySensor<>(),
                new HurtBySensor<>()
        );
    }

    @Override
    public BrainActivityGroup<AutomatonEntity> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<AutomatonEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<AutomatonEntity>(
                        new TargetOrRetaliate<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>(),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60))));
    }

    @Override
    public BrainActivityGroup<AutomatonEntity> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new SetWalkTargetToAttackTarget<>(),
                new AnimatableMeleeAttack<>(0));
    }
}
