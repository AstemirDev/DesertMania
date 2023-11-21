package org.astemir.desertmania.common.enchantments;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.astemir.api.common.entity.EntityPredicates;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.common.misc.ParticleEmitter;
import org.astemir.api.math.MathUtils;
import org.astemir.api.math.components.Vector3;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.desertmania.common.item.ItemScimitar;
import org.astemir.desertmania.common.misc.IDMPlayer;
import org.astemir.desertmania.common.sound.DMSounds;

public class EnchantmentWhirling extends Enchantment {

    protected EnchantmentWhirling() {
        super(Rarity.RARE, DMEnchantmentCategory.SCIMITAR, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public static void use(Player player){
        if (IDMPlayer.asIDMPlayer(player).isSpinningWithScimitar()){
            Level level = player.level;
            float rot = player.tickCount%360;
            ItemStack a = player.getItemInHand(InteractionHand.MAIN_HAND);
            ItemStack b = player.getItemInHand(InteractionHand.OFF_HAND);
            if (player.tickCount % 2 == 0) {
                for (LivingEntity entity : EntityUtils.getEntities(LivingEntity.class, player.level, player.blockPosition(), 2, EntityPredicates.exclude(player))) {
                    EntityUtils.damageEntity(player,entity);
                    if (player.getRandom().nextInt(5) == 0) {
                        a.hurtAndBreak(1, player, (p_43388_) -> {
                            p_43388_.broadcastBreakEvent(InteractionHand.MAIN_HAND);
                        });
                    }
                    if (player.getRandom().nextInt(5) == 0) {
                        b.hurtAndBreak(1, player, (p_43388_) -> {
                            p_43388_.broadcastBreakEvent(InteractionHand.OFF_HAND);
                        });
                    }
                }
                level.playSound(null, player.blockPosition(), DMSounds.SCIMITAR_WHIRL.get(), SoundSource.PLAYERS, 1, RandomUtils.randomFloat(0.9f, 1.2f));
            }
            player.animationSpeed = 0.5F;
            player.move(MoverType.PLAYER,player.getViewVector(0).multiply(0.25f,0,0.25f));
            new ParticleEmitter(ParticleTypes.SWEEP_ATTACK).count(2).emit(player.level, Vector3.from(player.position().add(0,0.5f,0)).add(MathUtils.cos(rot)*1.25f, 1f, MathUtils.sin(rot)*1.25f).add(Vector3.from(player.getDeltaMovement())),new Vector3(0, 0, 0));
            new ParticleEmitter(ParticleEmitter.block(level.getBlockState(player.getOnPos()))).size(new Vector3(0.5f,0.1f,0.5f)).count(2).emit(player.level, Vector3.from(player.position()),new Vector3(0,0,0));
        }
    }

    @Override
    public boolean canEnchant(ItemStack p_44689_) {
        return p_44689_.getItem() instanceof ItemScimitar;
    }


    @Override
    public int getMinCost(int p_44679_) {
        return 10 + p_44679_ * 7;
    }

    @Override
    public int getMaxCost(int p_44691_) {
        return 50;
    }

    @Override
    public boolean isCurse() {
        return false;
    }

    @Override
    public boolean isTreasureOnly() {
        return false;
    }

    @Override
    public boolean isTradeable() {
        return true;
    }

    @Override
    public boolean isDiscoverable() {
        return true;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return true;
    }

    @Override
    protected boolean checkCompatibility(Enchantment pOther) {
        return super.checkCompatibility(pOther) && pOther != Enchantments.MENDING;
    }

    @Override
    public boolean allowedInCreativeTab(Item book, CreativeModeTab tab) {
        return super.allowedInCreativeTab(book, tab) || tab == CreativeModeTab.TAB_COMBAT;
    }
}
