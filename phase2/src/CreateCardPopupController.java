import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CreateCardPopupController extends Controller {
    /**
     * Stage on which the pop up is set
     */
    private Stage stage;
    /**
     * Stores card number
     */
    @FXML Label cardNumber;

    /**
     * Creates a card, links it to the associated user account
     * and displays the card number on the screen
     */
    public void createCard() {
        system.getCardManager().createCard();
        Card createdCard = system.getCardManager().getLastCard();
        CardHolder loggedInUser =(CardHolder)system.getAccountManager().getLoggedInUser();
        loggedInUser.linkCard(createdCard);
        cardNumber.setText("Card Number: " + createdCard.getCardNumber());
    }

    /**
     * @param stage sets stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Changes to Login screen
     * @param event button push
     */
    public void closeButtonPushed(ActionEvent event) {
        stage.close();
    }

}
