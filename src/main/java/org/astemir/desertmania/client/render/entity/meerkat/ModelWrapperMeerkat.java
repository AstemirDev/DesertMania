package org.astemir.desertmania.client.render.entity.meerkat;


import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.client.wrapper.SkillsWrapperEntity;
import org.astemir.desertmania.common.entity.EntityMeerkat;

public class ModelWrapperMeerkat extends SkillsWrapperEntity<EntityMeerkat> {


    private final ModelMeerkat MODEL = new ModelMeerkat();


    @Override
    public SkillsModel<EntityMeerkat, IDisplayArgument> getModel(EntityMeerkat meerkat) {
        return MODEL;
    }

}
