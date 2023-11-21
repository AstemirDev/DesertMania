package org.astemir.desertmania.data;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import org.astemir.api.data.loot.ItemDrop;
import org.astemir.api.data.loot.entity.MobDrop;
import org.astemir.api.data.loot.entity.MobDropParameters;
import org.astemir.api.data.loot.entity.LootProviderEntities;
import org.astemir.desertmania.common.entity.DMEntities;
import org.astemir.desertmania.common.item.DMItems;

public class DataGeneratorEntityLootTable extends LootProviderEntities {

    public DataGeneratorEntityLootTable() {
        super(DMEntities.ENTITIES);
        entities();
    }

    private MobDrop mobLoot(EntityType<?> type){
        return new MobDrop(type);
    }

    private void entities(){
        addDrop(mobLoot(DMEntities.MUMMY.get()).addDrop(new ItemDrop(DMItems.CLOTH.get()).count(1,1).parameters(new MobDropParameters().looting(1,1)).chance(0.1f)));
        addDrop(mobLoot(DMEntities.CAMEL.get()).addDrop(new ItemDrop(Items.LEATHER).count(1,2)));
        addDrop(mobLoot(DMEntities.DESERT_WORM.get()).addDrop(new ItemDrop(Items.SAND).count(16,32).parameters(new MobDropParameters().looting(8,14))));
        addDrop(mobLoot(DMEntities.MEERKAT.get()).addDrop(new ItemDrop(Items.COD).count(1,1).chance(0.01f).parameters(new MobDropParameters())));
        addDrop(mobLoot(DMEntities.SCARAB.get()));
        addDrop(mobLoot(DMEntities.FENICK.get()));
        addDrop(mobLoot(DMEntities.SCARAB_LORD.get()).addDrop(new ItemDrop(DMItems.ANCIENT_AMULET.get())));
        addDrop(mobLoot(DMEntities.BLUE_GENIE.get()));
        addDrop(mobLoot(DMEntities.GREEN_GENIE.get()));
        addDrop(mobLoot(DMEntities.RED_GENIE.get()));
        addDrop(mobLoot(DMEntities.PURPLE_GENIE.get()));
        addDrop(mobLoot(DMEntities.FENICK_MAGE.get()));
        addDrop(mobLoot(DMEntities.FAKIR.get()));
        addDrop(mobLoot(DMEntities.GOLDEN_SCARAB.get()).addDrop(new ItemDrop(DMItems.GOLDEN_SCARAB.get())));
        addDrop(mobLoot(DMEntities.SCORPION.get()).addDrop(new ItemDrop(DMItems.STING.get()).count(1,1).parameters(new MobDropParameters().looting(1,2)).chance(0.5f)));
        addDrop(mobLoot(DMEntities.CACTUPINE.get()).addDrop(new ItemDrop(Items.CACTUS.asItem()).count(1,1).parameters(new MobDropParameters())));
        addDrop(mobLoot(DMEntities.POLYMORPH.get()));
    }
}
