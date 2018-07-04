import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Card {
  private static int nextCardNumber = 1;
  private int cardNumber;
  private int balance;
  private CardHolder owner;
  private String status;
  private int currentFare;
  private ArrayList<ArrayList<TripSegment>> trips; // key-value pair
  private HashMap<Integer, Double> totalFares; // key-value pair, past 12 months
  private int currentTime;

  // registered = linked, different from "activate"

  public Card() {
    nextCardNumber += 1;
    this.cardNumber = nextCardNumber;
    this.owner = null;
    this.balance = 19;
    this.status = "deactivated";
    this.trips = new ArrayList<ArrayList<TripSegment>>(); // only keeps track of 3 most recent trips
    this.currentFare = 0;
    this.totalFares = new HashMap<>();
    this.currentTime = 0;
  }

  private void addBalance(int value) {
    this.balance += value;
  }

  private void deductBalance(int value) {
    this.balance -= value;
  }

  private void deactivate() {
    this.status = "deactivated";
  }

  private void activate() {
    this.status = "activated";
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

  CardHolder getOwner() {
    return this.owner;
  } // do we print out this?

  void setOwner(CardHolder owner) {
    this.owner = owner;
  }

  String getStatus() {
    return this.status;
  }

  int getCurrentFare() {
    return this.currentFare;
  }

  private void setCurrentFare(int fare) {
    this.currentFare = fare;
  }

  void updateTime(int time) {}

  boolean equals(Card other) {
    return this.cardNumber == other.getCardNumber();
  }
}
