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
            Arrays.asList("Finch", "North York Centre", "Sheppard-Yonge", "York Mills", "Lawrence", "Eglinton", "Davisville","St Clair", "Summerhill", "Rosedale", "Bloor-Yonge", "Wellesley", "College", "Dundas", "Queen", "King", "Union", "St Andrew", "Osgoode", "St Patrick"));
    ArrayList<String> route1Stops =
        new ArrayList<>(
            Arrays.asList("Dufferin", "Bathurst", "Sheppard-Yonge", "Bayview", "Leslie"));
    TransitLine line1 = createTransitLine(line1Stations, "S", "line1");
    TransitLine route1 = createTransitLine(route1Stops, "B", "route1");
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
  public TransitLine createTransitLine(ArrayList<String> points, String type, String id) {
    return new TransitLine(points, type, id);
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
