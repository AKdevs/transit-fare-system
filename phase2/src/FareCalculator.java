import java.io.Serializable;
import java.util.HashMap;

public class FareCalculator implements Serializable {
  private final double busFare = 2.0;
  private final double stationFare = 0.5;
  private final double fareCap = 6.0;
  /** Maximum amount of time where the fareCap is applicable. */
  private final int maximumDuration = 120;

  private HashMap<String, TransitLine> transitLines;
  // private double fare;
  /**
   * Get the maximum duration of enters for continue trip
   *
   * @return the maximum duration of
   */
  int getMaximumDuration() {
    return maximumDuration;
  }
  /**
   * Get the fare cap for bus
   *
   * @return fare cap for bus
   */
  double getFareCap(){return busFare;}

  /**
   * Add transit lines which are used in calculation
   */
  void addTransitLines(HashMap<String, TransitLine> transitLines) {
    this.transitLines = transitLines;
  }

  /**
   * Calculate the fare cost by a continue bus trip
   *
   * @return the fare cost by the continue bus trip
   */
  double calculateContiBusFare(double currentFares) {
    double fare;
    if (currentFares == fareCap) {
      fare = 0.0;
    } else if (currentFares < fareCap && currentFares + busFare > fareCap) {
      fare = fareCap - currentFares;
    } else {
      fare = busFare;
    }
    return fare;
  }

  /**
   * Calculate the fare cost by a continue subway trip
   *
   * @return the fare cost by the continue subway trip
   */
  double calculateContiSubFare(double currentFares, int distance) {
    double fare;
    double tripFare = distance * stationFare;
    if (currentFares == fareCap) {
      fare = 0.0;
    } else if (currentFares < fareCap && currentFares + tripFare > fareCap) {
      fare = fareCap - currentFares;
    } else {
      fare = tripFare;
    }
    return fare;
  }

  /**
   * Calculate the fare cost by a trip which one tap in and one tap out
   *
   * @return the fare cost by the trip
   */
  double calculateTripFares(TripSegment ts) {
    double result = 0.0;
    if (ts.getTransitType().equals("B")) {
      result = busFare;
    } else if (ts.getTransitType().equals("continueB")) {
      result = calculateContiBusFare(ts.getCurrentFares());
    } else if (ts.getTransitType().equals("S")) {
      result = stationFare * calculateStationsReached(ts);
      if (result > fareCap) {
          result = fareCap;
      }
    } else if (ts.getTransitType().equals("continueS")) {
      int distance = calculateStationsReached(ts);
      result = calculateContiSubFare(ts.getCurrentFares(), distance);
    }
    return result;
  }

  /**
   * Calculate stations reached of one trip
   *
   * @return number of stations reached
   */
  // for both subway and bus so we can add the number of stations reached to our daily report
  // and also we can calculate subway fares by using the result of this method
  int calculateStationsReached(TripSegment trip) {
    int enterIndex = 0;
    int exitIndex = 0;
    String startSpot;
    if (trip.getTransitType().equals("B") || trip.getTransitType().equals("S")){
        startSpot = trip.getEnterSpot();
    }else { startSpot = trip.getContiSpot();}

    for (String lineName : transitLines.keySet()) {
      TransitLine line = transitLines.get(lineName);
      for (int i = 0; i < line.getPoints().size(); i++) {
        if (line.getPoints().get(i).equals(startSpot)) {
          enterIndex = i;
        }
        if (line.getPoints().get(i).equals(trip.getExitSpot())) {
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

  /**
   * Calculate the time period between the current trip enter time and last enter time
   *
   * @return the duration in minute
   */
  int calculateDuration(String lastStartTime, String currentStartTime) {
    int last =
        Integer.parseInt(lastStartTime.substring(0, 2)) * 60
            + Integer.parseInt(lastStartTime.substring(3, 5));
    int current =
        Integer.parseInt(currentStartTime.substring(0, 2)) * 60
            + Integer.parseInt(currentStartTime.substring(3, 5));
    return current - last;
  }
}
