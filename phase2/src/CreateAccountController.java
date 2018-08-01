import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateAccountController extends Controller {

    @FXML private Label requiredInfoLabel;
    @FXML private TextField name;
    @FXML private TextField email;
    @FXML private TextField password;

    public void createAccountButtonPushed(ActionEvent event) throws IOException {
        String currentName = name.getText();
        String currentEmail = email.getText();
        String currentPassword = password.getText();

        if (currentName.equals("") || currentEmail.equals("") ||
                currentPassword.equals("")) {
            requiredInfoLabel.setText("* Please fill all required fields");
        } else {
            system.getAccountManager().createCardHolderAccount(currentName,
                    currentEmail, currentPassword);
            changeWindow(event,"view/Login.fxml" );
        }

    }



    public void cancelButtonPushed(ActionEvent event) throws IOException {
        changeWindowToHome(event);
    }
}
