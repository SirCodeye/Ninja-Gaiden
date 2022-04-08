package io.huffman.ninjagaiden.entity.enemy;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import io.huffman.ninjagaiden.entity.EntityType;
import javafx.util.Duration;


//import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameTimer;

public class EnemyAiComponent extends Component {

    private EnemyComponent enemyai;
    private LocalTimer timestamp;

    @Override
    public void onAdded(){
        timestamp = FXGL.newLocalTimer();
        timestamp.capture();
    }

    @Override
    public void onUpdate(double tpf) {
        Entity player = FXGL.getGameWorld().getSingleton(EntityType.PLAYER);
        if (getEntity().distance(player) < 100) {
                enemyai.attack();
        } else {
            if (timestamp.elapsed(Duration.seconds(3))) {
                enemyai.patrol();
                onAdded();
            }
        }
    }
}
