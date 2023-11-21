package org.astemir.desertmania.common.entity.fenick;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.ArrayUtils;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.common.action.Action;
import org.astemir.api.common.action.ActionController;
import org.astemir.api.common.action.ActionStateMachine;
import org.astemir.api.common.animation.Animation;
import org.astemir.api.common.animation.AnimationFactory;
import org.astemir.api.common.entity.EntityData;
import org.astemir.api.common.misc.ParticleEmitter;
import org.astemir.api.math.components.Vector3;
import org.astemir.api.math.random.WeightedRandom;
import org.astemir.desertmania.common.item.DMItems;
import org.astemir.desertmania.common.sound.DMSounds;


public class EntityFenickMage extends EntityAbstractFenick {

    public static EntityData<ItemStack> ITEM_A = new EntityData<>(EntityFenickMage.class,"ItemA", EntityDataSerializers.ITEM_STACK,ItemStack.EMPTY);
    public static EntityData<ItemStack> ITEM_B = new EntityData<>(EntityFenickMage.class,"ItemB", EntityDataSerializers.ITEM_STACK,ItemStack.EMPTY);
    public static EntityData<ItemStack> ITEM_C = new EntityData<>(EntityFenickMage.class,"ItemC", EntityDataSerializers.ITEM_STACK,ItemStack.EMPTY);
    public static EntityData<Boolean> ROLLED = new EntityData<>(EntityFenickMage.class,"Rolled", EntityDataSerializers.BOOLEAN,false);

    public static final Animation ANIMATION_IDLE = new Animation("animation.model.idle",2.08f).layer(0).smoothness(1.5f).loop();
    public static final Animation ANIMATION_START = new Animation("animation.model.start",1.32f).layer(0).loop(Animation.Loop.HOLD_ON_LAST_FRAME);
    public static final Animation ANIMATION_IDLE_GAME = new Animation("animation.model.idle_game",2.08f).smoothness(1.5f).layer(0).loop();
    public static final Animation ANIMATION_GAME = new Animation("animation.model.game",3.08f).layer(0);
    public static final Animation ANIMATION_VANISH = new Animation("animation.model.vanish",1.16f).layer(0).loop(Animation.Loop.HOLD_ON_LAST_FRAME);


    public static WeightedRandom<Item> RANDOM_ITEM = new WeightedRandom<>()
            .add(5,DMItems.GOLDEN_SCARAB_RIGHT_PART.get())
            .add(5,DMItems.GOLDEN_SCARAB_LEFT_PART.get())
            .add(10,DMItems.ENCHANTED_SCROLL.get())
            .add(5, DMItems.GENIE_LAMP.get())
            .add(10, DMItems.RED_FLYING_CARPET.get())
            .add(10, DMItems.BLUE_FLYING_CARPET.get())
            .add(10, DMItems.GREEN_FLYING_CARPET.get())
            .add(10,Items.DIAMOND)
            .add(20,DMItems.CLOTH)
            .add(40,Items.IRON_INGOT)
            .add(40,Items.GOLD_INGOT)
            .add(40,Items.ENDER_EYE)
            .add(40,DMItems.BASKET.get())
            .add(5,DMItems.MAGIC_BASKET.get())
            .add(5,DMItems.MUSIC_DISC_DESERTMANIA.get())
            .add(10,DMItems.SHEMAGH.get())
            .build();


    public AnimationFactory animationFactory = new AnimationFactory(this,ANIMATION_IDLE,ANIMATION_START,ANIMATION_IDLE_GAME,ANIMATION_GAME,ANIMATION_VANISH){
        @Override
        public void onAnimationEnd(Animation animation) {
            if (animation == ANIMATION_START){
                animationFactory.stop(ANIMATION_START);
                gameController.playAction(ACTION_IDLE_GAME);
            }
            if (animation == ANIMATION_GAME){
                getAnimationFactory().play(ANIMATION_IDLE_GAME);
            }
            if (animation == ANIMATION_VANISH){
                discard();
            }
        }
    };

    public static Action ACTION_SWAP_BASKETS = new Action(2,"swap",3.08f);


    public ActionController actionController = new ActionController(this,"actionController",ACTION_PUT_ITEMS,ACTION_SWAP_BASKETS);

    public static Action ACTION_PUT_ITEMS = new Action(0,"prepare",40);

    private final ActionController gameController = new ActionController(this,"gameController",ACTION_IDLE_GAME){

        @Override
        public void onActionBegin(Action state) {
            if (state == ACTION_SWAP_BASKETS){
                animationFactory.play(ANIMATION_GAME);
            }
            if (state == ACTION_PUT_ITEMS){
                setSlots(RANDOM_ITEM.random().getDefaultInstance(),RANDOM_ITEM.random().getDefaultInstance(),RANDOM_ITEM.random().getDefaultInstance());
            }
        }

        @Override
        public void onActionEnd(Action state) {
            if (state == ACTION_SWAP_BASKETS){
                ROLLED.set(EntityFenickMage.this,true);
            }
        }

    };
    public static Action ACTION_IDLE_GAME = new Action(0,"idle",-1);

    public ActionStateMachine stateMachine = new ActionStateMachine(actionController,gameController);

    public EntityFenickMage(EntityType<? extends EntityAbstractFenick> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Override
    protected void registerGoals() {
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        ITEM_A.register(this);
        ITEM_B.register(this);
        ITEM_C.register(this);
        ROLLED.register(this);
    }


    @Override
    public void aiStep() {
        super.aiStep();
        if (!animationFactory.isPlaying(ANIMATION_VANISH,ANIMATION_START)) {
            if (gameController.is(ACTION_IDLE_GAME)){
                if (!actionController.is(ACTION_SWAP_BASKETS)){
                    animationFactory.play(ANIMATION_IDLE_GAME);
                }
            }else{
                animationFactory.play(ANIMATION_IDLE);
            }
        }
        if (animationFactory.isPlaying(ANIMATION_VANISH)){
            Vec3 view = getViewVector(0);
            view = view.multiply(-1,-1,-2);
            float f = (float) animationFactory.getAnimationTick(ANIMATION_VANISH)/15f;
            new ParticleEmitter(ParticleTypes.CLOUD).count(16).size(new Vector3(f,f,f)).emit(level,Vector3.from(position().add(0,1,0).add(view.x,view.y,view.z)),new Vector3(0,0,0));
        }
    }


    @Override
    public InteractionResult interactAt(Player p_19980_, Vec3 p_19981_, InteractionHand p_19982_) {
        if (!animationFactory.isPlaying(ANIMATION_VANISH)) {
            if (!isDeadOrDying() && p_19982_ == InteractionHand.MAIN_HAND) {
                if (gameController.isNoAction()) {
                    animationFactory.play(ANIMATION_START);
                } else {
                    if (!actionController.is(ACTION_PUT_ITEMS)) {
                        if (getSlots()[0].isEmpty()) {
                            actionController.playAction(ACTION_PUT_ITEMS);
                        } else {
                            if (!actionController.is(ACTION_SWAP_BASKETS)) {
                                if (ROLLED.get(this)) {
                                    ROLLED.set(this, false);
                                    randomize();
                                    spawnAtLocation(getSlots()[1]);
                                    kill();
                                } else {
                                    actionController.playAction(ACTION_SWAP_BASKETS);
                                }
                            }
                        }
                    }
                }
            }
        }
        return super.interactAt(p_19980_, p_19981_, p_19982_);
    }


    public boolean isPushable() {
        return false;
    }


    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        animationFactory.play(ANIMATION_VANISH);
        return false;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return DMSounds.FENICK_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return DMSounds.FENICK_DEATH.get();
    }

    public ItemStack[] getSlots(){
        ItemStack[] res = new ItemStack[3];
        res[0] = ITEM_A.get(this);
        res[1] = ITEM_B.get(this);
        res[2] = ITEM_C.get(this);
        return res;
    }

    public void randomize(){
        ItemStack[] slots = getSlots();
        ArrayUtils.shuffle(slots);
        setSlots(slots[0],slots[1],slots[2]);
    }

    public void setSlots(ItemStack a,ItemStack b,ItemStack c){
        ITEM_A.set(this,a);
        ITEM_B.set(this,b);
        ITEM_C.set(this,c);
    }

    @Override
    public ActionStateMachine getActionStateMachine() {
        return stateMachine;
    }

    @Override
    public <K extends IDisplayArgument> AnimationFactory getAnimationFactory(K argument) {
        return animationFactory;
    }
}
