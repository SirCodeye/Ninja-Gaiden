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


public class IngameMenu extends FXGLMenu {
    public IngameMenu(@NotNull MenuType type) {
        super(type);

        Font font = Font.loadFont("file:src/main/resources/assets/ui/fonts/ninja-font.ttf", 18);

        Button button = new Button("Quit to main menu");
        button.setFont(font);

        Button button1 = new Button("Quit");
        button1.setFont(font);

        BorderPane pane = new BorderPane();
        VBox vBox = new VBox(10);

        vBox.getChildren().addAll(button, button1);
        pane.setCenter(vBox);

        vBox.setAlignment(Pos.CENTER);

        pane.setMinHeight(FXGL.getAppHeight());
        pane.setMinWidth(FXGL.getAppWidth());

        pane.setCenter(vBox);

        button1.setOnAction(e ->{
            fireExit();
        });

        button.setOnAction(e ->{
            fireExitToMainMenu();
        });

        BackgroundImage mainBackground = new BackgroundImage(new Image("assets/textures/background.png", FXGL.getAppWidth(), FXGL.getAppHeight(), false, true),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );

        //getContentRoot().setBackground(new Background(mainBackground));

        getContentRoot().getChildren().add(pane);
    }
}
