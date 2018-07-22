import java.util.ArrayList;

class TripManager extends TransitSystem {

  // for both subway and bus so we can add the number of stations reached to our daily report
  // and also we can calculate subway fares by using the result of this method
  int calculateStaionsReached(String enterSpot, String exitSpot) {
    int enterIndex = 0;
    int exitIndex = 0;
    for (String lineName : transitLines.keySet()) {
      TransitLine line = transitLines.get(lineName);
      for (int i = 0; i < line.getPoints().size(); i++) {
        if (line.getPoints().get(i).equals(enterSpot)) {
          enterIndex = i;
        }
        if (line.getPoints().get(i).equals(exitSpot)) {
          exitIndex = i;
        }
      }
    }
    if (enterIndex == exitIndex) {
      return 0;
    } else {
      return Math.abs(exitIndex - enterIndex);
    }
  }

  static void recordTapIn(String time, String spot, String cardNumber, String date, String type) {
    FareCalculator calculator = new FareCalculator();
    Card card = TransitSystem.findCard(cardNumber);
    if (card.getBalance() < 0) {
      System.out.println("Declined: Card is out of funds, please load money.");
    } else {
      // if the cardHolder traveled with this card before
      if (!card.getTrips().isEmpty()) {
        ArrayList<TripSegment> allTrips = card.getTrips();
        TripSegment lastTrip = allTrips.get(allTrips.size() - 1);
        // if it is a legal entry
        if (lastTrip.hasExit()) {
          int duration = calculator.calculateDuration(lastTrip.getEnterTime(), time);
          // if it the enter of a continuous trip
          if (lastTrip.getExitSpot().equals(spot) && duration < 120) {
            lastTrip.setContiSpot(spot);
            if (type.equals("B")) {
              double fares = calculator.calcBusFare();
              card.updateBalance(fares);
              card.updateTotalFares(fares);
              TransitSystem.updateAllFares(date, fares); // static problem here!!
              lastTrip.setTransitType("continueB");
            }
            lastTrip.setTransitType("continueS");
          } else { // if it is a new trip
            TripSegment trip = new TripSegment(spot, time, date, type);
            card.addTrip(trip);
          }
        } else { // illegal entry
          System.out.println("Declined: Illegal entry");
          // complete the trip segment( without exit) with "illegal", use enterTime as exitTime, use
          // enterDate as exitDate
          lastTrip.setExitSpot("illegal");
          lastTrip.setExitTime(time);
          // update fares (penalty)
        }
        // if this is the first time the CardHolder travel with this card
      } else {
        TripSegment trip = new TripSegment(spot, time, date, type);
        card.addTrip(trip);
      }
    }
  }

  static void recordTapOut(String time, String spot, String cardNumber, String date, String type) {
    FareCalculator calculator = new FareCalculator();
    Card card = TransitSystem.findCard(cardNumber);
    ArrayList<TripSegment> allTrips = card.getTrips();
    TripSegment current = allTrips.get(allTrips.size() - 1);
    // if it a illegal exit (didn't tap in for this trip)
    if (current.getExitSpot() != null) {
      System.out.println("Declined: Illegal exit.");
      TripSegment ts = new TripSegment(spot, time, date, type);
      card.addTrip(ts);
      TripSegment updatedCurrent = allTrips.get(allTrips.size() - 1);
      updatedCurrent.setExitSpot(spot);
      updatedCurrent.setExitTime(time);
      updatedCurrent.setTransitType("continue");
      // update fares(penalty)
    } else { // normal legal exit
      current.setExitSpot(spot);
      current.setExitTime(time);
      //update fares
      /*if (current.getTransitType().equals("S") || current.getTransitType().equals("B")) {
        // update fares
        // tap out of a continuous trip
      } else {

      }*/
    }
  }
}
