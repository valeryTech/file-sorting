package tech.valery.sub;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class FileDataReader implements DataReader {
    public static final Logger LOGGER = Logger.getLogger(FileDataReader.class.getName());
    public FileDataReader() {
    }

    /**
     *  Read elements of type T from file containing their string representations.
     * @param list List to be flouded by readed data
     * @param opt Options structure ({@link Options}) for setting read parameters
     * @param <T> type parameter
     * @throws IOException is raised in the case of incorrect file or erroneous reading from file.
     */
    @Override
    public <T> void read(DataContainer<T> list, Options opt)
            throws IOException {
        //todo autoclosable file resource
        Scanner s = new Scanner(new File(opt.getInputFile()));
        int wrongValuesCounter = 0;
        while (s.hasNextLine()){
            String element2add = s.nextLine();
            try {
                T convertedValue = (T)opt.getCurrentConverter().apply(element2add);
                list.add(convertedValue);
            }catch (ClassCastException | NumberFormatException e){
//                LOGGER.log(Level.WARNING, "Error while converting the value {0} to the type of {1}",
//                        new Object[]{element2add, list.getType()});
                wrongValuesCounter++;
            }
        }

        if(wrongValuesCounter > 0){
            LOGGER.log(Level.WARNING, "There is a number of inconsistent values in the input list: " + wrongValuesCounter);
        }
    }
}
