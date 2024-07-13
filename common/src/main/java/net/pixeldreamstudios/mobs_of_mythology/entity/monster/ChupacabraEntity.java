package net.pixeldreamstudios.mobs_of_mythology.entity.monster;

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
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.navigation.SmoothGroundNavigation;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;

import java.util.List;

public class ChupacabraEntity extends AbstractMythMonsterEntity implements GeoEntity, SmartBrainOwner<ChupacabraEntity>, Enemy {
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

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.5f, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.75f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, Animal.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public List<ExtendedSensor<ChupacabraEntity>> getSensors() {
        return ObjectArrayList.of(
                new NearbyLivingEntitySensor<ChupacabraEntity>()
                        .setPredicate((target, entity) -> target instanceof Player),
                new HurtBySensor<>()
        );
    }

    //TODO finish Chupcabra AI
//    @Override
//    public BrainActivityGroup<ChupacabraEntity> getCoreTasks() {
//        return BrainActivityGroup.coreTasks(
//                new FloatToSurfaceOfFluid<>(),
//                new LookAtTarget(),
//                new MoveToWalkTarget<>());
//    }
//
//    @Override
//    public BrainActivityGroup<ChupacabraEntity> getIdleTasks() { // These are the tasks that run when the mob isn't doing anything else (usually)
//        return BrainActivityGroup.idleTasks(
//                new FirstApplicableBehaviour<KoboldEntity>(      // Run only one of the below behaviours, trying each one in order. Include the generic type because JavaC is silly
//                        new TargetOrRetaliate<>(),            // Set the attack target and walk target based on nearby entities
//                        new SetPlayerLookTarget<>(),          // Set the look target for the nearest player
//                        new SetRandomLookTarget<>()),         // Set a random look target
//                new OneRandomBehaviour<>(                 // Run a random task from the below options
//                        new SetRandomWalkTarget<>(),          // Set a random walk target to a nearby position
//                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60)))); // Do nothing for 1.5->3 seconds
//    }
//
//    @Override
//    public BrainActivityGroup<ChupacabraEntity> getFightTasks() { // These are the tasks that handle fighting
//        return BrainActivityGroup.fightTasks(
//                new InvalidateAttackTarget<>().invalidateIf((target, entity) -> !target.isAlive() || !entity.hasLineOfSight(target)),
//                new SetWalkTargetToAttackTarget<>(),
//                new AnimatableMeleeAttack<>(10),
//                new FleeTarget<>()
//        );
//    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);
    }

    @Override
    public void tick() {
        super.tick();
        if (swinging) {
            produceParticles(ParticleTypes.CRIMSON_SPORE);
            if (getHealth() < getMaxHealth()) {
                this.heal(1.5f);
                produceParticles(ParticleTypes.HAPPY_VILLAGER);
            }
        }
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