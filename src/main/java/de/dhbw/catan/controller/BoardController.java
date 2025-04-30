package de.dhbw.catan.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

public class BoardController {

    @FXML
    private Polygon hexDesert;
    @FXML
    private Polygon hexPastures1;
    @FXML
    private Polygon hexPastures2;
    @FXML
    private Polygon hexPastures3;
    @FXML
    private Polygon hexPastures4;
    @FXML
    private Polygon hexForest1;
    @FXML
    private Polygon hexForest2;
    @FXML
    private Polygon hexForest3;
    @FXML
    private Polygon hexForest4;
    @FXML
    private Polygon hexMountains1;
    @FXML
    private Polygon hexMountains2;
    @FXML
    private Polygon hexMountains3;
    @FXML
    private Polygon hexHills1;
    @FXML
    private Polygon hexHills2;
    @FXML
    private Polygon hexHills3;
    @FXML
    private Polygon hexFields1;
    @FXML
    private Polygon hexFields2;
    @FXML
    private Polygon hexFields3;
    @FXML
    private Polygon hexFields4;

    @FXML
    public void initialize() {
        Image imgDesert = new Image(getClass().getResource("/images/desert.png").toExternalForm());
        Image imgPastures = new Image(getClass().getResource("/images/pastures.jpg").toExternalForm());
        Image imgForest = new Image(getClass().getResource("/images/forest.jpg").toExternalForm());
        Image imgMountains = new Image(getClass().getResource("/images/mountains.jpg").toExternalForm());
        Image imgHills = new Image(getClass().getResource("/images/hills.jpg").toExternalForm());
        Image imgFields = new Image(getClass().getResource("/images/fields.jpg").toExternalForm());
        hexDesert.setFill(new ImagePattern(imgDesert));
        hexPastures1.setFill(new ImagePattern(imgPastures));
        hexPastures2.setFill(new ImagePattern(imgPastures));
        hexPastures3.setFill(new ImagePattern(imgPastures));
        hexPastures4.setFill(new ImagePattern(imgPastures));
        hexForest1.setFill(new ImagePattern(imgForest, 0, 0, 2, 1, true));
        hexForest2.setFill(new ImagePattern(imgForest, 0, 0, 2, 1, true));
        hexForest3.setFill(new ImagePattern(imgForest, 0, 0, 2, 1, true));
        hexForest4.setFill(new ImagePattern(imgForest, 0, 0, 2, 1, true));
        hexMountains1.setFill(new ImagePattern(imgMountains));
        hexMountains2.setFill(new ImagePattern(imgMountains));
        hexMountains3.setFill(new ImagePattern(imgMountains));
        hexHills1.setFill(new ImagePattern(imgHills));
        hexHills2.setFill(new ImagePattern(imgHills));
        hexHills3.setFill(new ImagePattern(imgHills));
        hexFields1.setFill(new ImagePattern(imgFields));
        hexFields2.setFill(new ImagePattern(imgFields));
        hexFields3.setFill(new ImagePattern(imgFields));
        hexFields4.setFill(new ImagePattern(imgFields));
    }
}
