import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TransitSystem {

  static private ArrayList<Card> cards;
  private TripManager tripManager = new TripManager();
  // [Card(123), Card(24), Card(678), .....]

  private ArrayList<UserAccount> userAccounts;
  // [CardHolder(1),AdminUser(026), CardHolder(3), .....]

  // key for transitlines is transit type. value for transitlines is the arraylist of transitline
  // private static HashMap<String, ArrayList<TransitLine>> transitLines;
  private static HashMap<String, TransitLine> transitLines;
  //Subway fare is $0.5 per station travelled
  private static double subwayFare = 0.5;

  //Bus fare is $2.0 per trip
  private static double busFare = 2.0;

  protected TripSegment currentTripSegment;

  static HashMap<String, Double> allFares; //key is date, value is all the fares

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
  }

  void removeCard(int cardNumber) {
    cards.remove(findCard(cardNumber));
  }
  /*
  void removeCard(Card card) {
    for (Card c : cards) {
      if (c.equals(card)) {
        cards.remove(c);
      }
    }
  }
  */

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

  void createUserAccount(String name, String email) {
    UserAccount newAccount = new UserAccount(name, email);
    addUserAccount(newAccount);
  }

  void addUserAccount(UserAccount newUser) {
    this.userAccounts.add(newUser);
  }

  void removeUserAccount(int accountNumber) {
    userAccounts.remove(findUserAccount(accountNumber));
  }

  /*
  void removeUserAccount(UserAccount user) {
    for (UserAccount u : userAccounts) {
      if (u.equals(user)) {
        userAccounts.remove(u);
      }
    }
  }
  */
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
      for (Map.Entry type : transitLines.entrySet()) {
          if (type.equals("S")) {
              ArrayList<TransitLine> subwayLines = (ArrayList<TransitLine>)type.getValue();
              for (TransitLine tl: subwayLines){
                  for (String station: tl.getPoints()) {
                      if (station.equals(currentTripSegment.getEnterSpot())) {
                          enterSpotIndex = tl.getPoints().indexOf(station);
                      }else if (station.equals(currentTripSegment.getExitSpot())){
                          exitSpotIndex = tl.getPoints().indexOf(station);
                      }
                  }
                  }
              }
          }
          if (enterSpotIndex == exitSpotIndex) {
              return 0;
          }else if (currentTripSegment.getDuration() <= 180 && (exitSpotIndex - enterSpotIndex) * 0.5 > 6){
              addNumberOfStation(currentTripSegment.getEnterDate(), exitSpotIndex - enterSpotIndex + 1);
              return 6;
          } else {
              addNumberOfStation(currentTripSegment.getEnterDate(), exitSpotIndex - enterSpotIndex + 1);
              return (exitSpotIndex - enterSpotIndex) * 0.5;
          }
      }



  static void printDailyReport() {
    System.out.println(allFares);
    System.out.println(numberOfStations);
  }
}
