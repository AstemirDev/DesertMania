package org.astemir.desertmania.client;

import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.ViewportEvent;
import org.astemir.api.client.event.SkySetupEvent;
import org.astemir.api.math.MathUtils;
import org.astemir.api.math.components.Color;

public class LevelRenderStateHandler {

    public static Vec3 FOG_COLOR = null;
    public static Vec3 SKY_COLOR = null;
    public static float FOG_NEAR = -1;
    public static float FOG_FAR = -1;


    public static void applyToEvent(SkySetupEvent.ComputeSkyColor e){
        if (SKY_COLOR != null) {
            e.setColor(SKY_COLOR);
        }else{
            SKY_COLOR = e.getColor();
        }
    }

    public static void applyToEvent(ViewportEvent.ComputeFogColor e){
        if (FOG_COLOR != null) {
            e.setRed((float) FOG_COLOR.x);
            e.setGreen((float) FOG_COLOR.y);
            e.setBlue((float) FOG_COLOR.z);
        }else{
            FOG_COLOR = new Vec3(e.getRed(),e.getGreen(),e.getBlue());
        }
    }

    public static void applyToEvent(ViewportEvent.RenderFog e){
        if (FOG_NEAR != -1){
            e.setNearPlaneDistance(FOG_NEAR);
        }else{
            FOG_NEAR = e.getNearPlaneDistance();
        }
        if (FOG_FAR != -1){
            e.setFarPlaneDistance(FOG_FAR);

        }else{
            FOG_FAR = e.getFarPlaneDistance();
        }
    }



    public static void setSkyColor(Color color, float t){
       setSkyColor(color.toVec3(),t);
    }

    public static void setSkyColor(Vec3 color, float t){
        if (SKY_COLOR == null){
            SKY_COLOR = color;
        }else {
            SKY_COLOR = SKY_COLOR.lerp(color,t);
        }
    }

    public static void setFogColor(Color color, float t){
        setFogColor(color.r,color.g,color.b,t);
    }

    public static void setFogColor(float r,float g,float b, float t){
        if (FOG_COLOR == null){
            FOG_COLOR = new Vec3(r,g,b);
        }else {
            FOG_COLOR = FOG_COLOR.lerp(new Vec3(r,g,b),t);
        }
    }

    public static void setFar(float newValue,float t){
        if (FOG_FAR == -1){
            FOG_FAR = newValue;
        }else {
            FOG_FAR = MathUtils.lerp(FOG_FAR,newValue, t);
        }
    }

    public static void setNear(float newValue,float t){
        if (FOG_NEAR == -1){
            FOG_NEAR = newValue;
        }else {
            FOG_NEAR = MathUtils.lerp(FOG_NEAR,newValue, t);
        }
    }
}
