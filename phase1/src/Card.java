import java.util.ArrayList;

public class Card {
    private static int nextCardNumber = 1;
    private int cardNumber;
    private int balance;
    private CardHolder owner;
    private String status;
    private int currentFare;
    private ArrayList<ArrayList<TripSegment>> trips; // key-value pair
    private int totalFares; // key-value pair, past 12 months
    private int currentTime;


    // registered = linked, different from "activate"

    public Card(String cardNumber){
        nextCardNumber += 1;
        this.cardNumber = nextCardNumber;
        this.owner = null;
        this.balance = 19;
        this.status = "deactivated";
        this.trips = new ArrayList<ArrayList<TripSegment>>(); // only keeps track of 3 most recent trips
        this.currentFare = 0;
        this.totalFares = 0;
        this.currentTime = 0;
    }


    private void addBalance(int value) {
        this.balance += value;
    }

    private void deductBalance(int value) {
        this.balance -= value;
    }

    private void deactivate(){
        this.status = "deactivated";
    }

    private void activate(){
        this.status = "activated";
    }

    void viewRecentTrips(){
        System.out.println(this.trips);
    }

    void printCardNumber(){
        System.out.println(this.cardNumber);
    }

    int getCardNumber(){
        return this.cardNumber;
    }

    void getBalance(){
        System.out.println(this.balance);
    }

    CardHolder getOwner(){return this.owner;}// do we print out this?

    void setOwner(CardHolder owner) {
        this.owner = owner;
    }

    String getStatus(){return this.status;}

    int getCurrentFare(){return this.currentFare;}

    private void setCurrentFare(int fare){
        this.currentFare = fare;
    }

    void updateTime(int time){}

    boolean equals(Card other){
        return this.cardNumber == other.getCardNumber();
    }


}
