package org.astemir.desertmania.client.render.entity.cactupine;


import net.minecraft.ChatFormatting;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.client.wrapper.SkillsWrapperEntity;
import org.astemir.desertmania.common.entity.EntityCactupine;

public class ModelWrapperCactupine extends SkillsWrapperEntity<EntityCactupine> {


    private final ModelCactupine MODEL = new ModelCactupine();
    private final ModelCactupineXmas MODEL_XMAS = new ModelCactupineXmas();


    @Override
    public SkillsModel<EntityCactupine, IDisplayArgument> getModel(EntityCactupine entity) {
        String name = ChatFormatting.stripFormatting(entity.getName().getString());
        if (name.toLowerCase().contains("xmas")){
            return MODEL_XMAS;
        }
        return MODEL;
    }


}
