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
    @FXML private Label cardNumber;
    @FXML private Label name;
    @FXML private Label email;

    // choiceBox info
    @FXML private ChoiceBox cards;

    // Button info
    @FXML private Button viewMonthlyCost;
    @FXML private Button logOut;


    public void initialize(URL url, ResourceBundle rb) {

        viewMonthlyCost.setText("View Monthly Cost");
        logOut.setText("Log Out");

        cardNumber.setText("10000001"); // get this value from user input
        name.setText(""); // get this value from the manager
        email.setText(""); // get this value from the manager
        /*
        UserAccount ua = this.system.getAccountManager().findUserAccount(cardNumber.getText());
        ArrayList<Card> travelCards = ((CardHolder)ua).getTravelCards();
        for (Card card: travelCards) {
            cards.getItems().add(card.getCardNumber());
        }*/

        cards.getItems().add("30000001");

    }

    private void choiceBoxButtonPushed(ActionEvent event) throws IOException {

        //change to another scene

    }
}
