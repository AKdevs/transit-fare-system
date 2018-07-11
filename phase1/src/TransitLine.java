import java.util.ArrayList;

public class TransitLine {
  /** Specifies the stops/stations on the transit line, in order   */
  private ArrayList<String> points;
  /** Specifies the type of transit line. "B" - bus, "S" - subway   */
  private String type;
  /** Identifier   */
  private String id;

  TransitLine(ArrayList<String> points, String type, String id) {
    this.points = points;
    this.type = type;
    this.id = id;
  }

  /**
   * Sets the locations and order of the stops/station in this transit line.
   *
   * @param points ArrayList of String for locations of stops/stations
   */
  public void setPoints(ArrayList<String> points) {
    this.points = points;
  }

  /**
   * Returns the list of stops/stations.
   *
   * @return list of stops/stations.
   */
  public ArrayList<String> getPoints() {
    return this.points;
  }

  /**
   * Sets the type of transport ("B" - bus, "S" - subway) of this transit line
   *
   * @param type of transit line
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Returns the type of the transit line.
   *
   * @return type of transit line
   */
  public String getType() {
    return this.type;
  }

  /**
   * Returns the ID of the transit line.
   *
   * @return id of transit line
   */
  String getId() {
    return this.id;
  }

  /**
   * Change name of a station/stop in the transit line from oldName
   * to newName.
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
   * Adds a station/stop called newPoint after pointBefore. If
   * pointBefore is "", then adds newPoint to the front.
   * @param pointBefore The station/stop before the point to be added.
   * @param newPoint The station/stop to be added.
   */
  public void addPoint(String pointBefore, String newPoint) {
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
