package org.astemir.desertmania.client.render.entity.goldenscarab;


import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.client.wrapper.SkillsWrapperEntity;
import org.astemir.desertmania.common.entity.scarab.EntityGoldenScarab;

public class ModelWrapperGoldenScarab extends SkillsWrapperEntity<EntityGoldenScarab> {


    private final ModelGoldenScarab MODEL = new ModelGoldenScarab();


    @Override
    public SkillsModel<EntityGoldenScarab, IDisplayArgument> getModel(EntityGoldenScarab entity) {
        return MODEL;
    }
}
