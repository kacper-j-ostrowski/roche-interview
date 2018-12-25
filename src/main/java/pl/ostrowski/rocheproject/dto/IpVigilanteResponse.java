package pl.ostrowski.rocheproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IpVigilanteResponse implements Serializable {

    private double latitude;
    private String country_name;

    public IpVigilanteResponse() {}

    public IpVigilanteResponse(double latitude, String country_name) {
        this.latitude = latitude;
        this.country_name = country_name;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "IpVigilanteResponse{" +
                "latitude=" + latitude +
                ", country_name='" + country_name + '\'' +
                '}';
    }
}
