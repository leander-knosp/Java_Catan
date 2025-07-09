package de.dhbw.catan;

import de.dhbw.catan.controller.MainGameController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Hauptklasse der Anwendung, die den Startpunkt für das JavaFX-Programm bildet.
 * 
 * Diese Klasse erbt von {@link javafx.application.Application} und startet
 * die Benutzeroberfläche, indem sie die erste Szene (Intro-Screen) über den
 * MainGameController lädt.
 */
public class Main extends Application {

    /**
     * Statische Referenz auf das primäre Fenster (Stage) der Anwendung,
     * sodass andere Klassen darauf zugreifen können.
     */
    public static Stage primaryStage;

    /**
     * Einstiegspunkt für die JavaFX-Anwendung.
     * 
     * @param primaryStage Das Hauptfenster der Anwendung, die von JavaFX bereitgestellt wird.
     */
    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;

        // Controller erzeugen und Intro-Bildschirm anzeigen
        MainGameController mainGameController = new MainGameController();
        mainGameController.showIntroScreen();
    }

    /**
     * Hauptmethode zum Starten der Java-Anwendung.
     * 
     * @param args Kommandozeilenargumente
     */
    public static void main(String[] args) {
        launch(args);
    }
}
