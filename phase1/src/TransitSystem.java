import java.sql.SQLOutput;
import java.util.*;

public class TransitSystem {

  private static ArrayList<Card> cards = new ArrayList<>();

  private TripManager tripManager = new TripManager();

  private ArrayList<UserAccount> userAccounts = new ArrayList<>();
  // [CardHolder(1),AdminUser(026), CardHolder(3), .....]


  private static ArrayList<String> line1Stations = new ArrayList<>(Arrays.asList("Finch", "North York Centre", "Sheppard-Yonge", "King", "Bay", "Bloor" ));

  private static ArrayList<String> route1Stops = new ArrayList<>(Arrays.asList("Dufferin", "Bathurst", "Sheppard-Yonge", "Bayview", "Leslie"));

  private static TransitLine Line1 = new TransitLine(line1Stations, "S", "Line1");

  private static TransitLine Route1 = new TransitLine(route1Stops, "B", "Route1");

    // key is name of the line, value is the transit line
    private static HashMap<String, TransitLine> transitLines = new HashMap<>(){{
        put("Line1", Line1);
        put("Route1", Route1);
    }};

  // Subway fare is $0.5 per station travelled
  private static double subwayFare = 0.5;

  // Bus fare is $2.0 per trip
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


  String getCurrentMonth() {
        return this.currentMonth;
    }

    String getCurrentDate() {
        return this.currentDate;
    }

  public static HashMap<String, Double> getAllFares() {
        return allFares;
    }

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
    return this.tripManager;
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

  void addTransitLines(TransitLine newTransitLine) {
    transitLines.put(newTransitLine.getId(), newTransitLine);
  }

  static void addAllFares(String date, double fares) {
      if (allFares.isEmpty()){
          allFares.put(date, fares);
      }else{
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
    }else {
        numberOfStations.put(date, n);
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
      addNumberOfStation(currentTripSegment.getEnterDate(), Math.abs(exitSpotIndex - enterSpotIndex) + 1);
      return 6;
    } else {
      addNumberOfStation(currentTripSegment.getEnterDate(), Math.abs(exitSpotIndex - enterSpotIndex) + 1);
      return (Math.abs(exitSpotIndex - enterSpotIndex)) * 0.5;
    }
  }
}
