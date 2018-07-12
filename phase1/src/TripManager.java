import java.util.ArrayList;

class TripManager extends TransitSystem {

  /**
   * Records the card number, spot of entry, type of transit, time of entry and date of entry of a
   * card tap in and beginning a new trip segment.
   *
   * @param cardNumber card number of associated card
   * @param enterSpot stop/station of entry
   * @param transitType type of transit (bus/subway)
   * @param enterTime time of entry HH:MM
   * @param enterDate date of entry YY-MM-DD
   */
  void recordTapIn(
      String cardNumber, String enterSpot, String transitType, String enterTime, String enterDate) {
    // Find the associated card
    Card associatedCard = findCard(cardNumber);
    // If the associated card's balance is negative, the system won't allow entry
    if (associatedCard.getBalance() < 0) {
      System.out.println("Declined: Card is out of funds, please load money.");
    } else {
      // Find the ongoing/current TripSegment
      TripSegment ongoing = associatedCard.getOngoingTripSegment();
      // normal entry
      if (ongoing == null || !ongoing.getExitSpot().equals("unknown")) {
        TripSegment ts = new TripSegment(cardNumber, enterSpot, transitType, enterTime, enterDate);
        // the ongoing TripSegment right now is ts
        associatedCard.setOngoingTripSegment(ts);
        if (ts.getEnterTransitType().equals("B")) {
          calculateTripSegmentFares(ts);
        }
        addTripSegmentToCard(ts);

      } else if (ongoing.getExitSpot().equals("unknown")) { // illegal entry
        System.out.println("Declined: Illegal entry");
        // complete the trip segment( without exit) with "illegal", use enterTime as exitTime, use
        // enterDate as exitDate
        associatedCard
            .getLastTripSegment()
            .completeTripSegment("illegal", "illegal", enterTime, enterDate);
        // for subway, charge $6 for the illegal trip; for bus, except for the $2 fare, charge $6 as
        // penalty
        associatedCard.updateFares(associatedCard.getLastTripSegment(), fareCap);
        // add the new tripSegment as usual
        TripSegment ts = new TripSegment(cardNumber, enterSpot, transitType, enterTime, enterDate);
        // the ongoing TripSegment right now is ts
        associatedCard.setOngoingTripSegment(ts);
        // this.currentTripSegments.add(ts);
        if (ts.getEnterTransitType().equals("B")) {
          calculateTripSegmentFares(ts);
        }
        addTripSegmentToCard(ts);
      }
    }
  }

  /** @param ts TripSegment to be added to associated card. */
  private void addTripSegmentToCard(TripSegment ts) {
    String currentCardNumber = ts.getAssociatedCard();
    findCard(currentCardNumber).addTripSegment(ts);
  }

  /**
   * Records the card number, spot of exit, type of transit, time of exit and date of exit of a card
   * tap out and ends the ongoing trip segment.
   *
   * @param cardNumber card number of associated card
   * @param exitSpot stop/station of exit
   * @param transitType type of transit (bus/subway)
   * @param exitTime time of exit HH:MM
   * @param exitDate date of exit YY-MM-DD
   */
  void recordTapOut(
      String cardNumber, String exitSpot, String transitType, String exitTime, String exitDate) {
    // Find the associated card
    Card associatedCard = findCard(cardNumber);
    // Find the ongoing/current TripSegment
    TripSegment ongoing = associatedCard.getOngoingTripSegment();
    // normal tap out
    if (!(ongoing == null) && ongoing.getExitSpot().equals("unknown")) {
      TripSegment ts = associatedCard.getLastTripSegment();
      ts.completeTripSegment(exitSpot, transitType, exitTime, exitDate);
      TransitSystem.addNumberOfStation(ts.getEnterDate(), calculateStopsReachedByBus(ts));
      calculateDuration(ts);
      calculateTripSegmentFares(ts);
      if (ts.getEnterTransitType().equals("S") && !ts.getContiSub()) {
        String currentCardNumber = ts.getAssociatedCard();
        findCard(currentCardNumber).updateFares(ts, Math.min(ts.getSegmentFares(), 6.0));
      }
    } else { // illegal tap out
      System.out.println("Declined: Illegal exit.");
      // make a tap in info, use exit time as enterTime, use exitDate as enterDate
      TripSegment ts = new TripSegment(cardNumber, "illegal", "illegal", exitTime, exitDate);
      // the ongoing TripSegment right now is ts
      associatedCard.setOngoingTripSegment(ts);
      // complete the tap out info
      ts.completeTripSegment(exitSpot, transitType, exitTime, exitDate);
      // add ts to card
      addTripSegmentToCard(ts);
      // charge the cardHolder the cap $6 for both bus and subway
      associatedCard.updateFares(associatedCard.getLastTripSegment(), fareCap);
    }
  }

  /**
   * Calculates the duration of the trip segment in minutes.
   *
   * @param ts trip segment
   */
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
      ts.setSegmentFares(busFare);
    } else if (ts.getExitTransitType().equals("S")) {
      ts.setSegmentFares(calculateSubwayFares(ts));
    }
  }

  /**
   * Calculates the amount of fare accumulated for subway travel for TripSegment currentTripSegment
   *
   * @param currentTripSegment the ongoing trip segment
   * @return amount of money of subway fare
   */
  private double calculateSubwayFares(TripSegment currentTripSegment) {
    int enterSpotIndex = 0;
    int exitSpotIndex = 0;
    for (String lineName : transitLines.keySet()) {
      TransitLine line = transitLines.get(lineName);
      if (line.getType().equals("S")) {
        ArrayList<String> points = line.getPoints();
        for (String p : points) {
          if (p.equals(currentTripSegment.getEnterSpot())) {
            enterSpotIndex = points.indexOf(p);
          }
          if (p.equals(currentTripSegment.getExitSpot())) {
            exitSpotIndex = points.indexOf(p);
          }
        }
      }
    }
    if (enterSpotIndex == exitSpotIndex) {
      return 0;
    } /*else if (currentTripSegment.getDuration() <= 180
          && (Math.abs(exitSpotIndex - enterSpotIndex)) * 0.5 > fareCap) {
        addNumberOfStation(
            currentTripSegment.getEnterDate(), Math.abs(exitSpotIndex - enterSpotIndex) + 1);
        return fareCap;
      } */ else {
      addNumberOfStation(
          currentTripSegment.getEnterDate(), Math.abs(exitSpotIndex - enterSpotIndex) + 1);
      return (Math.abs(exitSpotIndex - enterSpotIndex)) * subwayFare;
    }
  }

  /**
   * Calculates the number of bus stops reached in TripSegment ts.
   *
   * @param ts trip segment
   * @return the number of stops reached in this trip segment.
   */
  private int calculateStopsReachedByBus(TripSegment ts) {
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
