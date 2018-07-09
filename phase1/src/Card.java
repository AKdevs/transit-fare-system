import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Card {
  private int cardNumber;
  private static int nextCardNumber = 30000001;
  private double balance;
  private CardHolder owner;
  private String status;
  private double currentFares;
  private HashMap<String, ArrayList<ArrayList<TripSegment>>> trips; // key is date
  private ArrayList<ArrayList> mostRecentTrips; // [completeTrio1, completeTrip2, completeTrip3]
  private ArrayList<Double> totalFares;
  // private int currentDuration;
  private int startEnterTime;
  private TripSegment lastTripSegment;
  private ArrayList<TripSegment> lastCompleteTrip;
  private String linkedness;


  public Card() {
    this.cardNumber = nextCardNumber;
    nextCardNumber += 1;
    this.owner = null;
    this.balance = 19;
    this.status = "activated";
    this.trips = new HashMap<>(); // only keeps track of 3 most recent trips
    this.currentFares = 0;
    this.totalFares = new ArrayList<>();// an ArrayList of length 12
    // this.currentDuration = 0;
    this.linkedness = "unlinked";
  }

  void setBalance(Double balance) {
    this.balance = balance;
  }

  void addBalance(Double fares) {
    this.balance += fares;
  }

  void deductBalance(Double fares) {
    this.balance -= fares;
  }

  void activate() {
    this.status = "activated";
  }

  void deactivate() {
    this.status = "deactivated";
  }

  public ArrayList<ArrayList> getMostRecentTrips() {
    return mostRecentTrips;
  }

  public void setLinked() {
    this.linkedness = "Linked";
  }

  public void setUnlinked() {
    this.linkedness = "Unlinked";
  }

  public String getLinkedness() {
    return linkedness;
  }

  int getCardNumber() {
    return this.cardNumber;
  }

  public double getBalance() {
    return balance;
  }

  ArrayList<Double> getTotalFares() {
    return this.totalFares;
  }

  CardHolder getOwner() {
    return this.owner;
  }

  void setOwner(CardHolder owner) {
    this.owner = owner;
  }

  String getStatus() {
    return this.status;
  }

  double getCurrentFares() {
    return this.currentFares;
  }

  void addCurrentFares(double fares) {
    this.currentFares += fares;
  }

  void deductCurrentFares(double fares) {
    this.currentFares -= fares;
  }

  boolean equals(Card other) {
    return this.cardNumber == other.getCardNumber();
  }

  boolean isEntryAllowed() {
    if ((this.balance > 0) && (this.status.equals("activated"))) {
      System.out.println("Accepted");
      return true;
    } else {
      System.out.println("Rejected");
      return false;
    }
  }

  void addTripSegment(TripSegment tripSegment) {
    // if tripSegment is the first TripSegment to be added to trips
      String currentDate = tripSegment.getEnterDate();
          if (!this.trips.containsKey(currentDate)) {
              int startTime = Integer.parseInt(tripSegment.getEnterTime().substring(0, 2)) * 60 + Integer.parseInt(tripSegment.getEnterTime().substring(3, 5));
              this.startEnterTime = startTime;
              String date = tripSegment.getEnterDate();
              ArrayList<TripSegment> firstCompleteTrip = new ArrayList<>();
              firstCompleteTrip.add(tripSegment);
              ArrayList<ArrayList<TripSegment>> firstDayTrips = new ArrayList<>();
              firstDayTrips.add(firstCompleteTrip);
              this.trips.put(date, firstDayTrips);
              this.lastTripSegment = tripSegment;
              this.lastCompleteTrip = firstCompleteTrip;
              this.updateFares(tripSegment, tripSegment.getSegmentFares());

      }
      // if tripSegment is the start of a new complete trip
     else if (!lastTripSegment.getExitSpot().equals(tripSegment.getEnterSpot())) {
      for (Map.Entry<String, ArrayList<ArrayList<TripSegment>>> date : this.trips.entrySet()) {
        if (date.toString().equals(tripSegment.getExitDate())) {
          ArrayList<ArrayList<TripSegment>> dayTrips = date.getValue();
          ArrayList<TripSegment> newCompleteTrip = new ArrayList<>();
          newCompleteTrip.add(tripSegment);
          dayTrips.add(newCompleteTrip);
          this.updateFares(tripSegment, tripSegment.getSegmentFares());
          this.lastTripSegment = tripSegment;
          this.lastCompleteTrip = newCompleteTrip;
          int startTime = Integer.parseInt(tripSegment.getEnterTime().substring(0, 2)) * 60 + Integer.parseInt(tripSegment.getEnterTime().substring(3, 5));
          this.startEnterTime = startTime;
        }
      }
      // if tripSegment and lastTripSegment can form a continuous Trip
    } else if (lastTripSegment.getExitSpot().equals(tripSegment.getEnterSpot())) {
      if (Integer.parseInt(tripSegment.getEnterTime()) - this.startEnterTime < 120) {
        for (Map.Entry<String, ArrayList<ArrayList<TripSegment>>> date : this.trips.entrySet()) {
          if (date.toString().equals(tripSegment.getExitDate())) {
            ArrayList<ArrayList<TripSegment>> dayTrips = date.getValue();
            for (ArrayList<TripSegment> ct : dayTrips) {
              if (ct.equals(this.lastCompleteTrip)) {
                ct.add(tripSegment);
                this.lastCompleteTrip.add(tripSegment);
                this.lastTripSegment = tripSegment;
                // currentFares + this trip fare > 6 and within 2 hours
                if (this.currentFares + tripSegment.getSegmentFares() >= 6) { // tap in time limit
                  double difference = 6 - this.currentFares;
                  this.currentFares = 6;
                  this.updateFares(tripSegment, difference);
                  // currentFares + this trip fare > 6 and within 2 hours
                } else if (this.currentFares + tripSegment.getSegmentFares() < 6) {
                  this.updateFares(tripSegment, tripSegment.getSegmentFares());
                }
              }
            }
          }
        }
      } else {
        for (Map.Entry<String, ArrayList<ArrayList<TripSegment>>> date : this.trips.entrySet()) {
          if (date.toString().equals(tripSegment.getExitDate())) {
            ArrayList<ArrayList<TripSegment>> dayTrips = date.getValue();
            ArrayList<TripSegment> newCompleteTrip = new ArrayList<>();
            newCompleteTrip.add(tripSegment);
            dayTrips.add(newCompleteTrip);
            this.updateFares(tripSegment, tripSegment.getSegmentFares());
            this.lastTripSegment = tripSegment;
            this.lastCompleteTrip = newCompleteTrip;
            int startTime = Integer.parseInt(tripSegment.getEnterTime().substring(0, 2)) * 60 + Integer.parseInt(tripSegment.getEnterTime().substring(3, 5));
            this.startEnterTime = startTime;
          }
        }
      }
    }
  }

  private void updateFares(TripSegment tripSegment, Double fares) {
      // deduct fares from card balance
      this.deductBalance(fares);
      // add currentFares
      this.addCurrentFares(fares);
      // add fares to totalFares
      Double monthFares = this.totalFares.get(this.totalFares.size() - 1);
      monthFares += fares;
      // add fares to allFares
      TransitSystem.addAllFares(tripSegment.getExitDate(), fares);
  }
}
