package pl.ostrowski.rocheproject.dto;

import java.util.ArrayList;

public class NorthCountryResponse {

    private ArrayList<String> northcountries;

    public NorthCountryResponse(ArrayList<String> northcountries) {
        this.northcountries = northcountries;
    }

    public ArrayList<String> getNorthcountries() {
        return northcountries;
    }
}