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
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
        Parent newWindowParent = loader.load();
        Controller newWindowController = loader.getController();
        if (newWindowController.system == null) {
            newWindowController.storeState(this.system);
        }
        Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
        window.setScene(new Scene(newWindowParent));
        window.show();
    }

    /*
    private void passState() {
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("view/Login.fxml"));
        Parent root = loginLoader.load();
        LoginController loginController = loginLoader.getController();
        //loginController.storeState(mainSystem);
    }
    */
}
