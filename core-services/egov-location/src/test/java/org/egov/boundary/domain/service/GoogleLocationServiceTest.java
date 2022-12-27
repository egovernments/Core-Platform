package org.egov.boundary.domain.service;

import org.egov.boundary.domain.model.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class GoogleLocationServiceTest {

    private final GoogleLocationService locationService = new GoogleLocationService();

    @Test
    public void reverseGeoCode() {
        double latitude = 16.56163928157282;
        double longitude = 81.99713218957186;
        String cityName = "Amalapuram";
        Optional<Location> location = locationService.reverseGeoCode(latitude, longitude);
        location.ifPresent(location1 -> Assertions.assertTrue(location1.getCity().equalsIgnoreCase(cityName), "Correct city is being returned"));
    }


    @Test
    public void reverseGeoCodeError() {
        double latitude = 1612.56163928157282;
        double longitude = 81.99713218957186;
        Optional<Location> location = locationService.reverseGeoCode(latitude, longitude);
        Assertions.assertTrue(!location.isPresent(), "Empty is returned for invalid lat lng combination");
    }
}
