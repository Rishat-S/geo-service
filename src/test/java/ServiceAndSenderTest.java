import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServiceAndSenderTest {

    public static final String RUSSIAN_IP = "172.123.12.19";
    public static final String USA_IP = "96.44.183.149";
    public static final String RUSSIAN_WORDS = "Добро пожаловать";
    public static final String ENGLISH_WORDS = "Welcome";

    @Test
    @DisplayName("Geo Russian")
    void geoRussian() {
        GeoService geoService = new GeoServiceImpl();
        Location location = geoService.byIp(RUSSIAN_IP);

        assertEquals(Country.RUSSIA, location.getCountry());
    }

    @Test
    @DisplayName("Geo USA")
    void geoUSA() {

        GeoService geoService = new GeoServiceImpl();
        Location location = geoService.byIp(USA_IP);

        assertEquals(Country.USA, location.getCountry());
    }

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

    @Test
    @DisplayName("Message sender sends Russian text")
    void messageSenderSendsRussianText() {

        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Location location = Mockito.mock(Location.class);

        Mockito.when(geoService.byIp(RUSSIAN_IP))
                .thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn(RUSSIAN_WORDS);
        Mockito.when(location.getCountry())
                .thenReturn(Country.RUSSIA);

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<String, String>();

        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, RUSSIAN_IP);
        String message = messageSender.send(headers);
        Assertions.assertEquals(RUSSIAN_WORDS, message);

    }

    @Test
    @DisplayName("Message sender sends English text")
    void messageSenderSendsEnglishText() {

        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Location location = Mockito.mock(Location.class);

        Mockito.when(geoService.byIp(USA_IP))
                .thenReturn(new Location("New York", Country.USA, null, 0));
        Mockito.when(localizationService.locale(Country.USA))
                .thenReturn(ENGLISH_WORDS);
        Mockito.when(location.getCountry())
                .thenReturn(Country.USA);

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<String, String>();

        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, USA_IP);
        String message = messageSender.send(headers);
        Assertions.assertEquals(ENGLISH_WORDS, message);

    }

}