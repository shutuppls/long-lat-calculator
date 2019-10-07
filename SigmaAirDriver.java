
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class SigmaAirDriver {
    
    public static void main(String[] args) {
        int K = 0; 
        SigmaAir matrix;
        try {
            FileInputStream file = new FileInputStream("sigma_air.obj");
            ObjectInputStream inStream = new ObjectInputStream(file);
            matrix = (SigmaAir) inStream.readObject();
        } catch (IOException e) {
            matrix = new SigmaAir();  
            System.out.println("sigma_air.obj is not found. New SigmaAir object will be created.");
        } catch (Exception e) {
            matrix = new SigmaAir();  
            System.out.println("sigma_air.obj is not found. New SigmaAir object will be created.");
        }
        while (K == 0) {
        menu();
        Scanner input = new Scanner(System.in);
        String selection = input.nextLine();
        if (selection.equalsIgnoreCase("A")) { // add city 
            System.out.print("Enter the name of the city: ");
            String city = input.nextLine();
            try {
                //int i = matrix.getCityList().get(0).getCityCount();
                matrix.addCity(city);
                //System.out.println(city + " has been added: (" + matrix.getCityList().get(i).getLocation().getLat() + ", " + matrix.getCityList().get(i).getLocation().getLng() + ")");
                System.out.println(city + " has been added");
            } catch (CityAlreadyExistsException e) {
                System.out.println(e.getMessage());
            }
        } else if (selection.equalsIgnoreCase("B")) { // add connection
            System.out.print("Enter source city: ");
            String src = input.nextLine();
            System.out.println("Enter destination city: ");
            String dest = input.nextLine();
            try {
                int i = matrix.getCityList().get(0).getCityCount();
                int j = i + 1;
                matrix.addConnection(src, dest);
                System.out.println(src + " --> " + dest + " added"); //+ LatLng.calculateDistance(matrix.getCityList().get(i).getLocation(), matrix.getCityList().get(j).getLocation()));
            } catch (CityDoesNotExistException e) {
                System.out.println(e.getMessage());
            }
        } else if (selection.equalsIgnoreCase("C")) { // load city file
            System.out.print("Enter the file name: ");
            String filename = input.nextLine();
            try {
                matrix.loadAllCities(filename);
            } catch (CityAlreadyExistsException e) {
                System.out.println(e.getMessage());
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        } else if (selection.equalsIgnoreCase("D")) { // load connection file
            System.out.print("Enter the file name: ");
            String filename = input.nextLine();
            try {
                matrix.loadAllConnections(filename);
            } catch (CityDoesNotExistException e) {
                System.out.println(e.getMessage());
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        } else if (selection.equalsIgnoreCase("E")) { // print all cities
            int S = 0;
            while (S == 0) {
                System.out.println("(EA) Sort Cities by Name");
                System.out.println("(EB) Sort Cities by Latitude");
                System.out.println("(EC) Sort Cities by Longitude");
                System.out.println("(Q) Quit");
                System.out.println();
                System.out.print("Enter a selection: ");
                String choice = input.nextLine();
                if (choice.equalsIgnoreCase("EA")) {
                    NameComparator c = new NameComparator();
                    matrix.printAllCities(c);
                } else if (choice.equalsIgnoreCase("EB")) {
                    LatComparator c = new LatComparator();
                    matrix.printAllCities(c);
                } else if (choice.equalsIgnoreCase("EC")) {
                    LngComparator c = new LngComparator();
                    matrix.printAllCities(c);
                } else if (choice.equalsIgnoreCase("Q")) {
                    S = 1;
                } else {
                    System.out.println("Invalid selection");
                }
            }
        } else if (selection.equalsIgnoreCase("F")) { // print all connections
            matrix.printAllConnections();
        } else if (selection.equalsIgnoreCase("G")) { // remove connection
            System.out.print("Enter source city: ");
            String src = input.nextLine();
            System.out.print("Input destination city: ");
            String dest = input.nextLine();
                try {
                    matrix.removeConnection(src, dest);
                    System.out.println("Connection from " + src + " to " + dest + " has been removed!");
                } catch (CityDoesNotExistException e) {
                    System.out.println(e.getMessage());
                }
        } else if (selection.equalsIgnoreCase("H")) { // find shortest path
            System.out.print("Enter source city: ");
            String src = input.nextLine();
            System.out.print("Input destination city: ");
            String dest = input.nextLine();
                try {
                    System.out.println(matrix.shortestPath(src, dest)); // fix
                } catch (CityDoesNotExistException e) {
                    System.out.println(e.getMessage());
                }
        } else if (selection.equalsIgnoreCase("Q")) { // quit
            K = 1;
            try {
                FileOutputStream file = new FileOutputStream("sigma_air.obj");
                ObjectOutputStream outStream = new ObjectOutputStream(file);
                outStream.writeObject(matrix);
                outStream.close();
                System.out.println("Saved. Quitting");
            } catch (IOException e) {
                System.out.println("Couldn't be saved. Quitting");
            }
        } else {
            System.out.println("Invalid selection");
        }
        
        }
    }
    
    /**
    * Prints the menu
    **/
    public static void menu() {
        System.out.println("(A) Add City\n(B) Add Connection\n(C) Load all Cities\n(D) Load all Connections\n(E) Print all Cities\n(F) Print all Connections\n(G) Remove Connection\n(H) Find Shortest Path\n(Q) Quit");    
    }
   
}
