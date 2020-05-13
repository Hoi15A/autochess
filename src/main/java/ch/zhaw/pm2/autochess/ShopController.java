package ch.zhaw.pm2.autochess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShopController implements Initializable {
    private Text player1Text;
    private Text player2Text;

    private Text buyText;




    private Text sellText;


    @FXML
    private GridPane shopMainGrid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializePlayerText();
        initializePlayerBuyView();

        shopMainGrid.add(player1Text,0,0);
        shopMainGrid.add(player2Text,1,0);

        Button nextButton = new Button("next");
        shopMainGrid.add(nextButton,1,3);

        nextButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage currentStage = (Stage)((Node) event.getSource()).getScene().getWindow();
                currentStage.close();
                loadGameWindow();
            }
        });
    }

    private void loadGameWindow(){
        Stage heroSelectStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/GameWindow.fxml"));
            Pane gamePane = loader.load();

            Stage gamestage = new Stage();
            gamestage.setTitle("SMAC");
            gamestage.setScene(new Scene(gamePane, 800, 600));
            gamestage.setResizable(false);
            gamestage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    private void initializePlayerText(){
        player1Text = new Text("Player 1 Shop");
        player2Text = new Text("Player 2 Shop");
    }

    private void initializePlayerBuyView(){
        class XCell extends ListCell<String> {
            HBox hbox = new HBox();
            Label label = new Label();
            Pane pane = new Pane();
            Button button = new Button("buy");
            TextField amountField = new TextField();
            String lastItem;

            public XCell() {
                super();
                hbox.getChildren().addAll(label, pane, button, amountField);
                HBox.setHgrow(label, Priority.ALWAYS);
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println(lastItem + " : " + event);
                    }
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(null);  // No text in label of super class
                if (empty) {
                    lastItem = null;
                    setGraphic(null);
                } else {
                    lastItem = item;
                    label.setText(item != null ? item : "<null>");
                    setGraphic(hbox);
                }
            }
        }

        ObservableList<String> list = FXCollections.observableArrayList(
            "Warrior", "Tank", "Ranger");
        ListView<String> lv = new ListView<>(list);
        lv.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new XCell();
            }
        });
        shopMainGrid.add(lv,0,1);
    }
}