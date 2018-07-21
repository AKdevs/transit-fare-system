import java.util.ArrayList;
import java.util.HashMap;

public class TransitSystem {
  /** Keeps a track of all cards in the system. */
  private static ArrayList<Card> cards = new ArrayList<>();
  /** Keeps a track of transit lines in the system by name */
  protected static HashMap<String, TransitLine> transitLines = new HashMap<>();
  /** Keeps a track of all user accounts in the system */
  private static ArrayList<UserAccount> userAccounts = new ArrayList<>();

  /** Stores system current month */
  private String currentMonth;
  /** Stores system current date */
  private String currentDate;

  /** Maximum amount of time where the fareCap is applicable. */
  protected static final int maximumDuration = 120;

  /** Stores the total amount of accumulated fares in the system by date */
  static HashMap<String, Double> allFares = new HashMap<>();

  /** Stores the number of stations reached in the entire system by date */
  static HashMap<String, Integer> numberOfStations = new HashMap<>();

  /** Operating status of system, either "on" or "off" */
  private String operatingStatus = "off";

  /** @return operating status of the system, either "on" or "off". */
  String getOperatingStatus() {
    return this.operatingStatus;
  }

  /** Power on the system. */
  void powerOnSystem() {
    this.operatingStatus = "on";
  }



  /** Power off the system. */
  void powerOffSystem() {
    this.operatingStatus = "off";
    System.out.println("The TransitSystem has been powered off.");
  }

  /** @return current month in MM format */
  String getCurrentMonth() {
    return this.currentMonth;
  }

  /** @return current date in YY-MM-DD format */
  String getCurrentDate() {
    return this.currentDate;
  }

  /** @param month month to be stored */
  void setCurrentMonth(String month) {
    this.currentMonth = month;
  }

  /** @param date date to be stored. */
  void setCurrentDate(String date) {
    this.currentDate = date;
  }

  /** @return amount of fares in system, stored by date. */
  public static HashMap<String, Double> getAllFares() {
    return allFares;
  }

  /**
   * Finds and returns the card denoted by cardNumber, or null if not found.
   *
   * @param cardNumber number of card to be found
   * @return card denoted by CardNumber, or null if not found.
   */
  static Card findCard(String cardNumber) {
    for (Card c : cards) {
      if (c.getCardNumber().equals(cardNumber)) {
        return c;
      }
    }
    return null;
  }

  /**
   * Adds newCard to list of existing cards.
   *
   * @param newCard new card to be added.
   */
  private void addCard(Card newCard) {
    cards.add(newCard);
  }

  /** Creates new card, card number is assigned automatically. */
  void createCard() {
    Card newCard = new Card();
    addCard(newCard);
    System.out.println("Card " + newCard.getCardNumber() + " created");
  }

  /**
   * Removes card and all information associated with it.
   *
   * @param cardNumber number of card to be removed.
   */
  void removeCard(String cardNumber) {
    cards.remove(findCard(cardNumber));
  }

  /**
   * Finds and returns user account for given accountNumber, null if not found.
   *
   * @param accountNumber number of account to be found.
   * @return user account for given accountNumber, null if not found.
   */
  UserAccount findUserAccount(String accountNumber) {
    for (UserAccount ua : userAccounts) {
      if (ua.getAccountNum().equals(accountNumber)) {
        return ua;
      }
    }
    return null;
  }

  /** @return list of user accounts. */
  ArrayList<UserAccount> getUserAccounts() {
    return userAccounts;
  }

  /**
   * Returns the amount of total fare accumulated in the day.
   *
   * @param date date for which fare is to be returned.
   * @return the amount of fare accumulated on date.
   */
  static Double getDailyFares(String date) {
    return allFares.get(date);
  }

  /**
   * Return the number of stations reached on date.
   *
   * @param date date for which number of stations is to be returned.
   * @return the number of stations reached on date.
   */
  static Integer getDailyStation(String date) {
    return numberOfStations.get(date);
  }

  /**
   * Add amount of fares accumulated on date to total list of fares (which is stored by date)
   *
   * @param date date on which fares were accumulated.
   * @param fares fares accumulated on date.
   */
  static void addAllFares(String date, double fares) {
    if (allFares.containsKey(date)) {
      for (String d : allFares.keySet()) {
        if (d.equals(date)) {
          Double f = allFares.get(d);
          f += fares;
          allFares.put(d, f);
        }
      }
    } else {
      allFares.put(date, fares);
    }
  }

  /**
   * Add number of stations reached on date to total list of stations reached (which is stored by
   * date)
   *
   * @param date date for which number of stations reached is to be added.
   * @param n number of stations reached on date.
   */
  static void addNumberOfStation(String date, int n) {
    if (numberOfStations.containsKey(date)) {
      for (String d : numberOfStations.keySet()) {
        if (d.equals(date)) {
          Integer stationNum = numberOfStations.get(d);
          stationNum += n;
          numberOfStations.put(d, stationNum);
        }
      }
    } else {
      numberOfStations.put(date, n);
    }
  }

}