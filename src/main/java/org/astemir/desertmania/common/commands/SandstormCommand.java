package org.astemir.desertmania.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import org.astemir.api.common.commands.build.CommandArgument;
import org.astemir.api.common.commands.build.CommandBuilder;
import org.astemir.api.common.commands.build.CommandPart;
import org.astemir.api.common.commands.build.CommandVariant;
import org.astemir.desertmania.common.world.DMWorldData;

public class SandstormCommand {


    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        CommandBuilder builder = new CommandBuilder("sandstorm");
        CommandArgument ticks = CommandArgument.integer("ticks",0);
        builder.variants(new CommandVariant(new CommandPart("start"),ticks).execute((p)->{
            DMWorldData.getInstance(p.getSource().getLevel()).getSandstorm().start(p.getSource().getLevel(),ticks.getInt(p));
            return 0;
        }),
        new CommandVariant(new CommandPart("end")).execute((p)->{
            DMWorldData.getInstance(p.getSource().getLevel()).getSandstorm().end(p.getSource().getLevel());
            return 0;
        }));
        dispatcher.register(builder.permission((p)->p.hasPermission(2)).build());
    }

}
