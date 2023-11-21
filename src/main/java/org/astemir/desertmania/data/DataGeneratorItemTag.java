package org.astemir.desertmania.data;

import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.astemir.api.data.tag.SkillsTagProvider;
import org.astemir.desertmania.common.item.DMItems;
import org.jetbrains.annotations.Nullable;

public class DataGeneratorItemTag extends SkillsTagProvider<Item> {

    public DataGeneratorItemTag(DataGenerator p_126546_, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_126546_, Registry.ITEM, modId, existingFileHelper);
        tags();
        register(p_126546_);
    }

    private void tags(){
        addTag(DMItems.PALM_PLANKS, ItemTags.PLANKS);
        addTag(DMItems.STING_ARROW,ItemTags.ARROWS);
    }
}
