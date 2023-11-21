package org.astemir.desertmania.client.render.entity.genie;


import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.client.wrapper.SkillsWrapperEntity;
import org.astemir.desertmania.common.entity.DMEntities;
import org.astemir.desertmania.common.entity.genie.EntityAbstractGenie;

public class ModelWrapperGenie extends SkillsWrapperEntity<EntityAbstractGenie> {

    private final ModelBlueGenie MODEL_BLUE = new ModelBlueGenie();
    private final ModelGreenGenie MODEL_GREEN = new ModelGreenGenie();
    private final ModelRedGenie MODEL_RED = new ModelRedGenie();
    private final ModelPurpleGenie MODEL_PURPLE = new ModelPurpleGenie();


    @Override
    public SkillsModel<EntityAbstractGenie, IDisplayArgument> getModel(EntityAbstractGenie entity) {
        if (entity.getType() == DMEntities.BLUE_GENIE.get()){
            return MODEL_BLUE;
        }
        if (entity.getType() == DMEntities.GREEN_GENIE.get()){
            return MODEL_GREEN;
        }
        if (entity.getType() == DMEntities.RED_GENIE.get()){
            return MODEL_RED;
        }
        if (entity.getType() == DMEntities.PURPLE_GENIE.get()){
            return MODEL_PURPLE;
        }
        return MODEL_BLUE;
    }
}
