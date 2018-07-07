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

    public void linkCard(Card card) {
        // if the card is not created in the system, return error message


        // link a valid card
        this.travelCards.add(card);
        card.setOwner(this);
        System.out.println("Card " + card.getCardNumber() + " is now linked with your account.");

    }

    public void deLinkCard(Card card) {
        this.travelCards.remove(card);
        System.out.println("Card " + card.getCardNumber() + " is removed from your account.");
    }

    //CardHolder is able to activate a card that is linked to his/her account.
    public void activateCard(Card card) {
        if (this.travelCards.contains(card)) {
            card.setStatus("activated");
            System.out.println("Card " + card.getCardNumber() + " has been activated successfully.");
        }
        else {
            System.out.println("Action denied. Card" + card.getCardNumber() + " is not linked to your account.");
        }
    }

    //CardHolder is able to deactivate a card that is linked to his/her account.
    public void deactivateCard(Card card) {
        if (this.travelCards.contains(card)) {
            card.setStatus("deactivated");
            System.out.println("Card " + card.getCardNumber() + " has been deactivated.");
        }
        else {
            System.out.println("Action denied. Card" + card.getCardNumber() + " is not linked to your account.");
        }
    }

    public void getMonthlyCost(Integer month){
        Card card = new Card();
        Double monthlycost =  card.getTotalFares().get(month);
        System.out.println("The total cost of " + month + "th month is " + monthlycost);
    }



}

