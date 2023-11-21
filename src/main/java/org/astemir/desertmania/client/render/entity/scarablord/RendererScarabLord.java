package org.astemir.desertmania.client.render.entity.scarablord;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.astemir.api.client.render.SkillsRendererLivingEntity;
import org.astemir.desertmania.client.render.entity.scarablord.layer.LayerEmissive;
import org.astemir.desertmania.common.entity.scarablord.EntityScarabLord;

public class RendererScarabLord extends SkillsRendererLivingEntity<EntityScarabLord, ModelWrapperScarabLord> {

    public RendererScarabLord(EntityRendererProvider.Context context) {
        super(context,new ModelWrapperScarabLord());
        shadowRadius = 1f;
        addLayer(new LayerEmissive(this));
    }

    @Override
    public void render(EntityScarabLord entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, yaw, partialTicks, poseStack, bufferSource, packedLight);
    }
}
