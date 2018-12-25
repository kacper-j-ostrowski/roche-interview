package pl.ostrowski.rocheproject.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.ostrowski.rocheproject.dto.NorthCountryResponse;
import pl.ostrowski.rocheproject.service.IpInformationService;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NorthCountriesRestControllerTest {

    @Mock
    private IpInformationService ipInformationService;

    private NorthCountriesRestController northCountriesRestController;


    @Test
    public void whenMoreThanFiveIps_ThenShouldResponseWithBadRequest() {
        //given
        northCountriesRestController = new NorthCountriesRestController(ipInformationService);
        final String[] ips = new String[]{"177.0.0.0", "190.0.0.0", "160.0.0.0", "123.0.0.1", "140.0.6.0", "177.0.0.0"};

        //when
        ResponseEntity<NorthCountryResponse> res = northCountriesRestController.checkNorthCountries(ips);

        //then
        assertNotNull(res);
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
    }


    @Test
    public void whenMoreNoIps_ThenShouldResponseWithBadRequest() {
        //given
        final String[] ips = new String[]{};
        northCountriesRestController = new NorthCountriesRestController(ipInformationService);

        //when
        ResponseEntity<NorthCountryResponse> res = northCountriesRestController.checkNorthCountries(ips);

        //then
        assertNotNull(res);
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
    }

    @Test
    public void whenMoreValidIps_ThenShouldResponseWithSuccess() {
        //given
        final String[] ips = new String[]{"177.0.0.0", "190.0.0.0", "160.0.0.0", "123.0.0.1"};
        ArrayList<String> countries = new ArrayList<>();
        countries.addAll(Arrays.asList("Austria", "Brazil", "Czechia", "Denmark"));
        when(ipInformationService.retrieveNorthCountriesFromIPAddresses(Arrays.asList(ips)))
                .thenReturn(countries);
        northCountriesRestController = new NorthCountriesRestController(ipInformationService);

        //when
        ResponseEntity<NorthCountryResponse> res = northCountriesRestController.checkNorthCountries(ips);

        //then
        assertNotNull(res);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(4, res.getBody().getNorthcountries().size());
        assertEquals("Austria", res.getBody().getNorthcountries().get(0));
        assertEquals("Brazil", res.getBody().getNorthcountries().get(1));
        assertEquals("Czechia", res.getBody().getNorthcountries().get(2));
        assertEquals("Denmark", res.getBody().getNorthcountries().get(3));
    }


}