import java.util.ArrayList;

public class TransitLine {
    ArrayList<String> points;
    String type;

    TransitLine(ArrayList<String> points, String type) {
        this.points = points;
        this.type = type;
    }

    ArrayList<String> getPoints() {
        return this.points;
    }

    void changeName(String oldName, String newName) {}

    void addPoint() {}

}
