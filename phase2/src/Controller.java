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
        passState(newWindowController);
        Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
        window.setScene(new Scene(newWindowParent));
        window.show();
    }

    void changeWindowPassAccount(ActionEvent event, String url) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
        Parent newWindowParent = loader.load();
        CardHolderController newWindowController = loader.getController();
        passState(newWindowController);
        newWindowController.initialCardHolderInfo();
        Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
        window.setScene(new Scene(newWindowParent));
        window.show();
    }

    void changeWindowToHome(ActionEvent event) throws IOException {
        changeWindow(event, "view/Login.fxml");
    }

    private void passState(Controller newWindowController) {
        if (newWindowController.system == null) {
            newWindowController.storeState(this.system);
        }
    }
}
