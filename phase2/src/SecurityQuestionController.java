import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecurityQuestionController extends Controller {

    public void initializeSecurityQuestions() {
        UserAccount loggedInUser = system.getAccountManager().getLoggedInUser();
        List<String> questionsList = system.getAccountManager().getPasswordManager().getQuestionList();
        Map<Integer, String> securityAnswers = loggedInUser.getSecurityAnswers();
    }
}
