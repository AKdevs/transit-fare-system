import java.util.ArrayList;
import java.util.HashMap;

public class CardHolder extends UserAccount {

  private ArrayList<Card> travelCards;
  private static int nextAccountNum = 10000001;

  public CardHolder(String name, String email) {
    super(name, email);
    this.accountNumber = nextAccountNum;
    nextAccountNum += 1;
    this.travelCards = new ArrayList<>();
  }

  public void addbalance(int cardnumber, Double amount){
    //cardholder can only addbalance in card which is linked to their account
    //the initial status of a card is set to "activated",  which means people can use a card right after they buy it, they can use the balance stored in the
    //card to tapIn and tapOut. but if they want to view balance, add balance, they have to get the card linked to their account
    // a new card cannot do anything except for enter and exit the stations
    //in eventhandler: try....catch{your card is not linked to your account}
    for (Card card : travelCards) {
      if (card.getCardNumber() == cardnumber) {
        if (card.getStatus().equals("activated")) {
          double balance = card.getBalance() + amount;
          card.setBalance(balance);
        }
        else{
          System.out.println("The card is not activated.");
        }
      }
    }
  }

  public void viewBalance(int cardnumber){
    //in eventhandler: try....catch{your card is not linked to your account}
    for (Card card : travelCards) {
      if (card.getCardNumber() == cardnumber) {
        if (card.getStatus().equals("activated")) {
          System.out.println("Card balance: " + card.getBalance());
        }
        else{
          System.out.println("The card is not activated.");
        }
      }
    }
  }



  public void linkCard(Card card) {
    // if the card is not created in the system, return error message

    // link a valid card
    this.travelCards.add(card);
    card.setOwner(this);
    card.setLinked();
    System.out.println("Card " + card.getCardNumber() + " is now linked with your account.");
  }

  public void deLinkCard(Card card) {
    this.travelCards.remove(card);
    card.setOwner(null);
    card.setUnlinked();
    System.out.println("Card " + card.getCardNumber() + " is removed from your account.");
  }

  // CardHolder is able to activate a card that is linked to his/her account.
  public void activateCard(Card card) {
    if (this.travelCards.contains(card)) {
      card.setActivated();
      System.out.println("Card " + card.getCardNumber() + " has been activated successfully.");
    } else {
      System.out.println(
          "Action denied. Card" + card.getCardNumber() + " is not linked to your account.");
    }
  }

  // CardHolder is able to deactivate a card that is linked to his/her account.
  public void deactivateCard(Card card) {
    if (this.travelCards.contains(card)) {
      card.setDeactivated();
      System.out.println("Card " + card.getCardNumber() + " has been deactivated.");
    } else {
      System.out.println(
          "Action denied. Card" + card.getCardNumber() + " is not linked to your account.");
    }
  }

  public void getMonthlyCost(Integer month) {
    Card card = new Card();
    Double monthlycost = card.getTotalFares().get(month);
    System.out.println("The total cost of " + month + "th month is " + monthlycost);
  }
}
