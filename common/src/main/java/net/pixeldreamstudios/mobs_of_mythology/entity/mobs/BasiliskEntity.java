package net.pixeldreamstudios.mobs_of_mythology.entity.mobs;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.azurelib.common.api.common.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animatable.instance.SingletonAnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.constant.DefaultMythAnimations;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
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
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;

import java.util.List;

public class BasiliskEntity extends AbstractChestedHorse implements GeoEntity, SmartBrainOwner<BasiliskEntity> {
    private static final float MOVEMENT_SPEED = 0.175f;
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public BasiliskEntity(EntityType<? extends AbstractChestedHorse> entityType, Level level) {
        super(entityType, level);
        this.navigation = new SmoothGroundNavigation(this, level);
    }

    @Override
    protected float getRiddenSpeed(Player player) {
        return MOVEMENT_SPEED;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, MobsOfMythology.config.basiliskHealth)
                .add(Attributes.ATTACK_DAMAGE, MobsOfMythology.config.basiliskAttackDamage)
                .add(Attributes.ATTACK_SPEED, 1.25f)
                .add(Attributes.ATTACK_KNOCKBACK, 1)
                .add(Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.75)
                .add(Attributes.JUMP_STRENGTH, 0.5f);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ItemTags.MEAT);
    }

    @Override
    protected Vec3 getPassengerAttachmentPoint(Entity entity, EntityDimensions entityDimensions, float f) {
        return super.getPassengerAttachmentPoint(entity, entityDimensions, f)
                .add(new Vec3(0.0, 0.01 * (double) f, -0.1 * (double) f).yRot(-this.getYRot() * (float) (Math.PI / 180.0)));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 3, state -> {
            // SADDLED
            if (isSaddled() || (!isSaddled() && hasExactlyOnePlayerPassenger())) {
                if (state.isMoving() && !swinging) {
                    if (isAggressive() || hasExactlyOnePlayerPassenger()) {
                        state.getController().setAnimation(DefaultMythAnimations.RUN_RIDING);
                        return PlayState.CONTINUE;
                    }
                    state.getController().setAnimation(DefaultMythAnimations.WALK_RIDING);
                    return PlayState.CONTINUE;
                }
                state.getController().setAnimation(DefaultMythAnimations.IDLE_RIDING);
                return PlayState.CONTINUE;
            }
            // NOT SADDLED
            if (state.isMoving() && !swinging) {
                if (isAggressive() || hasExactlyOnePlayerPassenger()) {
                    state.getController().setAnimation(DefaultMythAnimations.RUN);
                    return PlayState.CONTINUE;
                }
                state.getController().setAnimation(DefaultMythAnimations.WALK);
                return PlayState.CONTINUE;
            }
            state.getController().setAnimation(DefaultMythAnimations.IDLE);
            return PlayState.CONTINUE;
        })).add(new AnimationController<>(this, "attackController", 3, event -> {
            swinging = false;
            return PlayState.STOP;
        }).triggerableAnim("attack", DefaultMythAnimations.ATTACK));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public List<ExtendedSensor<BasiliskEntity>> getSensors() {
        return ObjectArrayList.of(
                new NearbyLivingEntitySensor<BasiliskEntity>()
                        .setPredicate((target, entity) -> target instanceof Animal && !(target instanceof BasiliskEntity)),
                new HurtBySensor<>()
        );
    }

    @Override
    public BrainActivityGroup<BasiliskEntity> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new FloatToSurfaceOfFluid<>(),
                new LookAtTarget<>(),
                new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<BasiliskEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<BasiliskEntity>(
                        //TODO group retaliation
                        new TargetOrRetaliate<>()
                                .alertAlliesWhen((mob, entity) -> this.isAggressive()),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>(),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60))));
    }

    //TODO fix attacking owner
    @Override
    public BrainActivityGroup<BasiliskEntity> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>()
                        .invalidateIf((target, entity) -> !target.isAlive() || !entity.hasLineOfSight(target) || target.is(getOwner())),
                new SetWalkTargetToAttackTarget<>()
                        .speedMod((mob, livingEntity) -> 1.5f),
                new AnimatableMeleeAttack<>(7)
                        .whenStarting(mob -> {
                            this.triggerAnim("attackController", "attack");
                        })
        );
    }

    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);
    }
}
