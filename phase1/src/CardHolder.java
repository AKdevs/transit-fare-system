import java.util.ArrayList;
import java.util.HashMap;

public class CardHolder extends UserAccount {

    private ArrayList<Card> travelCards;
    private static int nextAccountNum = 1;

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

    public void getMonthlyCost(Integer month){
        Card card = new Card();
        Double monthlycost =  card.getTotalFares().get(month);
        System.out.println("The total cost of " + month + "th month is " + monthlycost);
    }



}

