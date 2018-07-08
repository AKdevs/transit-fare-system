import java.util.ArrayList;

public class TripManager {
    private ArrayList<TripSegment> currenTripSegments;

    TripManager() {
        currenTripSegments = new ArrayList<>();
    }

    public void recordTapIn(String cardNumber, String enterSpot, String transitType, String enterTime, String enterDate) {
        TripSegment ts = new TripSegment(cardNumber, enterSpot, transitType, enterTime, enterDate);
        currenTripSegments.add(ts);
        addTripSegmentToCard(ts);
    }

    private void addTripSegmentToCard(TripSegment ts) {
        int currentCardNumber = ts.getAssociatedCard();
        TransitSystem.findCard(currentCardNumber).addTripSegment(ts);
    }

    public void recordTapOut(String cardNumber, String exitSpot, String transitType, String exitTime, String exitDate) {
        for (TripSegment ts: currenTripSegments) {
            if (ts.getAssociatedCard() == Integer.parseInt(cardNumber)) {
                ts.completeTripSegment(exitSpot, transitType, exitTime, exitDate);
            }
        }
        calculateDuration(this.enterTime, this.exitTime);
        calculateTripSegmentFares(this.exitTransitType);
    }
}
