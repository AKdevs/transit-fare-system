public class TripSegment {

  private String enterSpot;
  private String exitSpot;
  private String contiSpot;
  private String transitType; // ??
  private String enterTime;
  private String exitTime;
  private String date;
  private double currentFares;

  // private String contiExitS;
  // private String contiEnterTime;
  // private String contiExitTime;
  // private String contiType;
  // private int duration;
  // private int timetracker; // duration in this trip + (enter time of next trip - exit time of
  // this trip)
  // private double fare;
  // private double contiFare;
  // private double fareTracker;

  public TripSegment(String enterSpot, String enterTime, String enterDate, String type) {
    this.enterSpot = enterSpot;
    this.exitSpot = null;
    this.transitType = type;
    this.enterTime = enterTime;
    this.date = enterDate;
    this.contiSpot = "unknown";
  }

  // check whether it is illegal when they tap out
  boolean hasEnter() {
    return this.enterSpot != null;
  }

  boolean hasExit() {
    return this.exitSpot != null;
  }

  @Override
  public String toString() {
    String type;
    if (transitType.equals("B")) {
      type = "Bus";
    } else if (transitType.equals("S")) {
      type = "Subway";
    } else {
      type = "Continue";
    }

    StringBuilder s =
        new StringBuilder(
            "Date: "
                + date
                + " - "
                + type
                + " trip "
                + " - "
                + "Entered "
                + " at "
                + enterSpot
                + " at "
                + enterTime
                + "."
                + " Exited "
                + " at "
                + exitSpot
                + " at "
                + exitTime
                + "."
                + "\n");
    return s.toString();
  }

  public String getDate() {
    return date;
  }

  public String getEnterSpot() {

    return this.enterSpot;
  }

  public String getExitSpot() {

    return this.exitSpot;
  }

  public String getEnterTime() {

    return this.enterTime;
  }

  public String getExitTime() {

    return this.exitTime;
  }

  public String getContiSpot() {
    return contiSpot;
  }

    public String getTransitType() {
        return transitType;
    }

    public double getCurrentFares() {
        return currentFares;
    }

    public void setEnterSpot(String enterSpot) {
    this.enterSpot = enterSpot;
  }

  public void setDate(String enterDate) {
    this.date = enterDate;
  }

  public void setEnterTime(String enterTime) {
    this.enterTime = enterTime;
  }

  public void setExitSpot(String exitSpot) {
    this.exitSpot = exitSpot;
  }

  public void setExitTime(String exitTime) {
    this.exitTime = exitTime;
  }

  public void setTransitType(String transitType) {
    this.transitType = transitType;
  }

  public void setContiSpot(String contiSpot) {
    this.contiSpot = contiSpot;
  }
  }

