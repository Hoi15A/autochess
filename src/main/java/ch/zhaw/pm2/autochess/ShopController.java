package ch.zhaw.pm2.autochess;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ShopController implements Initializable {

    @FXML
    private GridPane ShopMainGrid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        createBuyableMinionList();



    }

    private void createBuyableMinionList(){
        TextField field = new TextField();
        field.setDisable(true);
        field.setText("0");

        ImageView imv = new ImageView();
        imv.setImage(new Image(String.valueOf(getClass().getResource("minion.jpg"))));
        imv.setFitHeight(70);
        imv.setFitWidth(70);

        FlowPane buttonPane = new FlowPane();
        Button plusButton = new Button();
        plusButton.setText("+");
        plusButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                field.setText(String.valueOf(Integer.parseInt(field.getText())+1));
            }
        });
        buttonPane.getChildren().add(plusButton);
        Button minusButton = new Button();
        minusButton.setText("-");
        minusButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(field.getText().equals("0")){
                }else {
                    field.setText(String.valueOf(Integer.parseInt(field.getText()) - 1));
                }
            }
        });
        buttonPane.getChildren().add(minusButton);
        field.setPrefSize(30,20);
        buttonPane.getChildren().add(field);

        GridPane minionPane = new GridPane();

        minionPane.setMargin(imv, new Insets(10, 10, 10, 10));
        minionPane.add(imv,0,0);
        minionPane.add(buttonPane,0,1);

        ShopMainGrid.add(minionPane,0,0);
    }
}
