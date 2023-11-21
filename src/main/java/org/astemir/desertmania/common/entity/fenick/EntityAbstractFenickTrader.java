package org.astemir.desertmania.common.entity.fenick;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.*;
import org.astemir.api.common.entity.EntityData;
import org.astemir.api.common.entity.utils.TradeUtils;
import org.astemir.api.common.world.WorldUtils;
import org.astemir.desertmania.common.sound.DMSounds;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public abstract class EntityAbstractFenickTrader extends EntityAbstractFenick implements Merchant, Npc, TradeUtils.ISpecialTrader {



    public static EntityData<Boolean> DANCING = new EntityData<>(EntityAbstractFenickTrader.class,"IsDancing", EntityDataSerializers.BOOLEAN,false);

    @javax.annotation.Nullable
    private BlockPos jukebox;

    private Player tradingPlayer;

    private final DynamicGameEventListener<EntityAbstractFenickTrader.JukeboxListener> dynamicJukeboxListener;


    protected EntityAbstractFenickTrader(EntityType<? extends EntityAbstractFenick> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        PositionSource positionsource = new EntityPositionSource(this, this.getEyeHeight());
        moveControl = new MoveControl(this){
            @Override
            public void tick() {
                if (!isDancing()) {
                    super.tick();
                }
            }
        };
        dynamicJukeboxListener = new DynamicGameEventListener<>(new EntityAbstractFenickTrader.JukeboxListener(positionsource, 6));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.isDancing() && (this.jukebox == null || !this.jukebox.closerToCenterThan(this.position(), 6) || !this.level.getBlockState(this.jukebox).is(Blocks.JUKEBOX)) && this.tickCount % 20 == 0) {
            DANCING.set(this,false);
            this.jukebox = null;
        }
    }

    @Override
    public void updateDynamicGameEventListener(BiConsumer<DynamicGameEventListener<?>, ServerLevel> p_218348_) {
        Level level = this.level;
        if (level instanceof ServerLevel serverlevel) {
            p_218348_.accept(this.dynamicJukeboxListener, serverlevel);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag p_21484_) {
        super.addAdditionalSaveData(p_21484_);
        TradeUtils.saveOffers(this,p_21484_);
    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        DANCING.register(this);
    }


    @Override
    public void readAdditionalSaveData(CompoundTag p_21450_) {
        super.readAdditionalSaveData(p_21450_);
        TradeUtils.loadOffers(this,p_21450_);
    }


    @Override
    protected InteractionResult mobInteract(Player p_21472_, InteractionHand p_21473_) {
        if (getTarget() == null && WorldUtils.isDay(level)) {
            return TradeUtils.interactWithTrader(this,p_21472_);
        }
        return super.mobInteract(p_21472_, p_21473_);
    }

    @Override
    public void die(DamageSource p_35270_) {
        super.die(p_35270_);
        TradeUtils.stopTrading(this);
    }


    @Override
    public Entity changeDimension(ServerLevel p_35295_, net.minecraftforge.common.util.ITeleporter teleporter) {
        TradeUtils.stopTrading(this);
        return super.changeDimension(p_35295_, teleporter);
    }

    @Override
    public void overrideOffers(MerchantOffers p_45306_) {
    }

    @Override
    public void notifyTradeUpdated(ItemStack p_45308_) {

    }

    @Override
    public int getVillagerXp() {
        return 0;
    }

    @Override
    public void overrideXp(int p_45309_) {
    }

    @Override
    public boolean showProgressBar() {
        return false;
    }

    @Override
    public boolean canRestock() {
        return false;
    }

    @Override
    public SoundEvent getNotifyTradeSound() {
        return DMSounds.FENICK_TRADE.get();
    }


    @Override
    public boolean isClientSide() {
        return this.level.isClientSide;
    }

    @Override
    public void setTradingPlayer(@Nullable Player p_45307_) {
        TradeUtils.setTradingPlayer(this,p_45307_);
    }

    @Nullable
    @Override
    public Player getTradingPlayer() {
        return tradingPlayer;
    }

    @Override
    public void setSpecialTradingPlayer(Player player) {
        this.tradingPlayer = player;
    }


    public void setJukeboxPlaying(BlockPos p_240102_, boolean p_240103_) {
        if (p_240103_) {
            if (!this.isDancing()) {
                this.jukebox = p_240102_;
                DANCING.set(this,true);
                hasImpulse = false;
                setDeltaMovement(0,0,0);
                setZza(0);
                setXxa(0);
                getNavigation().stop();
            }
        } else if (p_240102_.equals(this.jukebox) || this.jukebox == null) {
            this.jukebox = null;
            DANCING.set(this,false);
        }
    }

    public boolean isDancing() {
        return DANCING.get(this);
    }

    class JukeboxListener implements GameEventListener {

        private final PositionSource listenerSource;
        private final int listenerRadius;

        public JukeboxListener(PositionSource p_239448_, int p_239449_) {
            this.listenerSource = p_239448_;
            this.listenerRadius = p_239449_;
        }

        public PositionSource getListenerSource() {
            return this.listenerSource;
        }

        public int getListenerRadius() {
            return this.listenerRadius;
        }

        public boolean handleGameEvent(ServerLevel p_240002_, GameEvent.Message p_240003_) {
            if (p_240003_.gameEvent() == GameEvent.JUKEBOX_PLAY) {
                EntityAbstractFenickTrader.this.setJukeboxPlaying(new BlockPos(p_240003_.source()), true);
                return true;
            } else if (p_240003_.gameEvent() == GameEvent.JUKEBOX_STOP_PLAY) {
                EntityAbstractFenickTrader.this.setJukeboxPlaying(new BlockPos(p_240003_.source()), false);
                return true;
            } else {
                return false;
            }
        }
    }
}
