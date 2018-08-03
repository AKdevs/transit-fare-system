import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class PasswordResetController extends Controller {
    @FXML Label infoLabel;
    @FXML TextField passField;
    @FXML TextField confirmPassField;

    public void resetPasswordButtonPushed(ActionEvent event) throws IOException {
        String password = passField.getText();
        String confirmPassword = confirmPassField.getText();

        if (password.equals(confirmPassword)) {
            UserAccount loggedInUser = system.getAccountManager().getLoggedInUser();
            loggedInUser.setPassword(password);
            changeWindowToHome(event);
        } else {
            infoLabel.setText("Please reset your password below." +
                    " Required fields are marked with *.\n" +
            "Passwords do not match. Please make sure passwords match.");
        }
    }

    public void cancelButtonPushed(ActionEvent event) throws IOException {
        changeWindowToHome(event);
    }
}

