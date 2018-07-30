import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CardHolderController {

    // label info
    @FXML private Label cardHolder;
    @FXML private Label cardNumber;
    @FXML private Label name;
    @FXML private Label email;
    @FXML private Label travelCards;

    // choiceBox info
    @FXML private ChoiceBox cards;

    private void choiceBoxButtonPushed(ActionEvent event) throws IOException {

        //change to another scene

    }

    public void initialize(URL url, ResourceBundle rb) {
        String cn = cardNumber.getText();
    System.out.println(cn);
    }
}
