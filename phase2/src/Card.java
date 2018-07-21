import java.util.ArrayList;

/** Card is used to tap in and tap out when enter and exit a stop or station. */
class Card {

  /**
   * Stores number assigned to this card
   */
  private String cardNumber;
  /**
   * Keeps track of the next card number to be assigned.
   */
  private static int nextCardNumber = 30000001;
  /**
   * Keeps track of the balance in this card
   */
  private double balance;
  /**
   * Stores owner of this card
   */
  private CardHolder owner;
  /**
   * Stores whether the card is linked to an owner
   */
  private boolean linked;
  /**
   * Stores current status of card (activated/deactivated)
   */
  private boolean active;
  /**
   * Stores complete trips by date
   */
  private ArrayList<TripSegment> trips;
  /**
   * Keeps track of total fares accumulated on this card
   */
  private double totalFares;


  /**
   * Constructs a card.
   */
  Card() {
    this.cardNumber = Integer.toString(nextCardNumber);
    nextCardNumber += 1;
    this.owner = null;
    this.balance = 19;
    this.linked = false;
    this.active = true;
    this.trips = new ArrayList<>();
    this.totalFares = 0.0;

  }
  public ArrayList<TripSegment> getMostRecentTrips() {
    ArrayList<TripSegment> recentTrips = new ArrayList<>();
    recentTrips.add(trips.get(trips.size()-3));
    recentTrips.add(trips.get(trips.size()-2));
    recentTrips.add(trips.get(trips.size()-1));
    return recentTrips;
  }

  /**
   * Prints out all trips traveled and recorded by the card.
   */
  void viewAllTrips() {
    System.out.println(this.trips);
  }

  void addTotalFares(double newfare){
    totalFares += newfare;
  }
  void setBalance(Double balance) {
    if (active) {
      this.balance = balance;
    } else {
      System.out.println("Action denied: Card " + this.getCardNumber() + "is deactivated");
    }
  }

  public ArrayList<TripSegment> getTrips() {
    return this.trips;
  }

  /**
   * @return balance on this card
   */
  double getBalance() {
    return balance;
  }

  /**
   * Adds balance to the card.
   *
   * @param fares the amount of fares to be added to the card
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
   * @param fares the amount of fares to be deducted from the card
   */
  void deductBalance(Double fares) {
    if (active) {
      this.balance -= fares;
    } else {
      System.out.println("Action denied: Card " + this.getCardNumber() + "is deactivated");
    }
  }

  /**
   * Prints out the balance of the card.
   */
  void viewBalance() {
    if (active) {
      System.out.println("Card " + this.getCardNumber() + " balance: $" + this.balance);
    } else {
      System.out.println("Action denied: Card " + this.getCardNumber() + "is deactivated");
    }
  }

  /**
   * Activates the card.
   */
  void activate() {
    this.active = true;
  }

  /**
   * Deactivates the card.
   */
  void deactivate() {
    this.active = false;
  }

  /**
   * Sets the status of the card linking to a account to be true.
   */
  void linkAccount() {
    this.linked = true;
  }

  /**
   * Sets the status of the card linking to a account to be false.
   */
  void unlinkAccount() {
    this.linked = false;
  }

  /**
   * Return true iff this card is linked to an account.
   *
   * @return whether the account is linked to an account.
   */
  boolean isLinked() {
    return linked;
  }

  /**
   * Return true iff this card has been activated.
   *
   * @return status of activation.
   */
  boolean isActive() {
    return active;
  }

  /**
   * @return the card number
   */
  String getCardNumber() {
    return this.cardNumber;
  }

  /**
   * Prints out the total fares accumulated on this card
   */
  void viewTotalFares() {
    System.out.println(this.totalFares);
  }

  /**
   * @return the owner of the card
   */
  CardHolder getOwner() {
    return this.owner;
  }

  /**
   * @param owner a card holder who will be assigned as the owner of the card
   */
  void setOwner(CardHolder owner) {
    this.owner = owner;
  }

  /**
   * Returns true iff other is equal to this card.
   *
   * @param other card to be compared with for equality
   * @return true iff other is equal to this card.
   */
  boolean equals(Card other) {
    return this.cardNumber.equals(other.getCardNumber());
  }

  /**
   * @return total amount of fare accumualted on this card
   */
  double getTotalFares() {
    return this.totalFares;
  }

  /**
   * Prints out the three most recent trips which are stored in the card.
   */
  void viewMostRecentTrips() {
    if (trips.size() < 3){
      System.out.println(trips);
    }
    else {
      ArrayList<TripSegment> result = new ArrayList<>();
      result.add(trips.get(this.getTrips().size()-3));
      result.add(trips.get(this.getTrips().size()-2));
      result.add(trips.get(this.getTrips().size()-1));
      System.out.println(result);
    }
  }


  void addTrip(TripSegment newtrip){
    trips.add(newtrip);
  }

  /**
   * Updates all stored fares by the change of the amount of fares
   *
   * @param tripSegment the target trip segment
   * @param fares the amount of fares that need to be updated
   */
  void updateFares(TripSegment tripSegment, Double fares) {
    // deduct fares from card balance
    this.deductBalance(fares);
    // add fares to totalFare
    this.totalFares += fares;
    // add fares to allFares
    TransitSystem.addAllFares(tripSegment.getDate(), fares);
  }

  /**
   * Returns true iff card balance is positive and card is active.
   *
   * @return true iff card balance is positive and card is active.
   */
  boolean isEntryAllowed() {
    if ((this.balance > 0) && (active)) {
      System.out.println("Accepted");
      return true;
    } else {
      System.out.println("Rejected");
      return false;
    }
  }
}
