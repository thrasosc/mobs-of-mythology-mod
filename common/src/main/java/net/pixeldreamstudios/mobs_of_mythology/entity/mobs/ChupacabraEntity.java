package net.pixeldreamstudios.mobs_of_mythology.entity.mobs;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.azurelib.common.api.common.animatable.GeoEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.AbstractMythMonsterEntity;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.ReactToUnreachableTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FleeTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.navigation.SmoothGroundNavigation;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.custom.UnreachableTargetSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class ChupacabraEntity extends AbstractMythMonsterEntity implements GeoEntity {
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
    public List<ExtendedSensor<AbstractMythMonsterEntity>> getSensors() {
        return ObjectArrayList.of(
                new NearbyLivingEntitySensor<>(),
                new HurtBySensor<>(),
                new UnreachableTargetSensor<>()
        );
    }

    @Override
    public BrainActivityGroup<AbstractMythMonsterEntity> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>()
                        .invalidateIf((target, entity) -> !target.isAlive() || !entity.hasLineOfSight(target)),
                new SetWalkTargetToAttackTarget<>()
                        .speedMod((mob, livingEntity) -> 1.25f)
                        .startCondition(mob -> BrainUtils.getTargetOfEntity(this) instanceof Animal),
                new FleeTarget<>()
                        .speedModifier(1.75f)
                        .startCondition(pathfinderMob -> BrainUtils.getTargetOfEntity(this) instanceof Player || BrainUtils.getLastAttacker(this) instanceof Player || unreachableTarget)
                        .whenStopping(pathfinderMob -> unreachableTarget = false),
                new AnimatableMeleeAttack<>(8)
                        .whenStarting(mob -> {
                            this.triggerAnim("attackController", "attack");
                            if (level().isClientSide()) {
                                produceParticles(ParticleTypes.CRIMSON_SPORE);
                                if (getHealth() < getMaxHealth()) {
                                    this.heal(1.5f);
                                    produceParticles(ParticleTypes.HAPPY_VILLAGER);
                                }
                            }
                        }),
                new ReactToUnreachableTarget<>()
                        .reaction((livingEntity, aBoolean) -> unreachableTarget = true)
        );
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