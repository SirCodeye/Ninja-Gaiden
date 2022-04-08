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

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameTimer;

public class PlayerComponent extends Component {

    private PhysicsComponent physics;

    private PlayerState state;

    private AnimatedTexture texture;

    private AnimationChannel animIdle, animWalk, animJump, animDuck, animAttack;

    private int jumps;

    public PlayerComponent() {

        animIdle = new AnimationChannel(FXGL.image("idle.png"), 3, 27, 35, Duration.seconds(0.4), 0, 2);
        animWalk = new AnimationChannel(FXGL.image("walk.png"), 5, 27, 35, Duration.seconds(0.2), 1, 3);
        animJump = new AnimationChannel(FXGL.image("jump.png"), 3, 27, 35, Duration.seconds(0.4), 0, 2);
        animDuck = new AnimationChannel(FXGL.image("duck.png"), 4, 27, 35, Duration.seconds(0.4), 0, 3);
        animAttack = new AnimationChannel(FXGL.image("attack.png"), 4, 41, 41, Duration.seconds(0.2), 0, 3);
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

        if(state == PlayerState.ATTACK && texture.getAnimationChannel() != animAttack) {
            texture.playAnimationChannel(animAttack);
            getGameTimer().runOnceAfter(() -> {
                state = PlayerState.IDLE;
            }, Duration.seconds(0.2));
        }

        if (state == PlayerState.WALK && texture.getAnimationChannel() != animWalk) {
            texture.loopAnimationChannel(animWalk);
        }

        if(state == PlayerState.IDLE && texture.getAnimationChannel() != animIdle) {
            texture.loopAnimationChannel(animIdle);
        }
    }

    public void left() {
        getEntity().setScaleX(-1);
        state = PlayerState.WALK;
        physics.setVelocityX(-185);
    }

    public void right() {
        getEntity().setScaleX(1);
        state = PlayerState.WALK;
        physics.setVelocityX(185);
    }

    public void down() {
        texture.loopAnimationChannel(animDuck);
    }

    public void stop() {
        state = PlayerState.IDLE;
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
            state = PlayerState.ATTACK;
            Entity sword = FXGL.spawn("sword", FXGL.getGameWorld().getSingleton(EntityType.PLAYER).getX() + 20, FXGL.getGameWorld().getSingleton(EntityType.PLAYER).getY());
            FXGL.set("sword", sword);
            FXGL.despawnWithDelay(sword, Duration.millis(100));
        } else if (FXGL.getGameWorld().getSingleton(EntityType.PLAYER).getScaleX() == -1) {
            state = PlayerState.ATTACK;
            Entity sword = FXGL.spawn("sword", FXGL.getGameWorld().getSingleton(EntityType.PLAYER).getX() - 32, FXGL.getGameWorld().getSingleton(EntityType.PLAYER).getY());
            FXGL.set("sword", sword);
            FXGL.despawnWithDelay(sword, Duration.millis(100));
        }
    }
}
