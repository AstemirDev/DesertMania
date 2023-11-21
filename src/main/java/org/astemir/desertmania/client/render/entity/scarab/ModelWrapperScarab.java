package org.astemir.desertmania.client.render.entity.scarab;


import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.client.wrapper.SkillsWrapperEntity;
import org.astemir.desertmania.common.entity.scarab.EntityScarab;

public class ModelWrapperScarab extends SkillsWrapperEntity<EntityScarab> {


    private final ModelScarab MODEL = new ModelScarab();


    @Override
    public SkillsModel<EntityScarab, IDisplayArgument> getModel(EntityScarab entity) {
        return MODEL;
    }
}
