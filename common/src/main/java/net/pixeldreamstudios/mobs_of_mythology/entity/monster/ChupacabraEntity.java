package net.pixeldreamstudios.mobs_of_mythology.entity.monster;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.azurelib.common.api.common.animatable.GeoEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.ReactToUnreachableTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FleeTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.navigation.SmoothGroundNavigation;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.custom.UnreachableTargetSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class ChupacabraEntity extends AbstractMythMonsterEntity implements GeoEntity, SmartBrainOwner<ChupacabraEntity>, Enemy {
    private boolean unreachableTarget = false;

    public ChupacabraEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        navigation = new SmoothGroundNavigation(this, level());
        this.xpReward = Enemy.XP_REWARD_MEDIUM;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes().add(Attributes.MAX_HEALTH, MobsOfMythology.config.chupacabraHealth)
                .add(Attributes.ATTACK_DAMAGE, MobsOfMythology.config.chupacabraAttackDamage)
                .add(Attributes.ATTACK_SPEED, 1.25f)
                .add(Attributes.ATTACK_KNOCKBACK, 1)
                .add(Attributes.MOVEMENT_SPEED, 0.3);
    }

//    @Override
//    protected void registerGoals() {
//        this.goalSelector.addGoal(0, new FloatGoal(this));
//        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.5f, true));
//        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.75f));
//        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0f));
//        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
//        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
//        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, Animal.class, false));
//        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
//    }

    @Override
    public List<ExtendedSensor<ChupacabraEntity>> getSensors() {
        return ObjectArrayList.of(
                new NearbyLivingEntitySensor<>(),
                new HurtBySensor<>(),
                new UnreachableTargetSensor<>()
        );
    }

    //TODO finish Chupcabra AI
    @Override
    public BrainActivityGroup<ChupacabraEntity> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new FloatToSurfaceOfFluid<>(),
                new LookAtTarget(),
                new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<ChupacabraEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<KoboldEntity>(
                        new TargetOrRetaliate<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>(),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60))));
    }

    @Override
    public BrainActivityGroup<ChupacabraEntity> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>().invalidateIf((target, entity) -> !target.isAlive() || !entity.hasLineOfSight(target)),
                new SetWalkTargetToAttackTarget<>()
                        .speedMod((mob, livingEntity) -> 1.25f)
                        .startCondition(mob -> BrainUtils.getTargetOfEntity(this) instanceof Animal),
                new FleeTarget<>()
                        .speedModifier(1.75f)
                        .startCondition(pathfinderMob ->
                                BrainUtils.getTargetOfEntity(this) instanceof Player || BrainUtils.getLastAttacker(this) instanceof Player || unreachableTarget),
//                new Panic<>()
//                        .speedMod(pathfinderMob -> 1.5f)
//                        .panicIf((pathfinderMob, damageSource) -> unreachableTarget)
//                        .whenStopping(pathfinderMob -> unreachableTarget = false),
                new AnimatableMeleeAttack<>(12),
                new ReactToUnreachableTarget<>()
//                        .timeBeforeReacting(entity -> 10)
                        .reaction((livingEntity, aBoolean) -> {
                            unreachableTarget = true;
                        })
        );
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        this.triggerAnim("attackController", "attack");
        if (level().isClientSide()) {
            produceParticles(ParticleTypes.CRIMSON_SPORE);
            if (getHealth() < getMaxHealth()) {
                this.heal(1.5f);
                produceParticles(ParticleTypes.HAPPY_VILLAGER);
            }
        }
        return super.doHurtTarget(entity);
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
    protected SoundEvent getAmbientSound() {
        this.playSound(SoundEvents.WOLF_AMBIENT, 1.0f, 0.25f);
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        this.playSound(SoundEvents.WOLF_HURT, 1.0f, 0.25f);
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        this.playSound(SoundEvents.WOLF_DEATH, 1.0f, 0.25f);
        return null;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.WOLF_STEP, 0.5f, 1.0f);
    }
}