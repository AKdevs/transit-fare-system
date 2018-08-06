import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CardHolderController extends Controller {

    // label info
    @FXML private Label accountNumber;
    @FXML private Label name;
    @FXML private TextField email;
    @FXML private PasswordField passField;
    @FXML private PasswordField confirmPassField;
    @FXML private Label monthlyCost;
    @FXML private Label chooseCardInstructions;
    @FXML private Label transferBalanceInstructions;
    @FXML private Label linkCardInstructions;
    @FXML private Label autoLoadStatus;
    @FXML private Label accountBalance;
    @FXML private Label autoLoadHeader;
    @FXML private Label accountBalanceHeader;

    // choiceBox info
    @FXML private ChoiceBox cards;
    @FXML private ChoiceBox card1;
    @FXML private ChoiceBox card2;

    // Button info
    @FXML private Button viewMonthlyCost;
    @FXML private Button logOut;
    @FXML private Button goToCard;
    @FXML private Button linkCard;
    @FXML private Button unlinkCard;
    @FXML private Button transfer;
    @FXML private Button turnOn;
    @FXML private Button turnOff;
    @FXML private Button load10ToAccount;
    @FXML private Button load20ToAccount;
    @FXML private Button load50ToAccount;

    // textField info
    @FXML private TextField linkedCardNum;
    @FXML private TextField unlinkedCardNum;
    @FXML private TextField amount;



    @FXML
    private void goToCardButtonPushed(ActionEvent event) throws IOException {
        if (cards.getSelectionModel().getSelectedItem() == null) {
            chooseCardInstructions.setText("Please choose a card from below");
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/Card.fxml"));
            Parent cardParent = loader.load();

            Scene cardScene = new Scene(cardParent);

            CardController ct = loader.getController();
            ct.storeState(system);
            ct.setSource(1);
            ct.initialCardInfo(cards.getSelectionModel().getSelectedItem().toString());

            // get the Stage info
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(cardScene);
            window.show();
        }

    }

    @FXML
    void transferButtonPushed(ActionEvent event) {
        String c1Num = card1.getSelectionModel().getSelectedItem().toString();
        String c2Num = card2.getSelectionModel().getSelectedItem().toString();
        double balance = Double.parseDouble(amount.getText());
        Card c1 = system.getCardManager().findCard(c1Num);
        Card c2 = system.getCardManager().findCard(c2Num);
        CardHolder ch = (CardHolder) system.getAccountManager().findUserAccount(accountNumber.getText());
        transferBalanceInstructions.setText(ch.transferBalance(c1, c2, balance));
    }


    @FXML
    void viewMonthlyCostButtonPushed(ActionEvent event) {
        UserAccount ua = system.getAccountManager().findUserAccount(accountNumber.getText());
        CardHolder ch = (CardHolder)ua;
        monthlyCost.setText(Double.toString(ch.getMonthlyCost()));
    }

    @FXML
    void logOutButtonPushed(ActionEvent event) throws IOException {
        system.getAccountManager().setLoggedInUser(null);
        changeWindow(event, "view/Login.fxml");
    }

    void initialCardHolderInfo() {
        CardHolder loggedInUser = (CardHolder) system.getAccountManager().getLoggedInUser();
        accountNumber.setText(loggedInUser.getAccountNum());
        name.setText(loggedInUser.getName());
        email.setText(loggedInUser.getEmail());
        passField.setText(loggedInUser.getPassword());
        confirmPassField.setText(loggedInUser.getPassword());
        monthlyCost.setText("Monthly Cost:      " + Double.toString(loggedInUser.getMonthlyCost()));
        for (Card card: loggedInUser.getTravelCards()) {
            cards.getItems().add(card.getCardNumber());
            card1.getItems().add(card.getCardNumber());
            card2.getItems().add(card.getCardNumber());
        }

        if (autoLoadStatus.getText().equals("ON")) {
            accountBalanceHeader.setText("Account Balance: ");
            accountBalance.setText(Double.toString(loggedInUser.getAccountBalance()));
            autoLoadHeader.setText("Load Account Balance");
            load10ToAccount.setText("Load $10");
            load20ToAccount.setText("Load $20");
            load50ToAccount.setText("Load $50");
        }

    }

    @FXML
    void linkCardButtonPushed(ActionEvent event) throws IOException {
        String cardNum = linkedCardNum.getText();
        Card card = system.getCardManager().findCard(cardNum);
        if (card == null) {
            linkCardInstructions.setText("Card " + cardNum + "\ndoesn't exist");
    } else {
      UserAccount ua = system.getAccountManager().findUserAccount(accountNumber.getText());
      if (!cards.getItems().contains(cardNum)) {
          ((CardHolder) ua).linkCard(card);
        cards.getItems().add(cardNum);
        card1.getItems().add(cardNum);
        card2.getItems().add(cardNum);
          linkCardInstructions.setText("Card " + cardNum + "\n is now linked\nto your account");
      }else {
          linkCardInstructions.setText("You have already linked\nCard " + cardNum + "\nto your account");
      }
        }
        linkedCardNum.setText("");

    }

    @FXML
    void unlinkCardButtonPushed(ActionEvent event) throws IOException {
        String cardNum = unlinkedCardNum.getText();
        Card card = system.getCardManager().findCard(cardNum);
        if (card == null) {
            linkCardInstructions.setText("Card " + cardNum + "\ndoesn't exist");
    } else {
      UserAccount ua = system.getAccountManager().findUserAccount(accountNumber.getText());
      if (cards.getItems().contains(cardNum)) {
          ((CardHolder) ua).unlinkCard(card);
        cards.getItems().remove(cardNum);
        card1.getItems().remove(cardNum);
        card2.getItems().remove(cardNum);
          linkCardInstructions.setText("Card " + cardNum + "\n is now unlinked\nfrom your account");
      } else {
        linkCardInstructions.setText(
            "Card " + cardNum + "\nis not linked to your account");
      }
    }
        unlinkedCardNum.setText("");
    }


    @FXML
    void turnOnButtonPushed(ActionEvent event) throws IOException {
        autoLoadStatus.setText("ON");
        UserAccount ua = system.getAccountManager().findUserAccount(accountNumber.getText());
        CardHolder ch = (CardHolder)ua;
        ch.setAutoLoadStatus(1);
        accountBalanceHeader.setText("Account Balance: ");
        accountBalance.setText(Double.toString(ch.getAccountBalance()));
        autoLoadHeader.setText("Load Account Balance");
        load10ToAccount.setText("Load $10");
        load20ToAccount.setText("Load $20");
        load50ToAccount.setText("Load $50");
    }

    @FXML
    void turnOffButtonPushed(ActionEvent event) throws IOException {
        autoLoadStatus.setText("OFF");
        UserAccount ua = system.getAccountManager().findUserAccount(accountNumber.getText());
        CardHolder ch = (CardHolder)ua;
        ch.setAutoLoadStatus(0);
        accountBalanceHeader.setText("");
        accountBalance.setText("");
        autoLoadHeader.setText("");
        load10ToAccount.setText("");
        load20ToAccount.setText("");
        load50ToAccount.setText("");
    }

    @FXML
    void load10ToAccountButtonPushed(ActionEvent event) throws IOException {
        UserAccount ua = system.getAccountManager().findUserAccount(accountNumber.getText());
        CardHolder ch = (CardHolder)ua;
        double accBalance = ch.getAccountBalance();
        accountBalance.setText(Double.toString(accBalance + 10.0));
        ch.addAccountBalance(10.0);
    }

    @FXML
    void load20ToAccountButtonPushed(ActionEvent event) throws IOException {
        UserAccount ua = system.getAccountManager().findUserAccount(accountNumber.getText());
        CardHolder ch = (CardHolder)ua;
        double accBalance = ch.getAccountBalance();
        accountBalance.setText(Double.toString(accBalance + 20.0));
        ch.addAccountBalance(20.0);
    }

    @FXML
    void load50ToAccountButtonPushed(ActionEvent event) throws IOException {
        UserAccount ua = system.getAccountManager().findUserAccount(accountNumber.getText());
        CardHolder ch = (CardHolder)ua;
        double accBalance = ch.getAccountBalance();
        accountBalance.setText(Double.toString(accBalance + 50.0));
        ch.addAccountBalance(50.0);
    }




}
