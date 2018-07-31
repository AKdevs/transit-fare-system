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
import java.util.ResourceBundle;
import java.io.IOException;


public class AdminUserController extends Controller {
        @FXML private Button powerOnButton;
        @FXML private Button powerOffButton;

        TransitSystem theTransitSystem = TransitSystem.getInstance();


        public void powerOnSystem(ActionEvent event) throws IOException {
            theTransitSystem.powerOnSystem();
            System.out.println(theTransitSystem.getOperatingStatus());
        }

        public void powerOffSystem(ActionEvent event) throws IOException{
            theTransitSystem.powerOffSystem();
            System.out.println(theTransitSystem.getOperatingStatus());
        }

        /**@Override
        public void initialize(URL url, ResourceBundle rb) {

        }*/

}

