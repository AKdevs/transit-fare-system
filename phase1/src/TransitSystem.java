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
  private static HashMap<String, ArrayList<TransitLine>> transitLines;

  protected TripSegment currentTripSegment;

  static double allFares; // print out key-value pair?

  static int numberOfStation; // print out

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

  static void addAllFares(double fares) {}

  static void deductAllFares(double fares) {}

  // int getAllFares() {return this.allFares;}

  // int getNumberOfStation() {return this.getNumberOfStation();}

  void addNumberOfStation(int newStation) {}

  void deductNumberOfStation(int newStation) {}

  ArrayList getCards() {
    return this.cards;
  }

  static double calculateSubwayFares(TripSegment currentTripSegment) {
      int enterSpotIndex = 0;
      int exitSpotIndex = 0;
      for (Map.Entry type : transitLines.entrySet()) {
          if (type.equals("s")) {
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
              return 6;
          } else {
              return (exitSpotIndex - enterSpotIndex) * 0.5;
          }
      }



  static void printDailyReport() {
    System.out.println(allFares);
    System.out.println(numberOfStation);
  } // static????
}
