package org.astemir.desertmania.client.render.entity.boat;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;
import org.astemir.desertmania.DesertMania;
import org.astemir.desertmania.common.entity.boat.DMBoatType;
import org.astemir.desertmania.common.entity.boat.IDMBoat;

import java.util.Map;
import java.util.stream.Stream;

public class RendererDMBoat<T extends Boat & IDMBoat> extends EntityRenderer<T>{

    private final Map<DMBoatType, Pair<ResourceLocation, BoatModel>> boatResources;

    public RendererDMBoat(EntityRendererProvider.Context p_234563_, boolean boat) {
        super(p_234563_);
        this.shadowRadius = 0.8F;
        this.boatResources = Stream.of(DMBoatType.values()).collect(ImmutableMap.toImmutableMap((p_173938_) -> p_173938_, (p_234575_) -> Pair.of(new ResourceLocation(DesertMania.MOD_ID,getTextureLocation(p_234575_, boat)), this.createBoatModel(p_234563_, p_234575_, boat))));
    }

    private BoatModel createBoatModel(EntityRendererProvider.Context context, DMBoatType type, boolean boat) {
        ModelLayerLocation modellayerlocation = boat ? createChestBoatModelName(type) : createBoatModelName(type);
        return new BoatModel(context.bakeLayer(modellayerlocation), boat);
    }

    private static String getTextureLocation(DMBoatType type, boolean chest) {
        return chest ? "textures/entity/chest_boat/" + type.getName() + ".png" : "textures/entity/boat/" + type.getName() + ".png";
    }

    public void render(T p_113929_, float p_113930_, float p_113931_, PoseStack p_113932_, MultiBufferSource p_113933_, int p_113934_) {
        p_113932_.pushPose();
        p_113932_.translate(0.0D, 0.375D, 0.0D);
        p_113932_.mulPose(Vector3f.YP.rotationDegrees(180.0F - p_113930_));
        float f = (float)p_113929_.getHurtTime() - p_113931_;
        float f1 = p_113929_.getDamage() - p_113931_;
        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        if (f > 0.0F) {
            p_113932_.mulPose(Vector3f.XP.rotationDegrees(Mth.sin(f) * f * f1 / 10.0F * (float)p_113929_.getHurtDir()));
        }

        float f2 = p_113929_.getBubbleAngle(p_113931_);
        if (!Mth.equal(f2, 0.0F)) {
            p_113932_.mulPose(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), p_113929_.getBubbleAngle(p_113931_), true));
        }

        Pair<ResourceLocation, BoatModel> pair = getModelWithLocation(p_113929_);
        ResourceLocation resourcelocation = pair.getFirst();
        BoatModel boatmodel = pair.getSecond();
        p_113932_.scale(-1.0F, -1.0F, 1.0F);
        p_113932_.mulPose(Vector3f.YP.rotationDegrees(90.0F));
        boatmodel.setupAnim(p_113929_, p_113931_, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = p_113933_.getBuffer(boatmodel.renderType(resourcelocation));
        boatmodel.renderToBuffer(p_113932_, vertexconsumer, p_113934_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        if (!p_113929_.isUnderWater()) {
            VertexConsumer vertexconsumer1 = p_113933_.getBuffer(RenderType.waterMask());
            boatmodel.waterPatch().render(p_113932_, vertexconsumer1, p_113934_, OverlayTexture.NO_OVERLAY);
        }

        p_113932_.popPose();
        super.render(p_113929_, p_113930_, p_113931_, p_113932_, p_113933_, p_113934_);
    }

    @Deprecated
    public ResourceLocation getTextureLocation(T p_113927_) {
        return getModelWithLocation(p_113927_).getFirst();
    }

    public Pair<ResourceLocation, BoatModel> getModelWithLocation(T boat) { return this.boatResources.get(boat.getDMBoatType()); }


    private static ModelLayerLocation createLocation(String name, String path) {
        return new ModelLayerLocation(new ResourceLocation(DesertMania.MOD_ID, name), path);
    }

    public static ModelLayerLocation createBoatModelName(DMBoatType type) {
        return createLocation("boat/" + type.getName(), "main");
    }

    public static ModelLayerLocation createChestBoatModelName(DMBoatType type) {
        return createLocation("chest_boat/" + type.getName(), "main");
    }
}
