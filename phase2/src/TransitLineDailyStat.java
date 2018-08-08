import java.io.Serializable;
import java.util.HashMap;

/**
 * TransitLindDailyStat is used to keep track on the daily statistic for transit line
 */
public class TransitLineDailyStat implements Serializable{

    /** Stores a collection of daily stat for all Transit Lines
     * in <String id, SingleTransitLineDailyStat>
     */
    private HashMap<String, SingleTransitLineDailyStat> transitLineSummary;

    /**
     * Construct a transit line daily statistic
     */
    public TransitLineDailyStat() {
        this.transitLineSummary = new HashMap<>();
    }

    /**
     * Gets transitLineSummary
     * @return transitLineSummary
     */
    public HashMap<String, SingleTransitLineDailyStat> getTransitLineSummary() {
        return transitLineSummary;
    }

    /**
     *  Gets SingleTransitLineDailyStat via id
     * @param  id
     * @return SingleTransitLineDailyStat
     */
    public SingleTransitLineDailyStat getSingleTransitLineDailyStat(String id){
        return transitLineSummary.get(id);
    }

    /**
     * Add SingleTransitLineDailyStat to transitLineSummary with id
     * @param id
     * @param STLDS
     */
    public void addSingleTransitLineDailyStat(String id, SingleTransitLineDailyStat STLDS){
        this.transitLineSummary.put(id, STLDS);
    }


    /**
     * Checks if the transit line summary is empty
     * @return true iff the transit line summary is empty
     */
    public boolean isEmpty() {
        return transitLineSummary.isEmpty();
    }

}
