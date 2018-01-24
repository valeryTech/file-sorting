package tech.valery.sub;

import java.io.IOException;
import java.util.List;

public interface DataWriter{
    <T> void write(List<T> list, String outFileName) throws IOException;
}
