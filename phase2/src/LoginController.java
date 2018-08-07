import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController extends Controller {
    /**
     * Gives user instructions in Without An Account tab
     */
    @FXML private Label cardInputInstructions;
    /**
     * Gives user instructions in With An Account tab
     */
    @FXML private Label loginInstructions;
    /**
     * Records user's input for account number
     */
    @FXML private TextField accountNumber;
    /**
     * Records user's input for password
     */
    @FXML private TextField password;
    /**
     * Records user's input for card number
     */
    @FXML private TextField cardNumber;


    /**
     * Shows user their card's information if correct card number is entered,
     * or prompts them further if not
     * @param event
     * @throws IOException
     */
    public void showCard(ActionEvent event) throws IOException {
        if (system.getOperatingStatus().equals("off")) {
            cardInputInstructions.setTextFill(Color.RED);
            cardInputInstructions.setText("Please power on system before proceeding");
        } else if (cardExists()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Card.fxml"));
            Parent cardParent = loader.load();

            CardController ct = loader.getController();
            ct.storeState(system);
            ct.setSource(0);
            ct.initialCardInfo(cardNumber.getText());

            // get the Stage info
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(new Scene(cardParent));
            window.show();
        } else {
            cardInputInstructions.setText("Card not found. Try again");
        }

    }

    /**
     * Shows user their account profile if correct account number
     * and password are entered, or prompts them further if not
     * @param event Log in button is pushed
     * @throws IOException
     */
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
            } else if (system.getOperatingStatus().equals("off")) {
                loginInstructions.setTextFill(Color.RED);
                loginInstructions.setText("Please power on system before proceeding");
            } else {
                system.getAccountManager().setLoggedInUser(currentAccount);
                changeWindowPassAccount(event, "view/CardHolder.fxml");
            }

        } else {
            loginInstructions.setText("Invalid username or password.");
        }
    }

    /**
     *
     * @param currentAccountNumber
     * @param currentPassword
     * @return
     */
    private UserAccount verifyLogin(String currentAccountNumber, String currentPassword) {
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
        if (system.getOperatingStatus().equals("off")) {
            loginInstructions.setTextFill(Color.RED);
            loginInstructions.setText("Please power on system before proceeding");
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/CreateAccount.fxml"));
        Parent createAccountParent = loader.load();
        CreateAccountController controller = loader.getController();
        controller.storeState(system);
        controller.initializeCustom();
        Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
        window.setScene(new Scene(createAccountParent));
        window.show();
    }

    public void showCardCreation(ActionEvent event) throws Exception {
        if (system.getOperatingStatus().equals("off")) {
            loginInstructions.setTextFill(Color.RED);
            loginInstructions.setText("Please power on system before proceeding");
        } else {
            changeWindow(event, "view/CreateCard.fxml");
        }
    }

    public void showRecovery(ActionEvent event) throws Exception {
        if (system.getOperatingStatus().equals("off")) {
            loginInstructions.setTextFill(Color.RED);
            loginInstructions.setText("Please power on system before proceeding");
        } else {
            changeWindow(event, "view/PasswordRecovery.fxml");
        }
    }

    public void closeButtonPushed(ActionEvent event) throws IOException {
        Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
        saveData();
        window.close();
    }
}
