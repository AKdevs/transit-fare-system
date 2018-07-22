import java.util.ArrayList;

class TripManager {

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
                lastTrip.setTransitType("continueB");
                double fares = calculator.calculateTripFares(lastTrip);
                card.updateBalance(fares);
                card.updateTotalFares(fares);
                TransitSystem.updateAllFares(date, fares); // static problem here!!
            }
            lastTrip.setTransitType("continueS");
          } else { // if it is a new trip
            TripSegment trip = new TripSegment(spot, time, date, type);
            card.addTrip(trip);
            calculator.calculateTripFares(trip);
          }
        } else { // illegal entry
          System.out.println("Declined: Illegal entry");
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
      if (!current.getTransitType().equals("B") && !current.getTransitType().equals("continueB"))
      calculator.calculateTripFares(current);
    }
      current.setTransitType("continuous");
  }
}
