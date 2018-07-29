package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController {
    public void showRecovery(ActionEvent event) throws Exception {
        Parent rootParent = FXMLLoader.load(getClass().getResource("../view/PasswordRecovery.fxml"));
        Scene rootScene = new Scene(rootParent);

        Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
        window.setScene(rootScene);
        window.show();
    }
}
