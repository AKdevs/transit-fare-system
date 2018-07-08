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
  private HashMap<String, Double> totalFares; // key-value pair, past 12 months
  private int currentTime;
  private TripSegment lastTripSegment;
  private ArrayList<TripSegment> lastCompleteTrip;
  private String linked;

  // registered = linked, different from "activate"

  public Card() {
    this.cardNumber = nextCardNumber;
    nextCardNumber += 1;
    this.owner = null;
    this.balance = 19;
    this.status = "activated";
    this.trips = new HashMap<>(); // only keeps track of 3 most recent trips
    this.currentFares = 0;
    this.totalFares = new HashMap<>();
    this.currentTime = 0;
    this.linked = "unlinked";
  }

  void setBalance(Double balance){ this.balance = balance; }

  void setactivated() {
    this.status = "activated";
  }
  void setdeactivated() {
    this.status = "deactivated";
  }

  void viewRecentTrips() {
    System.out.println(this.mostRecentTrips);
  }

  void viewAverageFares() {
    // view the average fares of the past 12 months
    // use iterator?
    Double sumOfFares = 0.0;
    for (Map.Entry m : this.totalFares.entrySet()) {
      sumOfFares += (Double) m.getValue();
      System.out.println(sumOfFares / 12);
    }
  }

  public void setLinked() {
    this.linked = "Linked";
  }

  public void setUnlinked(){
    this.linked = "Unlinked";
  }

  void printCardNumber() {
    System.out.println(this.cardNumber);
  }

  int getCardNumber() {
    return this.cardNumber;
  }

  public double getBalance() {
    return balance;
  }

  HashMap<String, Double> getTotalFares() {
    return this.totalFares;
  }

  CardHolder getOwner() {
    return this.owner;
  } // do we print out this?

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

  void updateTime(int time) {}

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
    if (this.lastTripSegment == null) {
      String date = tripSegment.getEnterDate();
      ArrayList<TripSegment> firstCompleteTrip = new ArrayList<>();
      firstCompleteTrip.add(tripSegment);
      ArrayList<ArrayList<TripSegment>> firstDayTrips = new ArrayList<>();
      firstDayTrips.add(firstCompleteTrip);
      this.trips.put(date, firstDayTrips);
      this.lastTripSegment = tripSegment;
      this.lastCompleteTrip = firstCompleteTrip;
      // deduct fares from card balance
      this.deductBalance(tripSegment.getSegmentFares());
      // add currentFares
      this.addCurrentFares(tripSegment.getSegmentFares());
      // add fares to totalFares
      for (Map.Entry month : this.totalFares.entrySet()) {
        if (month.equals(tripSegment.getMonth())) {
          Double tf = (Double) month.getValue();
          tf += tripSegment.getSegmentFares();
        }
      }
      // add fares to allFares
      TransitSystem.addAllFares(tripSegment.getSegmentFares());
    } // if tripSegment and lastTripSegment can form a complete Trip
    else if (lastTripSegment.getExitSpot().equals(tripSegment.getEnterSpot())) {
      for (Map.Entry date : this.trips.entrySet()) {
        if (date.equals(tripSegment.getExitDate())) {
          ArrayList<ArrayList<TripSegment>> dayTrips =
              (ArrayList<ArrayList<TripSegment>>) date.getValue();
          for (ArrayList<TripSegment> ct : dayTrips) {
            if (ct.equals(this.lastCompleteTrip)) {
              ct.add(tripSegment);
              this.lastCompleteTrip.add(tripSegment);
              this.lastTripSegment = tripSegment;
              // check whether currentFares + this trip fare > 6
              if (this.currentFares + tripSegment.getSegmentFares() >= 6) {
                double difference = 6 - this.currentFares;
                this.currentFares = 6;
                // deduct fares from card balance
                this.deductBalance(difference);
                // add fares to totalFares
                for (Map.Entry month : this.totalFares.entrySet()) {
                  if (month.equals(tripSegment.getMonth())) {
                    Double tf = (Double) month.getValue();
                    tf += difference;
                  }
                }
                // add fares to allFares
                TransitSystem.addAllFares(difference);
              } else {
                // deduct fares from card balance
                this.deductBalance(tripSegment.getSegmentFares());
                // add currentFares
                this.addCurrentFares(tripSegment.getSegmentFares());
                // add fares to totalFares
                for (Map.Entry month : this.totalFares.entrySet()) {
                  if (month.equals(tripSegment.getMonth())) {
                    Double tf = (Double) month.getValue();
                    tf += tripSegment.getSegmentFares();
                  }
                }
                // add fares to allFares
                TransitSystem.addAllFares(tripSegment.getSegmentFares());
              }
            }
          }
        }
      }
    }
    // if tripSegment is the start of a new complete trip
    else if (!lastTripSegment.getExitSpot().equals(tripSegment.getEnterSpot())) {
      for (Map.Entry date : this.trips.entrySet()) {
        if (date.equals(tripSegment.getExitDate())) {
          ArrayList<ArrayList<TripSegment>> dayTrips =
              (ArrayList<ArrayList<TripSegment>>) date.getValue();
          ArrayList<TripSegment> newCompleteTrip = new ArrayList<>();
          newCompleteTrip.add(tripSegment);
          dayTrips.add(newCompleteTrip);
          // deduct fares from card balance
          this.deductBalance(tripSegment.getSegmentFares());
          // add currentFares
          this.addCurrentFares(tripSegment.getSegmentFares());
          // add fares to totalFares
          for (Map.Entry month : this.totalFares.entrySet()) {
            if (month.equals(tripSegment.getMonth())) {
              Double tf = (Double) month.getValue();
              tf += tripSegment.getSegmentFares();
            }
          }
          // add fares to allFares
          TransitSystem.addAllFares(tripSegment.getSegmentFares());
        }
      }
    }
  }
}