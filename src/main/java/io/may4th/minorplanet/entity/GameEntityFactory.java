package io.may4th.minorplanet.entity;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import io.may4th.minorplanet.MinorplanetGame;
import io.may4th.minorplanet.Resource;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;


public class GameEntityFactory implements EntityFactory {

    @Spawns(GameEntity.Name.PLAYER)
    public Entity newPlayer(SpawnData data) {
        return entityBuilder(data)
            .type(GameEntity.Type.PLAYER)
            .viewWithBBox(Resource.Texture.PLAYER)
            .with(new PlayerComponent(), new WallComponent())
            .collidable()
            .build();
    }

    @Spawns(GameEntity.Name.HUD)
    public Entity newHUD(SpawnData data) {
        var entity = entityBuilder(data)
            .view(getUIFactory().newText(MinorplanetGame.TEXT.get(data), 24))
            .with(new ExpireCleanComponent(Duration.seconds(0.66)).animateOpacity())
            .build();
        animationBuilder()
            .duration(Duration.seconds(0.66))
            .interpolator(Interpolators.EXPONENTIAL.EASE_OUT())
            .translate(entity)
            .from(new Point2D(data.getX(), data.getY()))
            .to(new Point2D(data.getX(), data.getY() - 30))
            .buildAndPlay();
        return entity;
    }
}
