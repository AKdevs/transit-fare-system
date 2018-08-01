import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        UserAccount currentAccount = userExists();
        if (currentAccount != null) {
            if (system.getAccountManager().isAdmin(currentAccount)) {
                //changeWindow(event,"view/AdminUser.fxml");
            } else {
                //changeWindow(event,"view/CardHolder.fxml" );
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("view/CardHolder.fxml"));
                Parent cardHolderParent = loader.load();

                Scene cardHolderScene = new Scene(cardHolderParent);

                CardHolderController cht = loader.getController();
                cht.initialCardHolderInfo(accountNumber.getText());

                // get the Stage info
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(cardHolderScene);
                window.show();
            }
        } else {
            loginInstructions.setText("Invalid username or password.");
        }
    }

    private UserAccount userExists() {
        String currentAccountNumber = accountNumber.getText();
        String currentPassword = password.getText();
        // temporary bypass
        if (currentAccountNumber.equals("") && currentPassword.equals("")) {
            return new UserAccount("A","B","C");
        }
        UserAccount currentAccount = system.getAccountManager().findUserAccount(currentAccountNumber);

        return currentAccount;
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

    public void showCardCreation(ActionEvent event) throws Exception {
        changeWindow(event, "view/CreateCard.fxml");
    }

    public void showRecovery(ActionEvent event) throws Exception {
        changeWindow(event, "view/PasswordRecovery.fxml");
    }

}
