/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Colin Ruan
 * SBU ID # 111705515
 * Recitation 05
 */
import latlng.LatLng;
import com.cse214.geocoder.GeocodeResponse;
import com.cse214.geocoder.Geocoder;  

public class City {
    
    private String name; //name of city
    private LatLng location; // location of city as latlng object
    private int indexPos; // index of city used later in SigmaAir array list
    static int cityCount; // number of cities
    /**
    * Returns an instance of <code>City</code>.
    *
    * @param n
    *   The name of the city
    **/
    public City (String n) {
        name = n;
        indexPos = cityCount;
        cityCount++;
        try {
    Geocoder geocoder = new Geocoder();
    GeocodeResponse geocodeResponse;
    String addr;
    double lat;
    double lng;
 
    geocodeResponse = geocoder.geocode(n);
    addr = geocodeResponse.getFormattedAddress();
    lat = geocodeResponse.getLat();
    lng = geocodeResponse.getLng();
    LatLng l = new LatLng(lat, lng);
    location = l;
    System.out.println(addr + " " + lat + " " + lng); // latitude longitude 
} catch (Exception e) {
    // error handling goes here
}


    }
    
    public String getName() {
        return name;
    }
    public LatLng getLocation() {
        return location;
    }
    public int getIndexPos() {
        return indexPos;
    }
    public int getCityCount() {
        return cityCount;
    }
    public void setName(String s) {
        name = s;
    }
    public void setLocation(LatLng l) {
        location = l;
    }
    public void setIndexPos(int i) {
        indexPos = i;
    }
    public void setCityCount(int c) {
        cityCount = c;
    }
    
    /**
    * Returns the City object as a string.
    *
    * @return 
    *   Returns <code>City</code> as a String
    **/
    @Override
    public String toString() {
        String s = String.format("%15s\t%30f\t%30f", this.getName(), this.getLocation().getLat(), this.getLocation().getLng());
        return s + "\n";
    }        
           
}
