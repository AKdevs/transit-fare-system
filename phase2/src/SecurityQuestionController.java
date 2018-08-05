import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecurityQuestionController extends Controller {
    @FXML Label infoLabel;
    @FXML Label question1Label;
    @FXML Label question2Label;
    @FXML TextField question1Answer;
    @FXML TextField question2Answer;
    public void initializeSecurityQuestions() {
        UserAccount loggedInUser = system.getAccountManager().getLoggedInUser();
        List<String> questionsList = system.getAccountManager().getPasswordManager().getQuestionList();
        List<Integer> userQuestionList = loggedInUser.getQuestionIndexList();
        String question1 = questionsList.get(userQuestionList.get(0));
        String question2 = questionsList.get(userQuestionList.get(1));
        question1Label.setText("Question 1: " + question1);
        question2Label.setText("Question 2: " + question2);
    }

    public void continueButtonPushed(ActionEvent event) throws IOException {
        UserAccount loggedInUser = system.getAccountManager().getLoggedInUser();
        String question1Ans = loggedInUser.getAnswerList().get(0);
        String question2Ans = loggedInUser.getAnswerList().get(1);
        String inputtedAns1 = question1Answer.getText();
        String inputtedAns2 = question2Answer.getText();
        if (question1Ans.equals(inputtedAns1) && question2Ans.equals(inputtedAns2)) {
            changeWindow(event, "view/PasswordReset.fxml");
        } else {
            infoLabel.setText("Please answer the following security questions so that we may verify your identity.\n" +
            "Incorrect security answers. Please try again.");
        }

    }

    public void cancelButtonPushed(ActionEvent event) throws IOException {
        changeWindowToHome(event);
    }
}
