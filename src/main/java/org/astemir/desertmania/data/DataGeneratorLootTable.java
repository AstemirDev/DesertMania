package org.astemir.desertmania.data;

import net.minecraft.data.DataGenerator;
import org.astemir.api.data.loot.SkillsLootTableProvider;

public class DataGeneratorLootTable extends SkillsLootTableProvider {
    
    public DataGeneratorLootTable(DataGenerator p_124437_) {
        super(p_124437_);
        blockDrops();
        entityDrops();
        register(p_124437_);
    }

    private void blockDrops(){
        DataGeneratorBlockLootTable blockDrops = new DataGeneratorBlockLootTable();
        loadBlockDrops(blockDrops);
    }

    private void entityDrops(){
        DataGeneratorEntityLootTable entityDrops = new DataGeneratorEntityLootTable();
        loadEntityDrops(entityDrops);
    }
}
