public class TripSegment {

  private String enterSpot;
  private String exitSpot;
  private String transitType;
  private String enterTime;
  private String exitTime;
  private String date;
  private String contiEnterS;
  private String contiExitS;
  private String contiEnterTime;
  private String contiExitTime;
  private String contiType;
  private int duration;
  private int timetracker; // duration in this trip + (enter time of next trip - exit time of this trip)
  private double fare;
  private double contiFare;
  private double fareTracker;

  public TripSegment(String enterSpot, String enterTime, String enterDate, String type){
    this.enterSpot = enterSpot;
    this.exitSpot = null;
    this.transitType = type;
    this.enterTime = enterTime;
    this.date = enterDate;
    this.contiEnterS = null;
    this.contiExitS = null;
    this.contiEnterTime = null;
    this.contiExitTime = null;
    this.duration = 0;
    this.timetracker = 0;
    this.fareTracker = 0;

  }

  boolean hasEnter(){

    if (transitType.equals("continue")){
      if(contiEnterS == null && contiExitS != null) {
        return false;
      }
    }
    else if (enterSpot == null && exitSpot!= null){
      return false;
    }
    return true;
  }

  boolean hasExit(){
    if (transitType.equals("continue")) {
      if (contiEnterS != null && contiExitS == null) {
        return false;
      }
    }
    else if(enterSpot != null && exitSpot == null){
      return false;
    }
    return true;
  }


  @Override
  public String toString() {
    String type;
    if (transitType.equals("B")) {
      type = "Bus";
    } else if(transitType.equals("S")){
      type = "Subway";
    } else{
      type = "Continue";
    }


    StringBuilder s =
        new StringBuilder(
            "Date: "
                + date
                + " - "
                + type
                +" trip "
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

  public void setContiFare(double contiFare) {
    this.contiFare = contiFare;
  }

  public double getContiFare() {
    return contiFare;
  }

  public double getFareTracker() {
    return fareTracker;
  }

  public void setFareTracker(double fareTracker) {
    this.fareTracker = fareTracker;
  }

  public String getTransitType() {
    return transitType;
  }

  public void setTimetracker(int timetracker) {
    this.timetracker = timetracker;
  }

  public int getTimetracker() {
    return timetracker;
  }

  public String getDate(){
    return date;
  }

  public String getEnterSpot() {

    return this.enterSpot;
  }

  public String getExitSpot() {

    return this.exitSpot;
  }

  public void setFare(double fare) {
    this.fare = fare;
  }

  public double getFare() {
    return fare;
  }

  public String getContiEnterS() {
    return contiEnterS;
  }

  public String getContiEnterTime() {
    return contiEnterTime;
  }

  public String getContiExitS() {
    return contiExitS;
  }

  public String getContiExitTime() {
    return contiExitTime;
  }


  public String getContiType() {
    return contiType;
  }

  public String getEnterTime() {

    return this.enterTime;
  }

  public String getExitTime() {

    return this.exitTime;
  }

  public int getDuration() {

    return this.duration;
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

  public void setContiEnterS(String contiEnterS) {
    this.contiEnterS = contiEnterS;
  }

  public void setContiEnterTime(String contiEnterTime) {
    this.contiEnterTime = contiEnterTime;
  }

  public void setContiExitS(String contiExitS) {
    this.contiExitS = contiExitS;
  }

  public void setContiExitTime(String contiExitTime) {
    this.contiExitTime = contiExitTime;
  }

  public void setContiType(String contiType) {
    this.contiType = contiType;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }
}