import java.util.ArrayList;

class TripManager extends TransitSystem {

  /**
   * Calculates the duration of the trip segment in minutes.
   *
   * @param startTime of timing
   * @param endTime of timing
   */
  static int calculateDuration(String startTime, String endTime) {
    // int version of enterTime: Hour converted to minutes + minutes
    int enter;
    enter = Integer.parseInt(startTime.substring(0, 2)) * 60
        + Integer.parseInt(startTime.substring(3, 5));
    // int version of exitTime: Hour converted to minutes + minutes
    int exit =
        Integer.parseInt(endTime.substring(0, 2)) * 60
            + Integer.parseInt(endTime.substring(3, 5));
    int duration = exit - enter;
    return duration;
  }

  static int calculateDistance(String enterSpot, String exitSpot) {
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
      int distance = Math.abs(exitIndex - enterIndex);
      return distance;
    }
  }
  public static void recordTapIn(String time,String spot, String cardNumber,String date, String type){
    Card card = TransitSystem.findCard(cardNumber);
    if (card.getBalance() < 0) {
      System.out.println("Declined: Card is out of funds, please load money.");
    } else {
      if (!card.getTrips().isEmpty()) {
        ArrayList<TripSegment> allTrips = card.getTrips();
        TripSegment lastTrip = allTrips.get(allTrips.size()-1);
        int timetracker = TripManager.calculateDuration(lastTrip.getEnterTime(), time);
        if (lastTrip.hasExit()){
          if (lastTrip.getExitSpot().equals(spot) && timetracker < 120){
            lastTrip.setContiEnterS(spot);
            lastTrip.setTimetracker(timetracker);
            lastTrip.setContiType(type);
            lastTrip.setContiEnterTime(time);
            lastTrip.setTransitType("continue");

          } else {
            TripSegment trip = new TripSegment(spot, time, date, type);
            trip.setTimetracker(0);
            card.addTrip(trip);
          }
        } else{
          System.out.println("Illegal entry");
        }
      } else {
        TripSegment trip = new TripSegment(spot, time, date, type);
        trip.setTimetracker(0);
        card.addTrip(trip);
      }
    }
  }

  public static void recordTapOut(String time,String spot,String cardNumber) {
    Card card = TransitSystem.findCard(cardNumber);
    assert card != null;
    ArrayList<TripSegment> allTrips = card.getTrips();
    TripSegment current = allTrips.get(allTrips.size() - 1);
    //trip with either type "B" or "S"
    if (!current.getTransitType().equals("continue")) {
      if (current.getExitSpot() == null) {
        current.setExitSpot(spot);
        current.setExitTime(time);
        int distance = TripManager.calculateDistance(current.getEnterSpot(),current.getExitSpot());
        if (current.getTransitType().equals("B")) {
          Double fare = 2.0;
          current.setFare(fare);
          current.setFareTracker(fare);
        } else if (current.getTransitType().equals("S")) {
          Double fare = distance * 0.5;
          if (fare > 6.0) {
            current.setFare(6.0);
          } else {
            current.setFare(fare);
          }
          current.setFareTracker(fare);
        }
        int duration = TripManager.calculateDuration(current.getEnterTime(), current.getExitTime());
        current.setDuration(duration);
        int timetracker = current.getTimetracker() + duration;
        current.setTimetracker(timetracker);
        TransitSystem.addAllFares(current.getDate(), current.getFare());
        TransitSystem.addNumberOfStation(current.getDate(), distance);
        card.deductBalance(current.getFare());
        card.addTotalFares(current.getFare());

      } else {
        System.out.println("Declined: Illegal exit.");
      }

      // the trip with continue type
    } else {
      current.setExitTime(time);
      current.setContiExitS(spot);
      current.setExitSpot(spot);
      current.setContiExitTime(time);
      int contiDistance = TripManager.calculateDistance(current.getContiEnterS(),current.getContiExitS());
      if (current.getContiType().equals("B")) {
        double contiFare = 2.0;
        double lastFare = current.getFare();
        double totalFare = contiFare + lastFare;
        if(lastFare == 6.0){
          current.setContiFare(0.0);
        } else if(lastFare < 6.0 && totalFare > 6.0){
          double tripFare = 6.0 - lastFare;
          current.setContiFare(tripFare);
        } else {
          double fares = 2.0;
          current.setContiFare(fares);
        }
        current.setFareTracker(totalFare);
      } else if (current.getContiType().equals("S")) {
        double lastFare = current.getFare();
        double fare = contiDistance * 0.5;
        double totalFare = fare + lastFare;
        if (lastFare == 6.0) {
          current.setContiFare(0.0);
        } else if (totalFare <= 6.0){
          current.setContiFare(fare);
        } else if (lastFare < 6.0 && totalFare > 6.0){
          double money = 6.0 - lastFare;
          current.setContiFare(money);
        }
        current.setFareTracker(totalFare);
        current.setFare(current.getFare() + current.getContiFare());
        int contiDuration;
        contiDuration = TripManager
            .calculateDuration(current.getContiEnterTime(), current.getContiExitTime());
        int timetracker = current.getTimetracker() + contiDuration;
        current.setDuration(timetracker);
        current.setTimetracker(timetracker);
        TransitSystem.addAllFares(current.getDate(), current.getContiFare());
        TransitSystem.addNumberOfStation(current.getDate(), contiDistance);
        card.deductBalance(current.getContiFare());
        card.addTotalFares(current.getContiFare());
      }
    }
  }

}
