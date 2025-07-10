package de.dhbw.catan.controller;

import de.dhbw.catan.model.Board;
import de.dhbw.catan.model.Player;
import de.dhbw.catan.model.Robber;
import de.dhbw.catan.model.Tile;
import de.dhbw.catan.model.TileType;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.control.OverrunStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

public class BoardController {

    private MainGameController mainGameController;

    public void setMainGameController(MainGameController mainGameController) {
        this.mainGameController = mainGameController;
        this.mainGameController.setBoard(this.board);
    }

    @FXML
    private Polygon hexOcean, hexDesert, hexPastures1, hexPastures2, hexPastures3, hexPastures4,
            hexForest1, hexForest2, hexForest3, hexForest4, hexMountains1, hexMountains2, hexMountains3,
            hexHills1, hexHills2, hexHills3, hexFields1, hexFields2, hexFields3, hexFields4;

    @FXML
    private AnchorPane circ2, circ3a, circ3b, circ4a, circ4b, circ5a, circ5b, circ6a, circ6b, circ8a,
            circ8b, circ9a, circ9b, circ10a, circ10b, circ11a, circ11b, circ12;

    @FXML
    private AnchorPane boardPane, sidebar;

    @FXML
    private VBox scoreboardBox;

    private Board board;
    private Robber robber;
    private ImageView robberImageView;
    private Player currentPlayer;
    private BuildController buildController;

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public List<Tile> getTiles() {
        return board.getTiles();
    }

    public AnchorPane getBoardPane() {
        return boardPane;
    }

    public List<Polygon> makeHexList() {
        return List.of(
                hexPastures1, hexPastures2, hexPastures3, hexPastures4,
                hexForest1, hexForest2, hexForest3, hexForest4,
                hexMountains1, hexDesert, hexMountains2, hexMountains3,
                hexHills1, hexHills2, hexHills3,
                hexFields1, hexFields2, hexFields3, hexFields4);
    }

    @FXML
    public void initialize() {
        List<Polygon> hexes = this.makeHexList();

        List<AnchorPane> tokens = List.of(circ2, circ3a, circ3b, circ4a, circ4b, circ5a, circ5b,
                circ6a, circ6b, circ8a, circ8b, circ9a, circ9b, circ10a, circ10b, circ11a, circ11b, circ12);

        board = new Board(hexes, tokens);
        board.setController(this);

        applyImages();
        positionTiles();
        loadSubComponent();
        initializeRobber();
    }

    // public void initializePlayer(Player player) {
    // this.currentPlayer = player;
    // if (this.buildController != null) {
    // this.buildController.initializePlayer(player);
    // }
    // System.out.println("Hier initializePlayer " + this.currentPlayer);
    // }
    private List<Player> players;
    private int currentPlayerIndex = 0;

    public void initializePlayers(List<Player> players) {
        this.players = players;
        this.currentPlayerIndex = 0;
        this.currentPlayer = players.get(0);

        if (this.buildController != null) {
            this.buildController.initializePlayer(this.currentPlayer);
        }
        System.out.println("Initialisiere Spieler:");
        for (Player p : players) {
            System.out.println("- " + p.getName() + " (" + p.getColor() + ")");
        }
        updateScoreboard();
    }

    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        currentPlayer = players.get(currentPlayerIndex);
        buildController.initializePlayer(currentPlayer);
        System.out.println("Jetzt am Zug: " + currentPlayer.getName());
    }

    private void initializeRobber() {
        robber = board.getRobber(); // statt eigenes Feld
        int desertIndex = -1;
        for (Tile tile : board.getTiles()) {
            // System.out.println(tile.getType());
        }

        for (int i = 0; i < board.getTiles().size(); i++) {
            if (board.getTiles().get(i).getType() == TileType.DESERT) {
                desertIndex = i;
                break;
            }
        }

        if (desertIndex == -1) {
            throw new IllegalStateException("Kein Desert-Tile gefunden");
        }

        robber.move(desertIndex);

        // Robber-Bild erstellen
        Image robberImage = new Image(getClass().getResource("/images/robber.png").toExternalForm());
        robberImageView = new ImageView(robberImage);
        robberImageView.setFitWidth(40);
        robberImageView.setFitHeight(40);

        boardPane.getChildren().add(robberImageView);

        updateRobberPosition();
    }

    public void updateRobberPosition() {
        Robber robber = board.getRobber(); // 🔁
        Tile robberTile = board.getTiles().get(robber.getPosition());
        Polygon hex = robberTile.getShape();

        robberImageView.setLayoutX(hex.getLayoutX() - robberImageView.getFitWidth() / 2);
        robberImageView.setLayoutY(hex.getLayoutY() - robberImageView.getFitHeight() / 2);

        // System.out.println("Räuber ist jetzt auf Tile " + robber.getPosition());
    }

    public void moveRobberTo(int newPosition) {
        board.getRobber().move(newPosition); // 🔁
        updateRobberPosition();
        // System.out.println("Räuber bewegt zu Position " + newPosition);
    }

    private void positionTiles() {
        List<Double> posX = board.getTiles().stream().map(t -> t.getShape().getLayoutX()).collect(Collectors.toList());
        List<Double> posY = board.getTiles().stream().map(t -> t.getShape().getLayoutY()).collect(Collectors.toList());

        int tokenIndex = 0;

        for (int i = 0; i < board.getTiles().size(); i++) {
            Polygon shape = board.getTiles().get(i).getShape();

            shape.setLayoutX(posX.get(i));
            shape.setLayoutY(posY.get(i));

            if (board.getTiles().get(i).getType() != TileType.DESERT) {
                AnchorPane token = board.getNumberTokens().get(tokenIndex);
                token.setLayoutX(posX.get(i) - 24);
                token.setLayoutY(posY.get(i) - 24);
                tokenIndex++;
            }
        }
    }

    private void applyImages() {
        var patterns = Map.of(
                TileType.OCEAN,
                new ImagePattern(new Image(getClass().getResource("/images/ocean.png").toExternalForm()), 0, 0, 0.25,
                        0.25, true),
                TileType.DESERT,
                new ImagePattern(new Image(getClass().getResource("/images/desert.jpg").toExternalForm())),
                TileType.PASTURES,
                new ImagePattern(new Image(getClass().getResource("/images/pastures.jpg").toExternalForm())),
                TileType.FOREST,
                new ImagePattern(new Image(getClass().getResource("/images/forest.jpg").toExternalForm())),
                TileType.MOUNTAINS,
                new ImagePattern(new Image(getClass().getResource("/images/mountains.jpg").toExternalForm())),
                TileType.HILLS,
                new ImagePattern(new Image(getClass().getResource("/images/hills.jpg").toExternalForm())),
                TileType.FIELDS,
                new ImagePattern(new Image(getClass().getResource("/images/fields.jpg").toExternalForm())));

        for (Tile tile : board.getTiles()) {
            tile.getShape().setFill(patterns.get(tile.getType()));
        }

        hexOcean.setFill(patterns.get(TileType.OCEAN));
        hexDesert.setFill(patterns.get(TileType.DESERT));
    }

    @FXML
    public void loadSubComponent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SubComponent.fxml"));
            Parent subComponent = loader.load();
            MainGameController subController = loader.getController();
            subController.setBoard(board);
            subController.setBoardController(this);

            BuildController buildController = new BuildController();
            buildController.setBoardController(this); // <--- wichtig!
            buildController.setMainGameController(subController);
            this.buildController = buildController;

            // Wenn Player bereits existiert, sofort initialisieren
            if (this.currentPlayer != null) {
                buildController.initializePlayer(this.currentPlayer);
            }

            subController.setBuildController(buildController);
            sidebar.getChildren().add(subComponent);
            System.out.println("SubComponent geladen und initialisiert.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showRobberOverlay() {
        // System.out.println("Räuber-Overlay aktiviert – bitte ein Feld auswählen.");

        int currentRobberPosition = board.getRobber().getPosition();

        for (int i = 0; i < board.getTiles().size(); i++) {
            final int index = i;
            Tile tile = board.getTiles().get(i);
            Polygon hex = tile.getShape();

            // Aktuelles Räuberfeld – optisch abmildern
            if (index == currentRobberPosition) {
                hex.setOpacity(0.7); // halbtransparent machen
                continue;
            }

            // Nur auf Land-Felder reagieren (optional, um z. B. Wasser auszuschließen)
            if (tile.getType() == TileType.OCEAN)
                continue;

            // Interaktiv machen
            hex.setOnMouseClicked(event -> {
                // System.out.println("Klick auf Feld " + index);
                moveRobberTo(index);
                disableRobberOverlay();
            });

            // Nur Cursor-Effekt (kein roter Rahmen oder Füllfarbe)
            hex.setCursor(javafx.scene.Cursor.HAND);
        }
    }

    public void disableRobberOverlay() {
        for (Tile tile : board.getTiles()) {
            Polygon hex = tile.getShape();
            hex.setOnMouseClicked(null);
            hex.setCursor(javafx.scene.Cursor.DEFAULT);
            hex.setStyle(""); // Reset style
            hex.setOpacity(1.0); // Reset Transparenz
        }
    }

    public void updateScoreboard() {
        scoreboardBox.getChildren().clear();
    
        // Fixe Breite der Box (im Layout darf das nicht überschritten werden)
        scoreboardBox.setMinWidth(223);
        scoreboardBox.setPrefWidth(223);
        scoreboardBox.setMaxWidth(223);
    
        Label title = new Label("Scoreboard");
        title.getStyleClass().add("scoreboard-title");
        scoreboardBox.getChildren().add(title);
    
        List<Player> sortedPlayers = new ArrayList<>(players);
        sortedPlayers.sort((p1, p2) -> Integer.compare(p2.getVictoryPoints(), p1.getVictoryPoints()));
    
        int lastPoints = -1;
        int displayedRank = 1;
    
        for (int i = 0; i < sortedPlayers.size(); i++) {
            Player player = sortedPlayers.get(i);
            int points = player.getVictoryPoints();
    
            if (points < lastPoints) {
                displayedRank = i + 1;
            }
            lastPoints = points;
    
            HBox playerBox = new HBox(5);
            playerBox.setPrefWidth(200);
            playerBox.setMaxWidth(200);
            playerBox.setMinWidth(200);
            playerBox.getStyleClass().add("scoreboard-entry-box");
            playerBox.setAlignment(Pos.CENTER_LEFT);


    
            Label platzLabel = new Label(displayedRank + ".");
            platzLabel.setPrefWidth(30);
            platzLabel.setMaxWidth(30);
            platzLabel.setMinWidth(30);
            platzLabel.getStyleClass().add("scoreboard-position");
            platzLabel.setAlignment(Pos.CENTER_LEFT);
    
            Label nameLabel = new Label(player.getName());
            nameLabel.setPrefWidth(90);
            nameLabel.setMaxWidth(90);
            nameLabel.setMinWidth(90);
            nameLabel.getStyleClass().add("scoreboard-name");
            nameLabel.setAlignment(Pos.CENTER_LEFT);
            nameLabel.setEllipsisString("...");
            nameLabel.setTextOverrun(OverrunStyle.ELLIPSIS);
    
            Label pointsLabel = new Label(String.valueOf(points));
            pointsLabel.setPrefWidth(40);
            pointsLabel.setMaxWidth(40);
            pointsLabel.setMinWidth(40);
            pointsLabel.getStyleClass().add("scoreboard-points");
            pointsLabel.setAlignment(Pos.CENTER_RIGHT);
    
            // Kein Wachstum erlauben, damit alles innerhalb der HBox bleibt
            HBox.setHgrow(platzLabel, Priority.NEVER);
            HBox.setHgrow(nameLabel, Priority.NEVER);
            HBox.setHgrow(pointsLabel, Priority.NEVER);
    
            playerBox.getChildren().addAll(platzLabel, nameLabel, pointsLabel);
            scoreboardBox.getChildren().add(playerBox);
        }
    }
    
  

}
