package de.dhbw.catan.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Circle;

public class BoardController {

    @FXML
    private Polygon hexOcean, hexDesert, hexPastures1, hexPastures2, hexPastures3, hexPastures4, 
            hexForest1, hexForest2, hexForest3, hexForest4, hexMountains1, hexMountains2, hexMountains3,
            hexHills1, hexHills2, hexHills3, hexFields1, hexFields2, hexFields3, hexFields4;
    
    private List<Polygon> hexList;
    private List<Double> posX;
    private List<Double> posY;

    @FXML
    private AnchorPane circ2, circ3a, circ3b, circ4a, circ4b, circ5a, circ5b, circ6a, circ6b, circ8a, circ8b,
            circ9a, circ9b, circ10a, circ10b, circ11a, circ11b, circ12;

    @FXML
    private AnchorPane boardPane;
    
    @FXML
    public void initialize() {

        hexList = List.of(hexPastures1, hexPastures2, hexPastures3, hexPastures4,
                hexForest1, hexForest2, hexForest3, hexForest4, hexMountains1, hexMountains2, hexMountains3,
                hexHills1, hexHills2, hexHills3, hexFields1, hexFields2, hexFields3, hexFields4);

        List<AnchorPane> circList = List.of(circ2, circ3a, circ3b, circ4a, circ4b, circ5a, circ5b,
                circ6a, circ6b, circ8a, circ8b, circ9a, circ9b, circ10a, circ10b, circ11a, circ11b, circ12);
        
        posX = hexList.stream().map(Polygon::getLayoutX).collect(Collectors.toList());
        posY = hexList.stream().map(Polygon::getLayoutY).collect(Collectors.toList());

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

    @FXML
    public void showCornerPoints() {

        // Set<String> uniqueCornerSet = new HashSet<>();
        // List<double[]> allHexCorners = new ArrayList<>();

        // for (Polygon hex : hexList) {
        //     Observablepoints = hex.getPoints();
        //     double layoutX = hex.getLayoutX();
        //     double layoutY = hex.getLayoutY();

        //     for (int i = 0; i < points.size(); i += 2) {
        //         double localX = layoutX + points.get(i);
        //         double localY = layoutY + points.get(i + 1);

        //         javafx.geometry.Point2D scenePoint = hex.localToParent(localX, localY);

        //         double x = scenePoint.getX();
        //         double y = scenePoint.getY();
        //         // Schlüssel zur Identifikation (z. B. gerundet auf 3 Dezimalstellen)
        //         String key = String.format("%.3f_%.3f", x, y);

        //         if (uniqueCornerSet.add(key)) {
        //             allHexCorners.add(new double[] { x, y });
        //             Circle point = new Circle(x, y, 8);
        //             point.setFill(javafx.scene.paint.Color.WHITE);
        //             boardPane.getChildren().add(point);
        //         }
        //     }
        // }

        // for (double[] corner : allHexCorners) {
        //     System.out.println("Ecke bei: x = " + corner[0] + ", y = " + corner[1]);
        // }

        List<double[]> offsets = List.of(
            new double[] { -64.95, -37.5 },
            new double[] { -64.95, 37.5 },
            new double[] { 0, 75.0 },
            new double[] { 64.95, 37.5 },
            new double[] { 64.95, -37.5 },
            new double[] { 0, -75.0 }
        );  
        
        Set<String> uniquePoints = new HashSet<>();
        
        for (int i = 0; i < hexList.size(); i++) {
            for (double[] offset : offsets) {
                double x = posX.get(i) + offset[0] * hexList.get(i).getScaleX();
                double y = posY.get(i) + offset[1] * hexList.get(i).getScaleY();
        
                String key = String.format("%.3f_%.3f", x, y);
                if (uniquePoints.add(key)) {
                    Circle point = new Circle(x, y, 8);
                    point.setFill(javafx.scene.paint.Color.WHITE);
                    boardPane.getChildren().add(point);
                }
            }
        }         
    }
}
