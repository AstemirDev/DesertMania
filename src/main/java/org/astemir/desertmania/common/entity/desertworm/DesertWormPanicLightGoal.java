package org.astemir.desertmania.common.entity.desertworm;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.astemir.desertmania.common.world.DMWorldData;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class DesertWormPanicLightGoal<T extends LivingEntity> extends Goal {

    protected final PathfinderMob mob;
    private BlockPos avoidPos;
    private final double walkSpeedModifier;
    private final double sprintSpeedModifier;
    protected final int maxDist;
    @Nullable
    protected Path path;
    protected final PathNavigation pathNav;


    public DesertWormPanicLightGoal(PathfinderMob mob, int maxDist, double walkSpeed, double sprintSpeed) {
        this.mob = mob;
        this.maxDist = maxDist;
        this.walkSpeedModifier = walkSpeed;
        this.sprintSpeedModifier = sprintSpeed;
        this.pathNav = mob.getNavigation();
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }


    public boolean canUse() {
        if (DMWorldData.getInstance(mob.level).getSandstorm().isEnabled()){
            return false;
        }
        Vec3 pos = this.mob.position();
        for (int i = -maxDist;i<maxDist;i++){
            for (int j = -maxDist;j<maxDist;j++){
                for (int k = -maxDist;k<maxDist;k++){
                    BlockPos blockPos = new BlockPos(pos.x+i,pos.y+j,pos.z+k);
                    int light = this.mob.level.getBrightness(LightLayer.BLOCK,blockPos);
                    if (light >= 5){
                        avoidPos = blockPos;
                    }
                }
            }
        }
        if (this.avoidPos == null) {
            return false;
        } else {
            Vec3 vec3 = DefaultRandomPos.getPosAway(this.mob, 16, 7, Vec3.atCenterOf(avoidPos));
            if (vec3 == null) {
                return false;
            } else if (avoidPos.distSqr(new BlockPos(vec3.x, vec3.y, vec3.z)) < avoidPos.distSqr(this.mob.blockPosition())) {
                return false;
            } else {
                this.path = this.pathNav.createPath(vec3.x, vec3.y, vec3.z, 0);
                return this.path != null;
            }
        }
    }

    public boolean canContinueToUse() {
        return !this.pathNav.isDone();
    }

    public void start() {
        this.pathNav.moveTo(this.path, this.walkSpeedModifier);
    }

    public void stop() {
        this.avoidPos = null;
    }

    public void tick() {
        if (this.mob.distanceToSqr(Vec3.atCenterOf(avoidPos)) < 49.0D) {
            this.mob.getNavigation().setSpeedModifier(this.sprintSpeedModifier);
        } else {
            this.mob.getNavigation().setSpeedModifier(this.walkSpeedModifier);
        }
    }

}
