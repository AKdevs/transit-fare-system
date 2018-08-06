import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

class TripManager implements Serializable{
  private HashMap<String, TransitLine> transitLines;
  private FareCalculator fareCalculator;
  private Aggregator aggregator;
  private DataSaving dataSaving;

  TripManager() {
    this.fareCalculator = new FareCalculator();
    this.aggregator = new Aggregator();
  }

  Aggregator getAggregator() {
    return aggregator;
  }

  void addTransitLines(HashMap<String, TransitLine> transitLines) {
    this.transitLines = transitLines;
    fareCalculator.addTransitLines(transitLines);
    dataSaving.save();
  }

  void recordTapIn(String time, String spot, Card card, String date, String type) {
    if (card.getBalance() < 0) {
      System.out.println("Declined: Card is out of funds, please load money.");
    } else {
      // if the cardHolder traveled with this card before
      if (!card.getTrips().isEmpty()) {
        ArrayList<TripSegment> allTrips = card.getTrips();
        TripSegment lastTrip = allTrips.get(allTrips.size() - 1);
        // if it is a legal entry
        if (lastTrip.hasExit()) {
          int duration = fareCalculator.calculateDuration(lastTrip.getEnterTime(), time);
          // if it is the enter of a continuous trip
          if (lastTrip.getExitSpot().equals(spot)
              && duration < fareCalculator.getMaximumDuration()) {
            lastTrip.setContiSpot(spot);
            if (type.equals("B")) {
              lastTrip.setTransitType("continueB");
              double fares = fareCalculator.calculateContiBusFare(lastTrip.getCurrentFares());
              // apply Observer pattern here? what is observable?
              //observers are: Card, TransitSystem
              updateFares(fares, card, date);
            }else {
                lastTrip.setTransitType("continueS");
            }
          } else { // if it is a new trip
              addNewTrip(time, spot, card, date, type);
            if (type.equals("B")) {
                TripSegment updatedLastTrip = allTrips.get(allTrips.size() - 1);
              double fares = fareCalculator.calculateTripFares(updatedLastTrip);
              updatedLastTrip.setCurrentFares(fares);
              updateFares(fares, card, date);
              }
          }
        } else { // illegal entry
            System.out.println("Declined: Illegal entry");
            lastTrip.setExitSpot("illegal");
            lastTrip.setExitTime(time);
            TripSegment trip = new TripSegment(spot, time, date, type);
            card.addTrip(trip);
            updateFares(fareCalculator.getFareCap(), card, date);//penalty is charging them the cap for last trip
        }
      } else {// if this is the first time the CardHolder travel with this card
          addNewTrip(time, spot, card, date, type);
          if (type.equals("B")){
          TripSegment updatedLastTrip = card.getTrips().get(card.getTrips().size() - 1);
          double fares = fareCalculator.calculateTripFares(updatedLastTrip);
          updatedLastTrip.setCurrentFares(fares);
          updateFares(fares, card, date);
          }
      }
    }
    dataSaving.save();
  }

  void recordTapOut(String time, String spot, Card card, String date, String type) {
    ArrayList<TripSegment> allTrips = card.getTrips();
    TripSegment current = allTrips.get(allTrips.size() - 1);
    // if it is a illegal exit (didn't tap in for this trip)
    if (current.getExitSpot() != null && !current.getTransitType().equals("continueB")&& !current.getTransitType().equals("continueS") ) {
      System.out.println("Declined: Illegal exit.");
      TripSegment ts = new TripSegment("illegal", time, date, type);
      card.addTrip(ts);
      TripSegment updatedCurrent = allTrips.get(allTrips.size() - 1);
      updatedCurrent.setExitSpot(spot);
      updatedCurrent.setExitTime(time);
      updateFares(fareCalculator.getFareCap(), card, date);//penalty is charging them the cap for this trip
    } else { // normal legal exit
      current.setExitSpot(spot);
      current.setExitTime(time);
      if (current.getTransitType().equals("S")) {
        double fares = fareCalculator.calculateTripFares(current);
        current.setCurrentFares(fares);
        updateFares(fares, card, date);
        }else if (current.getTransitType().equals("continueS")) {
          int distance = fareCalculator.calculateStationsReached(current);
          double fares = fareCalculator.calculateContiSubFare(current.getCurrentFares(), distance);
          current.setCurrentFares(fares);
          updateFares(fares, card, date);
      }
        aggregator.addNumberOfStation(date, fareCalculator.calculateStationsReached(current));
    }
    if (current.getTransitType().equals("continueB")
        || current.getTransitType().equals("continueS")) {
      current.setTransitType("continuous");
    }
    dataSaving.save();
  }

  private void addNewTrip(String time, String spot, Card card, String date, String type) {
      TripSegment trip = new TripSegment(spot, time, date, type);
      card.addTrip(trip);
  }

  private void updateFares(double fares, Card card, String date) {
      card.updateBalance(fares);
      card.updateTotalFares(fares);
      aggregator.updateAllFares(date, fares);
  }
}
