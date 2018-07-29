import java.io.Serializable;
import java.util.HashMap;

public class Aggregator implements Serializable{
    /** Stores the total amount of accumulated fares in the system by date */
    private HashMap<String, Double> allFares;

    /** Stores the number of stations reached in the entire system by date */
    private HashMap<String, Integer> numberOfStations;

    Aggregator(){
        this.allFares = new HashMap<>();
        this.numberOfStations = new HashMap<>();
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

    void getDailyReport(String date) {
        System.out.println(
                "All fares received by transit system on "
                        + date
                        + ": "
                        + getDailyFares(date));
        System.out.println(
                "Number of stations reached by travellers on "
                        + date
                        + ": "
                        + getDailyStation(date));
    }
}
