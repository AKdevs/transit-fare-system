import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Level;

public class CardManager implements Serializable{
  /** Keeps a track of all cards in the system. */
  private ArrayList<Card> cards;

  /**
   * Construct a card manager
   */
  CardManager(){
    this.cards = new ArrayList<>();
  }

  /**
   * Finds and returns the card denoted by cardNumber, or null if not found.
   *
   * @param cardNumber number of card to be found
   * @return card denoted by CardNumber, or null if not found.
   */
  Card findCard(String cardNumber) {
    for (Card c : cards) {
      if (c.getCardNumber().equals(cardNumber)) {
        return c;
      }
    }
    return null;
  }

  /**
   * Get the last card stores in the card manager
   *
   * @return the last card
   */
  Card getLastCard() {
    return cards.get(cards.size() - 1);
  }

  /**
   * Adds newCard to list of existing cards.
   *
   * @param newCard new card to be added.
   */
  private void addCard(Card newCard) {

    int cardnum;
    if (cards.size() == 0){
      cardnum = 30000001;
    } else {
      int lastnumber;
      lastnumber = Integer.parseInt(cards.get(cards.size() -1).getCardNumber());
      cardnum = lastnumber + 1;
    }

    cards.add(newCard);
    newCard.setCardNumber(Integer.toString(cardnum));
  }

  /** Creates new card, card number is assigned automatically. */
  void createCard() {
    Card newCard = new Card();
    addCard(newCard);
    System.out.println("Card " + newCard.getCardNumber() + " created");
  }

  /**
   * Removes card and all information associated with it.
   *
   * @param cardNumber number of card to be removed.
   */
  void removeCard(String cardNumber) {
    cards.remove(findCard(cardNumber));
  }

  /**
   * Deletes old TripSegments for all Cards in CardManager, which occurred on date.
   * @param date TripSegments occurred on this date shall be deleted.
   */
  void deleteOldTrips(String date) {
    if (!(this.cards.isEmpty())) {
      for (Card card : this.cards) {
        card.deleteOldTrips(date);
      }
    }
  }

}
