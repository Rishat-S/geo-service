package ru.netology.geo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import static org.junit.jupiter.api.Assertions.*;

class GeoServiceTest {

    public static final String RUSSIAN_IP = "172.123.12.19";
    public static final String USA_IP = "96.44.183.149";

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
}