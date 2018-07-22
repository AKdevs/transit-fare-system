import java.util.ArrayList;
import java.util.HashMap;

public class TransitSystem {
  /** Stores the trip manager */
  private TripManager tripManager = new TripManager();
  /** Stores the transit manager */
  // private TransitManager transitManager = new TransitManager();
  /** Stores the account manager */
  private AccountManager accountManager = new AccountManager();
  private CardManager cardManager = new CardManager();

  /** Keeps a track of all cards in the system. */
  // private static ArrayList<Card> cards = new ArrayList<>();
  /** Keeps a track of transit lines in the system by name */
  protected static HashMap<String, TransitLine> transitLines = new HashMap<>();
  /** Keeps a track of all user accounts in the system */
  // private static ArrayList<UserAccount> userAccounts = new ArrayList<>();

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


  TripManager getTripManager() {
    return tripManager;
  }

  /*
  TransitManager getTransitManager() {
    return transitManager;
  }
  */

  AccountManager getAccountManager() {
    return accountManager;
  }

  CardManager getCardManager() {
    return cardManager;
  }

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
  static void updateAllFares(String date, double fares) {
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