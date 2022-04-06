package io.huffman.ninjagaiden;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import javafx.scene.control.Button;

public class MainMenu extends FXGLMenu {

    public MainMenu() {
        super(MenuType.MAIN_MENU);

        this.getContentRoot().getChildren().add(this.createLayout());

    }


    public BorderPane createLayout() {
        BorderPane pane = new BorderPane();
        Button btnStart = new Button("Start");
        Button btnScoreboard = new Button("Scoreboard");
        Button btnExit = new Button("Exit");
        pane.setCenter(btnStart);

        btnStart.setOnAction(e ->{
            this.fireNewGame();
        });

        return pane;

    }

}