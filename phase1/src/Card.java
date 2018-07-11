import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/** Card is used to tap in and tap out when enter and exit a stop or station. */
public class Card {
  private String cardNumber;
  private static int nextCardNumber = 30000001;
  private double balance;
  private CardHolder owner;
  private boolean linked;
  private boolean active;
  private double currentFares;
  private HashMap<String, ArrayList<ArrayList<TripSegment>>> trips;
  private ArrayList<ArrayList<TripSegment>>
      mostRecentTrips; // keep track of all completeTrips, but only print out the last three when
  // user view it
  // private ArrayList<Double> totalFares;
  private double totalFares;
  private int startEnterTime;
  private TripSegment lastTripSegment;
  private ArrayList<TripSegment> lastCompleteTrip;
  private TripSegment ongoingTripSegment;

  /** Constructs a card. */
  public Card() {
    this.cardNumber = Integer.toString(nextCardNumber);
    nextCardNumber += 1;
    this.owner = null;
    this.balance = 19;
    this.linked = false;
    this.active = true;
    this.trips = new HashMap<>();
    this.mostRecentTrips = new ArrayList<>();
    this.currentFares = 0.0;
    this.totalFares = 0.0;
    this.ongoingTripSegment = null;
  }

  /** Prints out all trips traveled and recorded by the card. */
  void viewAllTrips() {
    System.out.println(this.trips);
  }

  void setBalance(Double balance) {
    if (active) {
      this.balance = balance;
    } else {
      System.out.println("Action denied: Card " + this.getCardNumber() + "is deactivated");
    }
  }

  public double getBalance() {
    return balance;
  }

  /**
   * Adds balance to the card.
   *
   * @param fares the amount of fares that are going to be added to the card
   */
  void addBalance(Double fares) {
    if (active) {
      this.balance += fares;
      System.out.println(
          "Sucessfully added $"
              + fares
              + " to card "
              + cardNumber
              + "."
              + " The balance is now "
              + this.balance);
    } else {
      System.out.println("Action denied: Card " + this.getCardNumber() + "is deactivated");
    }
  }

  /**
   * Deducts balance from the card.
   *
   * @param fares the amount of fares that are going to be deducted from the card
   */
  void deductBalance(Double fares) {
    if (active) {
      this.balance -= fares;
    } else {
      System.out.println("Action denied: Card " + this.getCardNumber() + "is deactivated");
    }
  }

  /** Prints out the balance of the card. */
  void viewBalance() {
    if (active) {
      System.out.println("Card " + this.getCardNumber() + " balance: $" + this.balance);
    } else {
      System.out.println("Action denied: Card " + this.getCardNumber() + "is deactivated");
    }
  }

  /** Activates the card. */
  void activate() {
    this.active = true;
  }

  /** Deactivates the card. */
  void deactivate() {
    this.active = false;
  }

  /** Sets the status of the card linking to a account to be true. */
  public void linkAccount() {
    this.linked = true;
  }
  /** Sets the status of the card linking to a account to be false. */
  public void unlinkAccount() {
    this.linked = false;
  }

  /**
   * Return true iff this card is linked to an account.
   * @return whether the account is linked to an account.
   */
  public boolean isLinked() {
    return linked;
  }

  /**
   * Return true iff this card has been activated.
   * @return status of activation.
   */
  public boolean isActive() {
    return active;
  }

  /**
   * Returns the card number
   *
   * @return the card number
   */
  String getCardNumber() {
    return this.cardNumber;
  }

  void viewTotalFares() {
    System.out.println(this.totalFares);
  }
  /**
   * Returns the owner of the card.
   *
   * @return the owner of the card
   */
  CardHolder getOwner() {
    return this.owner;
  }
  /**
   * Sets the owner of the card.
   *
   * @param owner a card holder who will be assigned as the owner of the card
   */
  void setOwner(CardHolder owner) {
    this.owner = owner;
  }

  /**
   * Returns the amount of fare accumulated.
   * @return amount of fare.
   */
  double getCurrentFares() {
    return this.currentFares;
  }

  /**
   * Add fare amount to running total of fares.
   *
   * @param fares the amount of money.
   */
  void addCurrentFares(double fares) {
    this.currentFares += fares;
  }

  void deductCurrentFares(double fares) {
    this.currentFares -= fares;
  }

    void setCurrentFares(double fares) {
        this.currentFares = fares;
    }

  boolean equals(Card other) {
    return this.cardNumber == other.getCardNumber();
  }
  /**
   * ???????????????????????
   *
   * @return
   */
  double getTotalFares() {
    return this.totalFares;
  }

  /** Prints out the three most recent trips which are stored in the card. */
  void viewMostRecentTrips() {
    if (active) {
      if (this.mostRecentTrips.size() >= 3) {
        ArrayList<TripSegment> last = this.mostRecentTrips.get(this.mostRecentTrips.size() - 1);
        ArrayList<TripSegment> secondLast =
            this.mostRecentTrips.get(this.mostRecentTrips.size() - 2);
        ArrayList<TripSegment> thirdLast =
            this.mostRecentTrips.get(this.mostRecentTrips.size() - 3);
        ArrayList<ArrayList<TripSegment>> result = new ArrayList<ArrayList<TripSegment>>();
        result.add(thirdLast);
        result.add(secondLast);
        result.add(last);
        System.out.println(result);
      } else {
        ArrayList<ArrayList<TripSegment>> result = new ArrayList<ArrayList<TripSegment>>();
        for (ArrayList<TripSegment> completeTrip : this.mostRecentTrips) {
          result.add(completeTrip);
        }
        System.out.println(result);
      }
    } else {
      System.out.println("Action denied: Card " + this.getCardNumber() + "is deactivated");
    }
  }

  boolean isEntryAllowed() {
    if ((this.balance > 0) && (active)) {
      System.out.println("Accepted");
      return true;
    } else {
      System.out.println("Rejected");
      return false;
    }
  }

  /**
   * ?????????????????????????????????
   *
   * @param tripSegment
   */
  void addTripSegment(TripSegment tripSegment) {
    // if tripSegment is the first TripSegment to be added to trips
    String currentDate = tripSegment.getEnterDate();
    if (!this.trips.containsKey(currentDate)) {
      int startTime =
          Integer.parseInt(tripSegment.getEnterTime().substring(0, 2)) * 60
              + Integer.parseInt(tripSegment.getEnterTime().substring(3, 5));
      this.startEnterTime = startTime;
      String date = tripSegment.getEnterDate();
      this.lastTripSegment = tripSegment;
      ArrayList<TripSegment> firstCompleteTrip = new ArrayList<>();
      firstCompleteTrip.add(this.lastTripSegment);
      this.lastCompleteTrip = firstCompleteTrip;
      ArrayList<ArrayList<TripSegment>> firstDayTrips = new ArrayList<>();
      firstDayTrips.add(this.lastCompleteTrip);
      this.trips.put(date, firstDayTrips);
      this.mostRecentTrips.add(this.lastCompleteTrip);
      this.updateFares(tripSegment, tripSegment.getSegmentFares());
    }
    // if tripSegment is the start of a new complete trip
    else if (!lastTripSegment.getExitSpot().equals(tripSegment.getEnterSpot())) {
        this.setCurrentFares(0.0);
      for (String date : this.trips.keySet()) {
        if (date.equals(tripSegment.getEnterDate())) {
          ArrayList<ArrayList<TripSegment>> dayTrips = this.trips.get(date);
          this.lastTripSegment = tripSegment;
          ArrayList<TripSegment> newCompleteTrip = new ArrayList<>();
          newCompleteTrip.add(this.lastTripSegment);
          this.lastCompleteTrip = newCompleteTrip;
          dayTrips.add(this.lastCompleteTrip);
          this.updateFares(tripSegment, tripSegment.getSegmentFares());
          this.mostRecentTrips.add(this.lastCompleteTrip);
          int startTime =
              Integer.parseInt(tripSegment.getEnterTime().substring(0, 2)) * 60
                  + Integer.parseInt(tripSegment.getEnterTime().substring(3, 5));
          this.startEnterTime = startTime;
        }
      }
      // if tripSegment and lastTripSegment can form a continuous Trip
    } else if (lastTripSegment.getExitSpot().equals(tripSegment.getEnterSpot())) {
      int segmentStartTime =
          Integer.parseInt(tripSegment.getEnterTime().substring(0, 2)) * 60
              + Integer.parseInt(tripSegment.getEnterTime().substring(3, 5));
      // within 2 hours
      if (segmentStartTime - this.startEnterTime < 120) {
          if (tripSegment.getEnterTransitType().equals("S")){
              tripSegment.setContiSub(true);
          }
          this.lastTripSegment = tripSegment;
          this.lastCompleteTrip.add(this.lastTripSegment);
          // currentFares + this trip fare > 6 and within 2 hours
          if (this.currentFares + tripSegment.getSegmentFares() >= 6) { // tap in time limit
              double difference = 6 - this.currentFares;
              this.setCurrentFares(6);
              this.updateFares(tripSegment, difference);
          // currentFares + this trip fare > 6 and within 2 hours
          } else if (this.currentFares + tripSegment.getSegmentFares() < 6) {
              this.updateFares(tripSegment, tripSegment.getSegmentFares());
          }
      }

      // more than 2 hours
      } else {
        this.setCurrentFares(0.0);
        for (String date : this.trips.keySet()) {
          if (date.equals(tripSegment.getEnterDate())) {
            ArrayList<ArrayList<TripSegment>> dayTrips = this.trips.get(date);
            ArrayList<TripSegment> newCompleteTrip = new ArrayList<>();
              this.lastTripSegment = tripSegment;
            newCompleteTrip.add(this.lastTripSegment);
              this.lastCompleteTrip = newCompleteTrip;
            dayTrips.add(this.lastCompleteTrip);
            this.updateFares(tripSegment, tripSegment.getSegmentFares());
            //this.lastTripSegment = tripSegment;
            //this.lastCompleteTrip = newCompleteTrip;
            this.mostRecentTrips.add(this.lastCompleteTrip);
            int startTime =
                Integer.parseInt(tripSegment.getEnterTime().substring(0, 2)) * 60
                    + Integer.parseInt(tripSegment.getEnterTime().substring(3, 5));
            this.startEnterTime = startTime;
          }
        }
      }
    }
  //}

  /**
   * Updates all stored fares by the change of the amount of fares
   *
   * @param tripSegment the target trip segment
   * @param fares the amount of fares that need to be updated
   */
  void updateFares(TripSegment tripSegment, Double fares) {
    // deduct fares from card balance
    this.deductBalance(fares);
    // add currentFares
    this.addCurrentFares(fares);
    // add fares to totalFare
    this.totalFares += fares;
    // add fares to allFares
    TransitSystem.addAllFares(tripSegment.getEnterDate(), fares);
  }

  TripSegment getOngoingTripSegment() {
      return this.ongoingTripSegment;
  }

  void setOngoingTripSegment(TripSegment ts) {
      this.ongoingTripSegment = ts;
  }

    public TripSegment getLastTripSegment() {
        return lastTripSegment;
    }
}
