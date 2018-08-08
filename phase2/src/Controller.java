import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    /**
     * Stores the instance of TransitSystem currently in operation
     */
    TransitSystem system;

    /**
     * Stores the TransitSystem instance and also serializes data
     * @param system TransitSystem instance
     * @throws IOException
     */
    void storeState(TransitSystem system) throws IOException {
        this.system = system;
        saveData();
    }

    /**
     * Changes scenes while passing the TransitSystem instance
     * Code idea was used from:
     * https://www.youtube.com/watch?v=XCgcQTQCfJQ
     * Author: Jaret Wright
     * @param event
     * @param url
     * @throws IOException
     */
    void changeWindow(ActionEvent event, String url) throws IOException {
        saveData();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
        Parent newWindowParent = loader.load();
        Controller newWindowController = loader.getController();
        passState(newWindowController);
        Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
        window.setScene(new Scene(newWindowParent));
        window.show();
    }

    /**
     * Changes window while passing the active account information to
     * the next scene
     * @param event
     * @param url FXML url of next window
     * @throws IOException
     */
    void changeWindowPassAccount(ActionEvent event, String url) throws IOException {
        saveData();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
        Parent newWindowParent = loader.load();
        CardHolderController newWindowController = loader.getController();
        passState(newWindowController);
        newWindowController.initialCardHolderInfo();
        Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
        window.setScene(new Scene(newWindowParent));
        window.show();
    }

    /**
     * Changes to Login screen
     * @param event
     * @throws IOException
     */
    void changeWindowToHome(ActionEvent event) throws IOException {
        saveData();
        changeWindow(event, "view/Login.fxml");
    }

    /**
     * Stores the instance of TransitSystem from one controller
     * into another
     * @param newWindowController
     * @throws IOException
     */
    private void passState(Controller newWindowController) throws IOException {
        if (newWindowController.system == null) {
            newWindowController.storeState(this.system);
        }
    }

    /**
     * Serializes data
     * @throws IOException
     */
    void saveData() throws IOException {
        system.saveToFile(system.getFilepath());
    }
}
