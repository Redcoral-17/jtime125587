package it.unicam.cs.mpgc.jtime125587;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Applicazione JavaFX principale per JTime 125587.
 * Carica il layout da `Main.fxml`, configura la scena e mostra la finestra principale.
 */
public class HelloApplication extends Application {
    /**
     * Inizializza e mostra lo Stage principale.
     *
     * @param stage Stage principale fornito dal runtime JavaFX
     * @throws IOException se il file FXML non viene trovato o non può essere caricato
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Main.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("JTime");
        stage.setScene(scene);
        stage.setMinWidth(514);
        stage.setMinHeight(717);
        stage.show();
    }

    /**
     * Punto d'ingresso dell'applicazione Java.
     *
     * @param args argomenti della linea di comando
     */
    public static void main(String[] args) {
        launch();
    }
}