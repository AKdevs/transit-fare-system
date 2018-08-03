import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class CreateAccountController extends Controller {
    @FXML private ComboBox<String> questionBox1;
    @FXML private ComboBox<String> questionBox2;
    @FXML private TextField answer1;
    @FXML private TextField answer2;
    @FXML private Label requiredInfoLabel;
    @FXML private TextField name;
    @FXML private TextField email;
    @FXML private TextField password;

    public void initializeComboBox() {
        ObservableList<String> elements = FXCollections.observableList(
                        system.getAccountManager().
                                getPasswordManager().getQuestionList());
        questionBox1.setItems(elements);
        questionBox2.setItems(elements);
    }

    public void createAccountButtonPushed(ActionEvent event) throws IOException {
        String currentName = name.getText();
        String currentEmail = email.getText();
        String currentPassword = password.getText();

        if (currentName.equals("") || currentEmail.equals("") ||
                currentPassword.equals("")) {
            requiredInfoLabel.setText("* Please fill all required fields");
        } else {
            system.getAccountManager().createCardHolderAccount(currentName,
                    currentEmail, currentPassword);
            Map<Integer, String> securityAnswers = new LinkedHashMap<>();
            String question1 = questionBox1.getValue();
            String question2 = questionBox2.getValue();
            List<String> questions = system.getAccountManager().getPasswordManager()
                    .getQuestionList();
            securityAnswers.put(questions.indexOf(question1), answer1.getText());
            securityAnswers.put(questions.indexOf(question2), answer2.getText());
            system.getAccountManager().getLastCreatedAccount().addSecurityAnswers(securityAnswers);
            changeWindow(event,"view/Login.fxml" );
        }
    }

    public void cancelButtonPushed(ActionEvent event) throws IOException {
        changeWindowToHome(event);
    }
}
