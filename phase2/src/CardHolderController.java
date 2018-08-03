import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CardHolderController extends Controller implements Initializable {

    // label info
    @FXML private Label accountNumber;
    @FXML private Label name;
    @FXML private Label email;
    @FXML private Label monthlyCost;
    @FXML private Label chooseCardInstructions;

    // choiceBox info
    @FXML private ChoiceBox cards;

    // Button info
    @FXML private Button viewMonthlyCost;
    @FXML private Button logOut;
    @FXML private Button goToCard;
    @FXML private Button linkCard;
    @FXML private Button unlinkCard;

    // textField info
    @FXML private TextField linkedCardNum;
    @FXML private TextField unlinkedCardNum;


    public void initialize(URL url, ResourceBundle rb) {
        viewMonthlyCost.setText("View Monthly Cost");
        logOut.setText("Log Out");
        goToCard.setText("Go To Card");
    }


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
        for (Card card: loggedInUser.getTravelCards()) {
            cards.getItems().add(card.getCardNumber());
        }
    }
    /*
    void initialCardHolderInfo(String accountNum) {
        accountNumber.setText(accountNum);
        UserAccount ua = system.getAccountManager().findUserAccount(accountNum);
        name.setText(ua.getName());
        email.setText(ua.getEmail());
        CardHolder ch = (CardHolder)ua;
        for (Card card: ch.getTravelCards()) {
            cards.getItems().add(card.getCardNumber());
        }
    }
    */

    @FXML
    void linkCardButtonPushed(ActionEvent event) throws IOException {
        String cardNum = linkedCardNum.getText();
        Card card = system.getCardManager().findCard(cardNum);
        UserAccount ua = system.getAccountManager().findUserAccount(accountNumber.getText());
        ((CardHolder)ua).linkCard(card);
        cards.getItems().add(cardNum);
    }

    @FXML
    void unlinkCardButtonPushed(ActionEvent event) throws IOException {
        String cardNum = unlinkedCardNum.getText();
        Card card = system.getCardManager().findCard(cardNum);
        UserAccount ua = system.getAccountManager().findUserAccount(accountNumber.getText());
        ((CardHolder)ua).unlinkCard(card);
        cards.getItems().remove(cardNum);
    }



}
