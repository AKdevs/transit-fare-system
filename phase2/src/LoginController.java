import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    TransitSystem system;
    @FXML private TextField accountNumber;
    @FXML private TextField password;
    @FXML private TextField cardTextField;

    public void showUserAccount(ActionEvent event) throws IOException {
        if (userExists()) {
            Parent createAccountParent = FXMLLoader.load(getClass().getResource(
                "view/CardHolder.fxml"));
            Scene createAccountScene = new Scene(createAccountParent);

            Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
            window.setScene(createAccountScene);
            window.show();
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
        Parent createAccountParent = FXMLLoader.load(getClass().getResource("view/CreateAccount.fxml"));
        Scene createAccountScene = new Scene(createAccountParent);

        Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
        window.setScene(createAccountScene);
        window.show();
    }

    void storeState(TransitSystem system) {
        this.system = system;
    }

    public void showRecovery(ActionEvent event) throws Exception {
        Parent PasswordRecoveryParent = FXMLLoader.load(getClass().getResource("view/PasswordRecovery.fxml"));
        Scene rootScene = new Scene(PasswordRecoveryParent);

        Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
        window.setScene(rootScene);
        window.show();
    }


}
