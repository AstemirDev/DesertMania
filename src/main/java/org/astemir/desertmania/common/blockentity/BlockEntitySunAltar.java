package org.astemir.desertmania.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.common.animation.*;
import org.astemir.api.common.animation.objects.IAnimatedBlock;
import org.astemir.api.common.misc.ICustomRendered;
import org.astemir.api.common.misc.ParticleEmitter;
import org.astemir.api.math.MathUtils;
import org.astemir.api.math.components.Color;
import org.astemir.api.math.components.Vector3;
import org.astemir.desertmania.common.block.DMBlocks;
import org.astemir.desertmania.common.item.DMItems;
import org.astemir.desertmania.common.particle.GlowingDustParticleOptions;
import org.astemir.desertmania.common.world.DMWorldData;

public class BlockEntitySunAltar extends AnimatedBlockEntity implements ICustomRendered {

    private ItemStack amulet = DMItems.ANCIENT_AMULET.get().getDefaultInstance();

    private final AnimationFactory animationFactory = new AnimationFactory(this,IDLE);
    public static Animation IDLE = new Animation("animation.model.idle",3.12f);

    public BlockEntitySunAltar(BlockPos p_155229_, BlockState p_155230_) {
        super(DMBlockEntities.SUN_ALTAR.get(), p_155229_, p_155230_);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        amulet = ItemStack.of(pTag.getCompound("Amulet"));
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("Amulet",amulet.serializeNBT());
    }

    @Override
    public void onChunkUnloaded() {
        super.onChunkUnloaded();
        if (!level.isClientSide) {
            DMWorldData.getInstance(level).setTimeStop(level, false);
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (level != null) {
            if (!level.isClientSide) {
                DMWorldData.getInstance(level).setTimeStop(level, false);
            }
        }
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, IAnimatedBlock block) {
        super.tick(level, pos, state, block);
        if (getTicks() < 2){
            ParticleEmitter.emit(new GlowingDustParticleOptions(Color.WHITE.toVector3f(),2f),level, Vector3.from(Vec3.atCenterOf(pos).add(0,0.5f,0)),new Vector3(0,0,0),new Vector3(0,0,0),1);
        }
        if (getTicks() < 40){
            ParticleEmitter.emit(new GlowingDustParticleOptions(Color.YELLOW.toVector3f(),1),level, Vector3.from(Vec3.atCenterOf(pos)).add(MathUtils.cos(getTicks())/4f,0.5f+((float)getTicks())/8f,MathUtils.sin(getTicks())/4f),new Vector3(0,0,0),new Vector3(0,0,0),1);
            ParticleEmitter.emit(new GlowingDustParticleOptions(Color.WHITE.toVector3f(),0.5f),level, Vector3.from(Vec3.atCenterOf(pos)).add(MathUtils.cos(getTicks())/4f,0.5f+((float)getTicks())/8f,MathUtils.sin(getTicks())/4f),new Vector3(0,0,0),new Vector3(0,0,0),1);

            ParticleEmitter.emit(new GlowingDustParticleOptions(Color.PURPLE.toVector3f(),1),level, Vector3.from(Vec3.atCenterOf(pos)).add(-MathUtils.cos(getTicks())/4f,0.5f+((float)getTicks())/8f,-MathUtils.sin(getTicks())/4f),new Vector3(0,0,0),new Vector3(0,0,0),1);
            ParticleEmitter.emit(new GlowingDustParticleOptions(Color.WHITE.toVector3f(),0.5f),level, Vector3.from(Vec3.atCenterOf(pos)).add(-MathUtils.cos(getTicks())/4f,0.5f+((float)getTicks())/8f,-MathUtils.sin(getTicks())/4f),new Vector3(0,0,0),new Vector3(0,0,0),1);

        }
        if (amulet != null && !amulet.isEmpty()) {
            if (amulet.is(DMItems.ANCIENT_AMULET.get())) {
                if (!level.isClientSide) {
                    if (!DMWorldData.getInstance(level).isTimeStop()) {
                        DMWorldData.getInstance(level).setTimeStop(level, true);
                    }
                    if (getTicks() % 20 == 0) {
                        if (amulet.getDamageValue() < amulet.getMaxDamage()) {
                            amulet.setDamageValue(amulet.getDamageValue()+1);
                        } else {
                            Block.popResource(level,pos,amulet);
                            level.setBlock(pos, DMBlocks.BRAZIER.get().defaultBlockState(), 19);
                            level.playSound(null,pos, SoundEvents.BEACON_DEACTIVATE, SoundSource.BLOCKS,1,1);
                            DMWorldData.getInstance(level).setTimeStop(level,false);
                        }
                    }
                }
            }
        }
        IDLE.loop(Animation.Loop.TRUE);
        animationFactory.play(IDLE);
        animationFactory.updateAnimations();
    }

    public ItemStack getAmulet() {
        return amulet;
    }

    public void setAmulet(ItemStack amulet) {
        this.amulet = amulet;
        setChanged();
    }

    @Override
    public <K extends IDisplayArgument> AnimationFactory getAnimationFactory(K argument) {
        return animationFactory;
    }
}
