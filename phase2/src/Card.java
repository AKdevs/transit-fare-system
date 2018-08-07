import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;

/** Card is used to tap in and tap out when enter and exit a stop or station. */
class Card implements Serializable {

  /** Stores number assigned to this card */
  private String cardNumber;
  /** Keeps track of the balance in this card */
  private double balance;
  /** Stores owner of this card */
  private CardHolder owner;
  /** Stores whether the card is linked to an owner */
  private boolean linked;
  /** Stores current status of card (activated/deactivated) */
  private boolean active;
  /** Stores complete trips by date */
  private ArrayList<TripSegment> trips;
  /** Keeps track of total fares accumulated on this card */
  private double totalFares;
  /** Keeps track of last tap in/out time of this card */
  private String lastTapTime;


  /** Constructs a card. */
  Card() {
    this.cardNumber = null;
    this.owner = null;
    this.balance = 19;
    this.linked = false;
    this.active = true;
    this.trips = new ArrayList<>();
    this.totalFares = 0.0;
    this.lastTapTime = "unknown";
  }

  public ArrayList<TripSegment> getMostRecentTrips() {
    ArrayList<TripSegment> recentTrips = new ArrayList<>();
    recentTrips.add(trips.get(trips.size() - 3));
    recentTrips.add(trips.get(trips.size() - 2));
    recentTrips.add(trips.get(trips.size() - 1));
    return recentTrips;
  }

  /** Prints out all trips traveled and recorded by the card. */
  void viewAllTrips() {
    System.out.println(this.trips);
  }

  void addTotalFares(double newfare) {
    totalFares += newfare;
  }

  /**
   * set the Balance for card
   *
   * @param balance the balance in card
   */
  void setBalance(Double balance) {
    if (active) {
      this.balance = balance;
    } else {
      System.out.println("Action denied: Card " + this.getCardNumber() + "is deactivated");
    }
  }

  /**
   * Get trips stored in the card
   * @return trips stored in the card
   */
  public ArrayList<TripSegment> getTrips() {
    return this.trips;
  }

  /**
   * Get balance of the card
   * @return balance on this card */
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
      TransitSystem.log(Level.ALL,
          "Successfully added $"
              + fares
              + " to card "
              + cardNumber
              + "."
              + " The balance is now "
              + this.balance);
    } else {
      TransitSystem.log(Level.ALL,"Action denied: Card " + this.getCardNumber() + "is deactivated");
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
        if (this.getBalance() < 10) {
            owner.autoLoad(this);
        }
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
  void linkAccount() {
    this.linked = true;
  }

  /** Sets the status of the card linking to a account to be false. */
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

  /** @return the card number */
  String getCardNumber() {
    return this.cardNumber;
  }

  /** Prints out the total fares accumulated on this card */
  void viewTotalFares() {
    System.out.println(this.totalFares);
  }

  /** @return the owner of the card */
  CardHolder getOwner() {
    return this.owner;
  }

  /** @param owner a card holder who will be assigned as the owner of the card */
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

  /** @return total amount of fare accumualted on this card */
  double getTotalFares() {
    return this.totalFares;
  }

  /** Prints out the three most recent trips which are stored in the card. */
  void viewMostRecentTrips() {
    if (trips.size() < 3) {
      System.out.println(trips);
    } else {
      ArrayList<TripSegment> result = new ArrayList<>();
      result.add(trips.get(this.getTrips().size() - 3));
      result.add(trips.get(this.getTrips().size() - 2));
      result.add(trips.get(this.getTrips().size() - 1));
      System.out.println(result);
    }
  }

  /**
   * Add a new trip to the trip collection stored in the card
   * @param newtrip new trip
   */
  void addTrip(TripSegment newtrip) {
    trips.add(newtrip);
  }

  void updateBalance(double fares) {
    this.deductBalance(fares);
  }

  void updateTotalFares(double fares) {
    this.totalFares += fares;
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

  /**
   * Set the card number for the card
   *
   * @param cardNumber
   */
  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  /**
   * Get the status for the card
   *
   * @return the status of card
   */
  String getStatus() {
      if (active) {
          return "active";
      }
      return "deactivated";
  }

  void setLastTapTime(String time) {
      this.lastTapTime = time;
  }

  String getLastTapTime() {
      return this.lastTapTime;
  }


}
