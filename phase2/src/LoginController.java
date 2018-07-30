import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController extends Controller {
    @FXML private TextField accountNumber;
    @FXML private TextField password;
    @FXML private TextField cardTextField;

    public void showUserAccount(ActionEvent event) throws IOException {
        if (userExists()) {
            changeWindow(event,"view/CardHolder.fxml" );
        }
    }

    private boolean userExists() {
        String currentAccountNumber = accountNumber.getText();
        String currentPassword = password.getText();
        UserAccount currentAccount = system.getAccountManager().findUserAccount(currentAccountNumber);
        return true;
        //return !(currentAccount == null);
    }

    public void showAccountCreation(ActionEvent event) throws Exception {
        changeWindow(event, "view/CreateAccount.fxml");
    }

    public void showRecovery(ActionEvent event) throws Exception {
        changeWindow(event, "view/PasswordRecovery.fxml");
    }


}
