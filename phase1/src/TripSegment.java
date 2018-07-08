public class TripSegment {

  private int associatedCard; // associated card number
  private String enterSpot;
  private String exitSpot;
  private String enterTransitType;
  private String exitTransitType;
  private String enterTime;
  private String exitTime;
  private String enterDate;
  private String exitDate;
  private int duration;
  private double segmentFares = 0.0;

  public TripSegment(String cardNumber, String enterSpot, String transitType, String enterTime, String enterDate) {
    this.associatedCard = Integer.parseInt(cardNumber);
    this.enterSpot = enterSpot;
    this.enterTransitType = transitType;
    this.enterTime = enterTime;
    this.enterDate = enterDate;
    calculateTripSegmentFares(this.enterTransitType);
  }

  public void completeTripSegment(String exitSpot, String transitType, String exitTime, String exitDate) {
    this.exitSpot = exitSpot;
    this.exitTransitType = transitType;
    this.exitTime = exitTime;
    this.exitDate = exitDate;
    calculateDuration(this.enterTime, this.exitTime);
    calculateTripSegmentFares(this.exitTransitType);
  }

  private void calculateDuration(String enterTime, String exitTime) {
    // int version of enterTime: Hour converted to minutes + minutes
    int enter =
        Integer.parseInt(this.enterTime.substring(0, 2)) * 60
            + Integer.parseInt(this.enterTime.substring(3, 5));
    // int version of exitTime: Hour converted to minutes + minutes
    int exit =
        Integer.parseInt(this.exitTime.substring(0, 2)) * 60
            + Integer.parseInt(this.exitTime.substring(3, 5));
    this.duration = exit - enter;
  }

  private void calculateTripSegmentFares(String transitType) {
      if (this.enterTransitType.equals("B")) {
          this.segmentFares = 2.0;
      }else if (this.exitTransitType.equals("s")) {
          this.segmentFares = TransitSystem.calculateSubwayFares(this);
      }
  }

  public int getAssociatedCard() {
        return associatedCard;
    }

  public String getExitSpot() { return this.exitSpot; }

  public String getEnterTransitType() {
    return this.enterTransitType;
  }

  public String getExitTransitType() {
    return this.exitTransitType;
  }

  public String getEnterTime() { return this.enterTime; }

  public String getExitTime() { return this.exitTime; }

  public int getDuration() { return this.duration; }

  public String getEnterSpot() { return this.enterSpot; }

  public String getEnterDate() { return this.enterDate;}

  public String getExitDate() { return this.exitDate;}

  public double getSegmentFares() { return this.segmentFares; }

  public String getMonth() { return this.enterDate.substring(5, 7);}
}
