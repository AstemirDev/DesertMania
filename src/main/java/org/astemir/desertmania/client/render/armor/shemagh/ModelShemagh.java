package org.astemir.desertmania.client.render.armor.shemagh;

import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.display.DisplayArgumentArmor;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.io.ResourceUtils;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.item.ItemShemagh;

public class ModelShemagh extends SkillsModel<ItemShemagh, DisplayArgumentArmor> {

    public static ResourceLocation MODEL = ResourceUtils.loadModel(DesertMania.MOD_ID,"armor/shemagh.geo.json");
    public static ResourceLocation TEXTURE = ResourceUtils.loadTexture(DesertMania.MOD_ID,"armor/shemagh.png");

    public ModelShemagh() {
        super(MODEL);
    }


    @Override
    public ResourceLocation getTexture(ItemShemagh target) {
        return TEXTURE;
    }
}
