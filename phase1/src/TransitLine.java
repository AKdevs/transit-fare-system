import java.util.ArrayList;

public class TransitLine {

  private ArrayList<String> points;
  private String type;

  public TransitLine(ArrayList<String> points, String type) {
    this.points = points;
    this.type = type;
  }

  public void setPoints(ArrayList<String> points) {
    this.points = points;
  }

  public ArrayList<String> getPoints() {
    return this.points;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getType() {
    return this.type;
  }

  public void changeName(String oldName, String newName) {
    if (!points.isEmpty()) {
      for (int i = 0; i < points.size(); i++) {
        if (points.get(i).equals(oldName)) {
          points.set(i, newName);
        }
      }
    }
  }

  public void addPoint(String beforenew, String newpoint, String afternew) {
    if (beforenew.equals("")) {
      points.add(0, newpoint);
    } else {
      if (afternew.equals("")) {
        points.add(newpoint);
      } else {
        for (int i = 0; i < points.size(); i++) {
          if (points.get(i).equals(beforenew)) {
            points.add(i + 1, newpoint);
          }
        }
      }
    }
  }
}
