import java.util.ArrayList;

public class PasswordManager {
    private ArrayList<String> securityQuestions;
    private int minimumPasswordLength;


    PasswordManager() {
        securityQuestions.add("What city was your paternal grandfather born in?");
        securityQuestions.add("In what city did your mother and father meet?");
        securityQuestions.add("What was the name of your first pet?");
        securityQuestions.add("What was your childhood nickname?");
    }

    String getSecurityQuestion(int index) {
        return securityQuestions.get(index);
    }
}
