import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;

public class CreateCardController extends Controller {

    @FXML
    VBox container;
    @FXML Button getCardButton;
    @FXML Label userMessage;

    public void createCard(ActionEvent event) {
        system.getCardManager().createCard();
        String createdCardNumber = system.getCardManager().getLastCard().getCardNumber();
        userMessage.setText("Congratulations, you new card is ready for use! \n Please use " +
                "the card number below to access your card's information.");
        getCardButton.setVisible(false);
        getCardButton.setManaged(false);
        Label cardMessage = new Label("Card Number: " + createdCardNumber);
        cardMessage.setFont(Font.font("Amble CN", FontWeight.BOLD, 24));
        Button exitButton = new Button("Exit");

        exitButton.setOnAction(e -> {
            try {
                changeWindow(e, "view/Login.fxml");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        container.getChildren().add(cardMessage);
        container.getChildren().add(exitButton);
    }

}
