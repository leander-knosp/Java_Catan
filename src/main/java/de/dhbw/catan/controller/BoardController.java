package de.dhbw.catan.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

public class BoardController {

    @FXML
    private Polygon hexOcean, hexDesert, hexPastures1, hexPastures2, hexPastures3, hexPastures4, 
            hexForest1, hexForest2, hexForest3, hexForest4, hexMountains1, hexMountains2, hexMountains3,
            hexHills1, hexHills2, hexHills3, hexFields1, hexFields2, hexFields3, hexFields4;

    @FXML
    private AnchorPane circ2, circ3a, circ3b, circ4a, circ4b, circ5a, circ5b, circ6a, circ6b, circ8a, circ8b,
            circ9a, circ9b, circ10a, circ10b, circ11a, circ11b, circ12;

    @FXML
    private AnchorPane boardPane;
    
    @FXML
    public void initialize() {

        List<Polygon> hexList = List.of(hexPastures1, hexPastures2, hexPastures3, hexPastures4,
                hexForest1, hexForest2, hexForest3, hexForest4, hexMountains1, hexMountains2, hexMountains3,
                hexHills1, hexHills2, hexHills3, hexFields1, hexFields2, hexFields3, hexFields4);

        List<AnchorPane> circList = List.of(circ2, circ3a, circ3b, circ4a, circ4b, circ5a, circ5b,
                circ6a, circ6b, circ8a, circ8b, circ9a, circ9b, circ10a, circ10b, circ11a, circ11b, circ12);
        
        List<Double> posX = hexList.stream().map(Polygon::getLayoutX).collect(Collectors.toList());
        List<Double> posY = hexList.stream().map(Polygon::getLayoutY).collect(Collectors.toList());

        List<Polygon> shuffledPoly = new ArrayList<>(hexList);
        List<AnchorPane> shuffledCirc = new ArrayList<>(circList);
        Collections.shuffle(shuffledCirc);
        Collections.shuffle(shuffledPoly);
        
        // Vertauschte Polygone auf die ursprünglichen Positionen setzen
        for (int i = 0; i < hexList.size(); i++) {
            Polygon poly = shuffledPoly.get(i);
            AnchorPane circ = shuffledCirc.get(i);
            poly.setLayoutX(posX.get(i));
            poly.setLayoutY(posY.get(i));
            circ.setLayoutX(posX.get(i) - 24);
            circ.setLayoutY(posY.get(i) - 24);
        }        

        Image imgOcean = new Image(getClass().getResource("/images/ocean.png").toExternalForm());
        Image imgDesert = new Image(getClass().getResource("/images/desert.jpg").toExternalForm());
        Image imgPastures = new Image(getClass().getResource("/images/pastures.jpg").toExternalForm());
        Image imgForest = new Image(getClass().getResource("/images/forest.jpg").toExternalForm());
        Image imgMountains = new Image(getClass().getResource("/images/mountains.jpg").toExternalForm());
        Image imgHills = new Image(getClass().getResource("/images/hills.jpg").toExternalForm());
        Image imgFields = new Image(getClass().getResource("/images/fields.jpg").toExternalForm());

        hexOcean.setFill(new ImagePattern(imgOcean, 0, 0, 0.25, 0.25, true));
        hexDesert.setFill(new ImagePattern(imgDesert));
        hexPastures1.setFill(new ImagePattern(imgPastures));
        hexPastures2.setFill(new ImagePattern(imgPastures));
        hexPastures3.setFill(new ImagePattern(imgPastures));
        hexPastures4.setFill(new ImagePattern(imgPastures));
        hexForest1.setFill(new ImagePattern(imgForest));
        hexForest2.setFill(new ImagePattern(imgForest));
        hexForest3.setFill(new ImagePattern(imgForest));
        hexForest4.setFill(new ImagePattern(imgForest));
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
