import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * CardHolder is a person who owns and uses a Card, ie a passenger who travels in the TransitSystem.
 * The person is able to link Cards to his/her account and view his/her travel history.
 */
public class CardHolder extends UserAccount implements Serializable{

  private ArrayList<Card> travelCards;
  /* Account Number for CardHolder starts at 10000001 to distinguish with other account/card numbers.*/


    private double accountBalance;

    private int autoLoadStatus;

    private final double autoLoadLimit = 10.0;


    CardHolder(String name, String email, String password) {
    super(name, email, password);
    this.travelCards = new ArrayList<>();
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
      TransitSystem.log(Level.ALL, "Action denied. Card " + card.getCardNumber() + " is currently linked to another account.");
    } else if (!card.isLinked()) {
      // link a valid card to this CardHolder
      this.travelCards.add(card);
      card.setOwner(this);
      card.linkAccount();
      TransitSystem.log(Level.ALL,
          "Card " + card.getCardNumber() + " linked to CardHolder Account " + this.getAccountNum());
    }
  }

  /**
   * Unlinks card from this account. Prints out a message to confirm the action is completed.
   *
   * @param card the card that is to be unlinked
   */
  void unlinkCard(Card card) {
    if (travelCards.contains(card) && card.isLinked()) {
      this.travelCards.remove(card);
      card.setOwner(null);
      card.unlinkAccount();
      TransitSystem.log(Level.ALL,
          "Card "
              + card.getCardNumber()
              + " unlinked to CardHolder Account "
              + this.getAccountNum());
    }
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
      TransitSystem.log(Level.ALL,"Card " + card.getCardNumber() + " activated");
    } else {
      TransitSystem.log(Level.ALL,
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
      TransitSystem.log(Level.ALL,"Card " + card.getCardNumber() + " deactivated");
    } else {
      TransitSystem.log(Level.ALL,
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
  double getMonthlyCost() {
      double result = 0.0;
      if (travelCards.size() == 0) {
          return result;
    } else {
      for (Card c : travelCards) {
        result += c.getTotalFares();
      }
    }
    return result / travelCards.size();
  }

  ArrayList<Card> getTravelCards() {
      return this.travelCards;
  }


  String transferBalance(Card card1, Card card2, double amount) {
      if (amount <= card1.getBalance() && amount >= 0) {
          card1.deductBalance(amount);
          card2.addBalance(amount);
          TransitSystem.log(Level.ALL, Double.toString(amount) + "transfered from Card " + card1.getCardNumber() +" to " + card2.getCardNumber());
          if (card1.getBalance() < autoLoadLimit && autoLoadStatus == 1) {
              autoLoad(card1);
          }
      }else if (amount > card1.getBalance()){
          TransitSystem.log(Level.ALL,"Your balance in Card "  + card1.getCardNumber()  + " is not enough.");
          return "Your balance in Card \n"  + card1.getCardNumber()  + " is not enough.";
      }else if (amount < 0) {
          TransitSystem.log(Level.ALL,"Entered negative number to transfer balance for Card "  + card1.getCardNumber());
          return "Please type in \n a positive number";
      }
      return "Transfer balance \n succeed";
  }

    int getAutoLoadStatus() {
        return autoLoadStatus;
    }

    void setAutoLoadStatus(int status) {
        autoLoadStatus = status;
    }

    double getAccountBalance() {
        return accountBalance;
    }

    void addAccountBalance(double amount) {
        accountBalance += amount;
    }

    void deductAccountBalance(double amount) {
        accountBalance -= amount;
    }

    void autoLoad(Card card) {
        double difference = autoLoadLimit - card.getBalance();
        if (accountBalance >= difference && this.autoLoadStatus == 1) {
            deductAccountBalance(difference);
            card.addBalance(difference);
            TransitSystem.log(Level.ALL, Double.toString(difference) + " autoloaded to " + card.getCardNumber());
        }
    }

    double getAutoLoadLimit() {
      return this.autoLoadLimit;
    }

}
