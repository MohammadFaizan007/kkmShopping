package com.digitalcashbag.utilities.movie.activities;

import java.util.ArrayList;
import java.util.List;

public class StoresData {
    private static List<String> stores;

    static {
        stores = new ArrayList<String>();
        stores.add("Lucknow");
        stores.add("Delhi");
        stores.add("Bangalore");
        stores.add("Chennai");
        stores.add("Mysore");
        stores.add("Agra");
        stores.add("Kolkata");
        stores.add("Mumbai");
        stores.add("Jaipur");
        stores.add("Varanasi");
        stores.add("Udaipur");
        stores.add("Gwalior");
        stores.add("Kanpur");
        stores.add("Nagpur");
        stores.add("Indore");
        stores.add("Noida");
        stores.add("Amritsar");
        stores.add("Mathura");
    }

    public static List<String> getStores() {
        return stores;
    }

    public static List<String> filterData(String searchString) {
        List<String> searchResults = new ArrayList<String>();
        if (searchString != null) {
            searchString = searchString.toLowerCase();

            for (String rec : stores) {
                if (rec.toLowerCase().contains(searchString)) {
                    searchResults.add(rec);
                }
            }
        }
        return searchResults;
    }
}