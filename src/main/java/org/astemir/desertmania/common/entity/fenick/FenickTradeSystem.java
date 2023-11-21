package org.astemir.desertmania.common.entity.fenick;


import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import org.astemir.api.math.collection.Couple;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.api.math.random.WeightedRandom;
import org.astemir.desertmania.common.item.DMItems;


public class FenickTradeSystem {


    public static WeightedRandom<Item> RANDOM_LOVED_ITEM = new WeightedRandom<>()
            .add(80,Items.EGG)
            .add(60,Items.COPPER_INGOT)
            .add(30,Items.IRON_INGOT)
            .add(10,Items.GOLD_INGOT)
            .add(40,Items.TURTLE_EGG)
            .build();

    public static WeightedRandom<Item> RANDOM_TRADE_ITEM = new WeightedRandom<>()
            .add(80,Items.IRON_SWORD)
            .add(80,Items.IRON_PICKAXE)
            .add(80,Items.IRON_SHOVEL)
            .add(80,Items.IRON_AXE)
            .add(80,Items.IRON_HOE)
            .add(10,Items.DIAMOND_SWORD)
            .add(10,Items.DIAMOND_PICKAXE)
            .add(10,Items.DIAMOND_SHOVEL)
            .add(10,Items.DIAMOND_AXE)
            .add(10,Items.DIAMOND_HOE)
            .add(10, DMItems.SCIMITAR.get())
            .build();


    public static UniformInt getMinMaxCount(Item item){
        if (item == Items.EGG){
            return UniformInt.of(12,16);
        }
        if (item == Items.COPPER_INGOT){
            return UniformInt.of(24,48);
        }
        if (item == Items.IRON_INGOT){
            return UniformInt.of(18,36);
        }
        if (item == Items.TURTLE_EGG){
            return UniformInt.of(4,8);
        }
        if (item == Items.GOLD_INGOT){
            return UniformInt.of(16,25);
        }
        return UniformInt.of(1,1);
    }

    public static ItemStack generateSellStack(){
        Item item = RANDOM_TRADE_ITEM.random();
        ItemStack stack = item.getDefaultInstance();
        WeightedRandom<Couple<Enchantment,Integer>> randomToolEnchantments = new WeightedRandom<>().
                add(50, new Couple<>(Enchantments.BLOCK_EFFICIENCY, RandomUtils.randomInt(1,4))).
                add(40,new Couple<>(Enchantments.UNBREAKING,RandomUtils.randomInt(1,3))).
                add(20,new Couple<>(Enchantments.BLOCK_FORTUNE,RandomUtils.randomInt(1,2))).
                add(10,new Couple<>(Enchantments.SILK_TOUCH,1)).build();
        WeightedRandom<Couple<Enchantment,Integer>> randomSwordEnchantments = new WeightedRandom<>().
                add(50, new Couple<>(Enchantments.SHARPNESS,RandomUtils.randomInt(1,4))).
                add(40,new Couple<>(Enchantments.BANE_OF_ARTHROPODS,RandomUtils.randomInt(1,4))).
                add(20,new Couple<>(Enchantments.SMITE,RandomUtils.randomInt(1,4))).
                add(10,new Couple<>(Enchantments.MOB_LOOTING,1)).
                add(20,new Couple<>(Enchantments.SWEEPING_EDGE,1)).build();
        if (stack.getItem() instanceof SwordItem){
            Couple<Enchantment,Integer> pair = randomSwordEnchantments.random();
            stack.enchant(pair.getKey(),pair.getValue());
        }else
        if (stack.getItem() instanceof DiggerItem){
            Couple<Enchantment,Integer> pair = randomToolEnchantments.random();
            stack.enchant(pair.getKey(),pair.getValue());
        }
        if (stack.isDamageableItem()) {
            float maxDamage = stack.getMaxDamage();
            float percent = 1-RandomUtils.randomFloat(0.15f,0.25f);
            stack.setDamageValue((int) (maxDamage*percent));
        }
        return stack;
    }

    public static ItemStack generateBuyStack(){
        Item item = RANDOM_LOVED_ITEM.random();
        UniformInt buyCount = getMinMaxCount(item);
        return new ItemStack(item, RandomUtils.randomInt(buyCount.getMinValue(),buyCount.getMaxValue()));
    }


    public static MerchantOffer itemToOffer(ItemStack stack){
        return new MerchantOffer(generateBuyStack(),stack,1,1,0);
    }

    public static boolean isItemValuableEnough(ItemStack stack){
        return stack.is(DMItems.GOLDEN_SCARAB.get()) || stack.is(DMItems.GOLDEN_SCARAB_RIGHT_PART.get()) || stack.is(DMItems.GOLDEN_SCARAB_LEFT_PART.get()) || stack.getItem() instanceof TieredItem || stack.getItem() instanceof Vanishable;
    }


    public static MerchantOffers offersGenerate(){
        MerchantOffers offers = new MerchantOffers();
        int count = 3;
        for (int i = 0;i<count;i++){
            MerchantOffer offer = new MerchantOffer(generateBuyStack(),generateSellStack(),1,1,0);
            offers.add(offer);
        }
        return offers;
    }
}
