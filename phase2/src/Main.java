import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        String path="serializedobjects.ser";
        TransitSystem mainSystem = new TransitSystem(path);
        mainSystem.readFromFile(path);
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("view/Login.fxml"));
        Parent root = loginLoader.load();
        Scene loginScene = new Scene(root);



        //LoginController loginController = loginLoader.getController();
        //loginController.storeState(mainSystem);

        Controller controller = loginLoader.getController();
        controller.storeState(mainSystem);


        primaryStage.setTitle("Login");
        //Scene scene = new Scene(root, 534, 400);
        primaryStage.setScene(loginScene);
        primaryStage.setResizable(false);
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
