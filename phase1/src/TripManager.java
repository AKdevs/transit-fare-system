import java.util.ArrayList;

public class TripManager extends TransitSystem {
  /** Stores any ongoing trip segments   */
  private ArrayList<TripSegment> currentTripSegments = new ArrayList<>();

    /**
     * Records the card number, spot of entry, type of transit, time of entry
     * and date of entry of a card tap in and beginning a new trip segment.
     * @param cardNumber card number of associated card
     * @param enterSpot stop/station of entry
     * @param transitType type of transit (bus/subway)
     * @param enterTime time of entry HH:MM
     * @param enterDate date of entry YY-MM-DD
     */
  public void recordTapIn(
      String cardNumber, String enterSpot, String transitType, String enterTime, String enterDate) {
    TripSegment ts = new TripSegment(cardNumber, enterSpot, transitType, enterTime, enterDate);
    this.currentTripSegments.add(ts);
    if (ts.getEnterTransitType().equals("B")) {
      calculateTripSegmentFares(ts);
    }
    addTripSegmentToCard(ts);
  }

    /**
     * Adds trip segment to associated card.
     * @param ts TripSegment to be added to associated card.
     */
  private void addTripSegmentToCard(TripSegment ts) {
    String currentCardNumber = ts.getAssociatedCard();
    findCard(currentCardNumber).addTripSegment(ts);
  }

    /**
     * Records the card number, spot of exit, type of transit, time of exit
     * and date of exit of a card tap out and ends the ongoing trip segment.
     * @param cardNumber card number of associated card
     * @param exitSpot stop/station of exit
     * @param transitType type of transit (bus/subway)
     * @param exitTime time of exit HH:MM
     * @param exitDate date of exit YY-MM-DD
     */
  public void recordTapOut(
      String cardNumber, String exitSpot, String transitType, String exitTime, String exitDate) {
    for (TripSegment ts : this.currentTripSegments) {
      if (ts.getAssociatedCard().equals(cardNumber) && ts.getExitSpot().equals("unknown")) {
        ts.completeTripSegment(exitSpot, transitType, exitTime, exitDate);
        TransitSystem.addNumberOfStation(ts.getEnterDate(), calculateStopsReachedByBus(ts));
        calculateDuration(ts);
        calculateTripSegmentFares(ts);
        if (ts.getEnterTransitType().equals("S")) {
          String currentCardNumber = ts.getAssociatedCard();
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

  int calculateStopsReachedByBus(TripSegment ts){
      int enterSpotIndex = 0;
      int exitSpotIndex = 0;
      if (ts.getEnterTransitType().equals("B")) {
          for (String lineName : transitLines.keySet()) {
              TransitLine line = transitLines.get(lineName);
              if (line.getType().equals("B")) {
                  ArrayList<String> points = line.getPoints();
                  for (String p : points) {
                      if (p.equals(ts.getEnterSpot())) {
                          enterSpotIndex = points.indexOf(p);
                      } else if (p.equals(ts.getExitSpot())) {
                          exitSpotIndex = points.indexOf(p);
                      }
                  }
              }
          }
          return Math.abs(exitSpotIndex - enterSpotIndex) + 1;
      }
      return 0;
  }
}
