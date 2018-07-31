public class TransitSystem {
  /** Stores the trip manager */
  private TripManager tripManager;
  /** Stores the transit manager */
  private TransitManager transitManager;
  /** Stores the account manager */
  private AccountManager accountManager;

  private CardManager cardManager;

  /** Stores system current month */
  private String currentMonth;
  /** Stores system current date */
  private String currentDate;

  /** Operating status of system, either "on" or "off" */
  private String operatingStatus = "off";

  /** The instance of the only TransitSystem */
  private static TransitSystem instance;

  public TransitSystem() {
    tripManager = new TripManager();
    transitManager = new TransitManager();
    tripManager.addTransitLines(transitManager.getTransitLines());
    accountManager = new AccountManager();
    cardManager = new CardManager();
  }

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

  /**
   *
   * @return instance of the TransitSystem
   */
  public static TransitSystem getInstance() {
    if (instance == null) {
      instance = new TransitSystem();
    }
    return instance;
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
}
