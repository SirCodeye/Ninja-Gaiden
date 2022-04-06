package io.huffman.ninjagaiden;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import io.huffman.ninjagaiden.entity.GaidenFactory;
import javafx.scene.input.KeyCode;

public class NinjaGaidenApp extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1600);
        settings.setHeight(900);
        settings.setTicksPerSecond(60);
    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new GaidenFactory());
        FXGL.setLevelFromMap("act1_1.tmx");
//        FXGL.getGameWorld().

        player = FXGL.spawn("player", 50, 50);
        enemy = FXGL.spawn("enemy", 200, 50);

        FXGL.set("player", player);
        FXGL.set("enemy", enemy);

        Viewport viewport = FXGL.getGameScene().getViewport();
        viewport.setBounds(0, 0, 192 * 16, FXGL.getAppHeight());
        viewport.bindToEntity(player, FXGL.getAppWidth() / 2.0, FXGL.getAppHeight() / 2.0);
        viewport.setZoom(3);
        viewport.setLazy(true);
    }

    private Entity player;
    private Entity enemy;

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

        FXGL.getInput().addAction(new UserAction("Jump") {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerComponent.class).jump();
            }
        }, KeyCode.W, VirtualButton.A);
    }

    //@Override
    //protected void initPhysics() {
    //    FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.GROUND) {
    //@Override
    //        protected void onCollisionBegin(Entity a, Entity b) {

    //        }
    //    });
    //}


    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().setGravity(0, 800);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
