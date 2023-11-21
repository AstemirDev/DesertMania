package org.astemir.desertmania.client.render.block.sunaltar;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.client.wrapper.SkillsWrapperBlockEntity;
import org.astemir.desertmania.common.block.DMBlocks;
import org.astemir.desertmania.common.blockentity.BlockEntitySunAltar;

public class ModelWrapperSunAltar extends SkillsWrapperBlockEntity<BlockEntitySunAltar> {

    private final ModelSunAltar MODEL = new ModelSunAltar();

    @Override
    protected void setupRotations(BlockEntitySunAltar blockEntity, PoseStack stack, float partialTicks) {
        BlockEntitySunAltar altar = getRenderTarget();
        if (altar != null) {
            BlockState blockState = altar.getLevel().getBlockState(altar.getBlockPos());
            if (blockState != null && blockState.is(DMBlocks.SUN_ALTAR.get())) {
                if (blockState.hasProperty(HorizontalDirectionalBlock.FACING)) {
                    Direction direction = blockState.getValue(HorizontalDirectionalBlock.FACING);
                    stack.mulPose(Vector3f.YP.rotationDegrees(direction.toYRot()));
                }
            }
        }
    }

    @Override
    public SkillsModel<BlockEntitySunAltar, IDisplayArgument> getModel(BlockEntitySunAltar target) {
        return MODEL;
    }
}
