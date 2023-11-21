package org.astemir.desertmania.client.render.entity.genie.other.charge;

import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.client.wrapper.SkillsWrapperEntity;
import org.astemir.desertmania.common.entity.genie.misc.EntityGenieCharge;

public class ModelWrapperCharge extends SkillsWrapperEntity<EntityGenieCharge> {

    public ModelCharge MODEL = new ModelCharge();


    @Override
    public SkillsModel<EntityGenieCharge, IDisplayArgument> getModel(EntityGenieCharge target) {
        return MODEL;
    }
}
