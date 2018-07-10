import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TransitSystem {

  private static ArrayList<Card> cards = new ArrayList<>();
  private TripManager tripManager = new TripManager();
  // [Card(123), Card(24), Card(678), .....]

  private ArrayList<UserAccount> userAccounts = new ArrayList<>();
  // [CardHolder(1),AdminUser(026), CardHolder(3), .....]

  // key is name of the line, value is the transit line
  private static HashMap<String, TransitLine> transitLines = new HashMap<>();
  // Subway fare is $0.5 per station travelled
  private static double subwayFare = 0.5;

  // Bus fare is $2.0 per trip
  private static double busFare = 2.0;

  String currentMonth;

  String currentDate;

  String getCurrentMonth() {
    return this.currentMonth;
  }

  String getCurrentDate() {
    return this.currentDate;
  }

  public static HashMap<String, Double> getAllFares() {
    return allFares;
  }

  // Fare is capped at $6.0 for continuous trips travlled within timeForCap
  private static double fareCap = 6.0;

  // Time allowed to be eligible for capped fare is 120 minutes in our TransitSystem
  private static int timeForCap = 120;

  protected TripSegment currentTripSegment;

  static HashMap<String, Double> allFares; // key is date, value is all the fares

  static HashMap<String, Integer> numberOfStations;

  static Card findCard(int cardNumber) {
    for (Card c : cards) {
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

  public TripManager getTripManager() {
    return tripManager;
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

  static int getDailyStation(String date) {
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

  void addTransitLines(TransitLine newTransitLine) {
    transitLines.put(newTransitLine.getId(), newTransitLine);
  }
  /*
  void addTransitLines(String type, TransitLine newTransitLine) {
    if (transitLines.containsKey(type)) {
      ArrayList<TransitLine> originalline = transitLines.get(type);

      // https://blog.csdn.net/mustbehard/article/details/17310043
      Iterator<TransitLine> iter = originalline.iterator();
      ArrayList<TransitLine> newlines = new ArrayList<>();
      while (iter.hasNext()) {
        TransitLine lines = iter.next();
        newlines.add(lines);
      }
      newlines.add(newTransitLine);
      transitLines.put(type, newlines);
    }
  }
  */

  static void addAllFares(String date, double fares) {
    for (Map.Entry d : allFares.entrySet()) {
      if (d.equals(date)) {
        Double f = (Double) d.getValue();
        f += fares;
      }
    }
  }

  static void addNumberOfStation(String date, int n) {
    for (Map.Entry d : numberOfStations.entrySet()) {
      if (d.equals(date)) {
        Integer stationNum = (Integer) d.getValue();
        stationNum += n;
      }
    }
  }

  ArrayList getCards() {
    return cards;
  }

  HashMap<String, TransitLine> getTransitLines() {
    return transitLines;
  }

  static double calculateSubwayFares(TripSegment currentTripSegment) {
    int enterSpotIndex = 0;
    int exitSpotIndex = 0;
    for (Map.Entry lineName : transitLines.entrySet()) {
        TransitLine line = (TransitLine) lineName.getValue();
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
        && (exitSpotIndex - enterSpotIndex) * 0.5 > 6) {
      addNumberOfStation(currentTripSegment.getEnterDate(), exitSpotIndex - enterSpotIndex + 1);
      return 6;
    } else {
      addNumberOfStation(currentTripSegment.getEnterDate(), exitSpotIndex - enterSpotIndex + 1);
      return (exitSpotIndex - enterSpotIndex) * 0.5;
    }
  }
}
