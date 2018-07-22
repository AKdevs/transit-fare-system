public class FareCalculator extends TransitSystem{
  private double busFare = 2.0;
  private double stationFare = 0.5;
  private double cap = 6.0;
  private double fare;

  public double calcBusFare() {
    return busFare;
  }

  public double calcSubwayFare(int distance){
    fare = distance * stationFare;
    if (fare > 6.0){
      return cap;
    }else{
      return fare;
    }
  }
  public double calcCoBusFare(double lastFare){
    double tripFare = busFare;
    double totalFare = tripFare + lastFare;
    if(lastFare == cap){
      fare = 0.0;
    } else if(lastFare < 6.0 && totalFare > 6.0){
      fare = 6.0 - lastFare;
    } else {
      fare = busFare;
    }
    return fare;
  }

  public double calcCoSubFare(double lastFare, int distance){
    double tripFare = distance * stationFare;
    double totalFare = tripFare + lastFare;
    if(lastFare == cap){
      fare = 0.0;
    } else if(lastFare < 6.0 && totalFare > 6.0){
      fare = 6.0 - lastFare;
    } else {
      fare = tripFare;
    }
    return fare;
  }

  public double calculateTripFares(TripSegment ts) {
      if (ts.getTransitType().equals("B")) {
      }
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