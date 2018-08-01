import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.io.IOException;
import java.util.Set;


public class AdminUserController extends Controller implements Initializable {
        @FXML private Button returnToMainButton;
        @FXML private Button powerOnButton;
        @FXML private Button powerOffButton;
        @FXML private Label powerResult;
        @FXML private TextField dailyReportDate;
        @FXML private Button runDailyReportButton;
        @FXML private ComboBox<String> transitLinesList;
        @FXML private TextField transitSchedulingDate;
        @FXML private TextField transitNumOfTrips;
        @FXML private Button setSchedulingButton;
        @FXML private Label schedulingResult;


        // TransitSystem theTransitSystem = TransitSystem.getInstance();

        public void returnToMain(ActionEvent event) throws IOException {
            changeWindow(event, "view/login.fxml");
        }


        public void powerOnSystem(ActionEvent event) throws IOException {
            // theTransitSystem.powerOnSystem();
            system.powerOnSystem();
            powerResult.setText("The Transit System is now operating.");
            //System.out.println(theTransitSystem.getOperatingStatus());
        }

        public void powerOffSystem(ActionEvent event) throws IOException{
            system.powerOnSystem();
            //theTransitSystem.powerOffSystem();
            //System.out.println(theTransitSystem.getOperatingStatus());
            powerResult.setText("The Transit System has been shut down.");
        }

        @Override
        public void initialize(URL url, ResourceBundle rb) {

            // Set<String> transitLines =  theTransitSystem.getTransitManager().getTransitLines().keySet();
            Set<String> transitLines =  system.getTransitManager().getTransitLines().keySet();
            for (String line : transitLines) {
                transitLinesList.getItems().add(line);
            }

        }

}

