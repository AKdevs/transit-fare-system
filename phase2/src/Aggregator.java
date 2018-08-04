import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Aggregator {
    /** Stores the total amount of accumulated fares in the system by date */
    private HashMap<String, Double> allFares;

    /** Stores the number of stations reached in the entire system by date */
    private HashMap<String, Integer> numberOfStations;

    Aggregator(){
        this.allFares = new HashMap<>();
        this.numberOfStations = new HashMap<>();
/*
      try
      {
        // Reading the object from a file
        FileInputStream file = new FileInputStream("data-Aggregator.bin");
        ObjectInputStream in = new ObjectInputStream(file);

        // Method for deserialization of object
        allFares = (HashMap<String, Double>)in.readObject();
        numberOfStations = (HashMap<String, Integer>)in.readObject();

        in.close();
        file.close();

        System.out.println("Object has been deserialized ");
      }

      catch(IOException ex)
      {
        System.out.println("IOException is caught");
      }

      catch(ClassNotFoundException ex)
      {
        System.out.println("ClassNotFoundException is caught");
      }*/
    }

    /** @return amount of fares in system, stored by date. */
    public HashMap<String, Double> getAllFares() {
        return allFares;
    }

    /**
     * Returns the amount of total fare accumulated in the day.
     *
     * @param date date for which fare is to be returned.
     * @return the amount of fare accumulated on date.
     */
    Double getDailyFares(String date) {
        return allFares.get(date);
    }

    /**
     * Return the number of stations reached on date.
     *
     * @param date date for which number of stations is to be returned.
     * @return the number of stations reached on date.
     */
    Integer getDailyStation(String date) {
        return numberOfStations.get(date);
    }

    /**
     * Add amount of fares accumulated on date to total list of fares (which is stored by date)
     *
     * @param date date on which fares were accumulated.
     * @param fares fares accumulated on date.
     */
    void updateAllFares(String date, double fares) {
        if (allFares.containsKey(date)) {
            for (String d : allFares.keySet()) {
                if (d.equals(date)) {
                    Double f = allFares.get(d);
                    //double f = allFares.get(d);
                    f += fares;
                    allFares.put(d, f);
                }
            }
        } else {
            allFares.put(date, fares);
        }

      try
      {
        //Saving of object in a file
        FileOutputStream file = new FileOutputStream("data-Aggregator.bin");
        ObjectOutputStream out = new ObjectOutputStream(file);

        // Method for serialization of object
        out.writeObject(allFares);

        out.close();
        file.close();

        System.out.println("Object has been serialized");

      }
      catch(IOException ex)
      {
        System.out.println("IOException is caught");
      }

    }

    /**
     * Add number of stations reached on date to total list of stations reached (which is stored by
     * date)
     *
     * @param date date for which number of stations reached is to be added.
     * @param n number of stations reached on date.
     */
    void addNumberOfStation(String date, int n) {
        if (numberOfStations.containsKey(date)) {
            for (String d : numberOfStations.keySet()) {
                if (d.equals(date)) {
                    Integer stationNum = numberOfStations.get(d);
                    stationNum += n;
                    numberOfStations.put(d, stationNum);
                }
            }
        } else {
            numberOfStations.put(date, n);
        }

      try
      {
        //Saving of object in a file
        FileOutputStream file = new FileOutputStream("data-Aggregator.bin");
        ObjectOutputStream out = new ObjectOutputStream(file);

        // Method for serialization of object
        out.writeObject(numberOfStations);

        out.close();
        file.close();

        System.out.println("Object has been serialized");

      }
      catch(IOException ex)
      {
        System.out.println("IOException is caught");
      }
    }

    /**
     * Prints out total amount of fare collected for date and total number of bus stops/subway
     * stations reached by passengers on that date.
     *
     * @param date the date which AdminUser would like to view the report for
     */

    String getDailyReport(String date) {

        String dailyReport = "\n";
        dailyReport = dailyReport + "The amount of all fares collected is $" + getDailyFares(date)
                + ".\n";
        dailyReport = dailyReport + "The number of stations reached by travellers is " + getDailyStation(date)
                + ".\n";

        return dailyReport;

        /*System.out.println(
                "All fares received by transit system on "
                        + date
                        + ": "
                        + getDailyFares(date)); */
        /*System.out.println(
                "Number of stations reached by travellers on "
                        + date
                        + ": "
                        + getDailyStation(date)); */
    }

    public void initializeDate(String date) {
        if (!(allFares.containsKey(date))){
            allFares.put(date,0.0);
        }
        if (!(numberOfStations.containsKey(date))) {
            numberOfStations.put(date,0);
        }
    }
}
