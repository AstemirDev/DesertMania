package org.astemir.desertmania.common.entity;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.astemir.desertmania.common.item.DMItems;

public class EntityStingArrow extends AbstractArrow {

    protected EntityStingArrow(EntityType<EntityStingArrow> type,Level p_36722_) {
        super(type, p_36722_);
    }

    public EntityStingArrow(Level pLevel, LivingEntity pShooter) {
        super(DMEntities.STING_ARROW.get(), pShooter, pLevel);
    }

    public EntityStingArrow(Level pLevel, double pX, double pY, double pZ) {
        super(DMEntities.STING_ARROW.get(), pX, pY, pZ, pLevel);
    }

    protected void doPostHurtEffects(LivingEntity pLiving) {
        super.doPostHurtEffects(pLiving);
        MobEffectInstance mobeffectinstance = new MobEffectInstance(MobEffects.POISON, 200, 4);
        pLiving.addEffect(mobeffectinstance, this.getEffectSource());
    }

    @Override
    protected ItemStack getPickupItem() {
        return DMItems.STING_ARROW.get().getDefaultInstance();
    }
}
