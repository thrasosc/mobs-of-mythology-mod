//package net.pixeldreamstudios.mobs_of_mythology.entity.mobs;
//
//import mod.azure.azurelib.common.api.common.animatable.GeoEntity;
//import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
//import mod.azure.azurelib.core.animatable.instance.SingletonAnimatableInstanceCache;
//import mod.azure.azurelib.core.animation.AnimatableManager;
//import mod.azure.azurelib.core.animation.AnimationController;
//import mod.azure.azurelib.core.object.PlayState;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
//import net.minecraft.world.entity.ai.attributes.Attributes;
//import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
//import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
//import net.minecraft.world.entity.monster.Monster;
//import net.minecraft.world.level.Level;
//import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
//import net.pixeldreamstudios.mobs_of_mythology.entity.constant.DefaultMythAnimations;
//
//public class BasiliskEntity extends AbstractChestedHorse implements GeoEntity {
//    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
//
//    protected BasiliskEntity(EntityType<? extends AbstractChestedHorse> entityType, Level level) {
//        super(entityType, level);
//        GroundPathNavigation groundPathNavigation = (GroundPathNavigation)this.getNavigation();
//        groundPathNavigation.setCanWalkOverFences(true);
//    }
//
////    public BasiliskEntity(EntityType<? extends BasiliskEntity> entityType, Level world) {
////        super((EntityType<? extends AbstractHorse>)entityType, world);
////        GroundPathNavigation mobNavigation = (GroundPathNavigation)this.getNavigation();
////        mobNavigation.setCanFloat(true);
////        mobNavigation.setCanWalkOverFences(true);
////    }
//
//    public static AttributeSupplier.Builder createAttributes() {
//        return Monster.createMobAttributes()
//                .add(Attributes.MAX_HEALTH, MobsOfMythology.config.basiliskHealth)
//                .add(Attributes.ATTACK_DAMAGE, MobsOfMythology.config.basiliskAttackDamage)
//                .add(Attributes.ATTACK_SPEED, 1.25f)
//                .add(Attributes.ATTACK_KNOCKBACK, 1)
//                .add(Attributes.MOVEMENT_SPEED, 0.175)
//                .add(Attributes.KNOCKBACK_RESISTANCE, 0.75)
//                .add(Attributes.JUMP_STRENGTH, 1.5f);
//    }
//
//    @Override
//    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
//        controllerRegistrar.add(new AnimationController<>(this, "controller", 3, state -> {
//            if (state.isMoving() && !swinging) {
//                if (isAggressive()) {
//                    state.getController().setAnimation(DefaultMythAnimations.RUN);
//                    return PlayState.CONTINUE;
//                }
//                state.getController().setAnimation(DefaultMythAnimations.WALK);
//                return PlayState.CONTINUE;
//            }
//            state.getController().setAnimation(DefaultMythAnimations.IDLE);
//            return PlayState.CONTINUE;
//        })).add(new AnimationController<>(this, "attackController", 3, event -> {
//            swinging = false;
//            return PlayState.STOP;
//        }).triggerableAnim("attack", DefaultMythAnimations.ATTACK));
//    }
//
//    @Override
//    public AnimatableInstanceCache getAnimatableInstanceCache() {
//        return cache;
//    }
//}
