package org.astemir.desertmania.common.entity.scarablord.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.common.action.ActionController;
import org.astemir.desertmania.common.entity.scarablord.EntityScarabLord;
import org.astemir.desertmania.common.entity.scarablord.ScarabLordActions;
import org.astemir.desertmania.common.entity.scarablord.ScarabLordMoveControl;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class ScarabLordRandomWalkGoal extends Goal {

    protected final EntityScarabLord mob;
    protected double wantedX;
    protected double wantedY;
    protected double wantedZ;
    protected int interval;
    protected boolean forceTrigger;
    private final boolean checkNoActionTime;

    public ScarabLordRandomWalkGoal(EntityScarabLord p_25734_) {
        this(p_25734_, 120);
    }

    public ScarabLordRandomWalkGoal(EntityScarabLord p_25737_, int p_25739_) {
        this(p_25737_, p_25739_, true);
    }

    public ScarabLordRandomWalkGoal(EntityScarabLord p_25741_, int p_25743_, boolean p_25744_) {
        this.mob = p_25741_;
        this.interval = p_25743_;
        this.checkNoActionTime = p_25744_;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.mob.isVehicle() && !ScarabLordMoveControl.canMove(this.mob)) {
            return false;
        } else {
            if (!this.forceTrigger) {
                int newInterval = mob.getMovementController().is(ScarabLordActions.ACTION_MOVEMENT_DOWN) ? 5 : interval;
                if (this.checkNoActionTime && this.mob.getNoActionTime() >= newInterval) {
                    return false;
                }

                if (this.mob.getRandom().nextInt(reducedTickDelay(newInterval)) != 0) {
                    return false;
                }
            }

            Vec3 vec3 = this.getPosition();
            if (vec3 == null) {
                return false;
            } else {
                this.wantedX = vec3.x;
                this.wantedY = vec3.y;
                this.wantedZ = vec3.z;
                this.forceTrigger = false;
                return true;
            }
        }
    }

    @Nullable
    protected Vec3 getPosition() {
        if (this.mob.isInWaterOrBubble()) {
            Vec3 vec3 = LandRandomPos.getPos(this.mob, 15, 7);
            return vec3 == null ? DefaultRandomPos.getPos(this.mob, 10, 7) : vec3;
        } else {
            float prob = 0.001f;
            if (mob.getMovementController().is(ScarabLordActions.ACTION_MOVEMENT_DOWN)){
                prob = 0.1f;
            }
            return this.mob.getRandom().nextFloat() >= prob ? LandRandomPos.getPos(this.mob, 10, 7) : DefaultRandomPos.getPos(this.mob, 10, 7);
        }
    }


    @Override
    public boolean canContinueToUse() {
        return !this.mob.getNavigation().isDone() && !this.mob.isVehicle() && ScarabLordMoveControl.canMove(this.mob);
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, getSpeedModifier());
    }


    @Override
    public void stop() {
        this.mob.getNavigation().stop();
        super.stop();
    }

    public float getSpeedModifier() {
        ActionController<EntityScarabLord> movementController = mob.getMovementController();
        if (movementController.is(ScarabLordActions.ACTION_MOVEMENT_FLY)){
            return 0.7f;
        }
        if (movementController.is(ScarabLordActions.ACTION_MOVEMENT_DOWN)){
            return 1f;
        }
        return 0.3f;
    }

    public void trigger() {
        this.forceTrigger = true;
    }
}
