package io.may4th.minorplanet.entity;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.dsl.components.*;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.TimeComponent;
import com.almasb.fxgl.particle.ParticleComponent;
import com.almasb.fxgl.particle.ParticleEmitters;
import com.almasb.fxgl.ui.ProgressBar;
import io.may4th.minorplanet.Resource;
import io.may4th.minorplanet.common.SpawnDataField;
import io.may4th.minorplanet.effect.SlowTimeEffect;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;


public class GameEntityFactory implements EntityFactory {

    @Spawns(GameEntity.Name.BACKGROUND)
    public Entity newBackground(SpawnData data) {
        return entityBuilder(data)
            .view(new Rectangle(getAppWidth(), getAppHeight()))
            .build();
    }

    @Spawns(GameEntity.Name.PLAYER)
    public Entity newPlayer(SpawnData data) {
        return entityBuilder(data)
            .type(GameEntity.Type.PLAYER)
            .viewWithBBox(Resource.Texture.PLAYER)
            .with(new PlayerComponent())
            .collidable()
            .build();
    }

    @Spawns(GameEntity.Name.ASTEROID)
    public Entity newAsteroid(SpawnData data) {
        var hp = new HealthIntComponent(2);
        var hpView = new ProgressBar(false);
        hpView.setFill(Color.LIGHTGREEN);
        hpView.setMaxValue(2);
        hpView.setWidth(85);
        hpView.setTranslateY(90);
        hpView.currentValueProperty().bind(hp.valueProperty());
        return entityBuilder(data)
            .type(GameEntity.Type.ASTEROID)
            .viewWithBBox(Resource.Texture.ASTEROID)
            .view(hpView)
            .with(hp)
            .with(new RandomMoveComponent(new Rectangle2D(0, 0, getAppWidth(), getAppHeight()), 100))
            .collidable()
            .build();
    }

    @Spawns(GameEntity.Name.BULLET)
    public Entity newBullet(SpawnData data) {
        var effectComponent = new EffectComponent();
        var entity = entityBuilder(data)
            .type(GameEntity.Type.BULLET)
            .viewWithBBox(Resource.Texture.BULLET)
            .with(new ProjectileComponent(SpawnDataField.DIRECTION.get(data), 500))
            .with(new OffscreenCleanComponent())
            .with(new TimeComponent())
            .with(effectComponent)
            .collidable()
            .build();
        entity.setOnActive(() -> {
            effectComponent.startEffect(new SlowTimeEffect());
        });
        return entity;
    }

    @Spawns(GameEntity.Name.EXPLOSION)
    public Entity newExplosion(SpawnData data) {
        play(Resource.Sound.EXPLOSION);
        var emitter = ParticleEmitters.newExplosionEmitter(350);
        emitter.setMaxEmissions(1);
        emitter.setSize(2, 10);
        emitter.setStartColor(Color.WHITE);
        emitter.setEndColor(Color.BLUE);
        emitter.setSpawnPointFunction(i -> new Point2D(64, 64));
        return entityBuilder(data)
            .view(texture(Resource.Texture.EXPLOSION).toAnimatedTexture(16, Duration.seconds(0.66)).play())
            .with(new ExpireCleanComponent(Duration.seconds(0.66)))
            .with(new ParticleComponent(emitter))
            .build();
    }

    @Spawns(GameEntity.Name.SCORE_TEXT)
    public Entity newScoreText(SpawnData data) {
        var entity = entityBuilder(data)
            .view(getUIFactory().newText(SpawnDataField.TEXT.get(data), 24))
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
