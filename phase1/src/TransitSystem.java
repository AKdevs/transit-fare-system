import java.sql.SQLOutput;
import java.util.*;

public class TransitSystem {
  private static ArrayList<Card> cards = new ArrayList<>();
  protected static HashMap<String, TransitLine> transitLines = new HashMap<>();
  private static ArrayList<UserAccount> userAccounts = new ArrayList<>();

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

  public Card findCard(String cardNumber) {
    for (Card c : this.cards) {
      if (c.getCardNumber().equals(cardNumber)) {
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

  void removeCard(String cardNumber) {
    cards.remove(findCard(cardNumber));
  }

  UserAccount findUserAccount(String accountNumber) {
    for (UserAccount ua : userAccounts) {
      if (ua.getAccountNum().equals(accountNumber)) {
        return ua;
      }
    }
    return null;
  }

  ArrayList<UserAccount> getUserAccounts() {
    return userAccounts;
  }

  static Double getDailyFares(String date) {
    return allFares.get(date);
  }

  static Integer getDailyStation(String date) {
    return numberOfStations.get(date);
  }

  static void addAllFares(String date, double fares) {
      if (allFares.containsKey(date)) {
          for (String d : allFares.keySet()) {
              if (d.equals(date)) {
                  Double f = allFares.get(d);
                  f += fares;
                  allFares.put(d, f);
              }
          }
      }else {
          allFares.put(date, fares);

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
}
