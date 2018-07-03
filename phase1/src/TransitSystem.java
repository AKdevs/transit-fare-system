import java.util.ArrayList;

public class TransitSystem {

    private ArrayList<Card> cards;
    //[Card(123), Card(24), Card(678), .....]

    private ArrayList<CardHolder> userAccounts;
    //[CardHolder(1),CardHolder(2), CardHolder(3), .....]

    private ArrayList<TransitLine> transitLines;

    private ArrayList allFares; //print out

    private ArrayList numberOfStation; //print out


    void addCard(Card newCard){
        this.cards.add(newCard);
    }

    void removeCard(Card card){
        for (Card c: cards){
            if (c.equals(card)){
                cards.remove(c);
            }
        }
    }

    void addUserAccount(CardHolder newUser){
        this.userAccounts.add(newUser);
    }

    void removeUserAccount(CardHolder user){
        for (CardHolder holder: userAccounts){
            if (holder.equals(user)){
                userAccounts.remove(holder);
            }
        }
    }

    void addTransitLines(TransitLine newTransitLine){
        this.transitLines.add(newTransitLine);
    }

    void addFares(int fares){}

    void deductFares(int fares){}

    void addNumberOfStation(int newStation){}

    void deductNumberOfStation(int newStation){}


}




