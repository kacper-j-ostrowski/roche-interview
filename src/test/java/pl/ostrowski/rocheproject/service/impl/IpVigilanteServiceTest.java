package pl.ostrowski.rocheproject.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import pl.ostrowski.rocheproject.dto.IpVigilanteResponse;
import pl.ostrowski.rocheproject.dto.IpVigilanteResponseWrapper;

import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@RestClientTest(IpVigilanteService.class)
public class IpVigilanteServiceTest {

    private static final String IP_VIGILANTE_HOST_URL = "https://ipvigilante.com/json/";

    @Autowired
    private IpVigilanteService client;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenOneIPGiven_ShouldResponse() throws JsonProcessingException {
        //given
        final String ipToTest = "8.8.8.8";
        IpVigilanteResponse ipVigilanteResponse = new IpVigilanteResponse(37.38600, "United States");
        String detailsString =
                objectMapper.writeValueAsString(new IpVigilanteResponseWrapper("success", ipVigilanteResponse));
        server.expect(requestTo(IP_VIGILANTE_HOST_URL.concat(ipToTest)))
                .andRespond(withSuccess(detailsString, MediaType.APPLICATION_JSON));

        //when
        List<String> res = client.retrieveNorthCountriesFromIPAddresses(Arrays.asList(new String[]{ipToTest}));

        //then
        assertEquals(1, res.size());
    }

    @Test
    public void whenFiIveIPsGiven_ShouldResponse_With_FiveUniqueCountriesSorted() throws JsonProcessingException {
        //given
        final String[] ipsToTest = new String[] {"177.0.0.0", "190.0.0.0", "160.0.0.0", "123.0.0.1", "140.0.6.0"};
        final IpVigilanteResponse[] countries = new IpVigilanteResponse[] {
                new IpVigilanteResponse(37.38600, "United States"),
                new IpVigilanteResponse(55.38600, "Poland"),
                new IpVigilanteResponse(64.83600, "UK"),
                new IpVigilanteResponse(14.38600, "Myanmar"),
                new IpVigilanteResponse(26.38600, "Japan")
        };

        for(int i=0; i<ipsToTest.length; i++) {
            String detailsString = objectMapper.writeValueAsString(new IpVigilanteResponseWrapper("success", countries[i]));
            server.expect(requestTo(IP_VIGILANTE_HOST_URL.concat(ipsToTest[i])))
                    .andRespond(withSuccess(detailsString, MediaType.APPLICATION_JSON));
        }

        //when
        List<String> res = client.retrieveNorthCountriesFromIPAddresses(Arrays.asList(ipsToTest));

        //then
        assertEquals(5, res.size());
        assertEquals("Japan", res.get(0));
        assertEquals("Myanmar", res.get(1));
        assertEquals("Poland", res.get(2));
        assertEquals("UK", res.get(3));
        assertEquals("United States", res.get(4));
    }

    @Test
    public void whenFiveIPsGiven_ShouldResponse_With_TwoUniqueCountriesSorted() throws JsonProcessingException {
        //given
        final String[] ipsToTest = new String[] {"177.0.0.0", "190.0.0.0", "160.0.0.0", "123.0.0.1", "140.0.6.0"};
        final IpVigilanteResponse[] countries = new IpVigilanteResponse[] {
                new IpVigilanteResponse(37.38600, "Poland"),
                new IpVigilanteResponse(55.38600, "Poland"),
                new IpVigilanteResponse(64.83600, "Myanmar"),
                new IpVigilanteResponse(14.38600, "Myanmar"),
                new IpVigilanteResponse(26.38600, "Myanmar")
        };

        for(int i=0; i<ipsToTest.length; i++) {
            String detailsString = objectMapper.writeValueAsString(new IpVigilanteResponseWrapper("success", countries[i]));
            server.expect(requestTo(IP_VIGILANTE_HOST_URL.concat(ipsToTest[i])))
                    .andRespond(withSuccess(detailsString, MediaType.APPLICATION_JSON));
        }

        //when
        List<String> res = client.retrieveNorthCountriesFromIPAddresses(Arrays.asList(ipsToTest));

        //then
        assertEquals(2, res.size());
        assertEquals("Myanmar", res.get(0));
        assertEquals("Poland", res.get(1));
    }

    @Test
    public void whenFiveIPsGivenThreeFromSouth_ShouldResponse_With_TwoUniqueCountriesSorted() throws JsonProcessingException {
        //given
        final String[] ipsToTest = new String[] {"177.0.0.0", "190.0.0.0", "160.0.0.0", "123.0.0.1", "140.0.6.0"};
        final IpVigilanteResponse[] countries = new IpVigilanteResponse[] {
                new IpVigilanteResponse(-37.38600, "Australia"),
                new IpVigilanteResponse(55.38600, "Poland"),
                new IpVigilanteResponse(-64.83600, "Bali"),
                new IpVigilanteResponse(14.38600, "Myanmar"),
                new IpVigilanteResponse(-26.38600, "South Africa")
        };

        for(int i=0; i<ipsToTest.length; i++) {
            String detailsString = objectMapper.writeValueAsString(new IpVigilanteResponseWrapper("success", countries[i]));
            server.expect(requestTo(IP_VIGILANTE_HOST_URL.concat(ipsToTest[i])))
                    .andRespond(withSuccess(detailsString, MediaType.APPLICATION_JSON));
        }

        //when
        List<String> res = client.retrieveNorthCountriesFromIPAddresses(Arrays.asList(ipsToTest));

        //then
        assertEquals(2, res.size());
        assertEquals("Myanmar", res.get(0));
        assertEquals("Poland", res.get(1));
    }

}