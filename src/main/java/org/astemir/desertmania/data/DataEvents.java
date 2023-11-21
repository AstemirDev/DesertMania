package org.astemir.desertmania.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.data.lang.DataGeneratorLang;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataEvents {

    @SubscribeEvent
    public static void onDataGather(GatherDataEvent e) {
        DataGenerator generator = e.getGenerator();
        new DataGeneratorSound(generator, DesertMania.MOD_ID,e.getExistingFileHelper());
        new DataGeneratorLang(generator,DesertMania.MOD_ID);
        new DataGeneratorItemModel(generator,DesertMania.MOD_ID,e.getExistingFileHelper());
        new DataGeneratorBlockState(generator,DesertMania.MOD_ID,e.getExistingFileHelper());
        new DataGeneratorBlockTag(generator, DesertMania.MOD_ID,e.getExistingFileHelper());
        new DataGeneratorLootTable(generator);
        new DataGeneratorRecipe(generator);
        new DataGeneratorItemTag(generator,DesertMania.MOD_ID,e.getExistingFileHelper());
    }
}
