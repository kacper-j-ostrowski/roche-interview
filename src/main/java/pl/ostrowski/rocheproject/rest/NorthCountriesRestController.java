package pl.ostrowski.rocheproject.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.ostrowski.rocheproject.dto.NorthCountryResponse;
import pl.ostrowski.rocheproject.service.IpInformationService;

import java.util.ArrayList;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static pl.ostrowski.rocheproject.validator.NorthCountriesEndpointValidator.validateLengthOfParams;
import static pl.ostrowski.rocheproject.validator.NorthCountriesEndpointValidator.validateLengthOfParamsIPFormat;

@Controller
@RequestMapping("/northcountries")
public class NorthCountriesRestController {

    private IpInformationService ipInformationService;

    private static final Logger logger = LoggerFactory.getLogger(NorthCountriesRestController.class);

    @Autowired
    public NorthCountriesRestController(IpInformationService ipInformationService) {
        this.ipInformationService = ipInformationService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<NorthCountryResponse> checkNorthCountries(@RequestParam String[] ip) {

        logger.info("Received request: " + Arrays.toString(ip));

        if(!validateLengthOfParams(ip.length) || !validateLengthOfParamsIPFormat(ip)) {
            logger.info("Bad request - validation failed");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        ArrayList<String> listOfNorthCountries =
                (ArrayList<String>)ipInformationService.retrieveNorthCountriesFromIPAddresses(Arrays.asList(ip));

        logger.info("Response :" + listOfNorthCountries);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new NorthCountryResponse(listOfNorthCountries));
    }
}
