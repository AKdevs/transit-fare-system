/**
 *
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.After;
//import org.junit.Before;
//import org.junit.Test;

public class TransitSystemTest {
    static TransitSystem t;
    static EventHandler e;
    @BeforeEach
    public void setup() throws Exception {
        t = new TransitSystem();
        e = new EventHandler(t);
        ArrayList<String> subwayPoints = new ArrayList<>();
        subwayPoints.add("A");
        subwayPoints.add("B");
        subwayPoints.add("C");
        subwayPoints.add("D");
        ArrayList<String> busPointsA1 = new ArrayList<>(Arrays.asList("A11, A12, A13"));
        ArrayList<String> busPointsA2 = new ArrayList<>(Arrays.asList("A21, A22"));
        TransitLine subwayLine = new TransitLine(subwayPoints, "S", "line1");
        TransitLine busLineA1 = new TransitLine(busPointsA1, "B", "A1");
        TransitLine busLineA2 = new TransitLine(busPointsA2, "B", "A2");
        // t.addTransitLines(subwayLine);
         //t.addTransitLines(busLineA1);
         //t.addTransitLines(busLineA2);

    }

    /*
    @Test
    public void testTransitLines() {
         HashMap<String, TransitLine> lines = t.getTransitLines();
        lines.get("line1").addPoint("", "G");
        ArrayList<String> testList = new ArrayList<>();
        testList.add("G"); testList.add("A"); testList.add("B"); testList.add("C");
        testList.add("D");
        assertEquals(testList, lines.get("line1").getPoints());
        lines.get("line1").addPoint("F", "H");
    }

    @Test
    public void testAccountCreation() {
        t.createCardHolderAccount("David Villa", "villa@gmail.com");
        CardHolder newUser = (CardHolder) t.findUserAccount(10000001);
    }
    */




}
