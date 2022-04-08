package io.huffman.ninjagaiden;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import org.jetbrains.annotations.NotNull;

public class StartMenu extends FXGLMenu {


    public StartMenu(@NotNull MenuType type) {
        super(type);

        Button button = new Button("Start");
        Button button1 = new Button("Quit");
        BorderPane pane = new BorderPane();
        VBox vbox = new VBox(10);
        BackgroundImage menuBackground = new BackgroundImage(new Image("/assets/textures/background.png", FXGL.getAppWidth(), FXGL.getAppHeight(), false, true),
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Font font = Font.loadFont("file:src/main/resources/assets/ui/fonts/ninja-font.ttf", 18);
        button.setFont(font);
        button1.setFont(font);

        getContentRoot().setBackground(new Background(menuBackground));

        vbox.getChildren().addAll(button, button1);

        pane.setMinHeight(FXGL.getAppHeight());
        pane.setMinWidth(FXGL.getAppWidth());

        pane.setCenter(vbox);

        vbox.setAlignment(Pos.CENTER);

        button.setOnAction(event -> {
            fireNewGame();
                });
        button1.setOnAction(event -> {
            fireExit();
        });

        getContentRoot().getChildren().add(pane);

    }
}
