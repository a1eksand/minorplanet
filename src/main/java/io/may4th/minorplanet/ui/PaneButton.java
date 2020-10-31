package io.may4th.minorplanet.ui;

import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.Bindings;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class PaneButton extends StackPane {
    public PaneButton(String name, Runnable action) {
        var background = new Rectangle(200, 40);
        background.setStroke(Color.WHITE);
        var text = FXGL.getUIFactoryService().newText(name, Color.WHITE, 18);
        background.fillProperty().bind(Bindings.when(hoverProperty()).then(Color.WHITE).otherwise(Color.BLACK));
        text.fillProperty().bind(Bindings.when(hoverProperty()).then(Color.BLACK).otherwise(Color.WHITE));
        setOnMouseClicked(event -> action.run());
        getChildren().addAll(background, text);
    }
}
