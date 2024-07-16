package net.pixeldreamstudios.mobs_of_mythology.entity.monster;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.variant.KoboldVariant;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
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
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class KoboldEntity extends AbstractKoboldEntity implements SmartBrainOwner<KoboldEntity> {
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(KoboldEntity .class, EntityDataSerializers.ITEM_STACK);;

    public KoboldEntity(EntityType<? extends AbstractKoboldEntity> entityType, Level world) {
        super(entityType, world, Monster.XP_REWARD_SMALL);
        navigation = new SmoothGroundNavigation(this, level());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ITEM_STACK, ItemStack.EMPTY);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        if (!getItemStack().isEmpty()) {
            nbt.put("ItemStack", this.getItemStack().save(this.registryAccess()));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setItemStack(ItemStack.parse(this.registryAccess(), nbt.getCompound("ItemStack")).orElse(ItemStack.EMPTY));
    }

    public void setItemStack(ItemStack itemStack) {
        this.getEntityData().set(DATA_ITEM_STACK, itemStack);
        this.playSound(SoundEvents.VINDICATOR_CELEBRATE, 1.0f, 2.0f);
        this.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
    }

    public ItemStack getItemStack() {
        return this.getEntityData().get(DATA_ITEM_STACK);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
        KoboldVariant variant = Util.getRandom(KoboldVariant.values(), this.random);
        setVariant(variant);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    @Override
    public void die(DamageSource arg) {
        super.die(arg);
        this.spawnAtLocation(getItemStack());
        setItemStack(ItemStack.EMPTY);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, MobsOfMythology.config.koboldHealth)
                .add(Attributes.ATTACK_DAMAGE, MobsOfMythology.config.koboldAttackDamage)
                .add(Attributes.ATTACK_SPEED, 2)
                .add(Attributes.ATTACK_KNOCKBACK, 1)
                .add(Attributes.MOVEMENT_SPEED, 0.3);
    }

    @Override
    public List<ExtendedSensor<KoboldEntity>> getSensors() {
        return ObjectArrayList.of(
                new NearbyLivingEntitySensor<KoboldEntity>()
                        .setPredicate((target, entity) -> target instanceof Player),
                new HurtBySensor<>()
        );
    }

    @Override
    public BrainActivityGroup<KoboldEntity> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new FloatToSurfaceOfFluid<>(),
                new LookAtTarget(),
                new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<KoboldEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<KoboldEntity>(
                        new TargetOrRetaliate<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>(),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60))));
    }

    //TODO re-implement item stealing using `ItemTemptingSensor` and `FollowTemptation`
    @Override
    public BrainActivityGroup<KoboldEntity> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>().invalidateIf((target, entity) -> !target.isAlive() || !entity.hasLineOfSight(target)),
                new SetWalkTargetToAttackTarget<>().startCondition(mob -> getItemStack().isEmpty() && !getTarget().getItemInHand(InteractionHand.MAIN_HAND).isEmpty()),
                new AnimatableMeleeAttack<>(10)
                        .startCondition(mob -> getItemStack().isEmpty() && !getTarget().getItemInHand(InteractionHand.MAIN_HAND).isEmpty())
                        .stopIf(mob -> !getItemStack().isEmpty())
                        .whenStopping(mob -> {
                            LivingEntity target = getTarget();
		                    setItemStack(target.getItemInHand(InteractionHand.MAIN_HAND).copy());
                            target.getItemInHand(InteractionHand.MAIN_HAND).shrink(getItemStack().getCount());
                        }),
                new FleeTarget<>()
                        .speedModifier(2.0f)
                        .startCondition(mob -> !getItemStack().isEmpty() || BrainUtils.getTargetOfEntity(this).is(BrainUtils.getLastAttacker(this)))
        );
    }

    // TODO implement eating (see gigeresque)
//    public void tick() {
//        super.tick();
//        super.tick();
//        ItemStack itemStack = getItemStack();
//        if (!itemStack.isEmpty()) {
//            if (itemStack.is(ItemTags.WOLF_FOOD)) {
//                eat(level(), itemStack);
//            }
//        }
//    }

    @Override
    public <T> T getVariant() {
        return (T) KoboldVariant.byId(this.getTypeVariant() & 255);
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);
    }

    private void setVariant(KoboldVariant variant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }
}
