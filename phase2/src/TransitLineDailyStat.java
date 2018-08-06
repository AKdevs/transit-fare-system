import java.io.Serializable;
import java.util.HashMap;

public class TransitLineDailyStat implements Serializable{

    /** Stores a collection of daily stat for all Transit Lines
     * in <String id, SingleTransitLineDailyStat>
     */

    private HashMap<String, SingleTransitLineDailyStat> transitLineSummary;

    public TransitLineDailyStat() {
        this.transitLineSummary = new HashMap<>();
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


    public boolean isEmpty() {
        return transitLineSummary.isEmpty();
    }

}
