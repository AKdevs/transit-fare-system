import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.io.IOException;
import java.util.Set;
import java.text.DateFormat;


public class AdminUserController extends Controller implements Initializable {
        @FXML private Button returnToMainButton;
        @FXML private DatePicker systemDate;
        @FXML private Label powerOnDateWarning;
        @FXML private Button powerOnButton;
        @FXML private Button powerOffButton;
        @FXML private Label systemStatusLabel;
        @FXML private Label todayDateLabel;
        @FXML private DatePicker dailyReportDate;
        @FXML private Label dailyReportDateWarning;
        @FXML private Label dailyReportUnavailableWarning;
        @FXML private Button runDailyReportButton;
        @FXML private ComboBox<String> transitLinesList;
        @FXML private DatePicker transitSchedulingDate;
        @FXML private TextField transitNumOfTrips;
        @FXML private Button setSchedulingButton;
        @FXML private Label schedulingResult;

        private final String datePattern = "yyyy-MM-dd";


        // TransitSystem theTransitSystem = TransitSystem.getInstance();

        public void returnToMain(ActionEvent event) throws IOException {
            changeWindow(event, "view/login.fxml");
        }


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

        public void powerOffSystem(ActionEvent event) throws IOException{
            system.powerOffSystem();
            showSystemStatus();
            powerOnDateWarning.setText("");
            todayDateLabel.setText("");
            //theTransitSystem.powerOffSystem();
            //System.out.println(theTransitSystem.getOperatingStatus());
            //powerResult.setText("The Transit System has been shut down.");
        }

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

            }
            else {
                dailyReportUnavailableWarning.setText("No report is available for the selected date.\n" +
                        "Please select another date.");
                dailyReportUnavailableWarning.setTextFill(Color.RED);
            }
        }

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

        }

}

