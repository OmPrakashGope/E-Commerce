package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Table .fxml"));
//// Call setRoot on the instance
//        fxmlLoader.setRoot(new AnchorPane());
//// Call the load() function on the file
//        Parent root = fxmlLoader.load();
//        Scene scene = new Scene(root);
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
//        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("E-cart");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
