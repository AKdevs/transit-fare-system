import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.util.StringConverter;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.io.IOException;
import java.util.Set;
import java.text.DateFormat;


public class DailyReportController extends Controller implements Initializable {

    private String reportDateString;
    private Double fare;
    private Integer numStationServiced;
    private TransitLineDailyStat statSummary;

    @FXML private Label dateLabel;
    @FXML private Label fareLabel;
    @FXML private Label costLabel;
    @FXML private Label profitLabel;
    @FXML private Label totalRidershipLabel;
    @FXML private Label numStationReachedLabel;
    @FXML private Label numStationServicedLabel;
    @FXML private Label reachServiceRatioLabel;
    @FXML private TableView<SingleTransitLineDailyStat> transitStatTable;
    @FXML private TableColumn<SingleTransitLineDailyStat, String> transitLineColumn;
    @FXML private TableColumn<SingleTransitLineDailyStat, Integer> numTripsColumn;
    @FXML private TableColumn<SingleTransitLineDailyStat, Integer> ridershipColumn;
    @FXML private TableColumn<SingleTransitLineDailyStat, Integer> avgRiderColumn;

    public void initReport(String date){
        this.reportDateString = date;
        showDate();
        showFare(reportDateString);
        showCost(reportDateString);
        showRidership(reportDateString);
        showStationTravelled(date);
        populateTable();
    }


    /**
     *  set date in the dateLabel
     */
    void showDate() {
        dateLabel.setText(reportDateString);
    }

    /**
     *  set all fares in fareLabel
     */
    void showFare(String date) {
        Double fare = system.getTripManager().getAggregator().getDailyFares(date);
        //System.out.println("Fare:" + fare);
        fareLabel.setText(fare.toString());
        this.fare = fare;
    }

    /**
     *  set all cost in costLabel
     *  set number of stations serviced in numStationServicedLabel
     *  set profit in profitLabel
     */

    void showCost(String date){
        Integer totalServiced = 0;
        this.statSummary = system.getTripManager().getAggregator().getTransitLineDailyStat(date);
        for (String id : statSummary.getTransitLineSummary().keySet()) {
            TransitLine transitLine = system.getTransitManager().getTransitLines().get(id);
            Integer numPerTrip = transitLine.getNumOfStops();
            SingleTransitLineDailyStat singleStat = statSummary.getSingleTransitLineDailyStat(id);
            totalServiced += numPerTrip * singleStat.getNumOfTrips();
        }
        Double cost = totalServiced * system.getTripManager().getAvgCostPerStation();
        costLabel.setText(cost.toString());
        numStationServicedLabel.setText(totalServiced.toString());

        Double profit = system.getTripManager().getAggregator().getDailyFares(date) - cost;
        if (profit < 0 ){
            profitLabel.setTextFill(Color.RED);
        }
        else{
            profitLabel.setTextFill(Color.BLACK);
        }
        profitLabel.setText(profit.toString());

        this.numStationServiced = totalServiced;
    }

    /**
     *  set Total ridership in totalRidershipLabel
     */
    void showRidership(String date){
        Integer totalRider = 0;
        //TransitLineDailyStat summary = system.getTripManager().getAggregator().getTransitLineDailyStat(date);
        for (String id : statSummary.getTransitLineSummary().keySet()) {
            SingleTransitLineDailyStat singleStat = statSummary.getSingleTransitLineDailyStat(id);
            totalRider += singleStat.getRidership();
        }
        totalRidershipLabel.setText(totalRider.toString());
    }

    /**
     *  set Number of Stations reached by traveller and Reach/Serviced ratio
     */

    void showStationTravelled(String date){
        Integer travelled = system.getTripManager().getAggregator().getDailyStation(date);
        numStationReachedLabel.setText(travelled.toString());

        Double decTravelled = travelled + 0.00001;
        //Double decTravelled = 56 + 0.00001;
        Double ratio = decTravelled / numStationServiced;
        DecimalFormat df = new DecimalFormat("#.##");

        reachServiceRatioLabel.setText(df.format(ratio));
    }

    /**
     *  Populates info for transitStatTable from statSummary
     */

    void populateTable(){
        transitLineColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        numTripsColumn.setCellValueFactory(new PropertyValueFactory<>("numOfTrips"));
        ridershipColumn.setCellValueFactory(new PropertyValueFactory<>("ridership"));
        avgRiderColumn.setCellValueFactory(new PropertyValueFactory<>("avgRiderPerTrip"));

        transitStatTable.setItems(getData());
    }
    public ObservableList<SingleTransitLineDailyStat> getData(){
        ObservableList<SingleTransitLineDailyStat> statRows = FXCollections.observableArrayList();
        for (String id : statSummary.getTransitLineSummary().keySet()) {
            SingleTransitLineDailyStat singleStat = statSummary.getSingleTransitLineDailyStat(id);
            singleStat.setAvgRiderPerTrip();
            statRows.add(singleStat);
        }

        return statRows;
    }



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //String date = system.getCurrentDate();

    }

}