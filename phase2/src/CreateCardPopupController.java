import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CreateCardPopupController extends Controller {
    private Stage stage;
    @FXML Label cardNumber;
    public void createCard() {
        system.getCardManager().createCard();
        Card createdCard = system.getCardManager().getLastCard();
        CardHolder loggedInUser =(CardHolder)system.getAccountManager().getLoggedInUser();
        loggedInUser.linkCard(createdCard);
        cardNumber.setText("Card Number: " + createdCard.getCardNumber());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void closeButtonPushed(ActionEvent event) {
        stage.close();
    }

}
