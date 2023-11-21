package org.astemir.desertmania.client.render.block.genielamp;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.world.level.block.state.BlockState;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.client.wrapper.SkillsWrapperBlockEntity;
import org.astemir.desertmania.common.block.BlockGenieLamp;
import org.astemir.desertmania.common.block.DMBlocks;
import org.astemir.desertmania.common.blockentity.BlockEntityGenieLamp;

public class ModelWrapperGenieLamp extends SkillsWrapperBlockEntity<BlockEntityGenieLamp> {

    private final ModelGenieLamp MODEL = new ModelGenieLamp();

    @Override
    protected void setupRotations(BlockEntityGenieLamp blockEntity, PoseStack stack, float partialTicks) {
        BlockEntityGenieLamp genieLamp = getRenderTarget();
        if (genieLamp != null) {
            BlockState blockState = genieLamp.getLevel().getBlockState(genieLamp.getBlockPos());
            if (blockState != null && blockState.is(DMBlocks.GENIE_LAMP.get())) {
                int rot = blockState.getValue(BlockGenieLamp.ROTATION);
                stack.mulPose(Vector3f.YP.rotationDegrees(rot*22.5f));
            }
        }
    }

    @Override
    public SkillsModel<BlockEntityGenieLamp, IDisplayArgument> getModel(BlockEntityGenieLamp target) {
        return MODEL;
    }
}
