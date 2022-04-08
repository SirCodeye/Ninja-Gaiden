package io.huffman.ninjagaiden;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import io.huffman.ninjagaiden.entity.EntityType;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class PlayerComponent extends Component {

    private PhysicsComponent physics;

    private AnimatedTexture texture;

    private AnimationChannel animIdle, animWalk, animJump, animDuck;

    private int jumps;

    public PlayerComponent() {

        animIdle = new AnimationChannel(FXGL.image("idle.png"), 3, 27, 35, Duration.seconds(0.4), 0, 2);
        animWalk = new AnimationChannel(FXGL.image("walk.png"), 5, 27, 35, Duration.seconds(0.2), 1, 3);
        animJump = new AnimationChannel(FXGL.image("jump.png"), 3, 27, 35, Duration.seconds(0.4), 0, 2);
        animDuck = new AnimationChannel(FXGL.image("duck.png"), 4, 27, 35, Duration.seconds(0.4), 0, 3);
        texture = new AnimatedTexture(animIdle);
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
        entity.getViewComponent().addChild(texture);

        physics.onGroundProperty().addListener((obs, old, isOnGround) -> {
            if (isOnGround) {
                jumps = 2;
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

    public void left() {
        getEntity().setScaleX(-1);
        physics.setVelocityX(-185);
    }

    public void right() {
        getEntity().setScaleX(1);
        physics.setVelocityX(185);
    }

    public void down() {
        texture.loopAnimationChannel(animDuck);
    }

    public void stop() {
        physics.setVelocityX(0);
    }

    public void jump() {
        if (jumps == 0)
            return;

        physics.setVelocityY(-300);

        texture.loopAnimationChannel(animJump);

        jumps--;
    }
    public void attack() {
        if (FXGL.getGameWorld().getSingleton(EntityType.PLAYER).getScaleX() == 1) {
            Entity sword = FXGL.spawn("sword", FXGL.getGameWorld().getSingleton(EntityType.PLAYER).getX() + 20, FXGL.getGameWorld().getSingleton(EntityType.PLAYER).getY());
            FXGL.set("sword", sword);
            FXGL.despawnWithDelay(sword, Duration.millis(100));
        } else if (FXGL.getGameWorld().getSingleton(EntityType.PLAYER).getScaleX() == -1) {
            Entity sword = FXGL.spawn("sword", FXGL.getGameWorld().getSingleton(EntityType.PLAYER).getX() - 32, FXGL.getGameWorld().getSingleton(EntityType.PLAYER).getY());
            FXGL.set("sword", sword);
            FXGL.despawnWithDelay(sword, Duration.millis(100));
        }
    }
}
