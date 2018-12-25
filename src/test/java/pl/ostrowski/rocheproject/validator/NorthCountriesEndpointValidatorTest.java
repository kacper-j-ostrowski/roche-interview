package pl.ostrowski.rocheproject.validator;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class NorthCountriesEndpointValidatorTest {

    @Test
    public void validateLengthOfParams() {
        boolean testRes = NorthCountriesEndpointValidator.validateLengthOfParams(0);
        assertFalse(testRes);
        testRes = NorthCountriesEndpointValidator.validateLengthOfParams(6);
        assertFalse(testRes);
        testRes = NorthCountriesEndpointValidator.validateLengthOfParams(1000);
        assertFalse(testRes);
        testRes = NorthCountriesEndpointValidator.validateLengthOfParams(-3);
        assertFalse(testRes);
        testRes = NorthCountriesEndpointValidator.validateLengthOfParams(4);
        assertTrue(testRes);
    }

    @Test
    public void validateLengthOfParamsIPFormat() {
        boolean testRes = NorthCountriesEndpointValidator.validateLengthOfParamsIPFormat((new String[]{"wrong_ip"}));
        assertFalse(testRes);
        testRes = NorthCountriesEndpointValidator.validateLengthOfParamsIPFormat((new String[]{"177.0.0.0"}));
        assertTrue(testRes);
        testRes = NorthCountriesEndpointValidator.validateLengthOfParamsIPFormat((new String[]{"177.0.0.0", "wrong_ip"}));
        assertFalse(testRes);
        testRes = NorthCountriesEndpointValidator.validateLengthOfParamsIPFormat((new String[]{"177.0.0.0", "8.8.8.8"}));
        assertTrue(testRes);
    }
}