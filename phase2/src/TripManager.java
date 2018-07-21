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

  private int calculateDuration(String lastStartTime, String currentStartTime) {
       int last = Integer.parseInt(lastStartTime.substring(0, 2)) * 60
                      + Integer.parseInt(lastStartTime.substring(3, 5));
       int current = Integer.parseInt(currentStartTime.substring(0, 2)) * 60
               + Integer.parseInt(currentStartTime.substring(3, 5));
       return current - last;
  }

  void recordTapIn(String time,String spot, String cardNumber,String date, String type){
    // find the associated card
    Card card = TransitSystem.findCard(cardNumber);
    // if card balance is negative
    if (card.getBalance() < 0) {
      System.out.println("Declined: Card is out of funds, please load money.");
    } else {
        // if the cardHolder traveled with this card before
      if (!card.getTrips().isEmpty()) {
        // get the last trip
        ArrayList<TripSegment> allTrips = card.getTrips();
        TripSegment lastTrip = allTrips.get(allTrips.size()-1);
        // if it is a legal entry
        if (lastTrip.hasExit()){
            int duration = this.calculateDuration(lastTrip.getEnterTime(), time);
          // if it the enter of a continuous trip
          if (lastTrip.getExitSpot().equals(spot) && duration < 120){
            lastTrip.setContiSpot(spot);
            if (type.equals("B")){
                //update fares
            }
            lastTrip.setTransitType("continue");

          } else {// if it is a new trip
            TripSegment trip = new TripSegment(spot, time, date, type);
            card.addTrip(trip);
          }
        } else{ // illegal entry
            System.out.println("Declined: Illegal entry");
            // complete the trip segment( without exit) with "illegal", use enterTime as exitTime, use
            // enterDate as exitDate
        }
      // if this is the first time the CardHolder travel with this card
      } else {
        TripSegment trip = new TripSegment(spot, time, date, type);
        card.addTrip(trip);
      }
    }
  }

  void recordTapOut(String time,String spot,String cardNumber) {
    FareCalculator calculator = new FareCalculator();
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
        double fare = 0.0;
        if (current.getTransitType().equals("B")) {
          fare = calculator.calcBusFare();
        } else if (current.getTransitType().equals("S")) {
          fare = calculator.calcSubwayFare(distance);
        }
        current.setFare(fare);
        current.setFareTracker(fare);
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
        double contiFare = calculator.calcBusFare();
        double lastFare = current.getFare();
        double chargeFare = calculator.calcCoBusFare(lastFare);
        double totalFare = contiFare + lastFare;
        current.setContiFare(chargeFare);
        current.setFareTracker(totalFare);
      } else if (current.getContiType().equals("S")) {
        double contiFare = calculator.calcSubwayFare(contiDistance);
        double lastFare = current.getFare();
        double chargeFare = calculator.calcCoSubFare(lastFare, contiDistance);
        double totalFare = contiFare + lastFare;
        current.setContiFare(chargeFare);
        current.setFareTracker(totalFare);
      }
      current.setFare(current.getFare() + current.getContiFare());
      int contiDuration;
      contiDuration = TripManager
          .calculateDuration(current.getContiEnterTime(), current.getContiExitTime());
      int timetracker = current.getTimetracker() + contiDuration;
      current.setDuration(timetracker);
      current.setTimetracker(timetracker);
      TransitSystem.addAllFares(current.getDate(), current.getContiFare());
      TransitSystem.addNumberOfStation(current.getDate(), contiDistance);
      card.updateFares(current,current.getContiFare());
    }
  }
}
