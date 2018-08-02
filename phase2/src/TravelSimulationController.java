import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class TravelSimulationController extends Controller {
    private int source; // 0 if loader is CardHolder, 1 if loader is Card
    @FXML private Button tapIn;
    @FXML private Button tapOut;
    @FXML private Button backToCard;

    @FXML private Label cardNumber;


    public void initialize(URL url, ResourceBundle rb) {

    }

    void initialTravelSimulationInfo(String cardNum) {
        cardNumber.setText(cardNum);
    }

    public void setSource(int source) {
        this.source = source;
    }

    //change scene method
    public void backToCardButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view/Card.fxml"));
        Parent cardHolderParent = loader.load();

        Scene cardHolderScene = new Scene(cardHolderParent);

        // read user input and set value in CardHolder window
        CardController ct = loader.getController();
        ct.storeState(system);
        ct.initialCardInfo(cardNumber.getText());
        ct.setSource(source);

        // get the Stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(cardHolderScene);
        window.show();
    }
}
