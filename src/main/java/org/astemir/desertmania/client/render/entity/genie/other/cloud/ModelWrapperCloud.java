package org.astemir.desertmania.client.render.entity.genie.other.cloud;

import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.client.wrapper.SkillsWrapperEntity;
import org.astemir.desertmania.common.entity.genie.misc.EntityCloud;

public class ModelWrapperCloud extends SkillsWrapperEntity<EntityCloud> {

    public ModelCloud MODEL = new ModelCloud();

    @Override
    public SkillsModel<EntityCloud, IDisplayArgument> getModel(EntityCloud target) {
        return MODEL;
    }
}
