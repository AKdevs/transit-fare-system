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

            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Card.fxml"));
            Parent cardParent = loader.load();

            CardController ct = loader.getController();
            ct.storeState(system);
            ct.initialCardInfoWithoutAccount(cardNumber.getText());
            ct.setSource(0);

            // get the Stage info
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(new Scene(cardParent));
            window.show();
        } else {
            cardInputInstructions.setText("Card not found. Try again");
        }

    }

    public void showUserAccount(ActionEvent event) throws IOException {
        String currentAccountNumber = accountNumber.getText();
        String currentPassword = password.getText();
        UserAccount currentAccount = verifyLogin(currentAccountNumber, currentPassword);
        if (currentAccount != null) {
            if (system.getAccountManager().isAdmin(currentAccount)) {
                AdminUserController adminUserController = new AdminUserController();
                adminUserController.storeState(system);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("view/AdminUser.fxml"));
                loader.setController(adminUserController);
                Parent adminUserParent = loader.load();
                Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
                window.setScene(new Scene(adminUserParent));
                window.show();
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("view/CardHolder.fxml"));
                Parent cardHolderParent = loader.load();

                Scene cardHolderScene = new Scene(cardHolderParent);

                // read user input and set value in CardHolder window
                CardHolderController cht = loader.getController();
                cht.storeState(system);
                cht.initialCardHolderInfo(currentAccountNumber);

                // get the Stage info
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(cardHolderScene);
                window.show();
            }
        } else {
            loginInstructions.setText("Invalid username or password.");
        }
    }

    private UserAccount verifyLogin(String currentAccountNumber, String currentPassword) {
        System.out.println(system == null);
        UserAccount currentAccount = system.getAccountManager().findUserAccount(currentAccountNumber);
        // check for password
        if (currentAccount != null) {
            if (!currentAccount.password.equals(currentPassword)) {
                currentAccount = null;
            }
        }
        return currentAccount;
    }

    private boolean cardExists() {
        String currentCardNumber = cardNumber.getText();
        /*
        // temporary bypass
        if (currentCardNumber.equals("")) {
            return true;
        }*/

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
