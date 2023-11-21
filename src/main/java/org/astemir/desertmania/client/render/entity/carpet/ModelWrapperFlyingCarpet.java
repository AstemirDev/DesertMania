package org.astemir.desertmania.client.render.entity.carpet;

import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.client.wrapper.SkillsWrapperEntity;
import org.astemir.desertmania.common.entity.EntityFlyingCarpet;

public class ModelWrapperFlyingCarpet extends SkillsWrapperEntity<EntityFlyingCarpet> {


    private final ModelFlyingCarpet MODEL = new ModelFlyingCarpet();



    @Override
    public SkillsModel<EntityFlyingCarpet,IDisplayArgument> getModel(EntityFlyingCarpet entity) {
        return MODEL;
    }
}
