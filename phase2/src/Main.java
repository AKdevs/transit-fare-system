import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        TransitSystem mainSystem = new TransitSystem();
        // Create base administrator, this will be moved to serialization
        mainSystem.getAccountManager().createAdminAccount("sysadmin", "admin@gmail.com", "sysadmin");

        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("view/Login.fxml"));
        Parent root = loginLoader.load();
        LoginController loginController = loginLoader.getController();
        loginController.storeState(mainSystem);
        primaryStage.setTitle("Login");
        Scene scene = new Scene(root, 534, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /*
    @Override
    public void start(Stage stage) throws Exception{

        TransitSystem mainSystem = new TransitSystem();

        mainSystem.getAccountManager().createAdminAccount("sysadmin", "admin@gmail.com", "sysadmin");
        mainSystem.getAccountManager().createCardHolderAccount("Isabel", "isabel@gmail.com", "123456");
        mainSystem.getAccountManager().createCardHolderAccount("Amy", "amy@gmail.com", "123456");
        mainSystem.getAccountManager().createCardHolderAccount("Yang", "yang@gmail.com", "123456");

        mainSystem.getCardManager().createCard();
        mainSystem.getCardManager().createCard();
        mainSystem.getCardManager().createCard();

        Card c1 = mainSystem.getCardManager().findCard("30000001");
        Card c2 = mainSystem.getCardManager().findCard("30000002");
        Card c3 = mainSystem.getCardManager().findCard("30000003");

        ((CardHolder)mainSystem.getAccountManager().findUserAccount("10000001")).linkCard(c1);
        ((CardHolder)mainSystem.getAccountManager().findUserAccount("10000001")).linkCard(c2);
        ((CardHolder)mainSystem.getAccountManager().findUserAccount("10000002")).linkCard(c3);

        Parent root = FXMLLoader.load(getClass().getResource("view/CardHolder.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }*/


    public static void main(String[] args) {
        launch(args);
    }

}
