import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    TransitSystem system;

    void storeState(TransitSystem system) {
        this.system = system;
    }

    void changeWindow(ActionEvent event, String url) throws IOException {
        Parent createAccountParent = FXMLLoader.load(getClass().getResource(url));
        Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
        window.setScene(new Scene(createAccountParent));
        window.show();
    }
}
