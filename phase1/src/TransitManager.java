import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TransitManager {
    private HashMap<String, TransitLine> transitLines;

    TransitManager() {
        transitLines = new HashMap<>();
    }

    public HashMap<String, TransitLine> getTransitLines() {
        return transitLines;
    }

    private void defaultSetup() {
        ArrayList<String> line1Stations = new ArrayList<>(Arrays.asList(
                "Finch", "North York Centre", "Sheppard-Yonge", "King", "Bay", "Bloor" ));
        ArrayList<String> route1Stops = new ArrayList<>(Arrays.asList(
                "Dufferin", "Bathurst", "Sheppard-Yonge", "Bayview", "Leslie"));
        TransitLine line1 = createTransitLine(line1Stations, "S" );
        TransitLine route1 = createTransitLine(route1Stops, "B");
        addTransitLine(line1.getId(), line1);
        addTransitLine(route1.getId(), route1);

    }

    public TransitLine createTransitLine(ArrayList<String> points, String type) {
        return new TransitLine(points, type, "line1");
    }

    public void addTransitLine(String name, TransitLine line) {
        transitLines.put(name, line);
    }
}
