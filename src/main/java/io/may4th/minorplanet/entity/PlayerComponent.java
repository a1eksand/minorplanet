package io.may4th.minorplanet.entity;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import io.may4th.minorplanet.common.SpawnDataField;

import static com.almasb.fxgl.dsl.FXGL.spawn;


public class PlayerComponent extends Component {

    public static final String ACTION_SHOOT = "shoot";

    private static final double angle = 3;

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(entity.getCenter());
    }

    public void rotateLeft() {
        entity.rotateBy(-angle);
    }

    public void rotateRight() {
        entity.rotateBy(+angle);
    }

    public void move() {
        entity.translate(Vec2.fromAngle(entity.getRotation() - 90).mulLocal(4));
    }

    public void shoot() {
        var center = entity.getCenter().subtract(37 / 2.0, 13 / 2.0);
        var direction = Vec2.fromAngle(entity.getRotation() - 90);
        spawn(GameEntity.Name.BULLET, SpawnDataField.DIRECTION.put(new SpawnData(center.getX(), center.getY()), direction.toPoint2D()));
    }
}
