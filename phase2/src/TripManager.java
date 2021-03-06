import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

/**
 * Manage trips
 */
class TripManager implements Serializable{

  /**
   * Stores all transit lines
   */
  private HashMap<String, TransitLine> transitLines;
  /**
   * Uses the fareCalculator to do calculations
   */
  private FareCalculator fareCalculator;
  /**
   * Uses the aggregator to update reports
   */
  private Aggregator aggregator;
  /**
   * Specifies the average cost per station
   */
  private Double avgCostPerStation;

  /**
   * Constructs a TripManager
   */
  TripManager() {
    this.fareCalculator = new FareCalculator();
    this.aggregator = new Aggregator();
    this.avgCostPerStation = 0.00;
  }

  /**
   * Get the aggregator
   *
   * @return aggregator
   */
  Aggregator getAggregator() {
    return aggregator;
  }

  /**
   * Adds new transit line
   * @param transitLines all transit lines
   */
  void addTransitLines(HashMap<String, TransitLine> transitLines) {
    this.transitLines = transitLines;
    fareCalculator.addTransitLines(transitLines);
  }

  /**
   * Gets average cost per station
   *
   * @return average cost per station
   */
  public Double getAvgCostPerStation() {
    return avgCostPerStation;
  }

  /**
   * Sets Average cost per station
   *
   * @param avgCostPerStation average cost per station
   */
  public void setAvgCostPerStation(Double avgCostPerStation) {
    this.avgCostPerStation = avgCostPerStation;
  }

  /**
   * Record the tap in to form a new trip and store it in card,
   * also deduct the bus fare from card if there is a bus trip
   *
   * @param time current time
   * @param spot current enter spot
   * @param card related card
   * @param date current date
   * @param type trip type
   * @param transitLine trip related transit line
   */
  void recordTapIn(String time, String spot, Card card, String date, String type, String transitLine) {
    if (card.getBalance() < 0) {
      //TransitSystem.log(Level.ALL,"Declined: Card " + card.getCardNumber() +" is out of funds, please load money.");
    } else {
        updateRidership(date,transitLine);
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
              card.setLastTapTime(time);
            lastTrip.setContiSpot(spot);
            TransitSystem.log(Level.ALL,"Card " + card.getCardNumber() + " tapped in at " + spot + "," + time + "," + date);
            if (type.equals("B")) {
              lastTrip.setTransitType("continueB");
              double fares = fareCalculator.calculateContiBusFare(lastTrip.getCurrentFares());
              updateFares(fares, card, date);
            }else {
                lastTrip.setTransitType("continueS");
            }
          } else { // if it is a new trip
              addNewTrip(time, spot, card, date, type);
              card.setLastTapTime(time);
            if (type.equals("B")) {
                TripSegment updatedLastTrip = allTrips.get(allTrips.size() - 1);
              double fares = fareCalculator.calculateTripFares(updatedLastTrip);
              updatedLastTrip.setCurrentFares(fares);
              updateFares(fares, card, date);
              }
          }
        } else { // illegal entry
          TransitSystem.log(Level.ALL, "Declined: Illegal entry by " + card.getCardNumber() + " at " + spot + "," + time + "," + date);
          lastTrip.setExitSpot("illegal");
          lastTrip.setExitTime(time);
          TripSegment trip = new TripSegment(spot, time, date, type);
          card.addTrip(trip);
            card.setLastTapTime(time);
          updateFares(fareCalculator.getFareCap(), card, date);//penalty is charging them the cap for last trip
        }
      } else {// if this is the first time the CardHolder travel with this card
          addNewTrip(time, spot, card, date, type);
          card.setLastTapTime(time);
          if (type.equals("B")){
          TripSegment updatedLastTrip = card.getTrips().get(card.getTrips().size() - 1);
          double fares = fareCalculator.calculateTripFares(updatedLastTrip);
          updatedLastTrip.setCurrentFares(fares);
          updateFares(fares, card, date);
          }
      }
    }
  }

  /**
   * Record the tap out to complete the tripSegment which is stored in the card, also
   * updates the balance in card and reports
   *
   *
   * @param time time of exit
   * @param spot exit spot
   * @param card related card
   * @param date current date
   * @param type trip type
   */
  void recordTapOut(String time, String spot, Card card, String date, String type) {
    ArrayList<TripSegment> allTrips = card.getTrips();
    if (allTrips.isEmpty()){
        TransitSystem.log(
                Level.ALL,
                "Declined: Illegal exit by "
                        + card.getCardNumber()
                        + " at "
                        + spot
                        + ","
                        + time
                        + ","
                        + date);
        TripSegment ts = new TripSegment("illegal", time, date, type);
        card.addTrip(ts);
        TripSegment updatedCurrent = allTrips.get(allTrips.size() - 1);
        updatedCurrent.setExitSpot(spot);
        updatedCurrent.setExitTime(time);
        card.setLastTapTime(time);
        updateFares(
                fareCalculator.getFareCap(),
                card,
                date); // penalty is charging them the cap for this trip
    } else {
      TripSegment current = allTrips.get(allTrips.size() - 1);
      // if it is a illegal exit (didn't tap in for this trip)
      if (current.getExitSpot() != null
          && !current.getTransitType().equals("continueB")
          && !current.getTransitType().equals("continueS")) {
        TransitSystem.log(
            Level.ALL,
            "Declined: Illegal exit by "
                + card.getCardNumber()
                + " at "
                + spot
                + ","
                + time
                + ","
                + date);
        TripSegment ts = new TripSegment("illegal", time, date, type);
        card.addTrip(ts);
        TripSegment updatedCurrent = allTrips.get(allTrips.size() - 1);
        updatedCurrent.setExitSpot(spot);
        updatedCurrent.setExitTime(time);
          card.setLastTapTime(time);
        updateFares(
            fareCalculator.getFareCap(),
            card,
            date); // penalty is charging them the cap for this trip
      } else { // normal legal exit
        current.setExitSpot(spot);
        current.setExitTime(time);
        card.setLastTapTime(time);
        TransitSystem.log(
            Level.ALL,
            "Card " + card.getCardNumber() + " tapped out at " + spot + "," + time + "," + date);
        if (current.getTransitType().equals("S")) {
          double fares = fareCalculator.calculateTripFares(current);
          current.setCurrentFares(fares);
          updateFares(fares, card, date);
        } else if (current.getTransitType().equals("continueS")) {
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
    }
  }

  /**
   * Add new trip to Card
   *
   *
   * @param time enter time
   * @param spot enter spot
   * @param card related card
   * @param date enter date
   * @param type trip type
   */
  private void addNewTrip(String time, String spot, Card card, String date, String type) {
      TripSegment trip = new TripSegment(spot, time, date, type);
      card.addTrip(trip);
      TransitSystem.log(Level.ALL,"Card " + card.getCardNumber() + " tapped in at " + spot + "," + time + "," + date);
  }

  /**
   * Updates fares in card and reports
   *
   *
   * @param fares fare cost for trip
   * @param card  related card
   * @param date date of trip
   */
  private void updateFares(double fares, Card card, String date) {
      card.updateBalance(fares);
      TransitSystem.log(Level.ALL,fares + " is deducted from Card " + card.getCardNumber());
      card.updateTotalFares(fares);
      aggregator.updateAllFares(date, fares);
  }

  /**
   * Update ridership to transitLine
   *
   *
   * @param date date of trip
   * @param transitLine trip related transitLine
   */
  private void updateRidership(String date, String transitLine) {
      if (transitLine != null) {
          SingleTransitLineDailyStat thisStat = aggregator.getTransitLineDailyStat(date).getSingleTransitLineDailyStat(transitLine);
          thisStat.increaseRidership();
      }
  }
}
