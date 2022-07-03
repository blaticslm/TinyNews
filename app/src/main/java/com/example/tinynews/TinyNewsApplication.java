package com.example.tinynews;

import android.app.Application;

import androidx.room.Room;

import com.example.tinynews.countryCode.CountryList;
import com.example.tinynews.database.TinyNewsDatabase;

import java.util.Map;
import java.util.TreeMap;

public class TinyNewsApplication extends Application {

    public static Map<String, String> currentCountry = new TreeMap<>();//should be here as well
    private static TinyNewsDatabase database; //The database should staying on here. Putting into the Activity will result in memory leak
    public static String country_select = "US";
    public static String country_full_name = "United States";
    public static final String UNIVERSAL_URL = "https://thumbs.dreamstime.com/z/basic-rgb-156262165.jpg";
    //First create: major process
    @Override
    public void onCreate() {
        super.onCreate();
        //new code here
        currentCountry = CountryList.getCountryMap();
        database = Room.databaseBuilder(this, TinyNewsDatabase.class, "tinynews_db").build();
    }

    public static TinyNewsDatabase getDatabase() {
        return database;
    }

    public static void country_setter(String country) {
        country_select = country;
    }

    public static void country_full_name_setter(String country) {
        country_full_name = country;
    }

}
