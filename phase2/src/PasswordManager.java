import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PasswordManager {
    private List<String> securityQuestions;
    private int minimumPasswordLength;


    PasswordManager() {
        securityQuestions = new ArrayList<>();
        securityQuestions.add("What city was your paternal grandfather born in?");
        securityQuestions.add("In what city did your mother and father meet?");
        securityQuestions.add("What was the name of your first pet?");
        securityQuestions.add("What was your childhood nickname?");


    }

    String getSecurityQuestion(int index) {
        return securityQuestions.get(index);
    }

    List<String> getQuestionList() {
        return securityQuestions;
    }
}
