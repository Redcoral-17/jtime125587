package it.unicam.cs.mpgc.jtime125587;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Main.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("JBudget v1.0");
        stage.setScene(scene);
        stage.setMinWidth(914);
        stage.setMinHeight(617);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}