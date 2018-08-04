import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

class TransitLine implements Serializable {
  /** Specifies the stops/stations on the transit line, in order */
  private ArrayList<String> points;
  /** Specifies the type of transit line. "B" - bus, "S" - subway */
  private String type;
  /** Identifier */
  private String id;
  /** Number of Stops/Stations in a single-way trip */
  private int numOfStops;
  /** Stores the number of single-way trips scheduled for the TransitLine on a specific date */
  private HashMap<String, Integer> numOfTrips;


  TransitLine(ArrayList<String> points, String type, String id) {
    this.points = points;
    this.type = type;
    this.id = id;
    this.numOfStops = points.size();
  }

  /** @param points ArrayList of String for locations of stops/stations */
  void setPoints(ArrayList<String> points) {
    this.points = points;
  }

  /** @return list of stops/stations. */
  ArrayList<String> getPoints() {
    return this.points;
  }

  /** @param type of transit line ("B" - bus, "S" - subvway) */
  void setType(String type) {
    this.type = type;
  }

  /** @return type of transit line */
  String getType() {
    return this.type;
  }

  /** @return id of transit line */
  String getId() {
    return this.id;
  }

  /**
   *
   * @return number of stops/stations of the Transit Line
   */
  int getNumOfStops() {return this.numOfStops; }

  /**
   *
   * @param date date
   * @return the number of single-way trips scheduled for the Transit Line on that date
   */
  public int getNumOfTrips(String date) {
    return numOfTrips.get(date);
  }

  /**
   * sets the number of single-way trips for date
   * @param date date
   * @param numOfTrips number of single-way trips scheduled for this Transit Line
   */
  public void setNumOfTrips(String date, int numOfTrips) {
    this.numOfTrips.put(date,numOfTrips);
  }

  /**
   * Change name of a station/stop in the transit line from oldName to newName.
   *
   * @param oldName the name to be changed.
   * @param newName the new name.
   */
  void changePointName(String oldName, String newName) {
    if (!points.isEmpty()) {
      for (int i = 0; i < points.size(); i++) {
        if (points.get(i).equals(oldName)) {
          points.set(i, newName);
        }
      }
    }
  }

  /**
   * Adds a station/stop called newPoint after pointBefore. If pointBefore is "", then adds newPoint
   * to the front.
   *
   * @param pointBefore The station/stop before the point to be added.
   * @param newPoint The station/stop to be added.
   */
  void addPoint(String pointBefore, String newPoint) {
    if (pointBefore.equals("")) {
      points.add(0, newPoint);
    } else {
      for (int i = 0; i < points.size(); i++) {
        if (points.get(i).equals(pointBefore)) {
          points.add(i + 1, newPoint);
        }
      }
    }
  }
}
