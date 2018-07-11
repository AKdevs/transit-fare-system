import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/** Manage the transit system. */
public class TransitManager extends TransitSystem {

  /** Constructs a transit system manager. */
  TransitManager() {
    defaultSetup();
  }

  /** Sets up transit lines in transit system. */
  private void defaultSetup() {
    ArrayList<String> line1Stations =
        new ArrayList<>(
            Arrays.asList("Finch", "North York Centre", "Sheppard-Yonge", "King", "Bay", "Bloor"));
    ArrayList<String> route1Stops =
        new ArrayList<>(
            Arrays.asList("Dufferin", "Bathurst", "Sheppard-Yonge", "Bayview", "Leslie"));
    TransitLine line1 = createTransitLine(line1Stations, "S");
    TransitLine route1 = createTransitLine(route1Stops, "B");
    addTransitLine(line1.getId(), line1);
    addTransitLine(route1.getId(), route1);
  }

  /**
   * Creates a new transit line in the transit system.
   *
   * @param points stations or stops in the new transit line
   * @param type type of the new transit line
   * @return the new transit line
   */
  public TransitLine createTransitLine(ArrayList<String> points, String type) {
    return new TransitLine(points, type, "line1");
  }

  /**
   * Adds the new transit line to the transit system
   *
   * @param name id of the new transit line
   * @param line new transit line
   */
  public void addTransitLine(String name, TransitLine line) {
    transitLines.put(name, line);
  }
}
