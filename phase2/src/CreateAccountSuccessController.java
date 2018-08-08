import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class CreateAccountSuccessController extends Controller {
    /**
     * Stores account number
     */
    @FXML Label accountNumberLabel;

    /**
     * Displays the account number on screen
     */
    public void setAccountNumber() {
        UserAccount lastCreatedAccount = system.getAccountManager().getLastCreatedAccount();
        accountNumberLabel.setText("Account Number: " + lastCreatedAccount.getAccountNum());
    }

    /**
     * Change screen to Login window
     * @param event button push
     * @throws IOException
     */
    public void finishButtonPushed(ActionEvent event) throws IOException {
        changeWindowToHome(event);
    }
}
