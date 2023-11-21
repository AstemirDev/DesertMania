package org.astemir.desertmania.common.item;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.astemir.api.client.registry.ArmorModelsRegistry;
import org.astemir.api.client.wrapper.SkillsWrapperArmor;
import org.astemir.api.common.item.SkillsArmorItem;
import org.astemir.desertmania.client.render.ShemaghOverlayRenderer;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ItemShemagh extends SkillsArmorItem {

    public ItemShemagh() {
        super(DMArmorMaterial.CLOTH, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).stacksTo(1));
    }


    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                SkillsWrapperArmor wrapper = ArmorModelsRegistry.getModel(itemStack.getItem());
                wrapper.setupAngles(livingEntity,itemStack.getItem(),itemStack,equipmentSlot,original);
                return wrapper;
            }
            @Override
            public @NotNull Model getGenericArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                SkillsWrapperArmor wrapper = ArmorModelsRegistry.getModel(itemStack.getItem());
                wrapper.setupAngles(livingEntity,itemStack.getItem(),itemStack,equipmentSlot,original);
                return wrapper;
            }

            @Override
            public void renderHelmetOverlay(ItemStack stack, Player player, int width, int height, float partialTick) {
                ShemaghOverlayRenderer.renderTextureOverlay(1.0f);
            }
        });
    }

}
