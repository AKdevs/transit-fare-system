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
    /**
     * Records user's input for account number
     */
    @FXML TextField accountNumberField;
    /**
     * Gives instructions to the user
     */
    @FXML Label instructionsLabel;

    /**
     * Changes back to the Login screen
     * @param event triggers when cancel button is pushed
     * @throws IOException
     */
    public void cancelButtonPushed(ActionEvent event) throws IOException {
        changeWindow(event, "view/Login.fxml");
    }

    /**
     * Sends user to next screen of password reset if account number is accepted,
     * or prompts them further if it is not
     * @param event triggers when cancel button is pushed
     * @throws IOException
     */
    public void continueButtonPushed(ActionEvent event) throws IOException {
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
