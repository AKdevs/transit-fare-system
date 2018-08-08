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

  /**
   * stores all the travel cards that belongs to the card holder
   */
  private ArrayList<Card> travelCards;
  /**
   * The account balance for the card holder
   */
  private double accountBalance;
  /**
   * The status of auto load balance: it is 1 if the cardHolder turned it on,
   * 0 if the cardHolder turned it off
   */
  private int autoLoadStatus;
  /**
   * The limit amount of money that is allowed for auto load is 10.0 dollar
   */
  private final double autoLoadLimit = 10.0;


  /**
   * Constructs a card holder
   *
   * @param name name of card holder account
   * @param email email of card holder account
   * @param password password for the card holder account
   */
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

  /**
   * Gets all the travel cards that belongs to the card holder
   * @return list of cards
   */
  ArrayList<Card> getTravelCards() {
      return this.travelCards;
  }


  /**
   * Transfers amount of money from card 1 to card 2.
   *
   * @param card1 transfer money form card1
   * @param card2 transfer money to card2
   * @param amount the amount of money that wanted to be transferred
   * @return message of transferring action
   */
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

  /**
   * Gets the status of auto load
   *
   * @return 1 if the card holder turns it on, 0 if the card holder turns it off
   */
    int getAutoLoadStatus() {
        return autoLoadStatus;
    }

  /**
   * Sets the auto load status
   *
   * @param status 1 if the card holder turns it on, 0 if the card holder turns it off
   */
  void setAutoLoadStatus(int status) {
        autoLoadStatus = status;
  }

  /**
   * Gets the account balance
   *
   * @return account balance
   */
  double getAccountBalance() {
    return accountBalance;
  }

  /**
   * Adds account balance
   *
   * @param amount amount of money that is wanted to be added
   */
  void addAccountBalance(double amount) {
    accountBalance += amount;
  }

  /**
   * Deducts the account balance
   *
   * @param amount amount of money that is going to be deducted
   */
  void deductAccountBalance(double amount) {
    accountBalance -= amount;
  }

  /**
   * Auto-loads money to the card that is belongs to the card holder
   *
   * @param card related card
   */
  void autoLoad(Card card) {
    double difference = autoLoadLimit - card.getBalance();
    if (accountBalance >= difference && this.autoLoadStatus == 1) {
      deductAccountBalance(difference);
      card.addBalance(difference);
      TransitSystem.log(Level.ALL, Double.toString(difference) + "$ is autoloaded to " + card.getCardNumber());
      }
  }

  /**
   * Gets the limit of auto-load
   *
   * @return auto-load limit
   */
  double getAutoLoadLimit() {
    return this.autoLoadLimit;
  }

}
