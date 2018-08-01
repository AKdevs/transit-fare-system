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



public class CardController extends Controller implements Initializable {
    //private Card myCard;
    @FXML private Label cardNumber;
    @FXML private Label owner;
    @FXML private Label status;
    @FXML private Label balance;

    @FXML private Button load10;
    @FXML private Button load20;
    @FXML private Button load50;
    @FXML private Button travelSimulation;
    @FXML private Button home;
    @FXML private Button backToAccount;


    public void travelSimulationButtonPushed(ActionEvent event) throws IOException {
        changeWindow(event,"view/TravelSimulation.fxml" );
    }

    public void homeButtonPushed(ActionEvent event) throws IOException {
        changeWindow(event,"view/Login.fxml" );
    }

    public void backToAccountButtonPushed(ActionEvent event) throws IOException {
        changeWindow(event,"view/CardHolder.fxml" );
    }


    public void initialize(URL url, ResourceBundle rb) {
        //myCard = new Card();
    }


    void initialCardInfo(String cardNum) {
        //Card card = this.system.getCardManager().findCard(cardNum);
        cardNumber.setText(cardNum);
        //owner.setText(card.getOwner().getName());
        //status.setText(card.getStatus());
        //balance.setText(Double.toString(card.getBalance()));
    }

    public void loadButtonPushed() {
        //this.card.addBalance(20.0);

    }
}
