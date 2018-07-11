import java.util.ArrayList;
import java.util.HashMap;

/**
 * CardHolder is a person who owns and uses a Card, ie a passenger who travels in the TransitSystem.
 * The person is able to link Cards to his/her account and view his/her travel history.
 */
public class CardHolder extends UserAccount {

  private ArrayList<Card> travelCards;
  /* Account Number for CardHolder starts at 10000001 to distinguish with other account/card numbers.*/
  private static int nextAccountNum = 10000001;

  public CardHolder(String name, String email) {
    super(name, email);
    this.accountNumber = nextAccountNum;
    nextAccountNum += 1;
    this.travelCards = new ArrayList<>();
  }

  /*public void addbalance(Card card, double amount) {
    // cardholder can only addbalance in card which is linked to their account
    // the initial status of a card is set to "activated",  which means people can use a card right
    // after they buy it, they can use the balance stored in the
    // card to tapIn and tapOut. but if they want to view balance, add balance, they have to get the
    // card linked to their account
    // a new card cannot do anything except for enter and exit the stations
    // in eventhandler: try....catch{your card is not linked to your account}
    if (this.travelCards.contains(card) && card.getStatus().equals("activated")) {
      double balance = card.getBalance() + amount;
      card.setBalance(balance);
    } else {
      System.out.println(
          "Action denied. Card"
              + card.getCardNumber()
              + " is not activated or linked to your account.");
    }
  } */

  /*public void viewBalance(Card card) {
    if (this.travelCards.contains(card) && card.getStatus().equals("activated")) {
      System.out.println("Card balance: " + card.getBalance());
    } else {
      System.out.println(
          "Action denied. Card"
              + card.getCardNumber()
              + " is not activated or linked to your account.");
    }
  }*/

  /**
   * Links Card to CardHolder's account. Prints out a message to inform the CardHolder whether the
   * card is linked to his/her account successfully.
   *
   * @param card the Card that CardHolder would like to link
   */
  public void linkCard(Card card) {
    if (!(card.getOwner() == null)) {
      // If card is currently linked to another CardHolder, it cannot be linked to this CardHolder.
      System.out.println("Action denied. This card is currently linked to another account.");
    } else {
      // link a valid card to this CardHolder
      this.travelCards.add(card);
      card.setOwner(this);
      card.linkAccount();
      System.out.println(
          "Card " + card.getCardNumber() + " linked to CardHolder Account " + this.getAccountNum());
    }
  }

  /**
   * Unlinks Card from CardHolder's account. Prints out a message to confirm the action is
   * completed.
   *
   * @param card the Card that CardHolder would like to unlink
   */
  public void unlinkCard(Card card) {
    this.travelCards.remove(card);
    card.setOwner(null);
    card.unlinkAccount();
    System.out.println(
        "Card " + card.getCardNumber() + " unlinked to CardHolder Account " + this.getAccountNum());
  }

  /**
   * Activates a Card that is linked to this CardHolder. Prints out a message to inform the
   * CardHolder whether it is successful.
   *
   * @param card the Card that CardHolder would like to activate
   */
  public void activateCard(Card card) {
    // CardHolder is able to activate a card that is linked to his/her account.
    if (this.travelCards.contains(card)) {
      card.activate();
      System.out.println("Card " + card.getCardNumber() + " activated");
    } else {
      System.out.println(
          "Action denied. Card" + card.getCardNumber() + " is not linked to your account");
    }
  }

  /**
   * Deactivates a Card that is linked to this CardHolder. Prints out a message to inform the
   * CardHolder whether it is successful.
   *
   * @param card the Card that CardHolder would like to deactivate
   */
  public void deactivateCard(Card card) {
    // CardHolder is able to deactivate a card that is linked to his/her account.
    if (this.travelCards.contains(card)) {
      card.deactivate();
      System.out.println("Card " + card.getCardNumber() + " deactivated");
    } else {
      System.out.println(
          "Action denied. Card" + card.getCardNumber() + " is not linked to your account");
    }
  }

  // public void getMonthlyCost(Integer month) {}

  /*public void viewRecentTrips(Card card) {
    if (this.travelCards.contains(card) && card.getStatus().equals("activated")) {
      card.viewMostRecentTrips();
    } else {
      System.out.println(
          "Action denied. Card"
              + card.getCardNumber()
              + " is not activated or linked to your account");
    }
  }*/

  /** Prints out information about the CardHolder and Card(s) linked to his/her account. */
  @Override
  // Will include cards linked to the CardHolder
  public void viewInfo() {
    System.out.println("Name: " + this.getName());
    System.out.println("Email: " + this.getEmail());
    System.out.println("Account Number: " + this.accountNumber);
    if (this.travelCards.isEmpty()) {
      System.out.println("Cards linked to your account: None.");
    } else {
      System.out.println("Cards linked to your account: ");
      for (Card card : this.travelCards) {
        System.out.println("     Card " + card.getCardNumber());
      }
    }
  }

  /** Prints out the average monthly cost of all Cards this CardHolder has. */
  void viewMonthlyCost() {
    double result = 0.0;
    for (Card c : travelCards) {
      result += c.getTotalFares();
    }
    System.out.println(result / travelCards.size());
  }
}
