//package net.pixeldream.mythicmobs.entity.mobs;
//
//import mod.azure.azurelib.animatable.GeoEntity;
//import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
//import mod.azure.azurelib.core.animatable.instance.SingletonAnimatableInstanceCache;
//import mod.azure.azurelib.core.animation.AnimatableManager;
//import mod.azure.azurelib.core.animation.AnimationController;
//import mod.azure.azurelib.core.object.PlayState;
//import net.minecraft.core.particles.ParticleTypes;
//import net.minecraft.sounds.SoundEvent;
//import net.minecraft.world.damagesource.DamageSource;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.EquipmentSlot;
//import net.minecraft.world.entity.RiderShieldingMount;
//import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
//import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
//import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
//import net.minecraft.world.entity.animal.horse.AbstractHorse;
//import net.minecraft.world.entity.monster.Monster;
//import net.minecraft.world.entity.player.Inventory;
//import net.minecraft.world.item.HorseArmorItem;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.Level;
//import net.pixeldream.mythicmobs.config.MythicMobsConfigs;
//import net.pixeldream.mythicmobs.entity.constant.DefaultAnimations;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.UUID;
//
//public class BasiliskEntity extends AbstractHorse implements GeoEntity, RiderShieldingMount {
//    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
//    private long ticksUntilAttackFinish = 0;
//    private static final UUID HORSE_ARMOR_BONUS_ID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
//
//    public BasiliskEntity(EntityType<? extends BasiliskEntity> entityType, Level world) {
//        super((EntityType<? extends AbstractHorse>)entityType, world);
//        this.setMaxUpStep(1.5f);
//        GroundPathNavigation mobNavigation = (GroundPathNavigation)this.getNavigation();
//        mobNavigation.setCanFloat(true);
//        mobNavigation.setCanWalkOverFences(true);
//    }
//
//    public static AttributeSupplier.Builder setAttributes() {
//        return Monster.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, MythicMobsConfigs.basiliskHealth).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, MythicMobsConfigs.basiliskAttackDamage).add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.25f).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.175).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.75);
//    }
//
//    @Override
//    protected void initGoals() {
//        this.goalSelector.add(0, new SwimGoal(this));
//        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.5f, true));
//        this.goalSelector.add(2, new HorseBondWithPlayerGoal(this, 1.2));
//        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.75f));
//        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
//        this.goalSelector.add(5, new LookAroundGoal(this));
//        this.targetSelector.add(1, new RevengeGoal(this));
//    }
//
//    @Override
//    public void tick() {
//        super.tick();
//    }
//
//    @Override
//    public void writeCustomDataToNbt(NbtCompound nbt) {
//        super.writeCustomDataToNbt(nbt);
//    }
//
//    @Override
//    public void readCustomDataFromNbt(NbtCompound nbt) {
//        super.readCustomDataFromNbt(nbt);
//    }
//
//    @Override
//    protected void initDataTracker() {
//        super.initDataTracker();
//    }
//
//    @Override
//    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
//        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
//    }
//
//    @Override
//    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
//        controllerRegistrar.add(new AnimationController<>(this, "controller", 3, state -> {
//            if (state.isMoving() && !handSwinging) {
//                if (isAttacking() && !handSwinging) {
//                    state.getController().setAnimation(DefaultAnimations.RUN);
//                    return PlayState.CONTINUE;
//                }
//                state.getController().setAnimation(DefaultAnimations.WALK);
//                return PlayState.CONTINUE;
//            } else if (handSwinging) {
//                state.getController().setAnimation(DefaultAnimations.ATTACK);
//                ticksUntilAttackFinish++;
//                if (ticksUntilAttackFinish > 20 * 2) {
//                    handSwinging = false;
//                    ticksUntilAttackFinish = 0;
//                }
//                return PlayState.CONTINUE;
//            }
//            state.getController().setAnimation(DefaultAnimations.IDLE);
//            return PlayState.CONTINUE;
//        }));
//    }
//
//    @Override
//    public AnimatableInstanceCache getAnimatableInstanceCache() {
//        return cache;
//    }
//
//    protected void produceParticles(ParticleEffect parameters) {
//        for (int i = 0; i < 5; ++i) {
//            double d = this.random.nextGaussian() * 0.02;
//            double e = this.random.nextGaussian() * 0.02;
//            double f = this.random.nextGaussian() * 0.02;
//            this.getWorld().addParticle(parameters, this.getParticleX(1.0), this.getRandomBodyY() + 1.0, this.getParticleZ(1.0), d, e, f);
//        }
//    }
//
//    @Override
//    protected void updateSaddle() {
//        if (this.getWorld().isClient) {
//            return;
//        }
//        super.updateSaddle();
//        this.setArmorTypeFromStack(this.items.getStack(1));
//        this.setEquipmentDropChance(EquipmentSlot.CHEST, 0.0f);
//    }
//
//    private void setArmorTypeFromStack(ItemStack stack) {
//        this.equipArmor(stack);
//        if (!this.getWorld().isClient) {
//            int i;
//            this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).removeModifier(HORSE_ARMOR_BONUS_ID);
//            if (this.isHorseArmor(stack) && (i = ((HorseArmorItem)stack.getItem()).getBonus()) != 0) {
//                this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).addTemporaryModifier(new EntityAttributeModifier(HORSE_ARMOR_BONUS_ID, "Horse armor bonus", (double)i, EntityAttributeModifier.Operation.ADDITION));
//            }
//        }
//    }
//
//    private void equipArmor(ItemStack stack) {
//        this.equipStack(EquipmentSlot.CHEST, stack);
//        this.setEquipmentDropChance(EquipmentSlot.CHEST, 0.0f);
//    }
//
//    @Override
//    public void onInventoryChanged(Inventory sender) {
//        ItemStack itemStack = this.getArmorType();
//        super.onInventoryChanged(sender);
//        ItemStack itemStack2 = this.getArmorType();
//        if (this.age > 20 && this.isHorseArmor(itemStack2) && itemStack != itemStack2) {
//            this.playSound(SoundEvents.ENTITY_HORSE_ARMOR, 0.5f, 1.0f);
//        }
//    }
//
//    public ItemStack getArmorType() {
//        return this.getEquippedStack(EquipmentSlot.CHEST);
//    }
//
//    @Override
//    public boolean hasArmorSlot() {
//        return true;
//    }
//
//    @Override
//    public boolean isHorseArmor(ItemStack item) {
//        return item.getItem() instanceof HorseArmorItem;
//    }
//
//    @Override
//    public ActionResult interactMob(PlayerEntity player, Hand hand) {
//        boolean bl;
//        boolean bl2 = bl = !this.isBaby() && this.isTame() && player.shouldCancelInteraction();
//        if (this.hasPassengers() || bl) {
//            return super.interactMob(player, hand);
//        }
//        ItemStack itemStack = player.getStackInHand(hand);
//        if (!itemStack.isEmpty()) {
//            if (this.isBreedingItem(itemStack)) {
//                return this.interactHorse(player, itemStack);
//            }
//            if (!this.isTame()) {
//                this.playAngrySound();
//                return ActionResult.success(this.getWorld().isClient);
//            }
//        }
//        return super.interactMob(player, hand);
//    }
//
//    @Override
//    public void onDeath(DamageSource damageSource) {
//        produceParticles(ParticleTypes.POOF);
//        super.onDeath(damageSource);
//    }
//
//    @Override
//    public void updatePostDeath() {
//        ++deathTime;
//        if (deathTime == 15) {
//            this.remove(Entity.RemovalReason.KILLED);
//            this.dropXp();
//        }
//    }
//
//    @Override
//    protected void playWalkSound(BlockSoundGroup group) {
//        super.playWalkSound(group);
//        if (this.random.nextInt(10) == 0) {
//            this.playSound(SoundEvents.ENTITY_HORSE_BREATHE, group.getVolume() * 0.6f, group.getPitch());
//        }
//    }
//
//    @Override
//    protected SoundEvent getAmbientSound() {
//        this.playSound(SoundEvents.ENTITY_WOLF_AMBIENT, 1.0f, 0.25f);
//        return null;
//    }
//
//    @Override
//    protected SoundEvent getHurtSound(DamageSource source) {
//        this.playSound(SoundEvents.ENTITY_WOLF_HURT, 1.0f, 0.25f);
//        return null;
//    }
//
//    @Override
//    protected SoundEvent getDeathSound() {
//        this.playSound(SoundEvents.ENTITY_WOLF_DEATH, 1.0f, 0.25f);
//        return null;
//    }
//
//    @Override
//    public double getPassengerAttackYOffset() {
//        return 0;
//    }
//
//    @Override
//    public EntityView method_48926() {
//        return null;
//    }
//}
