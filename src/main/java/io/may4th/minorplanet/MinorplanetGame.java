package io.may4th.minorplanet;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import io.may4th.minorplanet.common.SpawnDataField;
import io.may4th.minorplanet.entity.GameEntity;
import io.may4th.minorplanet.entity.GameEntityFactory;
import io.may4th.minorplanet.entity.PlayerComponent;
import io.may4th.minorplanet.ui.GameMenu;
import io.may4th.minorplanet.ui.GameVar;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;


public class MinorplanetGame extends GameApplication {

    public static final SpawnDataField<String> TEXT = new SpawnDataField<>("TEXT");

    private PlayerComponent playerComponent;
    private boolean isNewGmaeRequested = false;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(800);
        settings.setTitle("minor planet");
        settings.setVersion("0.0.1");

        settings.setMainMenuEnabled(true);
        settings.setDeveloperMenuEnabled(true);

        settings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu() {
                return new GameMenu();
            }
        });
    }

    @Override
    protected void initInput() {
        onKey(KeyCode.A, (tpf) -> playerComponent.rotate(-tpf));
        onKey(KeyCode.D, (tpf) -> playerComponent.rotate(tpf));
        onKey(KeyCode.W, (tpf) -> playerComponent.engine(tpf));
        onKey(KeyCode.S, (tpf) -> playerComponent.engine(-tpf));
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put(GameVar.SPEED, 0);
    }

    @Override
    protected void initGame() {
        getSettings().setGlobalSoundVolume(0.1);
        getGameWorld().addEntityFactory(new GameEntityFactory());
        var playerEntity = spawn(GameEntity.Name.PLAYER, getAppWidth() / 2, getAppHeight() / 2);
        playerComponent = playerEntity.getComponent(PlayerComponent.class);
        getGameScene().getViewport().setBounds(0, 0, Integer.MAX_VALUE, getAppHeight());
        getGameScene().getViewport().bindToEntity(playerEntity, getAppWidth() / 3, getAppHeight() / 2);
    }

    @Override
    protected void initPhysics() {
        onCollisionBegin(GameEntity.Type.PLAYER, GameEntity.Type.WALL, (player, wall) -> isNewGmaeRequested = true);
    }

    @Override
    protected void initUI() {
        var text = getUIFactoryService().newText("", Color.rgb(0, 0, 128), 24);
        text.textProperty().bind(getip(GameVar.SPEED).asString("Speed: [%d]"));
        getWorldProperties().addListener(GameVar.SPEED, (prev, now) -> {
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
    }

    @Override
    protected void onUpdate(double tpf) {
        var gameVars = getGameWorld().getProperties();
        if (isNewGmaeRequested) {
            isNewGmaeRequested = false;
            getGameController().startNewGame();
        } else {
            playerComponent.move();
            gameVars.setValue(GameVar.SPEED, (int) playerComponent.getDirection().length());
        }
    }
}
