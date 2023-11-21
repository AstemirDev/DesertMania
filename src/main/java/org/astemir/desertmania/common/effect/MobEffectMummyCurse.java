package org.astemir.desertmania.common.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.astemir.api.common.misc.ParticleEmitter;
import org.astemir.api.math.components.Vector3;
import org.astemir.desertmania.common.entity.DMEntities;
import org.astemir.desertmania.common.entity.scarab.EntityScarab;


import java.awt.*;

public class MobEffectMummyCurse extends MobEffect {

    protected MobEffectMummyCurse() {
        super(MobEffectCategory.HARMFUL,new Color(94, 34, 146).hashCode());
    }


    public static void tickEffect(Player player){
        Level level = player.level;
        int tick = player.tickCount;
        if (tick % 100 > 70){
            ParticleEmitter emitter = new ParticleEmitter(ParticleEmitter.block(Blocks.SAND.defaultBlockState())).count(4).size(new Vector3(0.5f, 1.7, 0.5f));
            emitter.emit(level, new Vector3((float)player.getX(),(float)player.getY(),(float)player.getZ()), new Vector3(0, 0, 0));
        }
        if (tick % 400 == 0 && player.getRandom().nextInt(10) == 0) {
            if (!level.isClientSide) {
                for (int i = 0;i<3;i++) {
                    EntityScarab mob = DMEntities.SCARAB.get().create(level);
                    mob.setPos(player.getX(), player.getY() + 0.7f, player.getZ());
                    level.addFreshEntity(mob);
                    player.hurt(DamageSource.mobAttack(mob), 1);
                }
            }
        }
    }
}
