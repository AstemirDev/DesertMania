package org.astemir.desertmania.client.render.entity.mummy;


import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.client.wrapper.SkillsWrapperEntity;
import org.astemir.desertmania.common.entity.EntityMummy;

public class ModelWrapperMummy extends SkillsWrapperEntity<EntityMummy> {


    private final ModelMummy MODEL = new ModelMummy();


    @Override
    public SkillsModel<EntityMummy, IDisplayArgument> getModel(EntityMummy entity) {
        return MODEL;
    }
}
