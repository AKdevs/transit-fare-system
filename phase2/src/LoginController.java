import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController {
    /*Button forgotPassBtn;
    public void forgotPasswordButton(ActionEvent event) {
        System.out.println("Hello World");
    }*/

    @FXML private Button LogIn;
    @FXML private Button cancel;
    @FXML private Button forgotPassword;

    public void initialize(URL url, ResourceBundle rb) {

    }

    //change scene method
    public void forgetPasswordButtonPushed(ActionEvent event) throws IOException {
        // new Scene
        Parent travelSimulationParent = FXMLLoader.load(getClass().getResource("PasswordRecovery.fxml"));
        Scene TravelSimulationScene = new Scene(travelSimulationParent);

        // get the Stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(TravelSimulationScene);
        window.show();
    }


}
