package pl.ostrowski.rocheproject.service;

import java.util.List;

public interface IpInformationService {
    List<String> retrieveNorthCountriesFromIPAddresses(List<String> iPAddresses);
}