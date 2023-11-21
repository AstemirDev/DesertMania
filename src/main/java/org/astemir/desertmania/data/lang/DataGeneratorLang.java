package org.astemir.desertmania.data.lang;

import net.minecraft.data.DataGenerator;
import org.astemir.api.data.lang.SkillsLangProvider;

public class DataGeneratorLang extends SkillsLangProvider {

    public DataGeneratorLang(DataGenerator generator, String modId) {
        super(modId);
        new LocalizationRussian(addLang("ru_ru"));
        new LocalizationEnglish(addLang("en_us"));
        register(generator);
    }


}
