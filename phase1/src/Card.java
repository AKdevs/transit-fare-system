import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Card {
  private int cardNumber;
  private static int nextCardNumber = 30000001;
  private int balance;
  private CardHolder owner;
  private String status;
  private int currentFares;
  private HashMap<String, ArrayList<ArrayList<TripSegment>>> trips;// key is date
  private ArrayList<ArrayList> mostRecentTrips; // [completeTrio1, completeTrip2, completeTrip3]
  private HashMap<Integer, Double> totalFares; // key-value pair, past 12 months
  private int currentTime;

  // registered = linked, different from "activate"

  public Card() {
    this.cardNumber = nextCardNumber;
    nextCardNumber += 1;
    this.owner = null;
    this.balance = 19;
    this.status = "deactivated";
    this.trips = new HashMap<>(); // only keeps track of 3 most recent trips
    this.currentFares = 0;
    this.totalFares = new HashMap<>();
    this.currentTime = 0;
  }

  void addBalance(int value) {
    this.balance += value;
  }

  void deductBalance(int value) {
    this.balance -= value;
  }

  void setStatus(String status) {
    this.status = status;
  }

  void viewRecentTrips() {
    System.out.println(this.trips);
  } // only print 3 most recent trips

  void viewAverageFares() {
    // view the average fares of the past 12 months
    // use iterator?
    Double sumOfFares = 0.0;
    for (Map.Entry m : this.totalFares.entrySet()) {
      sumOfFares += (Double) m.getValue();
      System.out.println(sumOfFares / 12);
    }
  }

  void printCardNumber() {
    System.out.println(this.cardNumber);
  }

  int getCardNumber() {
    return this.cardNumber;
  }

  void getBalance() {
    System.out.println(this.balance);
  }

  HashMap<Integer, Double> getTotalFares() {
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

  int getCurrentFares() {
    return this.currentFares;
  }

  void addCurrentFares(int fares) {
      this.currentFares += fares;
  }

  void deductCurrentFares(int fares) {
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

  void addTripSegment(TripSegment tripSegment) {}
}
