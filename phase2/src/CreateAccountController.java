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
    @FXML private Label promptLabel;
    @FXML private ComboBox<String> questionBox1;
    @FXML private ComboBox<String> questionBox2;
    @FXML private TextField answer1Field;
    @FXML private TextField answer2Field;
    @FXML private Label requiredInfoLabel;
    @FXML private TextField name;
    @FXML private TextField email;
    @FXML private TextField password;

    public void initializeCustom() {
        ObservableList<String> elements = FXCollections.observableList(
                        system.getAccountManager().
                                getPasswordManager().getQuestionList());
        questionBox1.setItems(elements);
        questionBox1.getSelectionModel().selectFirst();
        questionBox2.setItems(elements);
        questionBox2.getSelectionModel().select(1);
    }

    public void createAccountButtonPushed(ActionEvent event) throws IOException {
        String currentName = name.getText();
        String currentEmail = email.getText();
        String currentPassword = password.getText();
        String question1 = questionBox1.getValue();
        String question2 = questionBox2.getValue();
        String answer1 = answer1Field.getText();
        String answer2 = answer2Field.getText();

        if (currentName.equals("") || currentEmail.equals("") ||
                currentPassword.equals("") || answer1.equals("") ||
                answer2.equals("")) {
            promptLabel.setText("* Please fill all required fields");
        } else if(question1.equals(question2)) {
            promptLabel.setText("Please make sure your security questions are different!");
        } else {
            system.getAccountManager().createCardHolderAccount(currentName,
                    currentEmail, currentPassword);
            UserAccount currentAccount = system.getAccountManager().getLastCreatedAccount();
            List<Integer> userQuestionList = new ArrayList<>();
            List<String> answerList = new ArrayList<>();
            List<String> questions = system.getAccountManager().getPasswordManager()
                    .getQuestionList();
            userQuestionList.add(questions.indexOf(question1));
            userQuestionList.add(questions.indexOf(question2));
            answerList.add(answer1);
            answerList.add(answer2);
            currentAccount.setAnswerList(answerList);
            currentAccount.setQuestionIndexList(userQuestionList);


            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/CreateAccountSuccess.fxml"));
            Parent newWindowParent = loader.load();
            CreateAccountSuccessController controller = loader.getController();
            controller.storeState(system);
            controller.setAccountNumber();
            Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
            window.setScene(new Scene(newWindowParent));
            window.show();
        }
    }

    public void cancelButtonPushed(ActionEvent event) throws IOException {
        changeWindowToHome(event);
    }
}
