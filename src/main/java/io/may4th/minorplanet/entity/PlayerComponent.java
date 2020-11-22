package io.may4th.minorplanet.entity;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.component.Component;

import static com.almasb.fxgl.dsl.FXGL.spawn;


public class PlayerComponent extends Component {

    private static final double D90 = 90;
    private static final double ROTATION_ANGLE = 100;
    private static final double ENGINE_ACCELERATION = 40;
    private static final double GRAVITY_ACCELERATION = 30;
    private static final double TPF_MULTIPLIER = 0.5;

    private Vec2 direction;

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(entity.getCenter());
        direction = Vec2.fromAngle(entity.getRotation() - D90).mul(0);
        entity.rotateBy(D90);
    }

    public void move() {
        entity.translate(direction);
    }

    public void rotate(Double tpf) {
        entity.rotateBy(ROTATION_ANGLE * tpf * TPF_MULTIPLIER);
    }

    public void engine(Double tpf) {
        direction = direction.add(Vec2.fromAngle(entity.getRotation() - D90).mulLocal(ENGINE_ACCELERATION * tpf * TPF_MULTIPLIER));
    }

    public void gravity(double tpf) {
        direction = direction.add(Vec2.fromAngle(D90).mulLocal(GRAVITY_ACCELERATION * tpf * TPF_MULTIPLIER));
    }

    public Vec2 getDirection() {
        return direction;
    }
}
