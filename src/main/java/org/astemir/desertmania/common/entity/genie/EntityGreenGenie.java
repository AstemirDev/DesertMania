package org.astemir.desertmania.common.entity.genie;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.common.action.Action;
import org.astemir.api.common.action.ActionController;
import org.astemir.api.common.action.ActionStateMachine;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.desertmania.common.entity.genie.misc.GenieAnimations;
import org.astemir.desertmania.common.utils.MiscUtils;

import java.util.Arrays;

public class EntityGreenGenie extends EntityAbstractGenie{

    public boolean clientSideKushSkin = false;

    public ActionController jackpotController = new ActionController(this,"jackpotController",ACTION_JACKPOT){
        @Override
        public void onActionBegin(Action state) {
            if (state == ACTION_JACKPOT) {
                Player owner = EntityGreenGenie.this.getOwner();
                if (owner != null) {
                    level.playSound(null,owner.blockPosition(),SoundEvents.PLAYER_LEVELUP,SoundSource.PLAYERS,1,0.5f);
                    level.playSound(null,owner.blockPosition(),SoundEvents.PLAYER_LEVELUP,SoundSource.PLAYERS,0.5f,0.75f);
                    level.playSound(null,owner.blockPosition(),SoundEvents.PLAYER_LEVELUP,SoundSource.PLAYERS,0.25f,1.75f);
                    MiscUtils.showJackpot(owner);
                }
            }
        }

        @Override
        public void onActionTick(Action state, int ticks) {
            if (state == ACTION_JACKPOT) {
                if ((state.getLength()-ticks) > 10 && ticks % 4 == 0) {
                    Player player = EntityGreenGenie.this.getOwner();
                    if (player != null) {
                        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.AMBIENT, 1, 0.75f);
                        ItemStack item = RandomUtils.randomElement(Arrays.asList(Items.DIAMOND.getDefaultInstance(), Items.GOLD_INGOT.getDefaultInstance(), Items.GOLDEN_APPLE.getDefaultInstance(), Items.EMERALD.getDefaultInstance(), Items.GOLD_NUGGET.getDefaultInstance()));
                        throwItem(item, player.getEyePosition().add(RandomUtils.randomFloat(-0.5f, 0.5f), 1 + RandomUtils.randomFloat(-0.5f, 0.5f), RandomUtils.randomFloat(-0.5f, 0.5f)));
                    }
                }
            }
        }
    };
    public static Action ACTION_JACKPOT = new Action(0,"jackpot",100);

    private final ActionStateMachine stateMachine = new ActionStateMachine(spawnController,jackpotController);

    public EntityGreenGenie(EntityType<? extends PathfinderMob> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }


    @Override
    public void aiStep() {
        super.aiStep();
        if (tickCount % 1000 == 0) {
            if (random.nextInt(100) == 0){
                jackpotController.playAction(ACTION_JACKPOT);
            }
        }
        if (jackpotController.is()) {
            getAnimationFactory().play(GenieAnimations.ANIMATION_JACKPOT);
        }else{
            animation();
        }
    }


    public void throwItem(ItemStack stack,Vec3 pos){
        ItemEntity item = EntityType.ITEM.create(level);
        item.setItem(stack);
        item.setGlowingTag(true);
        item.setPos(pos.x,pos.y,pos.z);
        level.addFreshEntity(item);
        item.hasImpulse = true;
        item.setDeltaMovement(RandomUtils.randomFloat(-0.1f,0.2f),0.5f,RandomUtils.randomFloat(-0.1f,0.2f));
    }

    @Override
    public ActionStateMachine getActionStateMachine() {
        return stateMachine;
    }
}
