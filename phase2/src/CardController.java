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
    @FXML private Label recentTrips;

    @FXML private Button load10;
    @FXML private Button load20;
    @FXML private Button load50;
    @FXML private Button travelSimulation;
    @FXML private Button viewRecentTrips;


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
        loader.setLocation(getClass().getResource("view/TravelSimulation.fxml"));
        Parent travelSimulationParent = loader.load();

        Scene travelSimulationScene = new Scene(travelSimulationParent);

        TravelSimulationController tsc = loader.getController();
        tsc.storeState(system);
        tsc.setSource(source);
        tsc.initialTravelSimulationInfo(cardNumber.getText());

        // get the Stage info
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(travelSimulationScene);
        window.show();
    }


    public void logOutButtonPushed(ActionEvent event) throws IOException {
        changeWindow(event,"view/Login.fxml" );
    }

    public void backToAccountButtonPushed(ActionEvent event) throws IOException {
        changeWindowPassAccount(event, "view/CardHolder.fxml");
    }

    public void initialize(URL url, ResourceBundle rb) {
    }


    void initialCardInfo(String cardNum) {
        Card card = this.system.getCardManager().findCard(cardNum);
        cardNumber.setText(cardNum);
        status.setText(card.getStatus());
        balance.setText(Double.toString(card.getBalance()));
        if (card.getOwner() != null) {
            owner.setText(card.getOwner().getName());
        }else {
            owner.setText("not linked");
        }
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

    @FXML
    void viewRecentTripsPushed(ActionEvent event) throws IOException {
        Card card = this.system.getCardManager().findCard(cardNumber.getText());
        recentTrips.setText(card.viewMostRecentTrips());
    }
}
