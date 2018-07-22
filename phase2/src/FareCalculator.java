public class FareCalculator extends TransitSystem{
  private double busFare = 2.0;
  private double stationFare = 0.5;
  private double cap = 6.0;
  private double fare;

  private double calculateContiBusFare(double currentFares){
    if(currentFares == cap){
      fare = 0.0;
    } else if(currentFares < cap && currentFares + busFare > cap){
      fare = cap - currentFares;
    } else {
      fare = busFare;
    }
    return fare;
  }

  private double calculateContiSubFare(double currentFares, int distance){
    double tripFare = distance * stationFare;
    if(currentFares == cap){
      fare = 0.0;
    } else if(currentFares < cap && currentFares + tripFare > 6.0){
      fare = cap - currentFares;
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