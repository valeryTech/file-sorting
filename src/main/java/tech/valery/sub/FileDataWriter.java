package tech.valery.sub;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class FileDataWriter implements DataWriter{
    @Override
    public <T> void write(List<T> list, String outFileName) throws IOException {
        FileWriter fileWriter = new FileWriter(outFileName);
        for (T elem : list){
            fileWriter.write(elem.toString() + "\n");
        }
        fileWriter.close();
    }
}
