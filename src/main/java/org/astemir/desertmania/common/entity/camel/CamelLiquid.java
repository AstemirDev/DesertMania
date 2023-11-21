package org.astemir.desertmania.common.entity.camel;

import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

public class CamelLiquid {

    public static final int MAX_AMOUNT = 12;

    private LiquidType liquidType = LiquidType.EMPTY;
    private PotionContainer potionContainer;
    private int amount = 0;


    public boolean hasOverlayOnFace(){
        return potionContainer != null;
    }

    public int getLiquidColor(){
        if (hasOverlayOnFace()){
            if (potionContainer.isVanillaPotion()){
                return PotionUtils.getColor(potionContainer.getVanillaEffect());
            }else{
                return PotionUtils.getColor(potionContainer.getCustomEffects());
            }
        }
        return 0;
    }

    public ItemStack getFillResult(ItemStack stack){
        if (stack.is(Items.WATER_BUCKET)){
            return Items.BUCKET.getDefaultInstance();
        }
        if (stack.is(Items.POTION)){
            return Items.GLASS_BOTTLE.getDefaultInstance();
        }
        return stack;
    }

    public ItemStack getCollectResult(ItemStack stack){
        if (stack.is(Items.BUCKET)){
            return Items.WATER_BUCKET.getDefaultInstance();
        }
        if (stack.is(Items.GLASS_BOTTLE)){
            ItemStack res = Items.POTION.getDefaultInstance();
            if (liquidType == LiquidType.WATER){
                return PotionUtils.setPotion(res,Potions.WATER);
            }else {
                if (potionContainer != null) {
                    return PotionUtils.setCustomEffects(PotionUtils.setPotion(res, potionContainer.getVanillaEffect()), potionContainer.getCustomEffects());
                }
            }
        }
        return stack;
    }

    public LiquidType getItemLiquid(ItemStack stack){
        if (stack.is(Items.WATER_BUCKET)){
            return LiquidType.WATER;
        }
        if (stack.is(Items.POTION)){
            Potion potion = PotionUtils.getPotion(stack);
            if (potion == Potions.WATER){
                return LiquidType.WATER;
            }
            return LiquidType.POTION;
        }
        return LiquidType.EMPTY;
    }

    public boolean canBeCollectedWith(ItemStack stack){
        switch (liquidType){
            case WATER:{
                if (stack.is(Items.BUCKET) || stack.is(Items.GLASS_BOTTLE)){
                    return true;
                }
            }
            case POTION:{
                if (stack.is(Items.GLASS_BOTTLE)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canBeFilledWith(ItemStack stack){
        switch (liquidType){
            case EMPTY:{
                if (stack.is(Items.WATER_BUCKET) || stack.is(Items.POTION)){
                    return true;
                }
            }
            case WATER:{
                if (stack.is(Items.WATER_BUCKET) || stack.is(Items.POTION)){
                    return true;
                }
            }
            case POTION:{
                if (stack.is(Items.POTION)){
                    return true;
                }
            }
        }
        return false;
    }

    public PotionContainer getItemPotion(ItemStack stack){
        if (stack.is(Items.POTION)) {
            if (PotionUtils.getPotion(stack) != Potions.WATER) {
                PotionContainer container = new PotionContainer(PotionUtils.getPotion(stack));
                container.setCustomEffects(PotionUtils.getCustomEffects(stack));
                return container;
            }
        }
        return null;
    }

    public int getLiquidAmount(ItemStack stack){
        if (stack.is(Items.WATER_BUCKET) || stack.is(Items.BUCKET)){
            return 4;
        }
        return 1;
    }

    public LiquidType getLiquidType() {
        return liquidType;
    }

    public void setLiquidType(LiquidType liquidType) {
        this.liquidType = liquidType;
    }

    public PotionContainer getPotionContainer() {
        return potionContainer;
    }

    public void setPotionContainer(PotionContainer potionContainer) {
        this.potionContainer = potionContainer;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amm) {
        this.amount = amm;
        if (this.amount <= 0){
            emptyContainer();
        }
        if (this.amount > MAX_AMOUNT) {
            this.amount = MAX_AMOUNT;
        }
    }

    public void emptyContainer(){
        this.liquidType = LiquidType.EMPTY;
        this.potionContainer = null;
        this.amount = 0;
    }

    public enum LiquidType{
        POTION,WATER,EMPTY
    }

    public CompoundTag save(){
        CompoundTag tag = new CompoundTag();
        tag.putString("LiquidType",liquidType.toString().toLowerCase(Locale.ROOT));
        if (potionContainer != null) {
            tag.put("PotionContainer", potionContainer.save());
        }
        tag.putInt("Amount",amount);
        return tag;
    }

    public static CamelLiquid load(CompoundTag tag){
        CamelLiquid liquid = new CamelLiquid();
        LiquidType type = LiquidType.valueOf(tag.getString("LiquidType").toUpperCase(Locale.ROOT));
        if (tag.contains("PotionContainer")){
            liquid.setPotionContainer(PotionContainer.load(tag.getCompound("PotionContainer")));
        }
        liquid.setLiquidType(type);
        liquid.setAmount(tag.getInt("Amount"));
        return liquid;
    }

    public static class PotionContainer{

        private Potion vanillaEffect = Potions.WATER;
        private Collection<MobEffectInstance> customEffects = new ArrayList<>();

        public PotionContainer(Potion vanillaEffect) {
            this.vanillaEffect = vanillaEffect;
        }

        public PotionContainer(Collection<MobEffectInstance> customEffects) {
            this.customEffects = customEffects;
        }

        public boolean isVanillaPotion(){
            return !isCustomPotion();
        }

        public boolean isCustomPotion(){
            return !customEffects.isEmpty();
        }

        public Potion getVanillaEffect() {
            return vanillaEffect;
        }

        public Collection<MobEffectInstance> getCustomEffects() {
            return customEffects;
        }

        public void setCustomEffects(Collection<MobEffectInstance> customEffects) {
            this.customEffects = customEffects;
        }

        private CompoundTag save(){
            CompoundTag tag = new CompoundTag();
            tag.putString("Potion", Registry.POTION.getKey(vanillaEffect).getPath());
            if (isCustomPotion()){
                saveCustomEffects(tag,customEffects);
            }
            return tag;
        }

        private static PotionContainer load(CompoundTag tag){
            if (tag.contains("CustomPotionEffects")){
                return new PotionContainer(PotionUtils.getCustomEffects(tag));
            }else{
                return new PotionContainer(Potion.byName(tag.getString("Potion")));
            }
        }

        private void saveCustomEffects(CompoundTag tag,Collection<MobEffectInstance> effects) {
            if (!effects.isEmpty()) {
                ListTag listtag = tag.getList("CustomPotionEffects", 9);
                for(MobEffectInstance mobeffectinstance : effects) {
                    listtag.add(mobeffectinstance.save(new CompoundTag()));
                }
                tag.put("CustomPotionEffects", listtag);
            }
        }

        public boolean isHarmful(){
            if (isVanillaPotion()){
                for (MobEffectInstance effect : vanillaEffect.getEffects()) {
                    if (effect.getEffect().getCategory() == MobEffectCategory.HARMFUL){
                        return true;
                    }
                }
            }else{
                if (isCustomPotion()){
                    for (MobEffectInstance customEffect : customEffects) {
                        if (customEffect.getEffect().getCategory() == MobEffectCategory.HARMFUL){
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }
}
