package io.may4th.minorplanet;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import io.may4th.minorplanet.common.SpawnDataField;
import io.may4th.minorplanet.entity.GameEntity;
import io.may4th.minorplanet.entity.GameEntityFactory;
import io.may4th.minorplanet.entity.PlayerComponent;
import io.may4th.minorplanet.ui.GameMenu;
import io.may4th.minorplanet.ui.GameVar;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;


public class MinorplanetGame extends GameApplication {

    private Entity player;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(800);
        settings.setTitle("minor planet");
        settings.setVersion("0.0.1");
        settings.setMainMenuEnabled(true);
        settings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu() {
                return new GameMenu();
            }
        });
    }

    @Override
    protected void initInput() {
        onKey(KeyCode.A, () -> player.getComponent(PlayerComponent.class).rotateLeft());
        onKey(KeyCode.D, () -> player.getComponent(PlayerComponent.class).rotateRight());
        onKey(KeyCode.W, () -> player.getComponent(PlayerComponent.class).move());
        onKeyDown(KeyCode.F, PlayerComponent.ACTION_SHOOT, () -> player.getComponent(PlayerComponent.class).shoot());
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put(GameVar.SCORE, 0);
        vars.put(GameVar.LIVES, 3);
    }

    @Override
    protected void initGame() {
        getSettings().setGlobalSoundVolume(0.1);
        getGameWorld().addEntityFactory(new GameEntityFactory());
        spawn(GameEntity.Name.BACKGROUND);
        player = spawn(GameEntity.Name.PLAYER, getAppWidth() / 2, getAppHeight() / 2);
        run(() -> {
            Entity entity = getGameWorld().create(GameEntity.Name.ASTEROID, new SpawnData(100, 100));
            spawnWithScale(entity, Duration.seconds(0.75), Interpolators.BOUNCE.EASE_OUT());
        }, Duration.seconds(2));
    }

    @Override
    protected void initPhysics() {
        onCollisionBegin(GameEntity.Type.BULLET, GameEntity.Type.ASTEROID, this::onCollisionBulletAndAsteroid);
        onCollisionBegin(GameEntity.Type.PLAYER, GameEntity.Type.ASTEROID, this::onCollisionPlayerAndAsteroid);
    }

    @Override
    protected void initUI() {
        var text = getUIFactoryService().newText("", 24);
        text.textProperty().bind(getip(GameVar.SCORE).asString("Score: [%d]"));
        getWorldProperties().addListener(GameVar.SCORE, (prev, now) -> {
            animationBuilder()
                .duration(Duration.seconds(0.5))
                .interpolator(Interpolators.BOUNCE.EASE_OUT())
                .repeat(2)
                .autoReverse(true)
                .scale(text)
                .from(new Point2D(1, 1))
                .to(new Point2D(1.2, 1.2))
                .buildAndPlay();
        });
        addUINode(text, 20, 50);
        addVarText(GameVar.LIVES, 20, 70);
    }

    private void onCollisionBulletAndAsteroid(Entity bullet, Entity asteroid) {
        var hp = asteroid.getComponent(HealthIntComponent.class);
        if (hp.getValue() > 1) {
            bullet.removeFromWorld();
            hp.damage(1);
            return;
        }
        spawn(GameEntity.Name.SCORE_TEXT, SpawnDataField.TEXT.put(new SpawnData(asteroid.getX(), asteroid.getY()), "+100"));
        explosion(asteroid);
        bullet.removeFromWorld();
        inc(GameVar.SCORE, +100);
    }

    private void onCollisionPlayerAndAsteroid(Entity player, Entity asteroid) {
        explosion(asteroid);
        player.setPosition(getAppWidth() / 2, getAppHeight() / 2);
        inc(GameVar.LIVES, -1);
    }

    private void explosion(Entity entity) {
        spawn(GameEntity.Name.EXPLOSION, entity.getCenter().subtract(64, 64));
        entity.removeFromWorld();
    }
}
