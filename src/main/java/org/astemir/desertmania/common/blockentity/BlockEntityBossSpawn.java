package org.astemir.desertmania.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.common.gfx.GFXRoughShake;
import org.astemir.api.common.gfx.PlayerGFXEffectManager;
import org.astemir.desertmania.common.entity.DMEntities;
import org.astemir.desertmania.common.entity.scarablord.EntityScarabLord;
import org.astemir.desertmania.common.entity.scarablord.ScarabLordActions;

public class BlockEntityBossSpawn extends BlockEntity{

    private long ticks = 0;



    public BlockEntityBossSpawn(BlockPos pPos, BlockState pBlockState) {
        super(DMBlockEntities.BOSS_SPAWN.get(), pPos, pBlockState);
    }


    public void tick(Level level, BlockPos pos, BlockState state, BlockEntityBossSpawn entity) {
        ticks++;
        if (ticks % 20 == 0) {
            Player player = EntityUtils.getEntity(Player.class, level, pos, 10, (p) -> !(p.isCreative() && p.isSpectator()));
            if (player != null) {
                if (EntityUtils.canBeTargeted(player)) {
                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), 4);
                    EntityScarabLord scarabLord = DMEntities.SCARAB_LORD.get().create(level);
                    scarabLord.setPos(pos.getX(), pos.getY(), pos.getZ());
                    level.addFreshEntity(scarabLord);
                    scarabLord.getActionController().playAction(ScarabLordActions.ACTION_SPAWN);
                    for (ServerPlayer serverPlayer : EntityUtils.getEntities(ServerPlayer.class, level, pos, 10)) {
                        PlayerGFXEffectManager.addEffect(serverPlayer,new GFXRoughShake(0.25f,140),false);
                    }
                }
            }
        }
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T t) {
        ((BlockEntityBossSpawn)t).tick(level,pos,state, (BlockEntityBossSpawn) t);
    }

}
