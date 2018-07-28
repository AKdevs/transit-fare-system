import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public void showRecovery(ActionEvent event) throws Exception {
        Parent rootParent = FXMLLoader.load(getClass().getResource("view/PasswordRecovery.fxml"));
        Scene rootScene = new Scene(rootParent);

        Stage window = (Stage) (((Node)event.getSource()).getScene().getWindow());
        window.setScene(rootScene);
        window.show();
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
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
