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



public class CardController implements Initializable {
    Card card;
    @FXML private Label cardNumber;
    @FXML private Label owner;
    @FXML private Label status;
    @FXML private Label balance;

    @FXML private Button load10;
    @FXML private Button load20;
    @FXML private Button load50;
    @FXML private Button travelSimulation;


    //change scene method
    public void changeSceneButtonPushed(ActionEvent event) throws IOException {
        // new Scene
        Parent travelSimulationParent = FXMLLoader.load(getClass().getResource("TravelSimulation.fxml"));
        Scene TravelSimulationScene = new Scene(travelSimulationParent);

        // get the Stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(TravelSimulationScene);
        window.show();
    }


    public void initialize(URL url, ResourceBundle rb) {
        this.card = new Card();
        cardNumber.setText("Card: " + card.getCardNumber());
        owner.setText("Owner: " + card.getOwner());
        status.setText("status: activated");
        balance.setText("Balance: " + card.getBalance());
    }

    public void loadButtonPushed() {
        this.card.addBalance(20.0);
        balance.setText("Balance: " + card.getBalance());
    }
}
