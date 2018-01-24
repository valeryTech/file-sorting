package tech.valery.drivers;

// LONG comment

import tech.valery.algo.InsertionSorter;
import tech.valery.algo.Sorter;
import tech.valery.sub.*;

import java.io.IOException;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Class for process data in agree with the task (see applied pdf file).
 * @param <T> chosen type of data elements
 */
public class Conveyor<T>{
    public static final Logger LOGGER = Logger.getLogger(Conveyor.class.getName());
    private DataContainer<T> data;

    private DataReader reader;
    private DataWriter writer;
    private Sorter sorter;

    public Conveyor(DataContainer<T> data) {
        this.data = data;
    }

    /**
     *  Inject reader for create input data from source (maybe file or other input).
     * @param reader Data reader
     */
    public void setReader(DataReader reader) {
        this.reader = reader;
    }

    /**
     *  Inject {@link DataWriter} to save data into end point.
     * @param writer Data writer
     */
    public void setWriter(DataWriter writer) {
        this.writer = writer;
    }

    /**
     * Inject sorting algorithm
     * @param sorter Class implements sorting algorithm
     */
    public void setSorter(Sorter sorter) {
        this.sorter = sorter;
    }

    /**
     * Do all desired steps for processing data
     * @param opt Current and all options
     */
    public void process(Options opt){

        LOGGER.log(Level.INFO, "Start of converting values from {0}", opt.getInputFile());
        try {
            reader.read(data, opt);
        }
        catch (IOException e){
            LOGGER.log(Level.SEVERE, "Error while reading file", e);
        }

        try{
            sorter.sort(data, opt.getCurrentComparator());
            writer.write(data, opt.getOutFile());

            LOGGER.log(Level.INFO, "End converting");
        }
        catch (IOException e){
            LOGGER.log(Level.SEVERE, "Error while writing file");
        }catch (ClassCastException e){
            LOGGER.log(Level.SEVERE, "Cannot compare elements", e);
        }
        
    }

    public static void main(String[] args) {

        args = new String[]{"build/libs/in3.txt", "out.txt","-i" , "-a"};

        Options options = new Options();

        // Set relations and mappings
        options.addClass("-s", () -> {return new DataContainer<String>(String.class);});
        options.addClass("-i", () -> {return new DataContainer<Integer>(Integer.class);});

        options.addComparatorForType("-s", Comparator.naturalOrder());
        options.addComparatorForType("-i", (Comparator<Integer>)(i1, i2) -> Integer.compare(i1, i2));

        options.addConverter("-s", e -> e);
        options.addConverter("-i", (e) -> {return Integer.parseInt((String)e);});
        
        options.addComparatorOrder("-a", false);
        options.addComparatorOrder("-d", true);

        // Set current settings
        try {
            options.setCurrentSettings(args);
        }catch (IllegalArgumentException e){
            LOGGER.log(Level.SEVERE, e.getMessage());
            return;
        }

        // Data container injection
        Conveyor conveyor = new Conveyor(options.getCurrentTypeContainer());

        // Set process parameters through
        // injection of reader, sorter, writer
        conveyor.setReader(new FileDataReader());
        conveyor.setSorter(new InsertionSorter());
        conveyor.setWriter(new FileDataWriter());

        // Do all steps for converting and catch all exceptions
        conveyor.process(options);

    }
}
