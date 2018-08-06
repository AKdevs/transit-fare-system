import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class CreateAccountSuccessController extends Controller {
    @FXML Label accountNumberLabel;
    public void setAccountNumber() {
        UserAccount lastCreatedAccount = system.getAccountManager().getLastCreatedAccount();
        accountNumberLabel.setText("Account Number: " + lastCreatedAccount.getAccountNum());
    }

    public void finishButtonPushed(ActionEvent event) throws IOException {
        changeWindowToHome(event);
    }
}
