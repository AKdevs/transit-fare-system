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

public class CreateAccountController extends Controller {
    @FXML private TextField name;
    @FXML private TextField email;
    @FXML private TextField password;

    public void createAccount(ActionEvent event) throws IOException {
        String currentName = name.getText();
        String currentEmail = email.getText();
        String currentPassword = password.getText();

        system.getAccountManager().createCardHolderAccount(currentName,
                currentEmail, currentPassword);
        showLogin(event);
    }

    public void showLogin(ActionEvent event) throws IOException {
        changeWindow(event,"view/Login.fxml" );
    }
}
