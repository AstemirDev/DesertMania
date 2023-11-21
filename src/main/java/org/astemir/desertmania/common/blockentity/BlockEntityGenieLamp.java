package org.astemir.desertmania.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.common.animation.AnimatedBlockEntity;
import org.astemir.api.common.animation.Animation;
import org.astemir.api.common.animation.AnimationFactory;
import org.astemir.api.common.animation.objects.IAnimatedBlock;
import org.astemir.api.common.handler.WorldEventHandler;
import org.astemir.api.common.misc.ParticleEmitter;
import org.astemir.api.math.MathUtils;
import org.astemir.api.math.components.Color;
import org.astemir.api.math.components.Vector3;
import org.astemir.api.network.PacketArgument;
import org.astemir.desertmania.client.handler.GenieClientEventHandler;
import org.astemir.desertmania.common.block.BlockGenieLamp;
import org.astemir.desertmania.common.entity.genie.EntityAbstractGenie;
import org.astemir.desertmania.common.entity.genie.misc.GenieData;
import org.astemir.desertmania.common.particle.GlowingDustParticleOptions;

import javax.annotation.Nullable;

public class BlockEntityGenieLamp extends AnimatedBlockEntity {

    public static Animation ANIMATION_SHAKE = new Animation("animation.model.spawn",1.8f);

    private final GenieData genieData = new GenieData();

    private boolean isEmpty = false;

    @Nullable
    private Player clickedPlayer;

    private final AnimationFactory animationFactory = new AnimationFactory(this,ANIMATION_SHAKE){
        @Override
        public void onAnimationEnd(Animation animation) {
            if (animation == ANIMATION_SHAKE) {
                spawnGenie(clickedPlayer);
            }
        }
    };

    public BlockEntityGenieLamp(BlockPos pos, BlockState state) {
        super(DMBlockEntities.GENIE_LAMP.get(), pos, state);
    }

    @Override
    public void setLevel(Level p_155231_) {
        super.setLevel(p_155231_);
    }


    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    @Override
    public void load(CompoundTag p_155245_) {
        super.load(p_155245_);
        if (p_155245_.contains("IsEmpty")) {
            isEmpty = p_155245_.getBoolean("IsEmpty");
        }
        if (p_155245_.contains("GenieData")){
            genieData.load(p_155245_.getCompound("GenieData"));
            if (level != null) {
                WorldEventHandler.playClientEvent(level,getBlockPos(), GenieClientEventHandler.EVENT_GENIE_LAMP_SYNC.getValue(),getGenieData().entityIdPacket(), PacketArgument.create(PacketArgument.ArgumentType.COLOR, getGenieData().getColor()));
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag p_187471_) {
        super.saveAdditional(p_187471_);
        if (!genieData.isEmpty()) {
            p_187471_.put("GenieData", genieData.save());
        }
        p_187471_.putBoolean("IsEmpty",isEmpty);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, IAnimatedBlock block) {
        super.tick(level,pos,state,block);
        if (getAnimationFactory().isPlaying(ANIMATION_SHAKE)) {
            Color color = genieData.getColor();
            int tick = getAnimationFactory().getAnimationTick(ANIMATION_SHAKE)/3;
            float rot = getBlockState().getValue(BlockGenieLamp.ROTATION) * 22.5f;
            for (int i = 0; i < 5; i++) {
                Vector3 dir = Vector3.fromYawPitch(-rot + MathUtils.sin(tick / 2 + i * 5) * 20 * MathUtils.progressOfTime(tick + i * 5, 40), 0).mul(0.3f + 1.5f * MathUtils.progressOfTime(tick + i * 5, 40));
                Vector3 position = Vector3.from(Vec3.atCenterOf(getBlockPos()));
                position = position.add(dir.add(0, -0.25f + 0.25f * MathUtils.progressOfTime(tick + i * 5, 40), 0));
                new ParticleEmitter(new GlowingDustParticleOptions(color.toVector3f(), 0.5f)).emit(level, position, new Vector3(0, 0, 0));
            }
            if (getAnimationFactory().getAnimationTick(ANIMATION_SHAKE) == (ANIMATION_SHAKE.getLength()*20)){
                Vector3 position = getLampNosePosition();
                new ParticleEmitter(new GlowingDustParticleOptions(color.toVector3f(), 0.5f)).count(16).size(new Vector3(0.5f,1,0.5f)).emit(level, position, new Vector3(0, 0, 0));
            }
        }
    }



    public Vector3 getLampNosePosition(){
        float rot = getBlockState().getValue(BlockGenieLamp.ROTATION)*22.5f;
        Vector3 dir = Vector3.fromYawPitch(-rot,0);
        Vector3 position = Vector3.from(Vec3.atCenterOf(getBlockPos()));
        position = position.add(dir);
        return position;
    }

    public EntityAbstractGenie spawnGenie(Player player){
        if (!isEmpty && clickedPlayer != null) {
            Vector3 position = getLampNosePosition();
            EntityAbstractGenie genie = (EntityAbstractGenie) genieData.getType().create(level);
            genie.setPos(position.x,position.y,position.z);
            genie.setOwner(player);
            level.addFreshEntity(genie);
            genie.getSpawnController().playAction(EntityAbstractGenie.ACTION_SPAWN);
            genie.playClientEvent(0);
            isEmpty = true;
            return genie;
        }
        return null;
    }

    public void setClickedPlayer(@Nullable Player clickedPlayer) {
        this.clickedPlayer = clickedPlayer;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public GenieData getGenieData() {
        return genieData;
    }

    @Override
    public <K extends IDisplayArgument> AnimationFactory getAnimationFactory(K argument) {
        return animationFactory;
    }
}
