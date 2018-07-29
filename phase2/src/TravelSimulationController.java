import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class TravelSimulationController {
    /*
    @FXML private Button tapIn;
    @FXML private Button tapOut;
    @FXML private Button backToCard;
    */

    public void initialize(URL url, ResourceBundle rb) {
        /*this.tapIn.setText("Tap In");
        this.tapOut.setText("Tap Out");
        this.backToCard.setText("Back To Card");*/
    }

    //change scene method
    public void backToCardButtonPushed(ActionEvent event) throws IOException {
        // new Scene
        Parent cardParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene cardScene = new Scene(cardParent);

        // get the Stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(cardScene);
        window.show();
    }
}
