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
        Parent root = FXMLLoader.load(getClass().getResource("view/CardHolder.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }*/


    public static void main(String[] args) {
        launch(args);
    }

}
