import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PasswordManager implements Serializable{
    /**
     * Stores list of security questions available to user
     */
    private List<String> securityQuestions;

    /**
     * Creates instance and fills list of security questions
     */
    PasswordManager() {
        securityQuestions = new ArrayList<>();
        securityQuestions.add("What city was your paternal grandfather born in?");
        securityQuestions.add("In what city did your mother and father meet?");
        securityQuestions.add("What was the name of your first pet?");
        securityQuestions.add("What was your childhood nickname?");
    }

    /**
     * @return list of security questions
     */
    List<String> getQuestionList() {
        return securityQuestions;
    }
}
