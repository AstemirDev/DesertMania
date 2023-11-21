package org.astemir.desertmania.client.render.entity.scorpion;


import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.client.wrapper.SkillsWrapperEntity;
import org.astemir.desertmania.common.entity.EntityScorpion;

public class ModelWrapperScorpion extends SkillsWrapperEntity<EntityScorpion> {


    private final ModelScorpion MODEL = new ModelScorpion();


    @Override
    public SkillsModel<EntityScorpion, IDisplayArgument> getModel(EntityScorpion entity) {
        return MODEL;
    }
}
