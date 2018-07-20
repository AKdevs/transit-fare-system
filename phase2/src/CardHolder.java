import java.util.ArrayList;

/**
 * CardHolder is a person who owns and uses a Card, ie a passenger who travels in the TransitSystem.
 * The person is able to link Cards to his/her account and view his/her travel history.
 */
public class CardHolder extends UserAccount {

  private ArrayList<Card> travelCards;
  /* Account Number for CardHolder starts at 10000001 to distinguish with other account/card numbers.*/
  private static int nextAccountNum = 10000001;

  CardHolder(String name, String email) {
    super(name, email);
    this.accountNumber = Integer.toString(nextAccountNum);
    nextAccountNum += 1;
    this.travelCards = new ArrayList<>();
  }

  public void enter(String time,String spot, String cardNumber,String date, String type){

  }

  public void exit(String time,String spot,String cardNumber) {

  }


  /**
   * Links card to this account. Prints out a message to inform the CardHolder whether the card is
   * linked to his/her account successfully.
   *
   * @param card the card that is to be linked
   */
  void linkCard(Card card) {
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
   * Unlinks card from this account. Prints out a message to confirm the action is completed.
   *
   * @param card the card that is to be unlinked
   */
  void unlinkCard(Card card) {
    this.travelCards.remove(card);
    card.setOwner(null);
    card.unlinkAccount();
    System.out.println(
        "Card " + card.getCardNumber() + " unlinked to CardHolder Account " + this.getAccountNum());
  }

  /**
   * Activates card that is linked to this account. Prints out a message to inform the the user
   * whether it is successful.
   *
   * @param card the card that is to be activated
   */
  void activateCard(Card card) {
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
   * Deactivates a card that is linked to this account. Prints out a message to inform the user
   * whether it is successful.
   *
   * @param card the Card that CardHolder would like to deactivate
   */
  void deactivateCard(Card card) {
    // CardHolder is able to deactivate a card that is linked to his/her account.
    if (this.travelCards.contains(card)) {
      card.deactivate();
      System.out.println("Card " + card.getCardNumber() + " deactivated");
    } else {
      System.out.println(
          "Action denied. Card" + card.getCardNumber() + " is not linked to your account");
    }
  }

  /** Prints out information about the account and card(s) linked to his/her account. */
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

  /** Prints out the average monthly cost of all cards this account has. */
  void viewMonthlyCost() {
    double result = 0.0;
    for (Card c : travelCards) {
      result += c.getTotalFares();
    }
    System.out.println(
        "Account " + accountNumber + " cost for the month: " + result / travelCards.size());
  }
}
