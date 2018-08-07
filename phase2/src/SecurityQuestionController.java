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
    /**
     * Gives instructions to the user
     */
    @FXML Label infoLabel;
    /***
     * Sets the user's first security question
     */
    @FXML Label question1Label;
    /**
     * Sets the user's second security question
     */
    @FXML Label question2Label;
    /**
     * Records user's answer to first security question
     */
    @FXML TextField question1Answer;
    /**
     * Records user's answer to second security question
     */
    @FXML TextField question2Answer;

    /**
     * Displays the user's chosen security questions
     */
    public void initializeSecurityQuestions() {
        UserAccount loggedInUser = system.getAccountManager().getLoggedInUser();
        List<String> questionsList = system.getAccountManager().getPasswordManager().getQuestionList();
        List<Integer> userQuestionList = loggedInUser.getQuestionIndexList();
        String question1 = questionsList.get(userQuestionList.get(0));
        String question2 = questionsList.get(userQuestionList.get(1));
        question1Label.setText("Question 1: " + question1);
        question2Label.setText("Question 2: " + question2);
    }

    /**
     * Validates user answers to security questions and moves to the next screen
     * if accepted, or prompts them further if not accepted.
     * @param event triggers when continue button is pushed
     * @throws IOException
     */
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

    /**
     * Changes back to the Login screen
     * @param event triggers when cancel button is pushed
     * @throws IOException
     */
    public void cancelButtonPushed(ActionEvent event) throws IOException {
        changeWindowToHome(event);
    }
}
