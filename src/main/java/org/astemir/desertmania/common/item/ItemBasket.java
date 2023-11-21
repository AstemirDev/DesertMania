package org.astemir.desertmania.common.item;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.astemir.api.client.render.SkillsRendererItem;
import org.astemir.api.common.item.SkillsBlockItem;
import org.astemir.desertmania.common.block.DMBlocks;

import java.util.function.Consumer;

public class ItemBasket extends SkillsBlockItem {

    public ItemBasket() {
        super(DMBlocks.BASKET.get(),new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
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
