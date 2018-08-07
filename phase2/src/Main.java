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

        Controller controller = loginLoader.getController();
        controller.storeState(mainSystem);


        primaryStage.setTitle("Login");
        //Scene scene = new Scene(root, 534, 400);
        primaryStage.setScene(loginScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
