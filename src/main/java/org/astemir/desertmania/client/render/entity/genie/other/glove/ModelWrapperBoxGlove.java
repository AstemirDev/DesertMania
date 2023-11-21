package org.astemir.desertmania.client.render.entity.genie.other.glove;

import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.client.wrapper.SkillsWrapperEntity;
import org.astemir.desertmania.common.entity.genie.misc.EntityBoxGlove;

public class ModelWrapperBoxGlove extends SkillsWrapperEntity<EntityBoxGlove> {

    public ModelBoxGlove MODEL = new ModelBoxGlove();


    @Override
    public SkillsModel<EntityBoxGlove, IDisplayArgument> getModel(EntityBoxGlove target) {
        return MODEL;
    }
}
