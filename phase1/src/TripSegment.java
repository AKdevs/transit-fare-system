public class TripSegment {

    private int associatedCard; //associated card number
    private String enterSpot;
    private String exitSpot;
    private String transitType;
    private String enterTime;
    private String exitTime;
    private int duration;

    public TripSegment(String cardNumber, String enterSpot, String transitType, String enterTime){
        this.associatedCard = Integer.parseInt(cardNumber);
        this.enterSpot = enterSpot;
        this.transitType = transitType;
        this.enterTime = enterTime;
    }

    public void recordTapOut(String exitSpot, String transitType, String exitTime){
        this.exitSpot = exitSpot;
        this.transitType = transitType;
        this.exitTime = exitTime;
        calculateDuration(this.enterTime, this.exitTime);
        // add TripSegment to trips in Card (Find card first)
    }

    private void calculateDuration(String enterTime, String exitTime){
        // int version of enterTime: Hour converted to minutes + minutes
        int enter = Integer.parseInt(this.enterTime.substring(0, 2)) * 60 + Integer.parseInt(this.enterTime.substring(3, 5));
        // int version of exitTime: Hour converted to minutes + minutes
        int exit = Integer.parseInt(this.exitTime.substring(0, 2)) * 60 + Integer.parseInt(this.exitTime.substring(3, 5));
        this.duration = exit - enter;
    }

    public int getAssociatedCard(){return this.associatedCard;}

    public String getEnterSpot(){return this.enterSpot;}

    public String getExitSpot(){return this.exitSpot;}

    public String getTransitType(){return this.transitType;}

    public String getEnterTime(){return this.enterTime;}

    public String getExitTime(){return this.exitTime;}

    public int getDuration(){return this.duration;}


}
