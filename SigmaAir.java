
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import latlng.LatLng;
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
public class SigmaAir {
    
    private ArrayList<City> cities; // an arraylist that contains the City objects
    public static final int MAX_CITIES = 100; // max # of cities this object can contain
    private double[][] connections; // adjacency matrix for directed graph
    /**
    * Returns an instance of <code>SigmaAir</code>.
    * No parameters
    **/
    public SigmaAir() {
        cities = new ArrayList<City>();
        connections = new double[MAX_CITIES][MAX_CITIES];
        for (int i = 0; i < MAX_CITIES; i++) {
            for (int j = 0; j < MAX_CITIES; j++) {
                connections[i][j] = Double.MAX_VALUE;
            }
        }
    }
    
    public ArrayList<City> getCityList() {
        return cities;
    }
    
    /**
    * Adds a <code>City</code> in to the cities arraylist in this object.
    *
    * @param city 
    *    The name of new city
    * <dt>Preconditions:
    *    <dd> The city does not exist in the cities arraylist yet
    *
    * <dt>Postconditions:
    *    <dd>The city is added into the cities arraylist
    *
    * @exception CityAlreadyExistsException
    **/
    public void addCity(String city) throws CityAlreadyExistsException { // check if already exists or not
        boolean exists = false;
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).getName().equalsIgnoreCase(city)) {
                exists = true;
            }
        }
        if (exists == true) {
            throw new CityAlreadyExistsException("City already exists");
        } else {
        City c = new City(city);
        cities.add(c);
        
        }
    }
    /**
    * Adds a double value in to the connections adjacency matrix.
    *
    * @param cityFrom 
    *    The name of source city
    * @param cityTo
    *    The name of destination city
    * <dt>Preconditions:
    *    <dd> The city already exists in the cities arraylist and has been added beforehand
    *
    * <dt>Postconditions:
    *    <dd>The double value which represents the distance between the two cities is added to the adjacency matrix with its indices.
    *
    * @exception CityDoesNotExistException
    **/
    public void addConnection(String cityFrom, String cityTo) throws CityDoesNotExistException { // calc distance
        City c1 = null;
        City c2 = null;
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).getName().equalsIgnoreCase(cityFrom)) {
                c1 = cities.get(i);
            }
            if (cities.get(i).getName().equalsIgnoreCase(cityTo)) {
                c2 = cities.get(i);
            }
        }
        if (c1 == null || c2 == null) {
            throw new CityDoesNotExistException("City does not exist");
        }
        LatLng src = c1.getLocation();
        LatLng dest = c2.getLocation();
        double distance = LatLng.calculateDistance(src, dest);
        if (c1.getName().equalsIgnoreCase(c2.getName())) {
           connections[c1.getIndexPos()][c1.getIndexPos()] = 0; 
        } else {
        connections[c1.getIndexPos()][c2.getIndexPos()] = distance;
        }
    }
    
    /**
    * Removes a value from the connections adjacency matrix
    *
    * @param cityFrom 
    *    The name of source city
    * @param cityTo
    *    The name of destination city
    * <dt>Preconditions:
    *    <dd> The connection already exists in the adjacency matrix and has to be removed
    *
    * <dt>Postconditions:
    *    <dd>The connection is removed from the matrix
    *
    * @exception CityDoesNotExistException
    **/
    public void removeConnection(String cityFrom, String cityTo) throws CityDoesNotExistException { // only if it exists.
        City cityfrom = null; // temp city
        City cityto = null;// temp city
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).getName().equalsIgnoreCase(cityFrom)) {
                cityfrom = cities.get(i);
            }
            if (cities.get(i).getName().equalsIgnoreCase(cityTo)) {
                cityto = cities.get(i);
            }
        }
        if (cityfrom == null || cityto == null) {
            throw new CityDoesNotExistException("City does not exist");
        }
        connections[cityfrom.getIndexPos()][cityto.getIndexPos()] = Double.MAX_VALUE;   
    }
    
    /**
    * Returns the shortest path from one city to the other as a String and its total distance
    * @param cityFrom
    *    The source city name
    * * @param cityTo
    *    The destination city name
    * * <dt>Preconditions:
    *    <dd> There is a path to the destination city and it may be direct or indirect
    *
    * <dt>Postconditions:
    *    <dd>Total distance of the shortest path is calculated and printed out.
    *
    * @exception CityDoesNotExistException
    **
    * @return 
    *   Returns <code>String</code>
    **/
    public String shortestPath(String cityFrom, String cityTo) throws CityDoesNotExistException {
        City c1 = null;
        City c2 = null;
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).getName().equalsIgnoreCase(cityFrom)) {
                c1 = cities.get(i);
            }
            if (cities.get(i).getName().equalsIgnoreCase(cityTo)) {
                c2 = cities.get(i);
            }
        }
        if (c1 == null || c2 == null) {
            throw new CityDoesNotExistException("City does not exist");
        }
        if (connections[c1.getIndexPos()][c2.getIndexPos()] != Double.MAX_VALUE) {
            return "Shortest path from " + c1.getName() + " to " + c2.getName() + ": \n" + c1.getName() + " --> " + c2.getName() + " : " + connections[c1.getIndexPos()][c2.getIndexPos()];
        } 
        double shortestDist = Double.MAX_VALUE;
        int shortIndex = 0;
        int j = c2.getIndexPos();
        for (int i = 0; i < cities.size(); i++) { 
            if (connections[i][j] < shortestDist) {
                shortestDist = connections[i][j];
                shortIndex = i;
            }
        }
        j = shortIndex;
        if (shortestDist == Double.MAX_VALUE || connections[c1.getIndexPos()][j] == Double.MAX_VALUE) {
            return "Shortest path does not exist";
        }
        return "Shortest path from " + c1.getName() + " to " + c2.getName() + ": \n" + c1.getName() + " --> " + cities.get(shortIndex).getName() + " --> " + c2.getName() + " " + (connections[c1.getIndexPos()][j] + shortestDist);
        
    }
    
   /**
    * Prints all cities in the cities arraylist as Strings
    * * @param comp 
    *    The way the list is going to be sorted based on name, latitude, or longitude of the cities
    **/ 
    public void printAllCities(Comparator comp) {
        if (comp instanceof NameComparator) {
            ArrayList<City> sort = new ArrayList<City>();
            sort.addAll(cities);
            City[] sorted = (City[])sort.toArray(new City[0]);
            for (int i = 0; i < sorted.length - 1; i++) {
                for (int j = 0; j < sorted.length - 1; j++) {
                    if (comp.compare((City)sorted[j], (City)sorted[j+1]) > 0) {
                        City temp = sorted[j];
                        sorted[j] = sorted[j+1];
                        sorted[j+1] = temp;
                    }
                }  
            }
            String s = String.format("%15s\t%30s\t%30s", "City Name", "Latitude", "Longitude");
            s += "\n" + String.format("%15s\t%30s\t%30s", "----------------", "--------------------------------", "--------------------------------") + "\n";
            for (int i = 0; i < sorted.length; i++) {
                s += sorted[i].toString();
            }
            System.out.println(s);
            
        } else if (comp instanceof LatComparator) {
            ArrayList<City> sort = new ArrayList<City>();
            sort.addAll(cities);
            City[] sorted = (City[])sort.toArray(new City[0]);
            for (int i = 0; i < sorted.length - 1; i++) {
                for (int j = 0; j < sorted.length - 1; j++) {
                    if (comp.compare((City)sorted[j], (City)sorted[j+1]) == 1) {
                        City temp = sorted[j];
                        sorted[j] = sorted[j+1];
                        sorted[j+1] = temp;
                    }
                }
            }
            String s = String.format("%15s\t%30s\t%30s", "City Name", "Latitude", "Longitude");
            s += "\n" + String.format("%15s\t%30s\t%30s", "----------------", "--------------------------------", "--------------------------------") + "\n";
            for (int i = 0; i < sorted.length; i++) {
                 s += sorted[i].toString() ;
            }
            System.out.println(s);
        } else if (comp instanceof LngComparator) {
            ArrayList<City> sort = new ArrayList<City>();
            sort.addAll(cities);
            City[] sorted = (City[])sort.toArray(new City[0]);
            for (int i = 0; i < sorted.length - 1; i++) {
                for (int j = 0; j < sorted.length - 1; j++) {
                    if (comp.compare((City)sorted[j], (City)sorted[j+1]) == 1) {
                        City temp = sorted[j];
                        sorted[j] = sorted[j+1];
                        sorted[j+1] = temp;
                    }
                }  
            }
            String s = String.format("%15s\t%30s\t%30s", "City Name", "Latitude", "Longitude");
            s += "\n" + String.format("%15s\t%30s\t%30s", "----------------", "--------------------------------", "--------------------------------") + "\n";
            for (int i = 0; i < sorted.length; i++) {
                 s += sorted[i].toString();
            }
            System.out.println(s);
        }
    }
    
    /**
    * Prints all existing connections in the connections adjacency matrix as a String. Not printed if the value is infinity which indicates there is no link.
    **/ 
    public void printAllConnections() {
        System.out.println("Connections: ");
        String s = String.format("%30s\t%30s", "Route", "Distance");
        s += "\n" + String.format("%30s\t%30s", "--------------------------------", "--------------------------------") + "\n";
        for (int i = 0; i < cities.size(); i++) {
            for (int j = 0; j < cities.size(); j++) {
                if (connections[i][j] != Double.MAX_VALUE) {
                    s += String.format("%30s\t%30f", cities.get(i).getName() + " --> " + cities.get(j).getName(), LatLng.calculateDistance(cities.get(i).getLocation(), cities.get(j).getLocation()));
                    s += "\n";
                }
            }
        }
        System.out.println(s);
    }
    
    /**
    * Adds multiple <code>City</code> in to the cities arraylist by parsing a .txt file with the names of Cities in them.
    *
    * @param filename 
    *    The name of .txt file to be read.
    * <dt>Preconditions:
    *    <dd> The file exists
    *
    * <dt>Postconditions:
    *    <dd>All cities added
    *
    * @exception FileNotFoundException, CityAlreadyExistsException
    **/
    public void loadAllCities(String filename) throws FileNotFoundException, CityAlreadyExistsException {
        Scanner reader = new Scanner(new File(filename), "UTF-8"); 
        while (reader.hasNextLine()) {
            String city = reader.nextLine(); // the xml file name
            //int i = this.cities.get(0).getCityCount();
            addCity(city);
            //System.out.println(city + " has been added: (" + cities.get(i).getLocation().getLat() + ", " + cities.get(i).getLocation().getLng() + ")");
            System.out.println(city + " has been added");
        }
    }
    
    /**
    * Adds multiple double values in to the connections matrix by parsing a .txt file names of the cities and connections separated by a comma
    *
    * @param filename 
    *    The name of .txt file to be read.
    * <dt>Preconditions:
    *    <dd> The cities already exist
    *
    * <dt>Postconditions:
    *    <dd>All connections are added to the adjacency matrix
    *
    * @exception FileNotFoundException, CityDoesNotExistException
    **/
    public void loadAllConnections(String filename) throws FileNotFoundException, CityDoesNotExistException {
        Scanner reader = new Scanner(new File(filename), "UTF-8");
        while (reader.hasNextLine()) {
            String connection = reader.nextLine(); // the xml file name
            int comma = connection.indexOf(",");
            String c1 = connection.substring(0, comma);
            String c2 = connection.substring(comma + 1, connection.length());
            try {
//                int i = this.cities.get(0).getCityCount();
//                int j = i + 1;
                addConnection(c1, c2);
                System.out.println(c1 + " --> " + c2 + " added"); //+ LatLng.calculateDistance(cities.get(i).getLocation(), cities.get(j).getLocation()));
            } catch (CityDoesNotExistException e) {
                System.out.println("Error adding connection: " + c1 + " --> " + c2);
            }
            
        }
    }
}
