package org.astemir.desertmania.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.astemir.desertmania.DesertMania;


public class DMMobEffects {


    public static DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, DesertMania.MOD_ID);

    public static RegistryObject<MobEffect> MUMMY_CURSE = EFFECTS.register("mummy_curse",MobEffectMummyCurse::new);
}
