import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Level;

public class CardManager {
  /** Keeps a track of all cards in the system. */
  private ArrayList<Card> cards;

  CardManager(){
    this.cards = new ArrayList<>();

    try
    {
      // Reading the object from a file
      FileInputStream file = new FileInputStream("data-CardManager.bin");
      ObjectInputStream in = new ObjectInputStream(file);

      // Method for deserialization of object
      cards = (ArrayList<Card>)in.readObject();

      in.close();
      file.close();

      System.out.println("Object has been deserialized ");
    }

    catch(IOException ex)
    {
      System.out.println("IOException is caught");
    }

    catch(ClassNotFoundException ex)
    {
      System.out.println("ClassNotFoundException is caught");
    }
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


    try
    {
      //Saving of object in a file
      FileOutputStream file = new FileOutputStream("data-CardManager.bin");
      ObjectOutputStream out = new ObjectOutputStream(file);

      // Method for serialization of object
      out.writeObject(cards);

      out.close();
      file.close();

      System.out.println("Object has been serialized");

    }
    catch(IOException ex)
    {
      System.out.println("IOException is caught");
    }

  }

  /** Creates new card, card number is assigned automatically. */
  void createCard() {
    Card newCard = new Card();
    addCard(newCard);
    System.out.println("Card " + newCard.getCardNumber() + " created");
      TransitSystem.log(Level.ALL, "Card created!!!");
  }

  /**
   * Removes card and all information associated with it.
   *
   * @param cardNumber number of card to be removed.
   */
  void removeCard(String cardNumber) {
    cards.remove(findCard(cardNumber));
  }

  ArrayList<Card> getCards() {
      return cards;
  }
}
