import java.util.ArrayList;

public class TransitSystem {

    private ArrayList<Card> cards;
    //[Card(123), Card(24), Card(678), .....]

    private ArrayList<UserAccount> userAccounts;
    //[CardHolder(1),CardHolder(2), CardHolder(3), .....]

    private ArrayList<TransitLine> transitLines;

    static int allFares; //print out key-value pair?

    static int numberOfStation; //print out


    Card findCard(int cardNumber){
        for (Card c: cards){
            if (c.getCardNumber() == cardNumber){
                return c;
            }
        }
        return null;
    }
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

    void addUserAccount(UserAccount newUser){
        this.userAccounts.add(newUser);
    }

    void removeUserAccount(UserAccount user){
        for (UserAccount u: userAccounts){
            if (u.equals(user)){
                userAccounts.remove(u);
            }
        }
    }

    void addTransitLines(TransitLine newTransitLine){
        this.transitLines.add(newTransitLine);
    }

    void addFares(int fares){}

    void deductFares(int fares){}

    static int getAllFares(){
        return allFares;
    }

    static int getNumberOfStation(){
        return getNumberOfStation();
    }

    void addNumberOfStation(int newStation){}

    void deductNumberOfStation(int newStation){}

    ArrayList getCards() {
        return this.cards;
    }


}




