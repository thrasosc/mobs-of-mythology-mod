package net.pixeldream.mythicmobs.goal;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.Box;
import net.minecraft.world.GameRules;
import net.pixeldream.mythicmobs.entity.AbstractKoboldEntity;
import org.jetbrains.annotations.Nullable;

public class KoboldRevengeGoal extends TrackTargetGoal {
    private static final TargetPredicate VALID_AVOIDABLES_PREDICATE = TargetPredicate.createAttackable().ignoreVisibility().ignoreDistanceScalingFactor();
    private boolean groupRevenge;
    private int lastAttackedTime;
    private final Class<?>[] noRevengeTypes;
    @Nullable
    private Class<?>[] noHelpTypes;

    public KoboldRevengeGoal(PathAwareEntity mob, Class<?>... noRevengeTypes) {
        super(mob, true);
        this.noRevengeTypes = noRevengeTypes;
        this.setControls(EnumSet.of(Control.TARGET));
    }

    public boolean canStart() {
        int i = this.mob.getLastAttackedTime();
        LivingEntity livingEntity = this.mob.getAttacker();
        if (i != this.lastAttackedTime && livingEntity != null) {
            if (livingEntity.getType() == EntityType.PLAYER && this.mob.world.getGameRules().getBoolean(GameRules.UNIVERSAL_ANGER)) {
                return false;
            } else {
                Class[] var3 = this.noRevengeTypes;
                int var4 = var3.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    Class<?> class_ = var3[var5];
                    if (class_.isAssignableFrom(livingEntity.getClass())) {
                        return false;
                    }
                }

                return this.canTrack(livingEntity, VALID_AVOIDABLES_PREDICATE);
            }
        } else {
            return false;
        }
    }

    public KoboldRevengeGoal setGroupRevenge(Class<?>... noHelpTypes) {
        this.groupRevenge = true;
        this.noHelpTypes = noHelpTypes;
        return this;
    }

    public void start() {
        this.mob.setTarget(this.mob.getAttacker());
        this.target = this.mob.getTarget();
        this.lastAttackedTime = this.mob.getLastAttackedTime();
        this.maxTimeWithoutVisibility = 300;
        if (this.groupRevenge) {
            this.callSameTypeForRevenge();
        }

        super.start();
    }

    protected void callSameTypeForRevenge() {
        double d = this.getFollowRange();
        Box box = Box.from(this.mob.getPos()).expand(d, 10.0, d);
        List<? extends MobEntity> list = this.mob.world.getEntitiesByClass(AbstractKoboldEntity.class, box, EntityPredicates.EXCEPT_SPECTATOR);
        Iterator var5 = list.iterator();

        while(true) {
            MobEntity mobEntity;
            boolean bl;
            do {
                do {
                    do {
                        do {
                            do {
                                if (!var5.hasNext()) {
                                    return;
                                }

                                mobEntity = (MobEntity)var5.next();
                            } while(this.mob == mobEntity);
                        } while(mobEntity.getTarget() != null);
                    } while(this.mob instanceof TameableEntity && ((TameableEntity)this.mob).getOwner() != ((TameableEntity)mobEntity).getOwner());
                } while(mobEntity.isTeammate(this.mob.getAttacker()));

                if (this.noHelpTypes == null) {
                    break;
                }

                bl = false;
                Class[] var8 = this.noHelpTypes;
                int var9 = var8.length;

                for(int var10 = 0; var10 < var9; ++var10) {
                    Class<?> class_ = var8[var10];
                    if (mobEntity.getClass() == class_) {
                        bl = true;
                        break;
                    }
                }
            } while(bl);

            this.setMobEntityTarget(mobEntity, this.mob.getAttacker());
        }
    }

    protected void setMobEntityTarget(MobEntity mob, LivingEntity target) {
        mob.setTarget(target);
    }
}
