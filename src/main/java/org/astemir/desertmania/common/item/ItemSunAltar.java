package org.astemir.desertmania.common.item;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.astemir.api.client.render.SkillsRendererItem;
import org.astemir.api.common.item.SkillsBlockItem;
import org.astemir.desertmania.common.block.DMBlocks;

import java.util.function.Consumer;

public class ItemSunAltar extends SkillsBlockItem {

    public ItemSunAltar() {
        super(DMBlocks.SUN_ALTAR.get(),new Properties().tab(CreativeModeTab.TAB_DECORATIONS).rarity(Rarity.EPIC));
    }


    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return SkillsRendererItem.INSTANCE;
            }
        });
    }
}
