import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class PasswordResetController extends Controller {
    /**
     * Gives instructions to the user
     */
    @FXML Label infoLabel;
    /**
     * Records user's inputted password
     */
    @FXML TextField passField;
    /**
     * Records user's inputted password (for confirmation)
     */
    @FXML TextField confirmPassField;

    /**
     * Resets user password iff passwords match, or prompts them further
     * if not
     * @param event triggers when Reset Password button pushed
     * @throws IOException
     */
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

    /**
     * Changes back to the Login screen
     * @param event triggers when cancel button is pushed
     * @throws IOException
     */
    public void cancelButtonPushed(ActionEvent event) throws IOException {
        changeWindowToHome(event);
    }
}

