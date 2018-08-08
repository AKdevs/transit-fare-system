import java.io.Serializable;

/**
 * SingleTransitLineDailyStat is used to record and update the statistic for the daily single trip
 */

public class SingleTransitLineDailyStat implements Serializable {
    /** stores id of the Transit Line*/
    private String id;
    /** stores number of single-way trips operated */
    private Integer numOfTrips;
    /** stores number of passengers travelled in Transit Line*/
    private Integer ridership;
    /** stores average number of passengers travelled in Transit Line per trip*/
    private Integer avgRiderPerTrip;

    /**
     * Constructs a single transit line daily statistic
     *
     * @param id id of transit line
     * @param numOfTrips number of single-way trips operated
     * @param ridership number of passengers travelled in Transit Line
     * @param avgRiderPerTrip average number of passengers travelled in Transit Line per trip
     */

    public SingleTransitLineDailyStat(String id, Integer numOfTrips, Integer ridership, Integer avgRiderPerTrip) {
        this.id = id;
        this.numOfTrips = numOfTrips;
        this.ridership = ridership;
        this.avgRiderPerTrip = avgRiderPerTrip;
    }

    /**
     * Gets the id of the transit line
     *
     * @return id of the transit line
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id of the transit line
     *
     * @param id id of the transit line
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the number of single trips
     *
     * @return number of single trips
     */
    public Integer getNumOfTrips() {
        return numOfTrips;
    }

    /**
     * set the number of single trips
     *
     * @param numOfTrips number of trips
     */
    public void setNumOfTrips(Integer numOfTrips) {
        this.numOfTrips = numOfTrips;
    }

    /**
     * Gets the amount of single trip passengers on a transit line
     *
     * @return number of single trip passengers
     */
    public Integer getRidership() {
        return ridership;
    }

    /**
     *  Sets ridership
     * @param ridership ridership
     */
    public void setRidership(Integer ridership) {
        this.ridership = ridership;
    }

    /**
     * Increases the amount of single trip passengers on a transit line by 1
     */
    public void increaseRidership() {this.ridership += 1;}


    /**
     *  Gets avgRiderPerTrip
     * @return avgRiderPerTrip
     */
    public Integer getAvgRiderPerTrip() {
        return avgRiderPerTrip;
    }

    /**
     * Sets the average amount of passengers on one single trip
     */
    public void setAvgRiderPerTrip() {
        if ( numOfTrips == 0) {
            this.avgRiderPerTrip = 0;
        }
        else {
            this.avgRiderPerTrip = this.ridership / this.numOfTrips;
        }
    }

}
