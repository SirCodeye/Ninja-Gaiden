package io.huffman.ninjagaiden;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.CollisionHandler;
import io.huffman.ninjagaiden.entity.EntityType;

import static io.huffman.ninjagaiden.entity.EntityType.ENEMY;
import static io.huffman.ninjagaiden.entity.EntityType.SWORD;


public class SwordHandler extends CollisionHandler {
    public SwordHandler() {
        super(SWORD, ENEMY);
    }

    @Override
    protected void onCollisionBegin(Entity sword, Entity enemy) {
        enemy.removeFromWorld();
    }
}
