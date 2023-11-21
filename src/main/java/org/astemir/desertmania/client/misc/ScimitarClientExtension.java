package org.astemir.desertmania.client.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.astemir.api.math.MathUtils;
import org.astemir.desertmania.common.item.DMItems;
import org.astemir.desertmania.common.misc.IDMPlayer;
import org.astemir.desertmania.common.utils.MiscUtils;

public class ScimitarClientExtension implements IClientItemExtensions {

    @Override
    public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
        IDMPlayer idmPlayer = (IDMPlayer) player;
        float deltaTicks = ((float)(player.tickCount))+ Minecraft.getInstance().getPartialTick();
        if (idmPlayer.isSpinningWithScimitar()){
            if (player.getUseItem().is(DMItems.SCIMITAR.get())) {
                if (arm == HumanoidArm.RIGHT) {
                    poseStack.translate(0, -0.25f, -0.4f);
                    poseStack.mulPose(Vector3f.YN.rotationDegrees(50 + MathUtils.sin(deltaTicks)));
                    poseStack.mulPose(Vector3f.ZN.rotationDegrees(-85 + MathUtils.sin(deltaTicks)));
                }
                if (arm == HumanoidArm.LEFT){
                    poseStack.translate(0, 0, 10);
                }
            }else{
                if (arm == HumanoidArm.RIGHT) {
                    poseStack.translate(-0.9f, -0.775f, -0.4f);
                    poseStack.mulPose(Vector3f.YN.rotationDegrees(50 + MathUtils.sin(deltaTicks)));
                    poseStack.mulPose(Vector3f.ZN.rotationDegrees(-85 + MathUtils.sin(deltaTicks)));
                }
                if (arm == HumanoidArm.LEFT){
                    poseStack.translate(0, 0, 10);
                }
            }
        }else
        if (MiscUtils.isDoubleBlockingWithScimitar(player)){
            if (arm == HumanoidArm.RIGHT) {
                poseStack.translate(0.45f, -0.3, -0.4f);
                poseStack.mulPose(Vector3f.YN.rotationDegrees(-90));
                poseStack.mulPose(Vector3f.XN.rotationDegrees(80));
                poseStack.mulPose(Vector3f.ZN.rotationDegrees(0));
            }
            if (arm == HumanoidArm.LEFT){
                poseStack.translate(0, 0.525f, 0.1f);
                poseStack.mulPose(Vector3f.YN.rotationDegrees(90));
                poseStack.mulPose(Vector3f.XN.rotationDegrees(80));
                poseStack.mulPose(Vector3f.ZN.rotationDegrees(-5));
            }
        }else
        if (MiscUtils.isBlockingWithScimitar(player)){
            if (player.getUseItem().is(DMItems.SCIMITAR.get()) && arm == HumanoidArm.RIGHT){
                poseStack.translate(0.45f,-0.3, -0.5f);
                poseStack.mulPose(Vector3f.YN.rotationDegrees(-90));
                poseStack.mulPose(Vector3f.XN.rotationDegrees(80));
                poseStack.mulPose(Vector3f.ZN.rotationDegrees(5));
            }else
            if (player.getUseItem().is(DMItems.SCIMITAR.get()) && arm == HumanoidArm.LEFT){
                poseStack.translate(-0.45f,-0.3, -0.5f);
                poseStack.mulPose(Vector3f.YN.rotationDegrees(90));
                poseStack.mulPose(Vector3f.XN.rotationDegrees(80));
                poseStack.mulPose(Vector3f.ZN.rotationDegrees(-5));
            }
        }
        return IClientItemExtensions.super.applyForgeHandTransform(poseStack, player, arm, itemInHand, partialTick, equipProcess, swingProcess);
    }
}
