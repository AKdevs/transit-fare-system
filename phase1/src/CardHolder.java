import java.util.ArrayList;

public class CardHolder extends UserAccount {

    private ArrayList<Integer> travelCards;
    private static int nextAccountNum = 1;

    public CardHolder(String name, String email) {
        super(name, email);
        this.accountNumber = nextAccountNum;
        nextAccountNum += 1;
        this.travelCards = new ArrayList<>();
    }

    public void linkCard(Card card) {

    }

    public void deLinkCard(Card card) {

    }

    public void  getMonthlyCost(){

    }





}

