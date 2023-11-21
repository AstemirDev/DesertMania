package org.astemir.desertmania.client.render.entity.genie.other.polymorph;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsAnimatedModel;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.client.render.cube.ModelElement;
import org.astemir.api.io.ResourceUtils;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.entity.genie.misc.EntityPolymorph;

public class ModelPolymorph extends SkillsAnimatedModel<EntityPolymorph, IDisplayArgument> {

    public static ResourceLocation MODEL = ResourceUtils.loadModel(DesertMania.MOD_ID,"entity/genie/other/polymorph.geo.json");
    public static ResourceLocation ANIMATIONS = ResourceUtils.loadAnimation(DesertMania.MOD_ID,"entity/genie/other/polymorph.animation.json");
    public static ResourceLocation TEXTURE = ResourceUtils.loadTexture(DesertMania.MOD_ID,"entity/genie/other/polymorph.png");


    public ModelPolymorph() {
        super(MODEL,ANIMATIONS);
    }

    @Override
    public void renderModel(PoseStack stack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha, RenderCall renderCall, boolean resetBuffer) {
        super.renderModel(stack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha, renderCall, resetBuffer);
    }

    @Override
    public void customAnimate(EntityPolymorph animated, IDisplayArgument argument,float limbSwing, float limbSwingAmount, float ticks, float delta, float headYaw, float headPitch) {
        ModelElement head = getModelElement("head");
        if (head != null) {
            lookAt(head, headPitch, headYaw);
        }
    }


    @Override
    public ResourceLocation getTexture(EntityPolymorph target) {
        return TEXTURE;
    }
}
