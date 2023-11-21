package org.astemir.desertmania.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.common.commands.build.CommandBuilder;
import org.astemir.api.math.components.FDG;
import org.astemir.api.math.components.Rect3;
import org.astemir.api.math.components.Vector3;
import org.astemir.desertmania.common.world.HeightMap;

public class TestCommand {


    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        CommandBuilder builder = new CommandBuilder("zayka");
        LiteralArgumentBuilder<CommandSourceStack> argumentBuilder = (LiteralArgumentBuilder<CommandSourceStack>) builder.permission((p)->p.hasPermission(2)).build();
        argumentBuilder = argumentBuilder.executes(p ->{
            ServerLevel level = p.getSource().getLevel();
            Vec3 pos = p.getSource().getPosition();
            run(level,(int)pos.x,(int)pos.y,(int)pos.z);
            return 0;
        });
        dispatcher.register(argumentBuilder);
    }

    public static void run(ServerLevel level,int x,int y,int z){
        Rect3 smoothArea = new Rect3(x-10,y,z-10,20,20,20);
        HeightMap heightMap = new HeightMap(level,smoothArea);
        heightMap.applyFilter(level,new HeightMap.Filter(new HeightMap.Kernel.Linear(20)),1);
    }

    public static void setSquare(ServerLevel level, Block block,double x, double y, double z, int sizeX, int sizeY, int sizeZ){
        new FDG(){
            @Override
            public void onExecute(Vector3 point) {
                BlockPos pos = new BlockPos(point.toVec3().add(x,y,z));
                level.setBlock(pos, block.defaultBlockState(),19);
            }
        }.cube(new Vector3(sizeX,sizeY,sizeZ),1,new Vector3(0,0,0));
    }

}
