import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;


public class CardController extends Controller implements Initializable {
    // 0 for Login, 1 for CardHolder
    private int source;
    //private Card myCard;
    @FXML private Label cardNumber;
    @FXML private Label owner;
    @FXML private Label status;
    @FXML private Label balance;
    @FXML private Label alert;

    @FXML private Button load10;
    @FXML private Button load20;
    @FXML private Button load50;
    @FXML private Button travelSimulation;
    @FXML private Button logOut;
    @FXML private Button backToAccount;


    public void setSource(int source) {
        this.source = source;
    }

    public void backButtonPushed(ActionEvent event) throws IOException {
        if (source == 0) {
            changeWindowToHome(event);
        } else if (source == 1) {
            backToAccountButtonPushed(event);
        }
    }

    public void travelSimulationButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view/travelSimulation.fxml"));
        Parent cardParent = loader.load();

        Scene cardScene = new Scene(cardParent);

        TravelSimulationController tsc = loader.getController();
        tsc.storeState(system);
        tsc.initialTravelSimulationInfo(cardNumber.getText());

        // get the Stage info
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(cardScene);
        window.show();
    }


    public void logOutButtonPushed(ActionEvent event) throws IOException {
        changeWindow(event,"view/Login.fxml" );
    }

    public void backToAccountButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view/CardHolder.fxml"));
        Parent cardParent = loader.load();

        Scene cardScene = new Scene(cardParent);

        CardHolderController cht = loader.getController();
        cht.storeState(system);
        Card card = system.getCardManager().findCard(cardNumber.getText());
        CardHolder cardHolder = card.getOwner();
        cht.initialCardHolderInfo(cardHolder.getAccountNum());

        // get the Stage info
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(cardScene);
        window.show();
    }


    public void initialize(URL url, ResourceBundle rb) {
    }


    void initialCardInfo(String cardNum) {
        Card card = this.system.getCardManager().findCard(cardNum);
        cardNumber.setText(cardNum);
        owner.setText(card.getOwner().getName());
        status.setText(card.getStatus());
        balance.setText(Double.toString(card.getBalance()));
    }

    void initialCardInfoWithoutAccount(String cardNum) {
        Card card = this.system.getCardManager().findCard(cardNum);
        cardNumber.setText(cardNum);
        owner.setText("unlinked");
        status.setText(card.getStatus());
        balance.setText(Double.toString(card.getBalance()));
    }
    

    public void load20ButtonPushed(ActionEvent event) throws IOException {
        Card card = this.system.getCardManager().findCard(cardNumber.getText());
        card.addBalance(20.0);
        balance.setText(Double.toString(card.getBalance()));
    }

    public void load10ButtonPushed(ActionEvent event) throws IOException {
        Card card = this.system.getCardManager().findCard(cardNumber.getText());
        card.addBalance(10.0);
        balance.setText(Double.toString(card.getBalance()));
    }

    public void load50ButtonPushed(ActionEvent event) throws IOException {
        Card card = this.system.getCardManager().findCard(cardNumber.getText());
        card.addBalance(50.0);
        balance.setText(Double.toString(card.getBalance()));
    }
}
