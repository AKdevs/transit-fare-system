import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class CardController extends Controller implements Initializable {

    //private Card myCard;
    @FXML private Label cardNumber;
    @FXML private Label owner;
    @FXML private Label status;
    @FXML private Label balance;
    @FXML private Label alert;

    @FXML private Button load10;
    @FXML private Button load20;
    @FXML private Button load50;
    @FXML private Button travelSimulation;
    @FXML private Button logOut;
    @FXML private Button backToAccount;


    public void travelSimulationButtonPushed(ActionEvent event) throws IOException {
        changeWindow(event,"view/TravelSimulation.fxml" );
    }

    public void logOutButtonPushed(ActionEvent event) throws IOException {
        changeWindow(event,"view/Login.fxml" );
    }

    public void backToAccountButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view/CardHolder.fxml"));
        Parent cardParent = loader.load();

        Scene cardScene = new Scene(cardParent);

        CardHolderController cht = loader.getController();
        cht.storeState(system);
        Card card = system.getCardManager().findCard(cardNumber.getText());
        CardHolder cardHolder = card.getOwner();
        cht.initialCardHolderInfo(cardHolder.getAccountNum());

        // get the Stage info
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(cardScene);
        window.show();
    }


    public void initialize(URL url, ResourceBundle rb) {
    }


    void initialCardInfo(String cardNum) {
        Card card = this.system.getCardManager().findCard(cardNum);
        cardNumber.setText(cardNum);
        owner.setText(card.getOwner().getName());
        status.setText(card.getStatus());
        balance.setText(Double.toString(card.getBalance()));
    }

    void initialCardInfoWithoutAccount(String cardNum) {
        Card card = this.system.getCardManager().findCard(cardNum);
        cardNumber.setText(cardNum);
        owner.setText("unlinked");
        status.setText(card.getStatus());
        balance.setText(Double.toString(card.getBalance()));
        backToAccount.setText("Create an account");
    }

    void cardWithoutAccountCreateAccount(ActionEvent event) throws IOException{
        backToAccount.setOnAction(e -> alert.setText("Please press 'Home' Button"));
    }

    public void load20ButtonPushed(ActionEvent event) throws IOException {
        Card card = this.system.getCardManager().findCard(cardNumber.getText());
        card.addBalance(20.0);
        balance.setText(Double.toString(card.getBalance()));
    }

    public void load10ButtonPushed(ActionEvent event) throws IOException {
        Card card = this.system.getCardManager().findCard(cardNumber.getText());
        card.addBalance(10.0);
        balance.setText(Double.toString(card.getBalance()));
    }

    public void load50ButtonPushed(ActionEvent event) throws IOException {
        Card card = this.system.getCardManager().findCard(cardNumber.getText());
        card.addBalance(50.0);
        balance.setText(Double.toString(card.getBalance()));
    }
}
