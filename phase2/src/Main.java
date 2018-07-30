import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        TransitSystem mainSystem = new TransitSystem();
        Parent root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
        primaryStage.setTitle("Login");
        Scene scene = new Scene(root, 534, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
