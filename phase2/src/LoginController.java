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

public class LoginController extends Controller {
    @FXML private Label cardInputInstructions;
    @FXML private Label loginInstructions;
    @FXML private TextField accountNumber;
    @FXML private TextField password;
    @FXML private TextField cardNumber;


    public void showCard(ActionEvent event) throws IOException {
        if (cardExists()) {
            changeWindow(event, "view/Card.fxml");
        } else {
            cardInputInstructions.setText("Card not found. Try again");
        }

    }

    public void showUserAccount(ActionEvent event) throws IOException {
        if (userExists()) {
            changeWindow(event,"view/CardHolder.fxml" );
        } else {
            loginInstructions.setText("Invalid username or password.");
        }
    }

    private boolean userExists() {
        String currentAccountNumber = accountNumber.getText();
        String currentPassword = password.getText();
        // temporary bypass
        if (currentAccountNumber.equals("") && currentPassword.equals("")) {
            return true;
        }
        UserAccount currentAccount = system.getAccountManager().findUserAccount(currentAccountNumber);

        return !(currentAccount == null);
    }

    private boolean cardExists() {
        String currentCardNumber = cardNumber.getText();
        // temporary bypass
        if (currentCardNumber.equals("")) {
            return true;
        }

        Card currentCard = system.getCardManager().findCard(currentCardNumber);
        return !(currentCard == null);
    }

    public void showAccountCreation(ActionEvent event) throws Exception {
        changeWindow(event, "view/CreateAccount.fxml");
    }

    public void showRecovery(ActionEvent event) throws Exception {
        changeWindow(event, "view/PasswordRecovery.fxml");
    }


}
