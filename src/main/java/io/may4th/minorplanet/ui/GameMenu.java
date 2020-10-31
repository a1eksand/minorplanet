package io.may4th.minorplanet.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GameMenu extends FXGLMenu {

    public GameMenu() {
        super(MenuType.MAIN_MENU);
        var button = new PaneButton("Start new game", this::fireNewGame);
        button.setTranslateX(FXGL.getAppWidth() / 2 - 200 / 2);
        button.setTranslateY(FXGL.getAppHeight() / 2 - 40 / 2);
        getMenuContentRoot().getChildren().add(button);
    }

    @Override
    protected Button createActionButton(StringBinding stringBinding, Runnable runnable) {
        return new Button();
    }

    @Override
    protected Button createActionButton(String string, Runnable runnable) {
        return new Button();
    }

    @Override
    protected Node createBackground(double width, double height) {
        return new Rectangle(width, height, Color.DARKGRAY);
    }

    @Override
    protected Node createProfileView(String string) {
        return new Text();
    }

    @Override
    protected Node createTitleView(String string) {
        return new Text();
    }

    @Override
    protected Node createVersionView(String string) {
        return new Text();
    }

}
