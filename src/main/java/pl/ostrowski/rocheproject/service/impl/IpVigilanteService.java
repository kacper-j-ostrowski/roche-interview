package pl.ostrowski.rocheproject.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.ostrowski.rocheproject.dto.IpVigilanteResponse;
import pl.ostrowski.rocheproject.dto.IpVigilanteResponseWrapper;
import pl.ostrowski.rocheproject.service.IpInformationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class IpVigilanteService implements IpInformationService {

    private static final String IP_VIGILANTE_HOST_URL = "https://ipvigilante.com/json/";
    private static final Logger logger = LoggerFactory.getLogger(IpVigilanteService.class);
    private final RestTemplate restTemplate;

    public IpVigilanteService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    @Override
    public List<String> retrieveNorthCountriesFromIPAddresses(List<String> iPAddresses) {
        ArrayList<IpVigilanteResponse> ipVigilanteResponses = new ArrayList<>();

        iPAddresses.forEach(ip -> {
            String requestURL = IP_VIGILANTE_HOST_URL.concat(ip);
            logger.info("Requesting for: " + requestURL);
            ResponseEntity<IpVigilanteResponseWrapper> response =
                    restTemplate.getForEntity(requestURL, IpVigilanteResponseWrapper.class);

            IpVigilanteResponseWrapper wrapper = response.getBody();
            if (Objects.nonNull(wrapper) && Objects.nonNull(wrapper.getData())) {
                logger.info("Received: " + wrapper.getData());
                ipVigilanteResponses.add(wrapper.getData());
            }
        });

        return ipVigilanteResponses.stream()
                .filter(i -> i.getLatitude() > 0)
                .map(IpVigilanteResponse::getCountry_name)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}
