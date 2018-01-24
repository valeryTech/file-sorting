package tech.valery.sub;

import java.io.IOException;
import java.util.List;

/**
 * {@code DataReader} produces the data consumed by {@code Conveyor}.
 */
public interface DataReader {
    /**
     *  Read data from source to list in accordance with options.
     * @param list List to be flouded by readed data
     * @param opt Options structure ({@link Options}) for setting read parameters
     * @param <T> List elements type parameter
     * @throws IOException
     */
    <T> void read(DataContainer<T> list, Options opt) throws IOException;
}
