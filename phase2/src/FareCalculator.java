public class FareCalculator {
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
}