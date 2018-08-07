import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Aggregator is used to record the reports for the whole transit system
 */
public class Aggregator implements Serializable{
    /** Stores the total amount of accumulated fares in the system by date */
    private HashMap<String, Double> allFares;

    /** Stores the number of stations reached in the entire system by date */
    private HashMap<String, Integer> numberOfStations;

    /** Stores the date when the daily report would be available*/
    private Set<String> availableDate;

    /** Stores the daily stat information of Transit Line by date*/
    private HashMap<String, TransitLineDailyStat> transitLineDailySummary;

  /**
   * Constructs an aggregator
   */
  Aggregator() {
    this.allFares = new HashMap<>();
    this.numberOfStations = new HashMap<>();
    this.availableDate = new HashSet<String>();
    this.transitLineDailySummary = new HashMap<>();
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

    /**
     * Gets TransitLineDailyStat with date
     * @param date
     * @return TransitLineDailyStat
     */
    public TransitLineDailyStat getTransitLineDailyStat (String date) {
        return transitLineDailySummary.get(date);
    }

    /**
     * Adds <date, TransitLineDailyStat> to transitLineDailySummary.
     * @param date
     * @param tLDS
     */
    public void addTransitLineDailyStat (String date, TransitLineDailyStat tLDS){
        transitLineDailySummary.put(date, tLDS);
    }

    /**
     *  Sets default value for attributes when Transit System is powered on for the
     *  first time on date.
     * @param date
     */
    public void initializeDate(String date) {
        availableDate.add(date);
        if (!(allFares.containsKey(date))){
            allFares.put(date,0.0);
        }
        if (!(numberOfStations.containsKey(date))) {
            numberOfStations.put(date,0);
        }
        if (!(transitLineDailySummary.containsKey(date))) {
            TransitLineDailyStat tLDS = new TransitLineDailyStat();
            transitLineDailySummary.put(date, tLDS);
        }
    }

   /**
   * Checks if the report is available for the required date
   *
   * @param date required date for report
   * @return boolean
   */
    public boolean isReportAvailable (String date) {
        return availableDate.contains(date);
    }

   /**
   * Deletes the old data
    *
   * @param date date
   */
   public void deleteOldData(String date){
        this.allFares.remove(date);
        this.numberOfStations.remove(date);
        this.availableDate.remove(date);
        this.transitLineDailySummary.remove(date);

    }


}
