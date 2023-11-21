package org.astemir.desertmania.client.render.block.magicbasket;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.client.wrapper.SkillsWrapperBlockEntity;
import org.astemir.desertmania.common.block.BlockBasket;
import org.astemir.desertmania.common.block.DMBlocks;
import org.astemir.desertmania.common.blockentity.BlockEntityMagicBasket;

public class ModelWrapperMagicBasket extends SkillsWrapperBlockEntity<BlockEntityMagicBasket> {

    private final ModelMagicBasket MODEL = new ModelMagicBasket();

    @Override
    protected void setupRotations(BlockEntityMagicBasket blockEntity, PoseStack stack, float partialTicks) {
        BlockEntityMagicBasket basket = getRenderTarget();
        if (basket != null) {
            BlockState blockState = basket.getLevel().getBlockState(basket.getBlockPos());
            if (blockState != null && blockState.is(DMBlocks.MAGIC_BASKET.get())) {
                Direction direction = blockState.getValue(BlockBasket.FACING);
                stack.mulPose(Vector3f.YP.rotationDegrees(direction.toYRot()));
            }
        }
    }

    @Override
    public SkillsModel<BlockEntityMagicBasket, IDisplayArgument> getModel(BlockEntityMagicBasket target) {
        return MODEL;
    }
}
