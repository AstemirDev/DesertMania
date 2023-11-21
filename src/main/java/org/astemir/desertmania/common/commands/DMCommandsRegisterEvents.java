package org.astemir.desertmania.common.commands;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DMCommandsRegisterEvents {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent e){
        SandstormCommand.register(e.getDispatcher());
        TestCommand.register(e.getDispatcher());
    }
}
