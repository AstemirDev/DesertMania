package org.astemir.desertmania.client.render.block.basket;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.client.wrapper.SkillsWrapperBlockEntity;
import org.astemir.desertmania.common.block.BlockBasket;
import org.astemir.desertmania.common.block.DMBlocks;
import org.astemir.desertmania.common.blockentity.BlockEntityBasket;

public class ModelWrapperBasket extends SkillsWrapperBlockEntity<BlockEntityBasket> {

    private final ModelBasket MODEL = new ModelBasket();

    @Override
    protected void setupRotations(BlockEntityBasket blockEntity, PoseStack stack, float partialTicks) {
        BlockEntityBasket genieLamp = getRenderTarget();
        if (genieLamp != null) {
            BlockState blockState = genieLamp.getLevel().getBlockState(genieLamp.getBlockPos());
            if (blockState != null && blockState.is(DMBlocks.BASKET.get())) {
                Direction direction = blockState.getValue(BlockBasket.FACING);
                stack.mulPose(Vector3f.YP.rotationDegrees(direction.toYRot()));
            }
        }
    }

    @Override
    public SkillsModel<BlockEntityBasket, IDisplayArgument> getModel(BlockEntityBasket target) {
        return MODEL;
    }
}
