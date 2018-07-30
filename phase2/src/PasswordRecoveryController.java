import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PasswordRecoveryController {
    public void showLogin(ActionEvent event) throws IOException {
        Parent loginNode = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
        Scene scene = new Scene(loginNode);
        Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
        window.setScene(scene);
        window.show();
    }
}
