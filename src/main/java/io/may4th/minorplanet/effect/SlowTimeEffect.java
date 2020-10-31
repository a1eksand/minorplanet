package io.may4th.minorplanet.effect;

import com.almasb.fxgl.dsl.components.Effect;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.TimeComponent;
import javafx.util.Duration;

public class SlowTimeEffect extends Effect {

    public SlowTimeEffect() {
        super(Duration.seconds(0.5));
    }

    @Override
    public void onStart(Entity entity) {
        entity.getComponent(TimeComponent.class).setValue(0.05);
    }

    @Override
    public void onEnd(Entity entity) {
        entity.getComponent(TimeComponent.class).setValue(3.0);
    }
}
