package net.pixeldreamstudios.mobs_of_mythology.entity;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import mod.azure.azurelib.common.api.common.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animatable.instance.SingletonAnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.pixeldreamstudios.mobs_of_mythology.entity.constant.DefaultMythAnimations;
import net.pixeldreamstudios.mobs_of_mythology.registry.TagRegistry;
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

public abstract class AbstractMythEntity extends PathfinderMob
    implements GeoEntity, SmartBrainOwner<AbstractMythEntity> {
  private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

  protected AbstractMythEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
    super(entityType, level);
  }

  /*
   * Prevent myth mobs from spawning wherever they want to.
   * Adapted from net.minecraft.world.entity.animal.Animal.checkAnimalSpawnRules.
   */
  public static boolean checkMythEntitySpawnRules(
      EntityType<? extends PathfinderMob> entityType,
      LevelAccessor levelAccessor,
      MobSpawnType mobSpawnType,
      BlockPos blockPos,
      RandomSource randomSource) {
    boolean bl =
        MobSpawnType.ignoresLightRequirements(mobSpawnType)
            || isBrightEnoughToSpawn(levelAccessor, blockPos);
    return levelAccessor.getBlockState(blockPos.below()).is(TagRegistry.MYTH_ENTITIES_SPAWNABLE_ON)
        && bl;
  }

  protected static boolean isBrightEnoughToSpawn(
      BlockAndTintGetter blockAndTintGetter, BlockPos blockPos) {
    return blockAndTintGetter.getRawBrightness(blockPos, 0) > 8;
  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return cache;
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    controllerRegistrar
        .add(
            new AnimationController<>(
                this,
                "livingController",
                3,
                state -> {
                  if (state.isMoving() && !swinging) {
                    if (isAggressive() && !swinging) {
                      state.getController().setAnimation(DefaultMythAnimations.RUN);
                      return PlayState.CONTINUE;
                    }
                    state.getController().setAnimation(DefaultMythAnimations.WALK);
                    return PlayState.CONTINUE;
                  }
                  state.getController().setAnimation(DefaultMythAnimations.IDLE);
                  return PlayState.CONTINUE;
                }))
        .add(
            new AnimationController<>(
                    this,
                    "attackController",
                    3,
                    event -> {
                      swinging = false;
                      return PlayState.STOP;
                    })
                .triggerableAnim("attack", DefaultMythAnimations.ATTACK));
  }

  @Override
  public List<ExtendedSensor<AbstractMythEntity>> getSensors() {
    return ObjectArrayList.of(new NearbyLivingEntitySensor<>(), new HurtBySensor<>());
  }

  @Override
  public BrainActivityGroup<AbstractMythEntity> getCoreTasks() {
    return BrainActivityGroup.coreTasks(new LookAtTarget<>(), new MoveToWalkTarget<>());
  }

  @Override
  public BrainActivityGroup<AbstractMythEntity> getIdleTasks() {
    return BrainActivityGroup.idleTasks(
        new FirstApplicableBehaviour<AbstractMythEntity>(
            new TargetOrRetaliate<>(), new SetPlayerLookTarget<>(), new SetRandomLookTarget<>()),
        new OneRandomBehaviour<>(
            new SetRandomWalkTarget<>(),
            new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60))));
  }

  @Override
  public BrainActivityGroup<AbstractMythEntity> getFightTasks() {
    return BrainActivityGroup.fightTasks(
        new InvalidateAttackTarget<>()
            .invalidateIf((target, entity) -> !target.isAlive() || !entity.hasLineOfSight(target)),
        new SetWalkTargetToAttackTarget<>().speedMod((mob, livingEntity) -> 1.25f),
        new AnimatableMeleeAttack<>(20)
            .whenStarting(
                mob -> {
                  this.triggerAnim("attackController", "attack");
                }));
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
