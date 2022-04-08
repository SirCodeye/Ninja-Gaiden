package io.huffman.ninjagaiden.entity.enemy;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import io.huffman.ninjagaiden.entity.EntityType;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameTimer;

public class EnemyComponent extends Component {

    Random rand = new Random();

    private PhysicsComponent physics;

    private AnimatedTexture texture;

    private AnimationChannel animIdle, animWalk;

    private int jumps = 1;

    public EnemyComponent() {

        animIdle = new AnimationChannel(FXGL.image("bikerwalk.png"), 3, 27, 69, Duration.seconds(0.4), 0, 0);
        animWalk = new AnimationChannel(FXGL.image("bikerwalk.png"), 3, 27, 69, Duration.seconds(0.2), 0, 2);

        texture = new AnimatedTexture(animIdle);
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
        entity.getViewComponent().addChild(texture);

        physics.onGroundProperty().addListener((obs, old, isOnGround) -> {
            if (isOnGround) {
                jumps = 1;
            }
        });
    }

    @Override
    public void onUpdate(double tpf) {
        if (physics.isMovingX()) {
            if (texture.getAnimationChannel() != animWalk) {
                texture.loopAnimationChannel(animWalk);
            }
        } else {
            if (texture.getAnimationChannel() != animIdle) {
                texture.loopAnimationChannel(animIdle);
            }
        }
    }

    public void attack() {
        double playerx = FXGL.getGameWorld().getSingleton(EntityType.PLAYER).getX();
        double enemyx = FXGL.getGameWorld().getSingleton(EntityType.ENEMY).getX();

        if (playerx < enemyx) {
            getEntity().setScaleX(-1);
            physics.setVelocityX(-60);
        } else if (playerx > enemyx) {
            getEntity().setScaleX(1);
            physics.setVelocityX(60);
        } else {
            physics.setVelocityX(0);
        }
        if (FXGL.getGameWorld().getSingleton(EntityType.PLAYER).getY() != 117) {
            if (jumps == 0)
                return;
            physics.setVelocityY(-300);
            jumps--;
        }
    }

    public void patrol() {
        float random = rand.nextFloat();
            if (random > 0.5) {
                getEntity().setScaleX(1);
                physics.setVelocityX(50);
                getGameTimer().runOnceAfter(() -> {
                    physics.setVelocityX(0);
                }, Duration.seconds(1));
            } else {
                getEntity().setScaleX(-1);
                physics.setVelocityX(-50);
                getGameTimer().runOnceAfter(() -> {
                    physics.setVelocityX(0);
                }, Duration.seconds(1));
            }

    }

}
