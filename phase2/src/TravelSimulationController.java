import java.io.IOException;
import java.net.URL;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class TravelSimulationController extends Controller implements Initializable {
    private int source; // 0 if loader is CardHolder, 1 if loader is Card
    @FXML private Button tapIn;
    @FXML private Button tapOut;
    @FXML private Button backToCard;
    @FXML private Button ok1;
    @FXML private Button ok2;

    @FXML private Label cardNumber;
    @FXML private Label owner;
    @FXML private Label status;
    @FXML private Label balance;
    @FXML private Label enterDate;
    @FXML private Label exitDate;
    @FXML private Label exitType;
    @FXML private Label tapInInstructions;
    @FXML private Label tapOutInstructions;



    @FXML private ChoiceBox enterSpot;
    @FXML private ChoiceBox enterHour;
    @FXML private ChoiceBox enterMinute;
    @FXML private ChoiceBox<String> enterType;
    @FXML private ChoiceBox<String> enterTransitLine;

    @FXML private ChoiceBox exitSpot;
    @FXML private ChoiceBox exitHour;
    @FXML private ChoiceBox exitMinute;



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
        enterDate.setText(system.getCurrentDate());
        exitDate.setText(system.getCurrentDate());
        enterType.getItems().add("Subway");
        enterType.getItems().add("Bus");

        HashMap<String, TransitLine> transitLines = system.getTransitManager().getTransitLines();
        for (String id : transitLines.keySet()) {
                enterTransitLine.getItems().add(id);
            }

        if (enterHour.getItems().isEmpty()
                && enterMinute.getItems().isEmpty()
                && exitHour.getItems().isEmpty()
                && exitMinute.getItems().isEmpty()) {
            initializeTime();
        }
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
        String enterTime = enterHour.getSelectionModel().getSelectedItem().toString() + ":" + enterMinute.getSelectionModel().getSelectedItem().toString();
        if (associatedEntryCard.getBalance() < 0) {
            tapInInstructions.setText("Declined:\nyour card is out of funds,\n please load money.");
            TransitSystem.log(Level.ALL,"Declined: Your card is out of funds, please load money.");
        }else if (tapInTimeIllegal(enterHour.getValue().toString(), enterMinute.getValue().toString(), associatedEntryCard.getLastTapTime())){
            tapInInstructions.setText("Declined: Illegal tap in time.");
            TransitSystem.log(Level.ALL,"Declined: Illegal tap in time.");
        } else {
      system
          .getTripManager()
          .recordTapIn(
              enterTime,
              enterSpot.getSelectionModel().getSelectedItem().toString(),
              associatedEntryCard,
              enterDate.getText(),
              enterType.getSelectionModel().getSelectedItem().substring(0, 1),
              enterTransitLine.getValue());
      tapInInstructions.setText("");
        }
        balance.setText(Double.toString(associatedEntryCard.getBalance()));
    }

    @FXML
    void tapOutButtonPushed(ActionEvent event) throws IOException {
        String exitTime = exitHour.getSelectionModel().getSelectedItem().toString() + ":" + exitMinute.getSelectionModel().getSelectedItem().toString();
        Card associatedEntryCard = system.getCardManager().findCard(cardNumber.getText());
        // if the cardHolder was already asked to load money but the cardHolder didn't
        if (tapInInstructions.getText().equals("Declined:\nyour card is out of funds,\n please load money.")) {
      tapOutInstructions.setText("Declined:\nyour card is out of funds,\n please load money.");
            clearTapInfo();
            TransitSystem.log(Level.ALL,"Declined: Your card is out of funds, please load money.");
        }else {
            // if it is the first tap out and it's illegal
            if (associatedEntryCard.getLastTapTime().equals("00:00")) {
                system
                        .getTripManager()
                        .recordTapOut(
                                exitTime,
                                exitSpot.getSelectionModel().getSelectedItem().toString(),
                                associatedEntryCard,
                                exitDate.getText(),
                                exitType.getText());
                tapOutInstructions.setText("Illegal tap out");
                clearTapInfo();
                tapOutInstructions.setText("");
            }else if (tapOutTimeIllegal(exitHour.getValue().toString(), exitMinute.getValue().toString(), associatedEntryCard.getLastTapTime())){
                tapOutInstructions.setText("Declined: Illegal tap out time.");
                TransitSystem.log(Level.ALL,"Declined: Illegal tap out time.");
            } else {
                system
                        .getTripManager()
                        .recordTapOut(
                                exitTime,
                                exitSpot.getSelectionModel().getSelectedItem().toString(),
                                associatedEntryCard,
                                exitDate.getText(),
                                exitType.getText());
                tapOutInstructions.setText("Tap Out succeed");
                clearTapInfo();
                tapOutInstructions.setText("");

                enterType.getItems().add("Subway");
                enterType.getItems().add("Bus");

                HashMap<String, TransitLine> transitLines = system.getTransitManager().getTransitLines();
                for (String id : transitLines.keySet()) {
                    enterTransitLine.getItems().add(id);
                }

                if (enterHour.getItems().isEmpty()
                        && enterMinute.getItems().isEmpty()
                        && exitHour.getItems().isEmpty()
                        && exitMinute.getItems().isEmpty()) {
                    initializeTime();
                }
            }
        }
        balance.setText(Double.toString(associatedEntryCard.getBalance()));

    }


    void populateTransitLine(String type) {
        type.substring(0,1);
        HashMap<String, TransitLine> transitLines = system.getTransitManager().getTransitLines();
        for (String id : transitLines.keySet()) {
            if (transitLines.get(id).getType().equals(type)) {
                enterTransitLine.getItems().add(id);
            }
        }
    }



    /**
    void populateTransitLine(ActionEvent event) throws IOException {
        if (enterType.getSelectionModel().getSelectedItem() != null) {
            String type = enterType.getSelectionModel().getSelectedItem().substring(0, 1);
            HashMap<String, TransitLine> transitLines = system.getTransitManager().getTransitLines();
            for (String id : transitLines.keySet()) {
                if (transitLines.get(id).getType().equals(type)) {
                    enterTransitLine.getItems().add(id);
                }
            }
        }
    }
    */
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        enterType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> selected, String oldType, String newType) {
                if (newType != null) {
                    populateTransitLine(newType);
                }
    }
    });
    if (enterHour.getItems().isEmpty()
        && enterMinute.getItems().isEmpty()
        && exitHour.getItems().isEmpty()
        && exitMinute.getItems().isEmpty()) {
      initializeTime();
        }
    }

    void initializeTime() {
        for (int i = 0; i < 10; i ++) {
            String time = "0" + String.valueOf(i);
            enterHour.getItems().add(time);
            exitHour.getItems().add(time);
            enterMinute.getItems().add(time);
            exitMinute.getItems().add(time);
        }
        for (int i = 10; i < 25; i ++) {
            enterHour.getItems().add(String.valueOf(i));
            exitHour.getItems().add(String.valueOf(i));
        }

        for (int i = 10; i < 60; i ++) {
            enterMinute.getItems().add(String.valueOf(i));
            exitMinute.getItems().add(String.valueOf(i));
        }
    }

    @FXML
    void ok1ButtonPushed(ActionEvent event) throws IOException {
        String type = enterType.getValue().substring(0,1);
        exitType.setText(enterType.getValue());
        enterTransitLine.getItems().clear();
        enterSpot.getItems().clear();
        HashMap<String, TransitLine> transitLines = system.getTransitManager().getTransitLines();
        for (String id : transitLines.keySet()) {
            if (transitLines.get(id).getType().equals(type)) {
                enterTransitLine.getItems().add(id);
            }
        }
    }

    @FXML
    void ok2ButtonPushed(ActionEvent event) throws IOException {
        enterSpot.getItems().clear();
        exitSpot.getItems().clear();
        String lineId = enterTransitLine.getSelectionModel().getSelectedItem();
        HashMap<String, TransitLine> transitLines = system.getTransitManager().getTransitLines();
        for (String id : transitLines.keySet()) {
            if (id.equals(lineId)) {
                ArrayList<String> points = transitLines.get(id).getPoints();
                for (String pt : points) {
                    enterSpot.getItems().add(pt);
                    exitSpot.getItems().add(pt);
                }
            }
        }
    }

    boolean tapInTimeIllegal(String tapInHour, String tapInMinute, String lastTapTime) {
        String lastTapHour = lastTapTime.substring(0, 2);
        String lastTapMinute = lastTapTime.substring(3, 5);
        if (Integer.parseInt(tapInHour) < Integer.parseInt(lastTapHour)) {
            return true;
        }else if (Integer.parseInt(tapInHour) == Integer.parseInt(lastTapHour) && Integer.parseInt(tapInMinute) < Integer.parseInt(lastTapMinute)) {
            return true;
        }
        return false;
    }

    boolean tapOutTimeIllegal(String tapOutHour, String tapOutMinute, String lastTapTime) {
        String lastTapHour = lastTapTime.substring(0, 2);
        String lastTapMinute = lastTapTime.substring(3, 5);
        if (Integer.parseInt(tapOutHour) < Integer.parseInt(lastTapHour)) {
            return true;
        }else if (Integer.parseInt(tapOutHour) == Integer.parseInt(lastTapHour) && Integer.parseInt(tapOutMinute) < Integer.parseInt(lastTapMinute)) {
            return true;
        }
        return false;
    }

    void clearTapInfo() {
        enterType.getItems().clear();
        enterTransitLine.getItems().clear();
        enterHour.getItems().clear();
        exitHour.getItems().clear();
        enterMinute.getItems().clear();
        exitMinute.getItems().clear();
        enterSpot.getItems().clear();
        exitSpot.getItems().clear();
    }
}



