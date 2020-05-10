package ch.zhaw.pm2.autochess;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainGameController implements Initializable {
    private static final int FIELD_ROWS = 8;
    private static final int FIELD_COLS = 8;

    private static final int BENCH_ROWS = 1;
    private static final int BENCH_COLS = 8;

    private GridPane fieldGrid;
    private GridPane topBenchGrid;
    private GridPane bottomBenchGrid;
    private Button topBuyButton;
    private Button  bottomBuyButton;


    @FXML
    private GridPane mainGrid;

    @FXML
    private Stage primaryStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Draw board
        mainGrid.add(drawTopBenchGrid(), 0, 0);
        mainGrid.add(drawFieldGrid(), 0, 1);
        mainGrid.add(drawBottomBenchGrid(), 0, 2);
        mainGrid.add(topButtonAndImage(),1,0);
        mainGrid.add(bottomButtonAndImage(),1,2);


        //Add minions to imageView
        getMinions();
    }

    /**
     * Mit alex besprechen wie es geadded werden soll
     * Aktuell nur test version
     */
    private void getMinions() {
       ImageView imv = new ImageView();
       imv.setImage(new Image(String.valueOf(getClass().getResource("minion.jpg"))));
       imv.setFitHeight(70);
       imv.setFitWidth(70);
       bottomBenchGrid.setMargin(imv, new Insets(10, 10, 10, 10));
       bottomBenchGrid.add(imv,0,0);
    }

    private GridPane drawTopBenchGrid() {
        topBenchGrid = new GridPane();
        topBenchGrid.setId("topBenchGrid");
        topBenchGrid.getStylesheets().add(String.valueOf(getClass().getResource("Stylesheet.css")));

        createGridRowsCols(topBenchGrid, true);
        return topBenchGrid;
    }

    private GridPane drawFieldGrid() {
        fieldGrid = new GridPane();
        fieldGrid.setId("fieldGrid");
        fieldGrid.getStylesheets().add(String.valueOf(getClass().getResource("Stylesheet.css")));

        createGridRowsCols(fieldGrid, false);

        return fieldGrid;
    }

    private GridPane drawBottomBenchGrid() {
        bottomBenchGrid = new GridPane();
        bottomBenchGrid.setId("bottomBenchGrid");
        bottomBenchGrid.getStylesheets().add(String.valueOf(getClass().getResource("Stylesheet.css")));

        createGridRowsCols(bottomBenchGrid, true);
        return bottomBenchGrid;
    }

    private void createGridRowsCols(GridPane gridPane, Boolean benchOrField){
        if(benchOrField) {
            for (int i = 0; i < BENCH_COLS; i++) {
                ColumnConstraints column = new ColumnConstraints(90);
                gridPane.getColumnConstraints().add(column);
            }

            for (int i = 0; i < BENCH_ROWS; i++) {
                RowConstraints row = new RowConstraints(90);
                gridPane.getRowConstraints().add(row);
            }
        }else{
            for (int i = 0; i < FIELD_COLS; i++) {
                ColumnConstraints column = new ColumnConstraints(90);
                gridPane.getColumnConstraints().add(column);
            }

            for (int i = 0; i < FIELD_ROWS; i++) {
                RowConstraints row = new RowConstraints(90);
                gridPane.getRowConstraints().add(row);
            }

        }
    }

    private Node topButtonAndImage() {
        topBuyButton = new Button();
        topBuyButton.setId("topBuyButton");
        topBuyButton.getStylesheets().add(String.valueOf(getClass().getResource("Stylesheet.css")));
        topBuyButton.setText("BUY");
        topBuyButton.setMaxWidth(Double.MAX_VALUE);

        VBox vbButtons = new VBox();
        vbButtons.setSpacing(10);
        vbButtons.setPadding(new Insets(0, 20, 10, 20));
        vbButtons.getChildren().addAll(topBuyButton);

        return vbButtons;
    }

    private Node bottomButtonAndImage() {
        bottomBuyButton = new Button();
        bottomBuyButton.setId("bottomBuyButton");
        bottomBuyButton.getStylesheets().add(String.valueOf(getClass().getResource("Stylesheet.css")));
        bottomBuyButton.setText("BUY");
        bottomBuyButton.setMaxWidth(Double.MAX_VALUE);
        bottomBuyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                shopWindow();
            }
        });

        VBox vbButtons = new VBox();
        vbButtons.setSpacing(10);
        vbButtons.setPadding(new Insets(0, 20, 10, 20));
        vbButtons.getChildren().addAll(bottomBuyButton);

        return vbButtons;
    }

    private void shopWindow(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ShopWindow.fxml"));
            Pane shopPane = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Shop");
            stage.setScene(new Scene(shopPane, 450, 450));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }
}

