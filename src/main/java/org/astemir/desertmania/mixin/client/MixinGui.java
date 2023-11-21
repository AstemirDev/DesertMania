package org.astemir.desertmania.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import org.astemir.desertmania.client.misc.HeartType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({Gui.class})
public abstract class MixinGui extends GuiComponent {


    @Shadow @Final protected RandomSource random;

    /**
     * @author Astemir
     * @reason Custom heart drawing
     */
    @Overwrite
    protected void renderHearts(PoseStack p_168689_, Player p_168690_, int p_168691_, int p_168692_, int p_168693_, int p_168694_, float p_168695_, int p_168696_, int p_168697_, int p_168698_, boolean p_168699_) {
        HeartType type = HeartType.forPlayer(p_168690_);
        int i = 9 * (p_168690_.level.getLevelData().isHardcore() ? 5 : 0);
        int j = Mth.ceil((double)p_168695_ / 2.0D);
        int k = Mth.ceil((double)p_168698_ / 2.0D);
        int l = j * 2;

        for(int i1 = j + k - 1; i1 >= 0; --i1) {
            int j1 = i1 / 10;
            int k1 = i1 % 10;
            int l1 = p_168691_ + k1 * 8;
            int i2 = p_168692_ - j1 * p_168693_;
            if (p_168696_ + p_168698_ <= 4) {
                i2 += this.random.nextInt(2);
            }

            if (i1 < j && i1 == p_168694_) {
                i2 -= 2;
            }

            this.renderModdedHeart(p_168689_, HeartType.CONTAINER, l1, i2, i, p_168699_, false);
            int j2 = i1 * 2;
            boolean flag = i1 >= j;
            if (flag) {
                int k2 = j2 - l;
                if (k2 < p_168698_) {
                    boolean flag1 = k2 + 1 == p_168698_;
                    this.renderModdedHeart(p_168689_, type == HeartType.WITHERED ? type : HeartType.ABSORBING, l1, i2, i, false, flag1);
                }
            }

            if (p_168699_ && j2 < p_168697_) {
                boolean flag2 = j2 + 1 == p_168697_;
                this.renderModdedHeart(p_168689_, type, l1, i2, i, true, flag2);
            }

            if (j2 < p_168696_) {
                boolean flag3 = j2 + 1 == p_168696_;
                this.renderModdedHeart(p_168689_, type, l1, i2, i, false, flag3);
            }
        }

    }

    private void renderModdedHeart(PoseStack p_168701_, HeartType p_168702_, int p_168703_, int p_168704_, int p_168705_, boolean p_168706_, boolean p_168707_) {
        blit(p_168701_, p_168703_, p_168704_, p_168702_.getX(p_168707_, p_168706_), p_168705_, 9, 9);
    }
}
