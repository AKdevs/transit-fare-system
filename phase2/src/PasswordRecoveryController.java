import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PasswordRecoveryController extends Controller {
    public void showLogin(ActionEvent event) throws IOException {
        changeWindow(event, "view/login.fxml");
    }
}
