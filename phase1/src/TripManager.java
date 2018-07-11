import java.util.ArrayList;

public class TripManager extends TransitSystem {
    private ArrayList<TripSegment> currentTripSegments = new ArrayList<>();

    public void recordTapIn(
            String cardNumber, String enterSpot, String transitType, String enterTime, String enterDate) {
        TripSegment ts = new TripSegment(cardNumber, enterSpot, transitType, enterTime, enterDate);
        this.currentTripSegments.add(ts);
        if (ts.getEnterTransitType().equals("B")) {
            calculateTripSegmentFares(ts);
        }
        addTripSegmentToCard(ts);
    }

    private void addTripSegmentToCard(TripSegment ts) {
        int currentCardNumber = ts.getAssociatedCard();
        findCard(currentCardNumber).addTripSegment(ts);
    }

    public void recordTapOut(
            String cardNumber, String exitSpot, String transitType, String exitTime, String exitDate) {
        // TripSegment currentTs = new TripSegment("0", "default", "default", "default", "default");
        for (TripSegment ts : this.currentTripSegments) {
            if (ts.getAssociatedCard() == Integer.parseInt(cardNumber)
                    && ts.getExitSpot().equals("unknown")) {
                // currentTs = ts;
                ts.completeTripSegment(exitSpot, transitType, exitTime, exitDate);
                calculateDuration(ts);
                calculateTripSegmentFares(ts);
                if (ts.getEnterTransitType().equals("S")) {
                    int currentCardNumber = ts.getAssociatedCard();
                    findCard(currentCardNumber).updateFares(ts, ts.getSegmentFares());
                }
            }
        }
        /// account for tap out without tapping inr

    }

    private void calculateDuration(TripSegment ts) {
        // int version of enterTime: Hour converted to minutes + minutes
        int enter =
                Integer.parseInt(ts.getEnterTime().substring(0, 2)) * 60
                        + Integer.parseInt(ts.getEnterTime().substring(3, 5));
        // int version of exitTime: Hour converted to minutes + minutes
        int exit =
                Integer.parseInt(ts.getExitTime().substring(0, 2)) * 60
                        + Integer.parseInt(ts.getExitTime().substring(3, 5));
        ts.setDuration(exit - enter);
    }

    private void calculateTripSegmentFares(TripSegment ts) {
        if (ts.getEnterTransitType().equals("B")) {
            ts.setSegmentFares(2.0);
        } else if (ts.getExitTransitType().equals("S")) {
            ts.setSegmentFares(calculateSubwayFares(ts));
        }
    }

    double calculateSubwayFares(TripSegment currentTripSegment) {
        int enterSpotIndex = 0;
        int exitSpotIndex = 0;
        for (String lineName : transitLines.keySet()) {
            TransitLine line = transitLines.get(lineName);
            if (line.getType().equals("S")) {
                ArrayList<String> points = line.getPoints();
                for (String p : points) {
                    if (p.equals(currentTripSegment.getEnterSpot())) {
                        enterSpotIndex = points.indexOf(p);
                    } else if (p.equals(currentTripSegment.getExitSpot())) {
                        exitSpotIndex = points.indexOf(p);
                    }
                }
            }
        }
        if (enterSpotIndex == exitSpotIndex) {
            return 0;
        } else if (currentTripSegment.getDuration() <= 180
                && (Math.abs(exitSpotIndex - enterSpotIndex)) * 0.5 > 6) {
            addNumberOfStation(
                    currentTripSegment.getEnterDate(), Math.abs(exitSpotIndex - enterSpotIndex) + 1);
            return 6;
        } else {
            addNumberOfStation(
                    currentTripSegment.getEnterDate(), Math.abs(exitSpotIndex - enterSpotIndex) + 1);
            return (Math.abs(exitSpotIndex - enterSpotIndex)) * 0.5;
        }
    }
}