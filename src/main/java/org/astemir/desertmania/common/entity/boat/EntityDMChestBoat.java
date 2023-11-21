package org.astemir.desertmania.common.entity.boat;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import org.astemir.desertmania.common.entity.DMEntities;
import org.astemir.desertmania.common.item.DMItems;
import org.jetbrains.annotations.Nullable;

public class EntityDMChestBoat extends ChestBoat implements IDMBoat{

    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(EntityDMChestBoat.class, EntityDataSerializers.INT);

    public EntityDMChestBoat(EntityType<? extends Boat> p_38290_, Level p_38291_) {
        super(p_38290_, p_38291_);
    }

    public EntityDMChestBoat(Level p_38293_, double p_38294_, double p_38295_, double p_38296_) {
        this(DMEntities.CHEST_BOAT.get(), p_38293_);
        this.setPos(p_38294_, p_38295_, p_38296_);
        this.xo = p_38294_;
        this.yo = p_38295_;
        this.zo = p_38296_;
    }

    public EntityDMChestBoat(PlayMessages.SpawnEntity spawnEntity, Level level) {
        super(DMEntities.CHEST_BOAT.get(),level);
    }


    @Nullable
    @Override
    public ItemEntity spawnAtLocation(ItemStack p_19985_, float p_19986_) {
        ItemStack planks = getDMBoatType().getPlanks().asItem().getDefaultInstance();
        if (getBoatType().getPlanks().asItem() == p_19985_.getItem()){
            return super.spawnAtLocation(planks, p_19986_);
        }else {
            return super.spawnAtLocation(p_19985_, p_19986_);
        }
    }

    @Override
    public Item getDropItem() {
        Item item;
        switch (getDMBoatType()){
            case PALM:
                item = DMItems.PALM_PLANKS_CHEST_BOAT.get();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + getDMBoatType());
        }
        return item;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE, DMBoatType.PALM.ordinal());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_38359_) {
        p_38359_.putString("Type", this.getDMBoatType().getName());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_38338_) {
        if (p_38338_.contains("Type", 8)) {
            this.setDMBoatType(DMBoatType.byName(p_38338_.getString("Type")));
        }
    }

    @Override
    public DMBoatType getDMBoatType(){
        return DMBoatType.byId(this.entityData.get(DATA_ID_TYPE));
    }

    @Override
    public void setDMBoatType(DMBoatType p_38333_) {
        this.entityData.set(DATA_ID_TYPE, p_38333_.ordinal());
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public EntityType<?> getType() {
        return DMEntities.CHEST_BOAT.get();
    }

    @Override
    public Type getBoatType() {
        return Type.OAK;
    }
}
