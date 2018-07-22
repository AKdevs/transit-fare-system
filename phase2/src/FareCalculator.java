public class FareCalculator {
  private final double busFare = 2.0;
  private final double stationFare = 0.5;
  private final double fareCap = 6.0;
  private double fare;

  private double calculateContiBusFare(double currentFares){
    if(currentFares == fareCap){
      fare = 0.0;
    } else if(currentFares < fareCap && currentFares + busFare > fareCap){
      fare = fareCap - currentFares;
    } else {
      fare = busFare;
    }
    return fare;
  }

  private double calculateContiSubFare(double currentFares, int distance){
    double tripFare = distance * stationFare;
    if(currentFares == fareCap){
      fare = 0.0;
    } else if(currentFares < fareCap && currentFares + tripFare > fareCap){
      fare = fareCap - currentFares;
    } else {
      fare = tripFare;
    }
    return fare;
  }


    public double calculateTripFares(TripSegment ts) {
      double result = 0.0;
      if (ts.getTransitType().equals("B")) {
          result = busFare;
      }else if(ts.getTransitType().equals("continueB")) {
          result = calculateContiBusFare(ts.getCurrentFares());
      }
      else if (ts.getTransitType().equals("S")) {
          result = stationFare * calculateStaionsReached(ts.getEnterSpot(), ts.getExitSpot());
      }else if (ts.getTransitType().equals("continueS")){
          int distance = calculateStaionsReached(ts.getContiSpot(), ts.getExitSpot());
          calculateContiSubFare(ts.getCurrentFares(), distance);
      }
      return result;
  }

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

    int calculateDuration(String lastStartTime, String currentStartTime) {
        int last = Integer.parseInt(lastStartTime.substring(0, 2)) * 60
                + Integer.parseInt(lastStartTime.substring(3, 5));
        int current = Integer.parseInt(currentStartTime.substring(0, 2)) * 60
                + Integer.parseInt(currentStartTime.substring(3, 5));
        return current - last;
    }

}