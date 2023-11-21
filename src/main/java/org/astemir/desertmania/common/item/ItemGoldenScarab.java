package org.astemir.desertmania.common.item;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.common.entity.utils.PlayerUtils;
import org.astemir.desertmania.common.entity.DMEntities;
import org.astemir.desertmania.common.entity.scarab.EntityGoldenScarab;
import org.astemir.desertmania.common.world.generation.structures.DMStructures;

public class ItemGoldenScarab extends Item {

    public ItemGoldenScarab() {
        super(new Item.Properties().tab(CreativeModeTab.TAB_MISC).rarity(Rarity.EPIC).stacksTo(64));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel instanceof ServerLevel serverLevel) {
            Registry<Structure> registry = serverLevel.registryAccess().registryOrThrow(Registry.STRUCTURE_REGISTRY);
            Holder<Structure> holder = registry.getHolder(DMStructures.SCARAB_TEMPLE.getKey()).get();
            Pair<BlockPos, Holder<Structure>> pair = serverLevel.getChunkSource().getGenerator().findNearestMapStructure(serverLevel, HolderSet.direct(holder), pPlayer.blockPosition(), 40, false);
            if (pair.getFirst() != null) {
                if (!PlayerUtils.isOnCooldown(pPlayer, this)) {
                    Vec3 pos = pPlayer.getEyePosition().add(pPlayer.getViewVector(0).multiply(1.5f, 1.5f, 1.5f));
                    PlayerUtils.cooldownItem(pPlayer, this, 20);
                    ItemStack stack = pPlayer.getItemInHand(pUsedHand);
                    stack.shrink(1);
                    EntityGoldenScarab scarab = DMEntities.GOLDEN_SCARAB.get().create(pLevel);
                    scarab.setPos(pos);
                    pLevel.addFreshEntity(scarab);
                    return InteractionResultHolder.consume(stack);
                }
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
