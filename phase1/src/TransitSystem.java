import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class TransitSystem {

  private ArrayList<Card> cards;
  // [Card(123), Card(24), Card(678), .....]

  private ArrayList<UserAccount> userAccounts;
  // [CardHolder(1),AdminUser(026), CardHolder(3), .....]

  //kep for transitlines is transit type. value for transitlines is the arraylist of transitline
  private static HashMap<String, ArrayList<TransitLine>> transitLines;

  protected TripSegment currentTripSegment;

  static int allFares; // print out key-value pair?

  static int numberOfStation; // print out

  Card findCard(int cardNumber) {
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

  UserAccount findUserAccount(long accountNumber) {
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


  void addTransitLines(String type, TransitLine newTransitLine) {
    if (transitLines.containsKey(type)) {
      ArrayList<TransitLine> originalline = transitLines.get(type);

      //https://blog.csdn.net/mustbehard/article/details/17310043
      Iterator<TransitLine> iter = originalline.iterator();
      ArrayList<TransitLine> newlines = new ArrayList<>();
      while (iter.hasNext()) {
        TransitLine lines = iter.next();
        newlines.add(lines);
      }
      newlines.add(newTransitLine);
      transitLines.put(type,newlines);
    }
  }

  void addAllFares(int fares) {}

  void deductAllFares(int fares) {}

  //int getAllFares() {return this.allFares;}

  //int getNumberOfStation() {return this.getNumberOfStation();}

  void addNumberOfStation(int newStation) {}

  void deductNumberOfStation(int newStation) {}

  ArrayList getCards() {
    return this.cards;
  }

  void calculateTripSegementFares(TripSegment currentTripSegment){
      if (currentTripSegment.getEnterTransitType().equals("B")) {
          // deduct 2 dollars on card's balance
          // currentFare in Card increment by 2
      }
  }

  static void printDailyReport(){
      System.out.println(allFares);
      System.out.println(numberOfStation);
  } // static????
}
