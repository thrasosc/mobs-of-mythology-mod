package net.pixeldreamstudios.mobs_of_mythology.entity.mobs;

import mod.azure.azurelib.common.api.common.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animatable.instance.SingletonAnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.core.animation.AnimationController;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.constant.DefaultMythAnimations;
import org.jetbrains.annotations.Nullable;

public class PegasusEntity extends AbstractChestedHorse implements GeoEntity {

    private static final int FLYING_INTERVAL = 8;
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    protected int flyingTime;
    private boolean isFlying = false;

    public PegasusEntity(EntityType<? extends AbstractChestedHorse> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return AbstractChestedHorse.createBaseChestedHorseAttributes()
                .add(Attributes.MAX_HEALTH, MobsOfMythology.config.pegasusHealth)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FLYING_SPEED, 0.8)
                .add(Attributes.JUMP_STRENGTH, 1.0);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (flyingTime > 0) {
            flyingTime--;
        }
//        if (!isVehicle()) {
//            isFlying = false;
//        }
        if (this.onGround()) {
            isFlying = false;
        }
    }

    @Override
    public void travel(Vec3 movementInput) {
        if (this.isAlive()) {
            if (this.isVehicle() && this.getControllingPassenger() instanceof LivingEntity rider) {
                this.setYRot(rider.getYRot());
                this.yRotO = this.getYRot();
                this.setXRot(rider.getXRot() * 0.5F);
                this.setRot(this.getYRot(), this.getXRot());
                if (isFlying) {
                    this.setSpeed((float) this.getAttributeValue(Attributes.FLYING_SPEED));
                    Vec3 currentMotion = this.getDeltaMovement();
                    this.setDeltaMovement(currentMotion.add(0.0, 0.05, 0.0));
                    super.travel(new Vec3(rider.xxa, currentMotion.y, rider.zza));
                    this.setDeltaMovement(this.getDeltaMovement()
                                                  .multiply(0.91D, 0.98D, 0.91D));
                } else {
                    this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    super.travel(new Vec3(rider.xxa, 0, rider.zza));
                }
            } else {
                super.travel(movementInput);
            }
        }
    }

    @Override
    public void handleStartJump(int jumpPower) {
        if (!this.onGround()) {
            this.setStanding(false);
        }
    }

    @Override
    public boolean canJump() {
        return super.canJump() && flyingTime <= 0;
    }

    @Override
    public void onPlayerJump(int jumpPower) {
//        if (this.canJump()) {
//            this.setJumping(true);
        this.flyingJump();

    }

    public void flyingJump() {
        if (flyingTime <= 0 && this.canJump()) {
            float jumpMotion = 1.6F;
            this.setDeltaMovement(this.getDeltaMovement()
                                          .add(0, jumpMotion, 0));
            this.flyingTime = FLYING_INTERVAL;
            this.isFlying = true;
        }
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource source) {
        // Pegasus does not take fall damage
        return false;
    }

    @Override
    protected void playJumpSound() {
        this.playSound(SoundEvents.HORSE_JUMP, 0.4F, 1.0F);
    }

    @Override
    public boolean isJumping() {
        return false;
    }

    @Override
    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!this.level().isClientSide && !this.isVehicle()) {
            player.startRiding(this);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    @Nullable
    public LivingEntity getControllingPassenger() {
        Entity passenger = this.getFirstPassenger();
        return passenger instanceof Player ? (Player) passenger : null;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 3, state -> {
            if (isFlying) {
                return state.setAndContinue(DefaultMythAnimations.FLY);
            }
            if (this.onGround() && state.isMoving()) {
                return state.setAndContinue(DefaultMythAnimations.WALK);
            }
            return state.setAndContinue(DefaultMythAnimations.IDLE);
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
