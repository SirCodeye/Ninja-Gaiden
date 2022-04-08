package io.huffman.ninjagaiden;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.physics.CollisionHandler;
import io.huffman.ninjagaiden.entity.EntityType;
import io.huffman.ninjagaiden.entity.GaidenFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameTimer;

public class NinjaGaidenApp extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setAppIcon("logo.png");
        settings.setSceneFactory(new UISceneFactory());
        settings.setWidth(1600);
        settings.setHeight(900);
        settings.setTitle("Ninja Gaiden");
        settings.setMainMenuEnabled(true);
        settings.setGameMenuEnabled(true);
        settings.setManualResizeEnabled(false);
        settings.setTicksPerSecond(60);
        settings.setFontUI("ninja-font.ttf");
    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new GaidenFactory());
        FXGL.setLevelFromMap("act1-1.tmx");
//        FXGL.getGameWorld().
        Music backgroundMusic = FXGL.getAssetLoader().loadMusic("test.wav");
        FXGL.getAudioPlayer().loopMusic(backgroundMusic);

        player = FXGL.getGameWorld().getSingleton(EntityType.PLAYER);

        FXGL.set("player", player);
        FXGL.play("test.wav");

        Viewport viewport = FXGL.getGameScene().getViewport();
        viewport.setBounds(0, 0, 192 * 16, FXGL.getAppHeight());
        viewport.bindToEntity(player, FXGL.getAppWidth() / 2.0, FXGL.getAppHeight() / 2.0);
        viewport.setHeight(510);
        viewport.setZoom(3);
//        viewport.setHeight(680);
//        viewport.setLazy(true);

        FXGL.getWorldProperties().setValue("time", System.currentTimeMillis());

        FXGL.getGameTimer().runOnceAfter(() -> {
            System.out.println("Print");
        }, Duration.seconds(5));
    }

    private Entity player;


    @Override
    protected void initInput() {
        FXGL.getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).left();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.A, VirtualButton.LEFT);

        FXGL.getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).right();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.D, VirtualButton.RIGHT);

        FXGL.getInput().addAction(new UserAction("Down") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).down();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.S, VirtualButton.RIGHT);

        FXGL.getInput().addAction(new UserAction("Jump") {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerComponent.class).jump();
            }
        }, KeyCode.W, VirtualButton.A);
        FXGL.getInput().addAction(new UserAction("Attack") {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerComponent.class).attack();
            }
        }, KeyCode.F);
    }

    @Override
    protected void onUpdate(double tpf) {
        super.onUpdate(tpf);

        Long time = (Long) FXGL.getWorldProperties().getObject("time");
    }

    @Override
    protected void initPhysics() {
        SwordHandler swordHandler = new SwordHandler();

        FXGL.getPhysicsWorld().setGravity(0, 800);

        FXGL.getPhysicsWorld().addCollisionHandler(swordHandler);
        FXGL.getPhysicsWorld().addCollisionHandler(swordHandler.copyFor(EntityType.SWORD, EntityType.ENEMY));

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.DOOR) {
            protected void onCollisionBegin(Entity player, Entity door) {

                FXGL.showMessage("You're Winner!");
                getGameTimer().runOnceAfter(() -> {
                    FXGL.getGameController().gotoMainMenu();
                    FXGL.getAudioPlayer().stopAllMusic();
                }, Duration.seconds(0.1d));
            }
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}
