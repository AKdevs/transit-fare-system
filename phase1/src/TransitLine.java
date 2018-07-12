import java.util.ArrayList;

class TransitLine {
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
