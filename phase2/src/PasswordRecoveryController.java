import javafx.event.ActionEvent;
import java.io.IOException;

public class PasswordRecoveryController extends Controller {
    public void showLogin(ActionEvent event) throws IOException {
        changeWindow(event, "view/login.fxml");
    }
}
