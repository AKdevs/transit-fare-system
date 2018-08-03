import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class PasswordRecoveryController extends Controller {
    @FXML TextField accountNumberField;
    @FXML Label instructionsLabel;
    public void cancelButtonPressed(ActionEvent event) throws IOException {
        changeWindow(event, "view/login.fxml");
    }

    public void continueButtonPressed(ActionEvent event) throws IOException {
        UserAccount account = system.getAccountManager().findUserAccount(accountNumberField.getText());
        if (account != null) {
            system.getAccountManager().setLoggedInUser(account);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/SecurityQuestion.fxml"));
            Parent newWindowParent = loader.load();
            SecurityQuestionController newWindowController = loader.getController();
            newWindowController.storeState(system);
            newWindowController.initializeSecurityQuestions();
            Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
            window.setScene(new Scene(newWindowParent));
            window.show();
        } else {
            instructionsLabel.setText("Account not found. Please try again");
        }
    }
}
