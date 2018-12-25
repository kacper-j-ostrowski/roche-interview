package pl.ostrowski.rocheproject.validator;

import java.util.Arrays;
import java.util.regex.Pattern;

public class NorthCountriesEndpointValidator {

    private static final Integer MAX_PARAMS_LENGTH = 5;
    private static final Pattern PATTERN = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");

    public static boolean validateLengthOfParams(Integer length) {
        return !(length > MAX_PARAMS_LENGTH || length <= 0);
    }

    public static boolean validateLengthOfParamsIPFormat(String[] ipAddresses) {
        return Arrays.stream(ipAddresses)
                .allMatch(i -> PATTERN.matcher(i).matches());
    }
}