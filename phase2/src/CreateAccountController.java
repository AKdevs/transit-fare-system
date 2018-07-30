import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateAccountController {
    TransitSystem system;
    @FXML private TextField name;
    @FXML private TextField email;
    @FXML private TextField password;

    public void createAccount(ActionEvent event) {
        String currentName = name.getText();
        String currentEmail = email.getText();
        String currentPassword = password.getText();

    }

    void storeState(TransitSystem system) {
        this.system = system;
    }

    public void showLogin(ActionEvent event) throws IOException {
        Parent loginNode = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
        Scene scene = new Scene(loginNode);
        Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
        window.setScene(scene);
        window.show();
    }
}
