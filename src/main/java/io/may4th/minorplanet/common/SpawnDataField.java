package io.may4th.minorplanet.common;

import com.almasb.fxgl.entity.SpawnData;
import javafx.geometry.Point2D;

public class SpawnDataField<T> {

    public static final SpawnDataField<Point2D> DIRECTION = new SpawnDataField<>("DIRECTION");
    public static final SpawnDataField<String> TEXT = new SpawnDataField<>("TEXT");

    private final String field;

    public SpawnDataField(String field) {
        this.field = field;
    }

    public T get(SpawnData data) {
        return data.get(field);
    }

    public SpawnData put(SpawnData data, T value) {
        return data.put(field, value);
    }
}
