package org.astemir.desertmania.common.entity.genie.misc;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;
import org.astemir.api.math.components.Color;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.api.network.PacketArgument;
import org.astemir.desertmania.common.entity.DMEntities;

import java.util.Arrays;
import java.util.function.Supplier;

public class GenieData {

    private Supplier<EntityType> type = ()->DMEntities.BLUE_GENIE.get();
    private Color color = GenieColors.BLUE;



    public void randomize(){
        this.type = ()->RandomUtils.randomElement(Arrays.asList(DMEntities.BLUE_GENIE.get(),DMEntities.GREEN_GENIE.get(),DMEntities.RED_GENIE.get(),DMEntities.PURPLE_GENIE.get()));
        this.color = GenieColors.fromType(type.get());
    }

    public PacketArgument entityIdPacket(){
        return PacketArgument.create(PacketArgument.ArgumentType.STRING,ForgeRegistries.ENTITY_TYPES.getKey(type.get()).toString());
    }

    public boolean isEmpty(){
        return type == null;
    }

    public CompoundTag save(){
        CompoundTag tag = new CompoundTag();
        tag.putString("id", ForgeRegistries.ENTITY_TYPES.getKey(type.get()).toString());
        tag.putIntArray("Color",color.toArray());
        return tag;
    }

    public void load(CompoundTag tag){
        if (!tag.isEmpty()) {
            this.type = ()->EntityType.byString(tag.getString("id")).get();
            this.color = Color.fromArray(tag.getIntArray("Color"));
        }
    }

    public void setType(EntityType type) {
        this.type = ()->type;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public EntityType getType() {
        return type.get();
    }

    public Color getColor() {
        return color;
    }
}
