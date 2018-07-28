package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginController {
    Button button = new Button();

    @FXML Button forgotPassbtn;
    public void forgotPassBtn(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/PasswodRecovery.fxml"));
        Parent root1 = (Parent) loader.load();
        Stage recoveryWindow = new Stage();
        Scene scene = new Scene(root1);
        recoveryWindow.setScene(scene);
        recoveryWindow.show();
    }
}
