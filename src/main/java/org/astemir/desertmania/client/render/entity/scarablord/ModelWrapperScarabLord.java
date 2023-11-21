package org.astemir.desertmania.client.render.entity.scarablord;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.client.wrapper.SkillsWrapperEntity;
import org.astemir.desertmania.common.entity.scarablord.EntityScarabLord;

public class ModelWrapperScarabLord extends SkillsWrapperEntity<EntityScarabLord> {

    private final ModelScarabLord MODEL = new ModelScarabLord();

    @Override
    public void renderWrapper(PoseStack p_103111_, VertexConsumer p_103112_, int p_103113_, int p_103114_, float p_103115_, float p_103116_, float p_103117_, float p_103118_, RenderCall renderCall, boolean resetBuffer) {
        super.renderWrapper(p_103111_, p_103112_, p_103113_, p_103114_, p_103115_, p_103116_, p_103117_, p_103118_, renderCall, resetBuffer);
    }

    @Override
    public SkillsModel<EntityScarabLord, IDisplayArgument> getModel(EntityScarabLord lord) {
        return MODEL;
    }
}
