package ru.netology.i18n;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;

import static org.junit.jupiter.api.Assertions.*;

class LocalizationServiceTest {
    public static final String RUSSIAN_WORDS = "Добро пожаловать";
    public static final String ENGLISH_WORDS = "Welcome";

    @Test
    @DisplayName("Localization Russia")
    void localizationRussia() {
        LocalizationService localizationService = new LocalizationServiceImpl();
        String message = localizationService.locale(Country.RUSSIA);

        assertEquals(RUSSIAN_WORDS, message);
    }

    @Test
    @DisplayName("Localization USA")
    void localizationUSA() {
        LocalizationService localizationService = new LocalizationServiceImpl();
        String message = localizationService.locale(Country.USA);

        assertEquals(ENGLISH_WORDS, message);
    }

}