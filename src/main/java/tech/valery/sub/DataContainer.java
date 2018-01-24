package tech.valery.sub;

import java.util.ArrayList;

public class DataContainer<T> extends ArrayList<T> {
    private final Class<T> type;

    public DataContainer(Class<T> type) {
        this.type = type;
    }
    public Class<T> getType(){
        return this.type;
    }
}
