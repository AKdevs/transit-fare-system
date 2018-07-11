import java.sql.SQLOutput;
import java.util.*;

public class TransitSystem {

  static private ArrayList<Card> cards = new ArrayList<>();
  protected HashMap<String, TransitLine> transitLines = new HashMap<>();
  private ArrayList<UserAccount> userAccounts = new ArrayList<>();
  // [CardHolder(1),AdminUser(026), CardHolder(3), .....]

  private static double subwayFare = 0.5;
  private static double busFare = 2.0;

  String currentMonth;

  String currentDate;

  // Fare is capped at $6.0 for continuous trips travlled within timeForCap
  private static double fareCap = 6.0;

  // Time allowed to be eligible for capped fare is 120 minutes in our TransitSystem
  private static int timeForCap = 120;

  protected TripSegment currentTripSegment;

  static HashMap<String, Double> allFares = new HashMap<>(); // key is date, value is all the fares

  static HashMap<String, Integer> numberOfStations = new HashMap<>();

  // operatingStatus of TransitSystem, value can be on or off
  private String operatingStatus = "off";

  String getOperatingStatus() {
    return this.operatingStatus;
  }

  void powerOnSystem() {
    this.operatingStatus = "on";
  }

  void powerOffSystem() {
    this.operatingStatus = "off";
    System.out.println("The TransitSystem has been powered off.");
  }

  String getCurrentMonth() {
    return this.currentMonth;
  }

  String getCurrentDate() {
    return this.currentDate;
  }

  void setCurrentMonth(String month) {
    this.currentMonth = month;
  }

  void setCurrentDate(String date) {
    this.currentDate = date;
  }

  public static HashMap<String, Double> getAllFares() {
    return allFares;
  }

  public Card findCard(int cardNumber) {
    for (Card c : this.cards) {
      if (c.getCardNumber() == cardNumber) {
        return c;
      }
    }
    return null;
  }

  void addCard(Card newCard) {
    cards.add(newCard);
  }

  void createCard() {
    Card newCard = new Card();
    addCard(newCard);
    System.out.println("Card " + newCard.getCardNumber() + " created");
  }

  void removeCard(int cardNumber) {
    cards.remove(findCard(cardNumber));
  }

  UserAccount findUserAccount(int accountNumber) {
    for (UserAccount ua : userAccounts) {
      if (ua.getAccountNum() == accountNumber) {
        return ua;
      }
    }
    return null;
  }

  static Double getDailyFares(String date) {
    return allFares.get(date);
  }

  static Integer getDailyStation(String date) {
    return numberOfStations.get(date);
  }

  void createCardHolderAccount(String name, String email) {
    CardHolder newAccount = new CardHolder(name, email);
    addUserAccount(newAccount);
    System.out.println("CardHolder Account " + newAccount.getAccountNum() + " created");
  }

  void createAdminAccount(String name, String email) {
    AdminUser newAccount = new AdminUser(name, email);
    addUserAccount(newAccount);
    System.out.println("AdminUser Account " + newAccount.getAccountNum() + " created");
  }

  void addUserAccount(UserAccount newUser) {
    this.userAccounts.add(newUser);
  }

  void removeUserAccount(int accountNumber) {
    userAccounts.remove(findUserAccount(accountNumber));
  }

  ArrayList<UserAccount> getUserAccounts() {
    return userAccounts;
  }

  void addTripSegmentToCard() {
    int currentCardNumber = currentTripSegment.getAssociatedCard();
    findCard(currentCardNumber).addTripSegment(currentTripSegment);
  }

  static void addAllFares(String date, double fares) {
    if (allFares.isEmpty()) {
      allFares.put(date, fares);
    } else {
      for (String d : allFares.keySet()) {
        if (d.equals(date)) {
          Double f = allFares.get(d);
          f += fares;
          allFares.put(d, f);
        }
      }
    }
  }

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

  ArrayList getCards() {
    return cards;
  }

  /*
  HashMap<String, TransitLine> getTransitLines() {
    return transitLines;
  }
  */
  /*
  static double calculateSubwayFares(TripSegment currentTripSegment) {
    int enterSpotIndex = 0;
    int exitSpotIndex = 0;
    for (String lineName : transitLines.keySet()) {
      TransitLine line = transitLines.get(lineName);
      if (line.getType().equals("S")) {
        ArrayList<String> points = line.getPoints();
        for (String p : points) {
          if (p.equals(currentTripSegment.getEnterSpot())) {
            enterSpotIndex = points.indexOf(p);
          } else if (p.equals(currentTripSegment.getExitSpot())) {
            exitSpotIndex = points.indexOf(p);
          }
        }
      }
    }
    if (enterSpotIndex == exitSpotIndex) {
      return 0;
    } else if (currentTripSegment.getDuration() <= 180
        && (Math.abs(exitSpotIndex - enterSpotIndex)) * 0.5 > 6) {
      addNumberOfStation(
          currentTripSegment.getEnterDate(), Math.abs(exitSpotIndex - enterSpotIndex) + 1);
      return 6;
    } else {
      addNumberOfStation(
          currentTripSegment.getEnterDate(), Math.abs(exitSpotIndex - enterSpotIndex) + 1);
      return (Math.abs(exitSpotIndex - enterSpotIndex)) * 0.5;
    }
  }
  */
}
