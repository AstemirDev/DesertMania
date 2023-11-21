package org.astemir.desertmania.common.block;


import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.astemir.desertmania.common.blockentity.BlockEntitySunAltar;
import org.astemir.desertmania.common.item.DMItems;
import org.jetbrains.annotations.Nullable;


public class BlockBrazier extends Block implements SimpleWaterloggedBlock {

    private static final VoxelShape SHAPE_A = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D);
    private static final VoxelShape SHAPE_B = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D);
    private static final VoxelShape SHAPE_C = Block.box(2.0D, 10.0D, 2.0D, 14.0D, 12.0D, 14.0D);
    private static final VoxelShape SHAPE = Shapes.or(SHAPE_A,SHAPE_B,SHAPE_C);

    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public BlockBrazier() {
        super(BlockBehaviour.Properties.of(Material.STONE).noOcclusion().strength(3,3).lightLevel((state)->{
            if (state.getValue(LIT)){
                return 12;
            }
            return 0;
        }));
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, Boolean.valueOf(false)).setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    @Override
    public void stepOn(Level p_152431_, BlockPos p_152432_, BlockState p_152433_, Entity p_152434_) {
        if (p_152433_.getValue(LIT) && p_152434_ instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)p_152434_)) {
            p_152434_.hurt(DamageSource.IN_FIRE, 1);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_49820_) {
        return defaultBlockState().setValue(LIT,false).setValue(WATERLOGGED,false);
    }

    @Override
    public InteractionResult use(BlockState p_51274_, Level p_51275_, BlockPos p_51276_, Player p_51277_, InteractionHand p_51278_, BlockHitResult p_51279_) {
        ItemStack itemstack = p_51277_.getItemInHand(p_51278_);
        if (itemstack.is(Items.TORCH) || itemstack.is(Items.SOUL_TORCH) || itemstack.is(Items.FLINT_AND_STEEL)) {
            if (!p_51274_.getValue(LIT)){
                p_51275_.setBlock(p_51276_, p_51274_.setValue(LIT,true), 3);
                p_51275_.blockUpdated(p_51276_, Blocks.AIR);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }
        if (itemstack.is(DMItems.ANCIENT_AMULET.get())){
            if (itemstack.getDamageValue() < itemstack.getMaxDamage()) {
                p_51275_.playSound(null, p_51276_, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, 1, 1);
                p_51275_.setBlock(p_51276_, DMBlocks.SUN_ALTAR.get().defaultBlockState(), 3);
                p_51275_.blockUpdated(p_51276_, p_51274_.getBlock());
                BlockEntity blockEntity = p_51275_.getBlockEntity(p_51276_);
                if (blockEntity != null) {
                    if (blockEntity instanceof BlockEntitySunAltar sunAltar) {
                        sunAltar.setAmulet(itemstack.copy());
                    }
                }
            }else{
                popResource(p_51275_,p_51276_,itemstack.copy());
            }
            itemstack.shrink(1);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }


    private VoxelShape getVoxelShape(BlockState p_49767_) {
        return SHAPE;
    }

    public VoxelShape getCollisionShape(BlockState p_49760_, BlockGetter p_49761_, BlockPos p_49762_, CollisionContext p_49763_) {
        return this.getVoxelShape(p_49760_);
    }

    public VoxelShape getShape(BlockState p_49755_, BlockGetter p_49756_, BlockPos p_49757_, CollisionContext p_49758_) {
        return this.getVoxelShape(p_49755_);
    }

    @Override
    public void animateTick(BlockState p_220827_, Level p_220828_, BlockPos p_220829_, RandomSource p_220830_) {
        if (p_220827_.getValue(LIT)) {
            if (p_220830_.nextInt(24) == 0) {
                p_220828_.playLocalSound((double)p_220829_.getX() + 0.5D, (double)p_220829_.getY() + 0.5D, (double)p_220829_.getZ() + 0.5D, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 1.0F + p_220830_.nextFloat(), p_220830_.nextFloat() * 0.7F + 0.3F, false);
            }
            makeParticles(p_220828_,p_220829_,false,false);
        }
    }

    public static void makeParticles(Level p_220828_, BlockPos p_220829_, boolean p_51254_, boolean p_51255_) {
        RandomSource p_220830_ = p_220828_.getRandom();
        p_220828_.addAlwaysVisibleParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, true, (double)p_220829_.getX() + 0.5D + p_220830_.nextDouble() / 3.0D * (double)(p_220830_.nextBoolean() ? 1 : -1), (double)p_220829_.getY() + p_220830_.nextDouble() + p_220830_.nextDouble(), (double)p_220829_.getZ() + 0.5D + p_220830_.nextDouble() / 3.0D * (double)(p_220830_.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
        if (p_51255_) {
            p_220828_.addParticle(ParticleTypes.SMOKE, (double)p_220829_.getX() + 0.5D + p_220830_.nextDouble() / 4.0D * (double)(p_220830_.nextBoolean() ? 1 : -1), (double)p_220829_.getY() + 0.8D, (double)p_220829_.getZ() + 0.5D + p_220830_.nextDouble() / 4.0D * (double)(p_220830_.nextBoolean() ? 1 : -1), 0.0D, 0.005D, 0.0D);
        }

    }


    public static void makeParticles(LevelAccessor p_220828_, BlockPos p_220829_, boolean p_51254_, boolean p_51255_) {
        RandomSource p_220830_ = p_220828_.getRandom();
        if (p_51255_) {
            p_220828_.addParticle(ParticleTypes.SMOKE, (double)p_220829_.getX() + 0.5D + p_220830_.nextDouble() / 4.0D * (double)(p_220830_.nextBoolean() ? 1 : -1), (double)p_220829_.getY() + 0.8D, (double)p_220829_.getZ() + 0.5D + p_220830_.nextDouble() / 4.0D * (double)(p_220830_.nextBoolean() ? 1 : -1), 0.0D, 0.005D, 0.0D);
        }
    }


    public boolean placeLiquid(LevelAccessor p_51257_, BlockPos p_51258_, BlockState p_51259_, FluidState p_51260_) {
        if (!p_51259_.getValue(BlockStateProperties.WATERLOGGED) && p_51260_.getType() == Fluids.WATER) {
            boolean flag = p_51259_.getValue(LIT);
            if (flag) {
                if (!p_51257_.isClientSide()) {
                    p_51257_.playSound(null, p_51258_, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0F, 1.0F);
                }

                dowse(null, p_51257_, p_51258_, p_51259_);
            }

            p_51257_.setBlock(p_51258_, p_51259_.setValue(WATERLOGGED, Boolean.valueOf(true)).setValue(LIT, Boolean.valueOf(false)), 3);
            p_51257_.scheduleTick(p_51258_, p_51260_.getType(), p_51260_.getType().getTickDelay(p_51257_));
            return true;
        } else {
            return false;
        }
    }

    public static void dowse(@javax.annotation.Nullable Entity p_152750_, LevelAccessor p_152751_, BlockPos p_152752_, BlockState p_152753_) {
        if (p_152751_.isClientSide()) {
            for(int i = 0; i < 20; ++i) {
                makeParticles(p_152751_,p_152752_,false,true);
            }
        }

        BlockEntity blockentity = p_152751_.getBlockEntity(p_152752_);
        if (blockentity instanceof CampfireBlockEntity) {
            ((CampfireBlockEntity)blockentity).dowse();
        }

        p_152751_.gameEvent(p_152750_, GameEvent.BLOCK_CHANGE, p_152752_);
    }

    public void onProjectileHit(Level p_51244_, BlockState p_51245_, BlockHitResult p_51246_, Projectile p_51247_) {
        BlockPos blockpos = p_51246_.getBlockPos();
        if (!p_51244_.isClientSide && p_51247_.isOnFire() && p_51247_.mayInteract(p_51244_, blockpos) && !p_51245_.getValue(LIT) && !p_51245_.getValue(WATERLOGGED)) {
            p_51244_.setBlock(blockpos, p_51245_.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
        }
    }


    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_48725_) {
        p_48725_.add(LIT).add(WATERLOGGED);
    }
}
