package org.astemir.desertmania.client.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.astemir.api.client.animation.Animator;
import org.astemir.api.math.components.Transform;
import org.astemir.desertmania.common.entity.EntityFlyingCarpet;
import org.astemir.desertmania.common.entity.camel.EntityCamel;

public class RidingExtension {

    public static <T extends LivingEntity> void ridingPosition(T entity, PoseStack stack, float pAgeInTicks, float pRotationYaw, float pPartialTicks){
        if (entity instanceof Player player) {
            Entity vehicle = player.getVehicle();
            if (vehicle != null) {
                if (vehicle instanceof EntityCamel camel) {
                    Transform transform = Animator.INSTANCE.getTransformData(camel, "Body");
                    if (transform != null) {
                        if (camel.canBeMovedOrPushed()) {
                            stack.translate(transform.getPosition().x / 16, transform.getPosition().y / 16, 0.15f - transform.getPosition().z / 16);
                        } else {
                            stack.translate(0, 0, 0.25f);
                        }
                        stack.mulPose(Vector3f.XP.rotation(transform.getRotation().x));
                        stack.mulPose(Vector3f.YP.rotation(transform.getRotation().y));
                        stack.mulPose(Vector3f.ZP.rotation(transform.getRotation().z));
                    }
                }
                if (vehicle instanceof EntityFlyingCarpet carpet) {
                    Transform transform = Animator.INSTANCE.getTransformData(carpet, "carpet");
                    if (transform != null) {
                        stack.translate(transform.getPosition().x / 16, (transform.getPosition().y / 16)-0.1f, transform.getPosition().z / 16);
                        stack.mulPose(Vector3f.XP.rotation(transform.getRotation().x));
                        stack.mulPose(Vector3f.YP.rotation(transform.getRotation().y));
                        stack.mulPose(Vector3f.ZP.rotation(transform.getRotation().z));
                    }
                }
            }
        }
    }
}
