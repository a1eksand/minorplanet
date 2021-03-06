package io.may4th.minorplanet.entity;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class WallComponent extends Component {

    private double lastWall = 1000;

    @Override
    public void onUpdate(double tpf) {
        if (lastWall - entity.getX() < FXGL.getAppWidth()) {
            buildWalls();
        }
    }

    private Rectangle wallView(double width, double height) {
        Rectangle wall = new Rectangle(width, height);
        wall.setArcWidth(25);
        wall.setArcHeight(25);
        return wall;
    }

    private void buildWalls() {
        var height = FXGL.getAppHeight();
        var distance = height / 2;

        for (int i = 1; i <= 10; i++) {
            var topHeight = Math.random() * (height - distance);

            entityBuilder()
                .at(lastWall + i * 500, 0 - 25)
                .type(GameEntity.Type.WALL)
                .viewWithBBox(wallView(50, topHeight))
                .with(new CollidableComponent(true))
                .buildAndAttach();

            entityBuilder()
                .at(lastWall + i * 500, 0 + topHeight + distance + 25)
                .type(GameEntity.Type.WALL)
                .viewWithBBox(wallView(50, height - distance - topHeight))
                .with(new CollidableComponent(true))
                .buildAndAttach();
        }

        lastWall += 10 * 500;
    }
}
