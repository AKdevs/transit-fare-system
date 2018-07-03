import java.util.ArrayList;

public class TransitSystem {

    private ArrayList<Card> cards;
    //[Card(123), Card(24), Card(678), .....]

    private ArrayList userAccounts;
    //[cardHolder(1),cardHolder(2), cardHolder(3), .....]

    private ArrayList<TransitLines> transitLines;

    private ArrayList allFares; //print out

    private ArrayList numberOfStation; //print out


    void addCard(Card newCard){
        this.cards.add(newCard);
    }

    void removeCard(Card card){
        for (Card c: cards){
            if(c.getCardNumber()card.getCardNumber()){

            }
        }

    }

    void addUserAccount(CardHolder newUser){}

    void removeUserAccount(CardHolder user){}

    void addTransitLines(ArrayList newTransitLine){}

    void addFares(int fares){}

    void deductFares(int fares){}

    void addNumberOfStation(int newStation){}

    void deductNumberOfStation(int newStation){}


}




