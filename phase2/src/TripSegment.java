import java.io.Serializable;

/**
 * TripSegment is the full trip with its continue trips
 */
public class TripSegment implements Serializable{

  /**
   * Enter spot of a trip
   */
  private String enterSpot;
  /**
   * Exit spot of a trip
   */
  private String exitSpot;
  /**
   * Continue enter spot of a Trip
   */
  private String contiSpot;
  /**
   * Type of the transit
   */
  private String transitType;
  /**
   * Enter time of a trip
   */
  private String enterTime;
  /**
   * Exit time of a trip
   */
  private String exitTime;
  /**
   * Date of the trip
   */
  private String date;
  /**
   * Current fare cost of the trip
   */
  private double currentFares;

  /**
   * Construct a tripSegment
   * @param enterSpot enter spot
   * @param enterTime enter time
   * @param enterDate enter date
   * @param type type of trip
   */
  public TripSegment(String enterSpot, String enterTime, String enterDate, String type) {
    this.enterSpot = enterSpot;
    this.exitSpot = null;
    this.transitType = type;
    this.enterTime = enterTime;
    this.date = enterDate;
    this.contiSpot = null;
    this.currentFares = 0.0;
  }

  // check whether it is illegal when they tap out
  boolean hasEnter() {
    return this.enterSpot != null;
  }

  /**
   * Checkes whether the trip is completed with tap out
   *
   *
   * @return true if there is an exit; false if there is not an exit
   */
  boolean hasExit() {
    return this.exitSpot != null;
  }

  /**
   * Creates the String contains the information of the trip segment
   *
   *
   * @return trip segment information
   */
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

  /**
   * Gets the date of trip
   *
   *
   * @return date
   */
  public String getDate() {
    return date;
  }

  /**
   * Get the enter spot of the trip
   *
   *
   * @return enter spot
   */
  public String getEnterSpot() {

    return this.enterSpot;
  }

  /**
   * Get the exit spot of the trip
   *
   *
   * @return exit spot
   */
  public String getExitSpot() {

    return this.exitSpot;
  }

  /**
   * Get the enter time of the trip
   *
   *
   * @return enter time
   */
  public String getEnterTime() {

    return this.enterTime;
  }

  /**
   * Get the continue enter spot of the trip
   *
   *
   * @return continue enter spot
   */
  public String getContiSpot() {
    return contiSpot;
  }

  /**
   * Get the type of transit
   *
   *
   * @return transit type
   */
  public String getTransitType() {
    return transitType;
  }

  /**
   * Gets the current fare cost of the trip
   *
   *
   * @return current fares
   */
  public double getCurrentFares() {
    return currentFares;
  }

  /**
   * Sets the Date of the trip
   *
   *
   * @param enterDate enter date of the trip
   */
  public void setDate(String enterDate) {
    this.date = enterDate;
  }

  /**
   * Set the exit spot of the trip
   *
   *
   * @param exitSpot exit spot
   */
  public void setExitSpot(String exitSpot) {
    this.exitSpot = exitSpot;
  }

  /**
   * Sets the exit time of the trip
   *
   *
   * @param exitTime exit time of the trip
   */
  public void setExitTime(String exitTime) {
    this.exitTime = exitTime;
  }

  /**
   * Set the type of transit
   *
   *
   * @param transitType transit type
   */
  public void setTransitType(String transitType) {
    this.transitType = transitType;
  }

  /**
   * Set the continue enter spot of the trip
   *
   * @param contiSpot continue enter spot
   */
  public void setContiSpot(String contiSpot) {
    this.contiSpot = contiSpot;
  }

  /**
   * Sets the current fares of the trip
   *
   *
   * @param currentFares current fares
   */
  public void setCurrentFares(double currentFares) {
    this.currentFares = currentFares;
  }
}