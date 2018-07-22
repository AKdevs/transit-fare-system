import java.util.ArrayList;

public class CardManager {
    /** Keeps a track of all cards in the system. */
    private ArrayList<Card> cards = new ArrayList<>();

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
     * Adds newCard to list of existing cards.
     *
     * @param newCard new card to be added.
     */
    private void addCard(Card newCard) {
        cards.add(newCard);
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
}
