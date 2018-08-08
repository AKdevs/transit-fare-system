import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.util.StringConverter;
import javafx.scene.control.SpinnerValueFactory.*;
import javafx.util.converter.*;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.IOException;
import java.text.DateFormat;
import java.util.function.UnaryOperator;


public class AdminUserController extends Controller implements Initializable {

  /**
   * Button for return to main
   */
  @FXML private Button returnToMainButton;
  /**
   * DatePicker to pick system date
   */
  @FXML private DatePicker systemDate;
  /**
   * Power on date warning
   */
  @FXML private Label powerOnDateWarning;
  /**
   * Power-on button
   */
  @FXML private Button powerOnButton;
  /**
   * Power-off button
   */
  @FXML private Button powerOffButton;
  /**
   * System status label
   */
  @FXML private Label systemStatusLabel;
  /**
   * current date label
   */
  @FXML private Label todayDateLabel;
  /**
   * Daily report date
   */
  @FXML private DatePicker dailyReportDate;
  /**
   * Warning for daily report date
   */
  @FXML private Label dailyReportDateWarning;
  /**
   * Warning for daily report unavailable
   */
  @FXML private Label dailyReportUnavailableWarning;
  /**
   * Button for run daily report
   */
  @FXML private Button runDailyReportButton;
  /**
   * list for transit lines
   */
  @FXML private ComboBox<String> transitLinesList;
  /**
   * Date for transit scheduling
   */
  @FXML private DatePicker transitSchedulingDate;
  /**
   * Warning for scheduling date
   */
  @FXML private Label schedulingDateWarning;
  /**
   * Warning for scheduling unavailable
   */
  @FXML private Label schedulingUnavailableWarning;
  /**
   * Transit number of trips
   */
  //@FXML private TextField transitNumOfTrips;
  @FXML private Spinner<Integer> transitNumOfTrips;
  /**
   * Set Scheduling button
   */
  @FXML private Button setSchedulingButton;
  /**
   * Scheduling result label
   */
  @FXML private Label schedulingResult;
  /**
   * Average cost per station
   */
  @FXML private Spinner<Double> avgCostPerStation;
  /**
   * Cost setting result Label
   */
  @FXML private Label costSettingResultLabel;
  /**
   * Cost setting button
   */
  @FXML private Button costSettingButton;
  /**
   * View cost button
   */
  @FXML private Button viewCostButton;


  /**
   * Date pattern
   */
  private final String datePattern = "yyyy-MM-dd";


  // TransitSystem theTransitSystem = TransitSystem.getInstance();

  /**
   * Return to main page
   * @param event Action Event
   * @throws IOException
   */
  public void returnToMain(ActionEvent event) throws IOException {
    changeWindowToHome(event);
    }


  /**
   * Power on the transit system
   * @param event Action event
   * @throws IOException
   */
    public void powerOnSystem(ActionEvent event) throws IOException {
    // theTransitSystem.powerOnSystem();
      LocalDate date = systemDate.getValue();
      powerOnDateWarning.setTextFill(Color.RED);
      // warning when date is not selected
      if (date == null){
        powerOnDateWarning.setText("Please Select a Date!");
        }
        else if (system.getOperatingStatus().equals("on")) {
        powerOnDateWarning.setText("The System is currently OPERATING!");
        }
        else{
        // clear warning message
        powerOnDateWarning.setText("");
        system.powerOnSystem();
        // set currentDate in Transit System
        String dateString = date.toString();
        system.setCurrentDate(dateString);
        // create initial (date, value) pair in Aggregator
        system.getTripManager().getAggregator().initializeDate(dateString);
        //System.out.println("Fare:" + system.getTripManager().getAggregator().getDailyFares(dateString));
        //System.out.println(system.getTripManager().getAggregator().getDailyStation(dateString));
        // if TransitLineDailyStat is empty for the day, set up SingleTransitLineDailyStat for each
        // Transit Line
        TransitLineDailyStat todayStat = system.getTripManager().getAggregator().getTransitLineDailyStat(dateString);
        if (todayStat.isEmpty()) {
          HashMap<String, TransitLine> transitLines = system.getTransitManager().getTransitLines();
          for (String id: transitLines.keySet()) {
            SingleTransitLineDailyStat singleTransitStat = new SingleTransitLineDailyStat(id,0,0,0);
            todayStat.addSingleTransitLineDailyStat(id, singleTransitStat);
            }
            }

            // set display of date in systemDate and todayDateLabel at top
        systemDate.setPromptText(dateString);
        String todayDate = "Today is " + dateString +".";
        todayDateLabel.setText(todayDate);
        //create <date, 0> for attributes in Aggregator and Transit Scheduling
        showSystemStatus();
        }

        // powerResult.setText("The Transit System is now operating.");
      //System.out.println(theTransitSystem.getOperatingStatus());
    }

  /**
   * Power of the system
   * @param event
   * @throws IOException
   */
    public void powerOffSystem(ActionEvent event) throws IOException{
     system.powerOffSystem();
     showSystemStatus();
     powerOnDateWarning.setText("");
     todayDateLabel.setText("");
    }

  /**
   * Show the system status, which is either off or on
   */
  public void showSystemStatus() {
            if (system.getOperatingStatus().equals("on")){
                systemStatusLabel.setText("on");
            systemStatusLabel.setTextFill(Color.GREEN);
        }
            if (system.getOperatingStatus().equals("off")) {
                systemStatusLabel.setText("off");
                systemStatusLabel.setTextFill(Color.ORANGE);
            }
        }

  /**
   * Show the daily report to admin user
   * @param event
   * @throws IOException
   */
  public void showDailyReport(ActionEvent event) throws IOException {
        LocalDate date = dailyReportDate.getValue();
        dailyReportDateWarning.setTextFill(Color.RED);
        // warning when date is not selected
        if (date == null){
            dailyReportDateWarning.setText("Please Select a Date!");
        }
        else {
            String dateString = date.toString();
            dailyReportDateWarning.setText("");
            dailyReportUnavailableWarning.setText("");
            if (system.getTripManager().getAggregator().isReportAvailable(dateString)) {
                prepareReport(dateString);

            }
            else {
                dailyReportUnavailableWarning.setText("No report is available for the selected date.\n" +
                        "Please select another date.");
                dailyReportUnavailableWarning.setTextFill(Color.RED);
            }
        }

    }


  /**
   * Set the transit scheduling
   * @param event
   * @throws IOException
   */
    public void setTransitScheduling(ActionEvent event) throws IOException {
        LocalDate date = transitSchedulingDate.getValue();
        String transitLine = transitLinesList.getValue();
        schedulingDateWarning.setTextFill(Color.RED);
        //warning when transit line is not selected
        if (transitLine == null) {
            schedulingDateWarning.setText("Please Select a Transit Line!");
        } else {
            schedulingDateWarning.setText("");
            // warning when date is not selected
            if (date == null) {
                schedulingDateWarning.setText("Please Select a Date!");
            } else {

                String dateString = date.toString();
                schedulingDateWarning.setText("");
                schedulingUnavailableWarning.setText("");
                if (system.getTripManager().getAggregator().isReportAvailable(dateString)) {
                        schedulingResult.setText("");
                        Integer numToSet = transitNumOfTrips.getValue();
                        if ((numToSet >= 0) && (numToSet < 1201)) {
                            SingleTransitLineDailyStat singleTransitStat = system.getTripManager().getAggregator()
                                    .getTransitLineDailyStat(dateString).getSingleTransitLineDailyStat(transitLine);
                            singleTransitStat.setNumOfTrips(numToSet);
                            schedulingResult.setTextFill(Color.DARKGREEN);
                            String success = "Number of Trips of " + transitLine + " on " + dateString
                                    + " has been set to " + numToSet + " successfully.";
                            schedulingResult.setText(success);
                            //singleTransitStat.setAvgRiderPerTrip();
                        }
                        else {
                            schedulingResult.setText("Number of Trips has to be an integer between 0 and 1200!");
                            schedulingResult.setTextFill(Color.RED);
                            }


                } else {
                    schedulingUnavailableWarning.setText("Unable to set record for the selected date.\n" +
                            "Please select another date.");
                    schedulingUnavailableWarning.setTextFill(Color.RED);
                }


            }
        }
    }

  /**
   * Sets the average cost per station
   * @param event
   * @throws IOException
   */
    public void setAvgCost(ActionEvent event) throws IOException {
            Double cost = avgCostPerStation.getValue();
            system.getTripManager().setAvgCostPerStation(cost);
            costSettingResultLabel.setTextFill(Color.DARKGREEN);
            String success = "The Average Cost Per Station has been set to $" + cost + " successfully.";
            costSettingResultLabel.setText(success);
    }

  /**
   * View the average cost per station
   * @param event
   * @throws IOException
   */
    public void viewAvgCost(ActionEvent event) throws IOException {
            Double cost = system.getTripManager().getAvgCostPerStation();
            String message = "The current setting for Average Cost Per Station is $" + cost +".";
        costSettingResultLabel.setTextFill(Color.BLACK);
        costSettingResultLabel.setText(message);
    }

  /**
   * Repare the daily report for the admin User
   * @param dateString
   * @throws IOException
   */
    public void prepareReport(String dateString) throws IOException {
            Stage reportWindow = new Stage();
        FXMLLoader dailyReportLoader = new FXMLLoader(getClass().getResource("view/DailyReport.fxml"));
        Parent root = dailyReportLoader.load();
        Scene dailyReportScene = new Scene(root);

        DailyReportController controller = dailyReportLoader.getController();
        controller.storeState(system);
        controller.initReport(dateString);

        reportWindow.setTitle("Transit System Daily Report");
        reportWindow.initModality(Modality.APPLICATION_MODAL);
        reportWindow.setScene(dailyReportScene);
        reportWindow.setResizable(false);
        reportWindow.showAndWait();
    }


        //java.util.regex is not available
    /**
     *  Converts format in DatePicker to "yyyy-mm-dd"
     *  The below code was retrieved from Oracle Java Documentation website
     *  (Source: https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/date-picker.htm
     *  retrieved in August 2018)
     */


    StringConverter converter = new StringConverter<LocalDate>() {
        DateTimeFormatter dateFormatter =
                DateTimeFormatter.ofPattern(datePattern);
        @Override
        public String toString(LocalDate date) {
            if (date != null) {
                return dateFormatter.format(date);
            } else {
                return "";
            }
        }
        @Override
        public LocalDate fromString(String string) {
            if (string != null && !string.isEmpty()) {
                return LocalDate.parse(string, dateFormatter);
            } else {
                return null;
            }
        }
        };
   // public void setScheduling


  /**
   * Initialize the scene for admin user
   *
   *
   * @param url URL
   * @param rb Resource Bundle
   */
        @Override
        public void initialize(URL url, ResourceBundle rb) {

            //String systemStatus = system.getOperatingStatus();
            showSystemStatus();

            // Convert format of dailyReportDate and transitSchedulingDate to "yyyy-mm-dd"
            Locale.setDefault(Locale.US);
            systemDate.setConverter(converter);
            dailyReportDate.setConverter(converter);
            transitSchedulingDate.setConverter(converter);

            // Set<String> transitLines =  theTransitSystem.getTransitManager().getTransitLines().keySet();
            Set<String> transitLines =  system.getTransitManager().getTransitLines().keySet();
            for (String line : transitLines) {
                transitLinesList.getItems().add(line);
            }

            String currentDate = system.getCurrentDate();
            if ((currentDate != null) && (system.getOperatingStatus().equals("on"))) {
                String todayDate = "Today is " + currentDate +".";
                todayDateLabel.setText(todayDate);
            }

            /**
             *  Restricting user input in transitNumOfTrips to be integer only
             *  The below code was retrieved from Stackoverflow website
             *  Author: kleopatra
             *  (Source: https://stackoverflow.com/questions/25885005/insert-only-numbers-in-spinner-control
             *  retrieved in August 2018)
             */
            NumberFormat intFormat = NumberFormat.getIntegerInstance();
            UnaryOperator<TextFormatter.Change> intFilter = c -> {
                if (c.isContentChange()) {
                    ParsePosition parsePosition = new ParsePosition(0);
                    // NumberFormat evaluates the beginning of the text
                    intFormat.parse(c.getControlNewText(), parsePosition);
                    if (parsePosition.getIndex() == 0 ||
                            parsePosition.getIndex() < c.getControlNewText().length()) {
                        // reject parsing the complete text failed
                        return null;
                    }
                }
                return c;
            };
            TextFormatter<Integer> tripFormatter = new TextFormatter<>(
                    new IntegerStringConverter(), 100, intFilter);
            transitNumOfTrips.getEditor().setTextFormatter(tripFormatter);


            /** restricts user input in avgCostPerStation to #.00*/
            DecimalFormat costFormat = new DecimalFormat( "#.00" );
            UnaryOperator<TextFormatter.Change> costFilter = c -> {
                if (c.isContentChange()) {
                    ParsePosition parsePosition = new ParsePosition(0);
                    // NumberFormat evaluates the beginning of the text
                    costFormat.parse(c.getControlNewText(), parsePosition);
                    if (parsePosition.getIndex() == 0 ||
                            parsePosition.getIndex() < c.getControlNewText().length()) {
                        // reject parsing the complete text failed
                        return null;
                    }
                }
                return c;
            };
            TextFormatter<Double> costFormatter = new TextFormatter<>(
                    new DoubleStringConverter(), 5.00, costFilter);
            avgCostPerStation.getEditor().setTextFormatter(costFormatter);


        }

}

