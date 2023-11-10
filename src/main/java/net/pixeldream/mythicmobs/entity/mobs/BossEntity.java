//package net.pixeldream.mythicmobs.entity.mobs;
//
//import org.jetbrains.annotations.Nullable;
//
//public abstract class BossEntity extends HostileEntity {
//
//    protected final ServerBossBar bossBar;
//
//    protected BossEntity(EntityType<? extends HostileEntity> entityType, World world, Color barColor) {
//        super(entityType, world);
//        this.bossBar = (ServerBossBar)(new ServerBossBar(this.getDisplayName(), barColor, Style.NOTCHED_10)).setDarkenSky(true);
//        this.experiencePoints = 500;
//    }
//
//    @Override
//    protected void mobTick() {
//        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
//    }
//
//    @Override
//    public void readCustomDataFromNbt(NbtCompound nbt) {
//        super.readCustomDataFromNbt(nbt);
//        if (this.hasCustomName()) {
//            this.bossBar.setName(this.getDisplayName());
//        }
//    }
//
//    @Override
//    public void setCustomName(@Nullable Text name) {
//        super.setCustomName(name);
//        this.bossBar.setName(this.getDisplayName());
//    }
//
//    @Override
//    public void onStartedTrackingBy(ServerPlayerEntity player) {
//        super.onStartedTrackingBy(player);
//        this.bossBar.addPlayer(player);
//    }
//
//    @Override
//    public void onStoppedTrackingBy(ServerPlayerEntity player) {
//        super.onStoppedTrackingBy(player);
//        this.bossBar.removePlayer(player);
//    }
//
//    @Override
//    protected boolean shouldAlwaysDropXp() {
//        return true;
//    }
//
//    @Override
//    public boolean cannotDespawn() {
//        return true;
//    }
//
//    @Override
//    public boolean isPushable() {
//        return false;
//    }
//
//    @Override
//    protected boolean canStartRiding(Entity entity) {
//        return false;
//    }
//}