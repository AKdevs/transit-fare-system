import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CardHolderController extends Controller {

    /**
     *  Buttons, Labels, TextFields, and PasswordFields in CardHolder window
     */
    // label info
    @FXML private Button getCard;
    @FXML private Label namePrompt;
    @FXML private Label passwordPrompt;
    @FXML private Label securityPrompt;
    @FXML private TextField answer1Field;
    @FXML private TextField answer2Field;
    @FXML private ComboBox<String> question1Box;
    @FXML private ComboBox<String> question2Box;
    @FXML private Label accountNumber;
    @FXML private Label email;
    @FXML private TextField name;
    @FXML private PasswordField passField;
    @FXML private PasswordField confirmPassField;
    @FXML private Label monthlyCost;
    @FXML private Label transferBalanceInstructions;
    @FXML private Label linkCardInstructions;
    @FXML private Label accountBalance;
    @FXML private Label loadAccountBalanceInstructions;

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


    /**
     * Changes the scene to Card scene
     * @param event
     * @throws IOException
     */
    @FXML
    private void goToCardButtonPushed(ActionEvent event) throws IOException {
        if (cards.getSelectionModel().getSelectedItem() != null) {
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

    /**
     * Change the scene to pop up Create card
     * @param event
     * @throws IOException
     */
    @FXML
    void getCardButtonPushed(ActionEvent event) throws IOException {
        CardHolder loggedInUser =(CardHolder)system.getAccountManager().getLoggedInUser();
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/CreateCardPopup.fxml"));
        Parent layout = loader.load();
        CreateCardPopupController controller = loader.getController();
        controller.storeState(system);
        controller.createCard();
        controller.setStage(popupStage);
        popupStage.setScene(new Scene(layout));
        popupStage.showAndWait();
        updateCardBox();
    }

    /**
     * Update the cards in the box for card holder to choose
     */
    void updateCardBox() {
        CardHolder loggedInUser =(CardHolder)system.getAccountManager().getLoggedInUser();
        for (Card card: loggedInUser.getTravelCards()) {
            if (!cards.getItems().contains(card.getCardNumber())) {
                cards.getItems().add(card.getCardNumber());
                card1.getItems().add(card.getCardNumber());
                card2.getItems().add(card.getCardNumber());
            }
        }
        // cards = new ChoiceBox<>(FXCollections.observableArrayList(loggedInUser.getTravelCards()));
    }

    /**
     * Transfer the wanted balance from card1 to card2
     * @param event
     */
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

    /**
     * Log out from the account
     * @param event
     * @throws IOException
     */
    @FXML
    void logOutButtonPushed(ActionEvent event) throws IOException {
        system.getAccountManager().setLoggedInUser(null);
        changeWindow(event, "view/Login.fxml");
    }

    /**
     * Initialize the Card Holder Information
     */
    void initialCardHolderInfo() {
        CardHolder loggedInUser = (CardHolder) system.getAccountManager().getLoggedInUser();
        accountNumber.setText(loggedInUser.getAccountNum());
        name.setText(loggedInUser.getName());
        email.setText(loggedInUser.getEmail());
        passField.setText(loggedInUser.getPassword());
        confirmPassField.setText(loggedInUser.getPassword());
        initializeCustom();

        monthlyCost.setText("Monthly Cost:      " + Double.toString(loggedInUser.getMonthlyCost()));
        accountBalance.setText(Double.toString(loggedInUser.getAccountBalance()));
        updateCardBox();
    }


    /**
     * Initialize information of question index and answer list
     */
    public void initializeCustom() {
        ObservableList<String> elements = FXCollections.observableList(
                system.getAccountManager().
                        getPasswordManager().getQuestionList());
        UserAccount ch = system.getAccountManager().getLoggedInUser();
        question1Box.setItems(elements);
        question1Box.getSelectionModel().select(ch.getQuestionIndexList().get(0));
        question2Box.setItems(elements);
        question2Box.getSelectionModel().select(ch.getQuestionIndexList().get(1));
        answer1Field.setText(ch.getAnswerList().get(0));
        answer2Field.setText(ch.getAnswerList().get(1));
    }

    /**
     * Pops up the message to notice the action of change name
     * @param event
     * @throws IOException
     */
    @FXML
    void changeNameButtonPushed(ActionEvent event) throws IOException {
        if (name.getText().equals("")) {
            namePrompt.setTextFill(Color.RED);
            namePrompt.setText("Please enter a name!");
        } else {
            system.getAccountManager().getLoggedInUser().setName(name.getText());
            namePrompt.setTextFill(Color.GREEN);
            namePrompt.setText("Name successfully changed!");
        }
    }

    /**
     * Changes the password, and pops up the message for changing password
     * @param event
     * @throws IOException
     */
    @FXML
    void changePasswordButtonPushed(ActionEvent event) throws IOException {
        if (passField.getText().equals(confirmPassField.getText())) {
            system.getAccountManager().getLoggedInUser().setPassword(passField.getText());
            passwordPrompt.setTextFill(Color.GREEN);
            passwordPrompt.setText("Password successfully changed!");

        } else {
            passwordPrompt.setTextFill(Color.RED);
            passwordPrompt.setText("Passwords must match!");
        }
    }


    /**
     * Change the security questions
     * @param event
     * @throws IOException
     */
    @FXML
    void changeSecurityQuestionButtonPushed(ActionEvent event) throws IOException {
        if (!question1Box.getValue().equals(question2Box.getValue())) {
            UserAccount ch = system.getAccountManager().getLoggedInUser();
            String question1 = question1Box.getValue();
            String question2 = question2Box.getValue();
            List<Integer> userQuestionList = new ArrayList<>();
            List<String> answerList = new ArrayList<>();
            List<String> questions = system.getAccountManager().getPasswordManager()
                    .getQuestionList();
            userQuestionList.add(questions.indexOf(question1));
            userQuestionList.add(questions.indexOf(question2));
            answerList.add(answer1Field.getText());
            answerList.add(answer2Field.getText());
            ch.setAnswerList(answerList);
            ch.setQuestionIndexList(userQuestionList);
            securityPrompt.setTextFill(Color.GREEN);
            securityPrompt.setText("Questions successfully changed!");
        } else {
            securityPrompt.setTextFill(Color.RED);
            securityPrompt.setText("Questions cannot be the same!");
        }
    }


    /**
     * Link Card to the related account
     * @param event
     * @throws IOException
     */
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

    /**
     * Unlink a card from a account
     * @param event
     * @throws IOException
     */
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


    /**
     * Turn on the Auto load
     * @param event
     * @throws IOException
     */
    @FXML
    void turnOnButtonPushed(ActionEvent event) throws IOException {
        UserAccount ua = system.getAccountManager().findUserAccount(accountNumber.getText());
        CardHolder ch = (CardHolder)ua;
        ch.setAutoLoadStatus(1);
        accountBalance.setText(Double.toString(ch.getAccountBalance()));
        loadAccountBalanceInstructions.setText("");
    }

    /**
     * Turn off the auto load
     * @param event
     * @throws IOException
     */
    @FXML
    void turnOffButtonPushed(ActionEvent event) throws IOException {
        UserAccount ua = system.getAccountManager().findUserAccount(accountNumber.getText());
        CardHolder ch = (CardHolder)ua;
        ch.setAutoLoadStatus(0);
        accountBalance.setText(Double.toString(ch.getAccountBalance()));
    }

    /**
     * Load 10 dollars to account
     * @param event
     * @throws IOException
     */
    @FXML
    void load10ToAccountButtonPushed(ActionEvent event) throws IOException {
        UserAccount ua = system.getAccountManager().findUserAccount(accountNumber.getText());
        CardHolder ch = (CardHolder)ua;
        if (ch.getAutoLoadStatus() == 1) {
        double accBalance = ch.getAccountBalance();
        accountBalance.setText(Double.toString(accBalance + 10.0));
        ch.addAccountBalance(10.0);
        } else {
            loadAccountBalanceInstructions.setText("Please turn autoload\nservice on before loading.");
        }
    }

    /**
     * Load 20 dollars to account
     * @param event
     * @throws IOException
     */
    @FXML
    void load20ToAccountButtonPushed(ActionEvent event) throws IOException {
        UserAccount ua = system.getAccountManager().findUserAccount(accountNumber.getText());
        CardHolder ch = (CardHolder)ua;
        if (ch.getAutoLoadStatus() == 1) {
        double accBalance = ch.getAccountBalance();
        accountBalance.setText(Double.toString(accBalance + 20.0));
        ch.addAccountBalance(20.0);
        } else {
            loadAccountBalanceInstructions.setText("Please turn autoload\nservice on before loading.");
        }
    }

    /**
     * Load 50 dollars to account
     * @param event
     * @throws IOException
     */
    @FXML
    void load50ToAccountButtonPushed(ActionEvent event) throws IOException {
        UserAccount ua = system.getAccountManager().findUserAccount(accountNumber.getText());
        CardHolder ch = (CardHolder)ua;
        if (ch.getAutoLoadStatus() == 1) {
        double accBalance = ch.getAccountBalance();
        accountBalance.setText(Double.toString(accBalance + 50.0));
        ch.addAccountBalance(50.0);
        } else {
            loadAccountBalanceInstructions.setText("Please turn autoload\nservice on before loading.");
        }
    }




}
