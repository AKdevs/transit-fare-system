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

    // choiceBox info
    @FXML private ChoiceBox cards;

    // Button info
    @FXML private Button viewMonthlyCost;
    @FXML private Button logOut;
    @FXML private Button goToCard;


    public void initialize(URL url, ResourceBundle rb) {

        viewMonthlyCost.setText("View Monthly Cost");
        logOut.setText("Log Out");
        goToCard.setText("Go To Card");
        //monthlyCost.setText("");

    }


    @FXML
    private void goToCardButtonPushed(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view/Card.fxml"));
        Parent cardParent = loader.load();

        Scene cardScene = new Scene(cardParent);

        CardController ct = loader.getController();
        ct.initialCardInfo(cards.getSelectionModel().getSelectedItem().toString());

        // get the Stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(cardScene);
        window.show();

    }


    @FXML
    void viewMonthlyCostButtonPushed(ActionEvent event) {
        // should get from the account and it's manager
        //monthlyCost.setText(cards.getSelectionModel().getSelectedItem().toString());
    }

    @FXML
    private void logOutButtonPushed(ActionEvent event) throws IOException {


        //change to another scene
        // new Scene
        Parent logInParent = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
        Scene LogInScene = new Scene(logInParent);

        // get the Stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(LogInScene);
        window.show();

    }

    void initialCardHolderInfo(String accountNum) {
        accountNumber.setText(accountNum);
        System.out.println(system == null);
        //UserAccount ua = this.system.getAccountManager().findUserAccount(accountNum);

        //name.setText(ua.getName());
        //email.setText(ua.getEmail());
    }



}
