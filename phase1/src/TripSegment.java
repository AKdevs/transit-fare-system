public class TripSegment {
  /** Stores the card number of card associated with this trip segment.   */
  private String associatedCard;
  /** Stores the location (stop/station) of entry  */
  private String enterSpot;
  /** Stores the location (stop/station) of exit */
  private String exitSpot;
  /** Stores the type (bus/subway) of entry  */
  private String enterTransitType;
  /** Stores the location (bus/subway) of exit  */
  private String exitTransitType;
  /** Stores the time of entry  */
  private String enterTime;
  /** Stores the time of exit  */
  private String exitTime;
  /** Stores the date of entry  */
  private String enterDate;
  /** Stores the date of exit  */
  private String exitDate;
  /** Stores the duration of the trip (minutes)   */
  private int duration;
  /** Accumulates the total fare of the segment  */
  private double segmentFares = 0.0;
  /** stores whether TripSegment is a continued subway TripSegment */
  private boolean contiSub;

  public TripSegment(
      String cardNumber, String enterSpot, String transitType, String enterTime, String enterDate) {
    this.associatedCard = cardNumber;
    this.enterSpot = enterSpot;
    this.enterTransitType = transitType;
    this.enterTime = enterTime;
    this.enterDate = enterDate;
    this.exitSpot = "unknown";
    this.exitTransitType = "unknown";
    this.exitTime = "unknown";
    this.exitDate = "unknown";
    this.contiSub = false;
  }

  /**
   * Updates the exit information of the trip segment, thereby
   * completing it.
   * @param exitSpot Station/stop of exit
   * @param transitType Type of transport (bus or subway)
   * @param exitTime Time of exit
   * @param exitDate Date of exit
   */
  public void completeTripSegment(
      String exitSpot, String transitType, String exitTime, String exitDate) {
    this.exitSpot = exitSpot;
    this.exitTransitType = transitType;
    this.exitTime = exitTime;
    this.exitDate = exitDate;
  }


  /** @return Card number of associated card */
  public String getAssociatedCard() {
    return this.associatedCard;
  }

  /** @return stop/station of exit */
  public String getExitSpot() {
    return this.exitSpot;
  }

  /** @return type of transport of entry (bus/subway) */
  public String getEnterTransitType() {
    return this.enterTransitType;
  }

  /** @return type of transport of exit (bus/subway) */
  public String getExitTransitType() {
    return this.exitTransitType;
  }

  /** @return Returns time of entry in HH:MM format
   */
  public String getEnterTime() {
    return this.enterTime;
  }

  /** @return Returns time of exit in HH:MM format */
  public String getExitTime() {
    return this.exitTime;
  }

  /** @return duration of trip segment. */
  public int getDuration() {
    return this.duration;
  }

  /** @param duration of the trip segment. */
  public void setDuration(int duration) {
    this.duration = duration;
  }

  /** @return stop/station of entry. */
  public String getEnterSpot() {
    return this.enterSpot;
  }

  /** @return date of entry in YY-MM-DD format. */
  public String getEnterDate() {
    return this.enterDate;
  }

  /** @return date of exit. */
  public String getExitDate() {
    return this.exitDate;
  }

  /** @return total fare for this segment. */
  public double getSegmentFares() {
    return this.segmentFares;
  }

  /** @param fares amount of money accumulated for this segment. */
  public void setSegmentFares(double fares) {
    this.segmentFares = fares;
  }

  /** @return a string representation of the trip segment. */
  @Override
  public String toString() {
    String entryType;
    String exitType;
    if (enterTransitType.equals("B")) {
      entryType = "bus";
    } else {
      entryType = "subway";
    }

    if (exitTransitType.equals("B")) {
      exitType = "bus";
    } else {
      exitType = "subway";
    }

    StringBuilder s =
        new StringBuilder(
            "Date: "
                + this.enterDate
                + " - "
                + "Entered "
                + entryType
                + " at "
                + enterSpot
                + " at "
                + enterTime
                + "."
                + " Exited "
                + exitType
                + " at "
                + exitSpot
                + " at "
                + exitTime
                + "."
                + "\n");
    return s.toString();
  }

  /** Returns true iff subway trip is continuous
   * @return true iff subway trip is continous
   */
  boolean getContiSub() {
      return this.contiSub;
  }

  /** @param b status of whether subway trip is continuous   */
  void setContiSub(boolean b) {
      this.contiSub = b;
  }
}
