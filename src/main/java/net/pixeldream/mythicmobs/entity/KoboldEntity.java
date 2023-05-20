package net.pixeldream.mythicmobs.entity;

import net.minecraft.client.particle.Particle;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.pixeldream.mythicmobs.registry.ItemRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class KoboldEntity extends AbstractKoboldEntity {
    private int heldCounter = 0;
    public KoboldEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world, SMALL_MONSTER_XP);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        KoboldVariant variant = Util.getRandom(KoboldVariant.values(), this.random);
        setVariant(variant);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return HostileEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4).add(EntityAttributes.GENERIC_ATTACK_SPEED, 2).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.25f, false));
        this.goalSelector.add(2, new WanderAroundGoal(this, 0.75f));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, CowEntity.class, true));
    }

    @Override
    public <T> T getVariant() {
        return (T) KoboldVariant.byId(this.getTypeVariant() & 255);
    }

    @Override
    public void tick() {
        super.tick();
        if (isHoldingItem()) {
            heldCounter++;
            if (heldCounter == 20 * 3) {
                if (this.getStackInHand(Hand.MAIN_HAND).isOf(Items.NETHERITE_INGOT)) {
                    this.playSound(SoundEvents.ENTITY_VILLAGER_YES, 1.0f, 1.5f);
                    this.produceParticles(ParticleTypes.HAPPY_VILLAGER);
                    this.dropStack(new ItemStack(ItemRegistry.BRONZE_INGOT, 3));
                    this.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                    this.getBrain().forget(MemoryModuleType.LIKED_PLAYER);
                    heldCounter = 0;
                }
                else {
                    this.playSound(SoundEvents.ENTITY_VILLAGER_NO, 1.0f, 1.5f);
                    this.produceParticles(ParticleTypes.ANGRY_VILLAGER);
                    this.dropStack(getStackInHand(Hand.MAIN_HAND));
                    this.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                    this.getBrain().forget(MemoryModuleType.LIKED_PLAYER);
                    heldCounter = 0;
                }
            }
        }
    }

    private void setVariant(KoboldVariant variant) {
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    protected void produceParticles(ParticleEffect parameters) {
        for(int i = 0; i < 5; ++i) {
            double d = this.random.nextGaussian() * 0.02;
            double e = this.random.nextGaussian() * 0.02;
            double f = this.random.nextGaussian() * 0.02;
            this.world.addParticle(parameters, this.getParticleX(1.0), this.getRandomBodyY() + 1.0, this.getParticleZ(1.0), d, e, f);
        }
    }

    public boolean isHoldingItem() {
        return !this.getStackInHand(Hand.MAIN_HAND).isEmpty();
    }

    private void decrementStackUnlessInCreative(PlayerEntity player, ItemStack stack) {
        if (!player.getAbilities().creativeMode) {
            stack.decrement(1);
        }
    }

    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack playerStack = player.getStackInHand(hand);
        if (!isHoldingItem() && !playerStack.isEmpty()) {
            ItemStack koboldStack2 = playerStack.copy();
            koboldStack2.setCount(1);
            setStackInHand(Hand.MAIN_HAND, koboldStack2);
            decrementStackUnlessInCreative(player, playerStack);
            world.playSoundFromEntity(player, this, SoundEvents.BLOCK_FROGLIGHT_HIT, SoundCategory.NEUTRAL, 2.0F, 1.0F);
            this.produceParticles(ParticleTypes.POOF);
            getBrain().remember(MemoryModuleType.LIKED_PLAYER, player.getUuid());
            return ActionResult.SUCCESS;
        }
        return super.interactMob(player, hand);
    }
}
