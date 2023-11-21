package org.astemir.desertmania.common.entity.genie;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.astemir.api.common.action.ActionStateMachine;


public class EntityBlueGenie extends EntityAbstractGenie{

    private ActionStateMachine stateMachine = new ActionStateMachine(spawnController);

    public EntityBlueGenie(EntityType<? extends EntityAbstractGenie> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        animation();
    }

    @Override
    public ActionStateMachine getActionStateMachine() {
        return stateMachine;
    }
}
