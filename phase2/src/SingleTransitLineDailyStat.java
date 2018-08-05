import java.io.Serializable;

public class SingleTransitLineDailyStat implements Serializable {
    /** stores id of the Transit Line*/
    private String id;
    /** stores number of single-way trips operated */
    private Integer numOfTrips;
    /** stores number of passengers travelled in Transit Line*/
    private Integer ridership;
    /** stores average number of passengers travelled in Transit Line per trip*/
    private Integer avgRiderPerTrip;

    public SingleTransitLineDailyStat(String id, Integer numOfTrips, Integer ridership, Integer avgRiderPerTrip) {
        this.id = id;
        this.numOfTrips = numOfTrips;
        this.ridership = ridership;
        this.avgRiderPerTrip = avgRiderPerTrip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNumOfTrips() {
        return numOfTrips;
    }

    public void setNumOfTrips(Integer numOfTrips) {
        this.numOfTrips = numOfTrips;
    }

    public Integer getRidership() {
        return ridership;
    }

    public void setRidership() {
        this.ridership += 1;
    }

    public Integer getAvgRiderPerTrip() {
        return avgRiderPerTrip;
    }

    public void setAvgRiderPerTrip() {
        if ( numOfTrips == 0) {
            this.avgRiderPerTrip = 0;
        }
        else {
            this.avgRiderPerTrip = this.ridership / this.numOfTrips;
        }
    }

}
