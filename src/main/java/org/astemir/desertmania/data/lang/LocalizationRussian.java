package org.astemir.desertmania.data.lang;

import org.astemir.api.data.lang.LangDictionary;
import org.astemir.desertmania.common.block.DMBlocks;
import org.astemir.desertmania.common.effect.DMMobEffects;
import org.astemir.desertmania.common.enchantments.DMEnchantments;
import org.astemir.desertmania.common.entity.DMEntities;
import org.astemir.desertmania.common.item.DMItems;

public class LocalizationRussian {

    private final LangDictionary lang;

    public LocalizationRussian(LangDictionary dictionary) {
        this.lang = dictionary;
        items();
        blocks();
        entities();
        effects();
        lang.add("name.desertmania.empty","(Пустая)");
        lang.add("container.desertmania.basket","Корзина");
        lang.add("container.desertmania.magic_basket","Волшебная корзина");
        lang.add("item.desertmania.music_disc_desertmania.desc","Джонас Загго - Desert Mania");
        lang.addEnchantment(DMEnchantments.WHIRLING,"Вихрь");
    }


    private void items(){
        lang.addItem(DMItems.MUSIC_DISC_DESERTMANIA,"Пластинка");
        lang.addItem(DMItems.SCIMITAR,"Скимитар");
        lang.addItem(DMItems.GOLDEN_SCARAB, "Золотой скарабей");
        lang.addItem(DMItems.GOLDEN_SCARAB_LEFT_PART, "Левая часть скарабея");
        lang.addItem(DMItems.GOLDEN_SCARAB_RIGHT_PART, "Правая часть скарабея");
        lang.addItem(DMItems.CLOTH, "Ткань мумии");
        lang.addItem(DMItems.SHEMAGH, "Шемаг");
        lang.addItem(DMItems.ENCHANTED_SCROLL, "Зачарованный свиток");
        lang.addItem(DMItems.BLUE_GENIE_SPAWN_EGG,"Яйцо призыва синего джинна");
        lang.addItem(DMItems.GREEN_GENIE_SPAWN_EGG,"Яйцо призыва зелёного джинна");
        lang.addItem(DMItems.RED_GENIE_SPAWN_EGG,"Яйцо призыва красного джинна");
        lang.addItem(DMItems.PURPLE_GENIE_SPAWN_EGG,"Яйцо призыва фиолетового джинна");
        lang.addItem(DMItems.MEERKAT_SPAWN_EGG,"Яйцо призыва суриката");
        lang.addItem(DMItems.CAMEL_SPAWN_EGG,"Яйцо призыва верблюда");
        lang.addItem(DMItems.DESERT_WORM_SPAWN_EGG,"Яйцо призыва пустынного червя");
        lang.addItem(DMItems.MUMMY_SPAWN_EGG,"Яйцо призыва мумии");
        lang.addItem(DMItems.SCARAB_SPAWN_EGG,"Яйцо призыва скарабея");
        lang.addItem(DMItems.FENICK_SPAWN_EGG,"Яйцо призыва феника");
        lang.addItem(DMItems.SCARAB_LORD_SPAWN_EGG,"Яйцо призыва Владыки скарабеев");
        lang.addItem(DMItems.FENICK_MAGE_SPAWN_EGG,"Яйцо призыва феника мага");
        lang.addItem(DMItems.BLUE_FLYING_CARPET,"Синий ковёр-самолёт");
        lang.addItem(DMItems.RED_FLYING_CARPET,"Красный ковёр-самолёт");
        lang.addItem(DMItems.GREEN_FLYING_CARPET,"Зелёный ковёр-самолёт");
        lang.addItem(DMItems.PALM_PLANKS_BOAT,"Пальмовая лодка");
        lang.addItem(DMItems.PALM_PLANKS_CHEST_BOAT,"Пальмовая грузовая лодка");
        lang.addItem(DMItems.COCONUT_SLICE,"Половинка кокоса");
        lang.addItem(DMItems.STING_ARROW,"Стрела-жало");
        lang.addItem(DMItems.STING,"Жало");
        lang.addItem(DMItems.ANCIENT_AMULET,"Древний амулет");
    }

    private void blocks(){
        lang.addBlock(DMBlocks.PALM_LOG,"Пальмовое бревно");
        lang.addBlock(DMBlocks.PALM_LOG_STRIPPED,"Обтёсанное пальмовое бревно");
        lang.addBlock(DMBlocks.PALM_WOOD,"Пальмовое дерево");
        lang.addBlock(DMBlocks.PALM_WOOD_STRIPPED,"Обтёсанная пальмовая древесина");
        lang.addBlock(DMBlocks.PALM_PLANKS,"Пальмовые доски");
        lang.addBlock(DMBlocks.PALM_PLANKS_DOOR,"Пальмовая дверь");
        lang.addBlock(DMBlocks.PALM_PLANKS_BUTTON,"Пальмовая кнопка");
        lang.addBlock(DMBlocks.PALM_PLANKS_GATE,"Пальмовая калитка");
        lang.addBlock(DMBlocks.PALM_PLANKS_PRESSURE_PLATE,"Пальмовая нажимная плита");
        lang.addBlock(DMBlocks.PALM_PLANKS_STAIRS,"Ступеньки из пальмовых досок");
        lang.addBlock(DMBlocks.PALM_PLANKS_FENCE,"Забор из пальмы");
        lang.addBlock(DMBlocks.PALM_PLANKS_TRAPDOOR,"Пальмовый люк");
        lang.addBlock(DMBlocks.PALM_PLANKS_SLAB,"Полублок пальмовых досок");
        lang.addBlock(DMBlocks.COCONUT_BLOCK,"Кокос");
        lang.addBlock(DMBlocks.PALM_LEAVES,"Пальмовые листья");
        lang.addBlock(DMBlocks.PALM_PLANKS_SIGN,"Пальмовая табличка");
        lang.addBlock(DMBlocks.BRAZIER,"Жаровня");
        lang.addBlock(DMBlocks.DUNE_SAND,"Дюнный песок");
        lang.addBlock(DMBlocks.GENIE_LAMP,"Лампа джинна");
        lang.addBlock(DMBlocks.LOESS,"Лёсс");
        lang.addBlock(DMBlocks.CHISELED_LOESS,"Резной лёсс");
        lang.addBlock(DMBlocks.GIANT_LOESS_BRICK,"Огромный полированный лёссовый кирпич");
        lang.addBlock(DMBlocks.CUT_LOESS,"Полированный лёсс");
        lang.addBlock(DMBlocks.CUT_LOESS_STAIRS,"Ступеньки из полированного лёсса");
        lang.addBlock(DMBlocks.CUT_LOESS_WALL,"Ограда из полированного лёсса");
        lang.addBlock(DMBlocks.CUT_LOESS_SLAB,"Полублок полированного лёсса");
        lang.addBlock(DMBlocks.LOESS_BRICKS,"Лёссовые кирпичи");
        lang.addBlock(DMBlocks.LOESS_BRICKS_SYMBOLS,"Лёссовые кирпичи с символами");
        lang.addBlock(DMBlocks.LOESS_BRICKS_STAIRS,"Ступеньки из лёссового кирпича");
        lang.addBlock(DMBlocks.LOESS_BRICKS_WALL,"Ограда из лёссового кирпича");
        lang.addBlock(DMBlocks.LOESS_BRICKS_SLAB,"Полублок лёссового кирпича");
        lang.addBlock(DMBlocks.SMOOTH_LOESS,"Гладкий лёсс");
        lang.addBlock(DMBlocks.SMOOTH_LOESS_STAIRS,"Ступеньки из гладкого лёсса");
        lang.addBlock(DMBlocks.SMOOTH_LOESS_WALL,"Ограда из гладкого лёсса");
        lang.addBlock(DMBlocks.SMOOTH_LOESS_SLAB,"Полублок гладкого лёсса");
        lang.addBlock(DMBlocks.PALM_SAPLING,"Саженец пальмы");
        lang.addBlock(DMBlocks.OASIS_GRASS_BLOCK,"Плодородная трава");
        lang.addBlock(DMBlocks.OASIS_DIRT,"Плодородная земля");
        lang.addBlock(DMBlocks.FIRE_TRAP,"Огненная ловушка");
        lang.addBlock(DMBlocks.SUN_ALTAR,"Алтарь солнца");
        lang.addBlock(DMBlocks.BASKET,"Корзина");
        lang.addBlock(DMBlocks.MAGIC_BASKET,"Волшебная корзина");
        lang.addBlock(DMBlocks.OASIS_GRASS,"Трава оазиса");
        lang.addBlock(DMBlocks.SHORT_DESERT_GRASS,"Короткая пустынная трава");
        lang.addBlock(DMBlocks.OASIS_TALL_GRASS,"Высокая трава оазиса");
        lang.addBlock(DMBlocks.FADED_DESERT_GRASS,"Выцветшая высокая пустынная трава");
        lang.addBlock(DMBlocks.DESERT_GRASS,"Высокая пустынная трава");
        lang.addBlock(DMBlocks.SAND_GRASS,"Песочная трава");
        lang.addBlock(DMBlocks.CAMEL_THORN,"Верблюжья колючка");
        lang.addBlock(DMBlocks.OASIS_FARMLAND,"Пашня оазиса");
    }

    private void entities(){
        lang.addEntityType(DMEntities.MEERKAT,"Сурикат");
        lang.addEntityType(DMEntities.CAMEL,"Верблюд");
        lang.addEntityType(DMEntities.DESERT_WORM,"Пустынный червь");
        lang.addEntityType(DMEntities.MUMMY,"Мумия");
        lang.addEntityType(DMEntities.SCARAB,"Скарабей");
        lang.addEntityType(DMEntities.FLYING_CARPET,"Ковёр-самолёт");
        lang.addEntityType(DMEntities.FENICK,"Феник");
        lang.addEntityType(DMEntities.SCARAB_LORD,"Владыка скарабеев");
        lang.addEntityType(DMEntities.BLUE_GENIE,"Синий джинн");
        lang.addEntityType(DMEntities.GREEN_GENIE,"Зелёный джинн");
        lang.addEntityType(DMEntities.RED_GENIE,"Красный джинн");
        lang.addEntityType(DMEntities.PURPLE_GENIE,"Фиолетовый джинн");
        lang.addEntityType(DMEntities.BOAT,"Лодка");
        lang.addEntityType(DMEntities.CHEST_BOAT,"Грузовая лодка");
        lang.addEntityType(DMEntities.GENIE_CHARGE,"Снаряд джинна");
        lang.addEntityType(DMEntities.FENICK_MAGE,"Феник маг");
        lang.addEntityType(DMEntities.SCORPION,"Скорпион");
        lang.addEntityType(DMEntities.FAKIR,"Феник факир");
        lang.addEntityType(DMEntities.CACTUPINE,"Кактусобраз");
        lang.addEntityType(DMEntities.STING_ARROW,"Стрела-жало");
        lang.addEntityType(DMEntities.POLYMORPH,"Полиморф");
        lang.addEntityType(DMEntities.GOLDEN_SCARAB,"Золотой скарабей");
        lang.addEntityType(DMEntities.CLOUD,"Волшебное облако");
        lang.addEntityType(DMEntities.BOX_GLOVE,"Боксёрская перчатка");
    }

    private void effects(){
        lang.addEffect(DMMobEffects.MUMMY_CURSE,"Проклятие мумии");
    }
}
