package com.example.tinynews.countryCode;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class CountryList {

    public static Map<String, String> getCountryMap() {
        String[] isoCountries = Locale.getISOCountries();
        Map<String, String> countryMap = new TreeMap<>();

        for (String country : isoCountries) {
            Locale locale = new Locale("en", country);
            countryMap.put(locale.getCountry(),locale.getDisplayCountry());//code: full name
        }

        return countryMap;
    }
}
