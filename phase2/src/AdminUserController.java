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
        @FXML private Button powerOnButton;
        @FXML private Button powerOffButton;
        @FXML private Label systemStatusLabel;
        @FXML private DatePicker dailyReportDate;
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
            system.powerOnSystem();
            showSystemStatus();

            // powerResult.setText("The Transit System is now operating.");
            //System.out.println(theTransitSystem.getOperatingStatus());
        }

        public void powerOffSystem(ActionEvent event) throws IOException{
            system.powerOffSystem();
            showSystemStatus();
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

        @Override
        public void initialize(URL url, ResourceBundle rb) {

            //String systemStatus = system.getOperatingStatus();
            showSystemStatus();

            // Convert format of dailyReportDate and transitSchedulingDate to "yyyy-mm-dd"
            Locale.setDefault(Locale.US);
            dailyReportDate.setConverter(converter);
            transitSchedulingDate.setConverter(converter);

            // Set<String> transitLines =  theTransitSystem.getTransitManager().getTransitLines().keySet();
            Set<String> transitLines =  system.getTransitManager().getTransitLines().keySet();
            for (String line : transitLines) {
                transitLinesList.getItems().add(line);
            }

        }

}

