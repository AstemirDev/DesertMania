package org.astemir.desertmania.client.render.block.sunaltar;

import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsAnimatedModel;
import org.astemir.api.io.ResourceUtils;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.blockentity.BlockEntitySunAltar;

public class ModelSunAltar extends SkillsAnimatedModel<BlockEntitySunAltar, IDisplayArgument> {

    public static final ResourceLocation MODEL = ResourceUtils.loadModel(DesertMania.MOD_ID,"block/sun_altar.geo.json");
    public static final ResourceLocation TEXTURE = ResourceUtils.loadTexture(DesertMania.MOD_ID,"block/sun_altar.png");
    public static final ResourceLocation ANIMATIONS = ResourceUtils.loadAnimation(DesertMania.MOD_ID,"block/sun_altar.animation.json");

    public ModelSunAltar() {
        super(MODEL,ANIMATIONS);
        addLayer(new LayerSunAltar(this));
    }

    @Override
    public ResourceLocation getTexture(BlockEntitySunAltar target) {
        return TEXTURE;
    }
}
