package org.astemir.desertmania.client.render.entity.genie.other.glove;

import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsAnimatedModel;
import org.astemir.api.io.ResourceUtils;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.entity.genie.misc.EntityBoxGlove;

public class ModelBoxGlove extends SkillsAnimatedModel<EntityBoxGlove, IDisplayArgument> {

    public static ResourceLocation TEXTURE = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/genie/other/boxing_glove.png");
    public static ResourceLocation MODEL = ResourceUtils.loadModel(DesertMania.MOD_ID,"entity/genie/other/boxing_glove.geo.json");
    public static ResourceLocation ANIMATIONS = ResourceUtils.loadAnimation(DesertMania.MOD_ID,"entity/genie/other/boxing_glove.animation.json");

    public ModelBoxGlove() {
        super(MODEL, ANIMATIONS);
    }


    @Override
    public ResourceLocation getTexture(EntityBoxGlove target) {
        return TEXTURE;
    }
}
