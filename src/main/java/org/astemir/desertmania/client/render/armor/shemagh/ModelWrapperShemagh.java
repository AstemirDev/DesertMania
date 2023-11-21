package org.astemir.desertmania.client.render.armor.shemagh;

import net.minecraft.client.renderer.RenderType;
import org.astemir.api.client.display.DisplayArgumentArmor;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.client.wrapper.SkillsWrapperArmor;
import org.astemir.desertmania.common.item.ItemShemagh;

public class ModelWrapperShemagh extends SkillsWrapperArmor<ItemShemagh> {

    private final ModelShemagh MODEL = new ModelShemagh();

    @Override
    public RenderType getRenderType() {
        return super.getRenderType();
    }

    @Override
    public SkillsModel<ItemShemagh, DisplayArgumentArmor> getModel(ItemShemagh target) {
        return MODEL;
    }
}
