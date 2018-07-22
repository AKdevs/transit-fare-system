import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/** Manage the transit system. */
class TransitManager {
  private HashMap<String, TransitLine> transitLines = new HashMap<>();

  /** Constructs a transit system manager. */
  TransitManager() {
    defaultSetup();
  }

  HashMap<String, TransitLine> getTransitLines() {
    return transitLines;
  }

  /** Sets up transit lines in transit system. */
  private void defaultSetup() {
    ArrayList<String> line1Stations =
        new ArrayList<>(
            Arrays.asList(
                "Finch",
                "North York Centre",
                "Sheppard-Yonge",
                "York Mills",
                "Lawrence",
                "Eglinton",
                "Davisville",
                "St Clair",
                "Summerhill",
                "Rosedale",
                "Bloor-Yonge",
                "Wellesley",
                "College",
                "Dundas",
                "Queen",
                "King",
                "Union",
                "St Andrew",
                "Osgoode",
                "St Patrick"));
    ArrayList<String> route1Stops =
        new ArrayList<>(
            Arrays.asList(
                "Dufferin",
                "Bathurst",
                "Sheppard-Yonge",
                "Bayview",
                "Bessarion",
                "Leslie",
                "Don Mills",
                "Warden",
                "Victoria Park"));
    ArrayList<String> route3Stops =
        new ArrayList<>(
            Arrays.asList(
                "Harlem",
                "Central Park",
                "Braodway",
                "Times Square",
                "Chambers",
                "Park Place",
                "Fulton Street",
                "Union",
                "Wall Street",
                "Clark Street",
                "Atlantic Avenue"));
    ArrayList<String> route5Stops =
        new ArrayList<>(
            Arrays.asList(
                "Morris Park",
                "Burke Avenue",
                "Freeman Street",
                "Warden",
                "Simpson Street",
                "Jackson Street",
                "Franklin Street",
                "Sterling Road",
                "Beverly Road",
                "Newark Road",
                "Dennis Road",
                "Spring Street"));
    TransitLine line1 = createTransitLine(line1Stations, "S", "line1");
    TransitLine route1 = createTransitLine(route1Stops, "B", "route1");
    TransitLine route3 = createTransitLine(route3Stops, "B", "route3");
    TransitLine route5 = createTransitLine(route5Stops, "B", "route5");
    addTransitLine(line1.getId(), line1);
    addTransitLine(route1.getId(), route1);
    addTransitLine(route3.getId(), route3);
    addTransitLine(route5.getId(), route5);
  }

  /**
   * Creates a new transit line in the transit system.
   *
   * @param points stations or stops in the new transit line
   * @param type type of the new transit line
   * @return the new transit line
   */
  private TransitLine createTransitLine(ArrayList<String> points, String type, String id) {
    return new TransitLine(points, type, id);
  }

  /**
   * Adds the new transit line to the transit system
   *
   * @param name id of the new transit line
   * @param line new transit line
   */
  private void addTransitLine(String name, TransitLine line) {
    transitLines.put(name, line);
  }



}