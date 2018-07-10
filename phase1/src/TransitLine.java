import java.util.ArrayList;

public class TransitLine {

  private ArrayList<String> points;
  private String type;
  private String id;

  public TransitLine(ArrayList<String> points, String type, String id) {
    this.points = points;
    this.type = type;
    this.id = id;
  }

  /**
   * Sets the locations and order of the stops/station
   * in this transit line.
   * @param points ArrayList of String for
   *               locations of stops/stations
   */
  public void setPoints(ArrayList<String> points) {
    this.points = points;
  }

  /**
   * Returns the list of stops/stations.
   * @return list of stops/stations.
   */
  public ArrayList<String> getPoints() {
    return this.points;
  }

  /**
   * Sets the type of transport ("B" - bus, "S" - subway)
   * of this transit line
   * @param type of transit line
   */
  public void setType(String type) {
    this.type = type;
  }

  public String getType() {
    return this.type;
  }

  public String getId() {
    return this.id;
  }

  //should the two method under stay in TransitLine Class???
  public void changeName(String oldName, String newName) {
    if (!points.isEmpty()) {
      for (int i = 0; i < points.size(); i++) {
        if (points.get(i).equals(oldName)) {
          points.set(i, newName);
        }
      }
    }
  }

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
