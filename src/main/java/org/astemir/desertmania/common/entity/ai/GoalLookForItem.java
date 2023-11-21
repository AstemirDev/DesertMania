package org.astemir.desertmania.common.entity.ai;


import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class GoalLookForItem extends Goal {


    private final Mob mob;
    private final double speed;
    private final double radius;
    private final Predicate<ItemEntity> predicate;

    public GoalLookForItem(Mob mob, double speed, double radius,Predicate<ItemEntity> predicate) {
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        this.mob = mob;
        this.speed = speed;
        this.radius = radius;
        this.predicate = predicate;
    }

    @Override
    public boolean canUse() {
        if (!canSearch(mob)) {
            return false;
        } else if (mob.getTarget() == null && mob.getLastHurtByMob() == null) {
            List<ItemEntity> list = mob.level.getEntitiesOfClass(ItemEntity.class, mob.getBoundingBox().inflate(radius, 8.0D, radius),predicate);
            return !list.isEmpty() && canSearch(mob);
        } else {
            return false;
        }
    }

    @Override
    public void tick() {
        List<ItemEntity> list = mob.level.getEntitiesOfClass(ItemEntity.class, mob.getBoundingBox().inflate(radius, 8.0D, radius), predicate);
        if (canSearch(mob) && !list.isEmpty()) {
            ItemEntity item = list.get(0);
            mob.getNavigation().moveTo(item.getX(),item.getY(),item.getZ(),speed);
        }
    }

    @Override
    public void start() {
        List<ItemEntity> list = mob.level.getEntitiesOfClass(ItemEntity.class, mob.getBoundingBox().inflate(radius, 8.0D, radius), predicate);
        if (!list.isEmpty()) {
            ItemEntity item = list.get(0);
            mob.getNavigation().moveTo(item.getX(),item.getY(),item.getZ(), speed);
        }
    }

    public boolean canSearch(Mob mob){
        return true;
    }
}
