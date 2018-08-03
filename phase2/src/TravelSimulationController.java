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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class TravelSimulationController extends Controller {
    private int source; // 0 if loader is CardHolder, 1 if loader is Card
    @FXML private Button tapIn;
    @FXML private Button tapOut;
    @FXML private Button backToCard;

    @FXML private Label cardNumber;
    @FXML private Label owner;
    @FXML private Label status;
    @FXML private Label balance;

    @FXML private TextField enterSpot;
    @FXML private TextField enterTime;
    @FXML private TextField enterDate;
    @FXML private ChoiceBox enterType;

    @FXML private TextField exitSpot;
    @FXML private TextField exitTime;
    @FXML private TextField exitDate;
    @FXML private ChoiceBox exitType;



    void initialTravelSimulationInfo(String cardNum) {
        Card card = this.system.getCardManager().findCard(cardNum);
        cardNumber.setText(cardNum);
        status.setText(card.getStatus());
        balance.setText(Double.toString(card.getBalance()));
        if (card.getOwner() != null) {
            owner.setText(card.getOwner().getName());
        }else {
            owner.setText("unlinked");
        }
        enterType.getItems().add("Subway");
        enterType.getItems().add("Bus");
        exitType.getItems().add("Subway");
        exitType.getItems().add("Bus");
    }

    public void setSource(int source) {
        this.source = source;
    }

    //change scene method
    public void backToCardButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view/Card.fxml"));
        Parent cardParent = loader.load();

        Scene cardHolderScene = new Scene(cardParent);

        // read user input and set value in Card window
        CardController ct = loader.getController();
        ct.storeState(system);
        ct.initialCardInfo(cardNumber.getText());

        //ct.setSource(1);

        ct.setSource(source);


        // get the Stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(cardHolderScene);
        window.show();
    }

    @FXML
    void tapInButtonPushed(ActionEvent event) throws IOException {
        Card associatedEntryCard = system.getCardManager().findCard(cardNumber.getText());
        system.getTripManager().recordTapIn(
                        enterTime.getText(),
                        enterSpot.getText(),
                        associatedEntryCard,
                        enterDate.getText(),
                        enterType.getSelectionModel().getSelectedItem().toString().substring(0,1));
        balance.setText(Double.toString(associatedEntryCard.getBalance()));
    }

    @FXML
    void tapOutButtonPushed(ActionEvent event) throws IOException {
        Card associatedEntryCard = system.getCardManager().findCard(cardNumber.getText());
        system.getTripManager().recordTapOut(
                exitTime.getText(),
                exitSpot.getText(),
                associatedEntryCard,
                exitDate.getText(),
                exitType.getSelectionModel().getSelectedItem().toString().substring(0, 1));
        balance.setText(Double.toString(associatedEntryCard.getBalance()));
    }
}
