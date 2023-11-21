package org.astemir.desertmania.client.render.block.genielamp;

import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsAnimatedModel;
import org.astemir.api.io.ResourceUtils;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.blockentity.BlockEntityGenieLamp;

public class ModelGenieLamp extends SkillsAnimatedModel<BlockEntityGenieLamp, IDisplayArgument> {

    public static final ResourceLocation MODEL = ResourceUtils.loadModel(DesertMania.MOD_ID,"block/genie_lamp.geo.json");
    public static final ResourceLocation ANIMATIONS = ResourceUtils.loadAnimation(DesertMania.MOD_ID,"block/genie_lamp.animation.json");
    public static final ResourceLocation TEXTURE = ResourceUtils.loadTexture(DesertMania.MOD_ID,"block/genie_lamp.png");

    public ModelGenieLamp() {
        super(MODEL,ANIMATIONS);
    }

    @Override
    public ResourceLocation getTexture(BlockEntityGenieLamp target) {
        return ResourceUtils.loadTexture(DesertMania.MOD_ID,"block/genie_lamp.png");
    }
}
