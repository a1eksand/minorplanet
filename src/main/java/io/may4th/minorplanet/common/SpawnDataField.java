package io.may4th.minorplanet.common;

import com.almasb.fxgl.entity.SpawnData;

public class SpawnDataField<T> {

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
