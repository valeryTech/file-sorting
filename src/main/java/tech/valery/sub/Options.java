package tech.valery.sub;


import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * The class serves as a container for all possible and current settings are necessary for sorting process.
 * The Container is used as a source for dependency injection in {@link tech.valery.drivers.Conveyor}
 * as well as for storing input and output file names.
 * @param <T> type parameter
 */
public class Options <T>{

    // The group of possible settings
    private Map<String, Boolean> comparatorOrders;
    private Map<String, Supplier<DataContainer<T>>> dataContainers;
    private Map<String, Comparator<T>> comparators;
    private Map<String, Function<String, T>> converters;

    // Current settings
    private String inFile;
    private String outFile;

    private Supplier<DataContainer<T>> currentContainer;
    private Comparator<T> currentComparator;
    private Function<String, T> currentConverter;

    /**
     *  Adds a mapping for type-container relation.
     * @param key Type of elements in an input data
     * @param containerProducer produces container of specified type for data
     */
    public void addClass(String key, Supplier<DataContainer<T>> containerProducer){
        dataContainers.put(key, containerProducer);
    }

    /**
     * Adds a mapping for a type-comparator relation.
     * @param key Type of elements in an input data
     * @param comparator Comparator for the type
     */
    public void addComparatorForType(String key, Comparator<T> comparator){
        comparators.put(key, comparator);
    }

    /**
     * Adds relation for ascending/descending order option
     * @param orderKey
     * @param isReversed True if descending order, false otherwise
     */
    public void addComparatorOrder(String orderKey, Boolean isReversed){
        comparatorOrders.put(orderKey, isReversed);
    }

    /**
     * Add relation for type-converter mapping
     * @param key key of type of data elements
     * @param conv Converter from {@code String} for the specified type
     */
    public void addConverter(String key, Function<String, T> conv){converters.put(key, conv);}

    /**
     *  Returns converter of type for elements in current setting.
     * @return Converter of type for elements in current settings.
     */
    public Function<String, T> getCurrentConverter() {
        return currentConverter;
    }

    /**
     * Set input file from which data would be read
     * @param inFile path to input file
     */
    public void setInputFile(String inFile) {
        this.inFile = inFile;
    }

    /**
     * Get input file which data would be read from
     * @return path to the input file
     */
    public String getInputFile() {
        return inFile;
    }

    /**
     * Get ouput file which data would be writed to
     * @return path to ouput file
     */
    public String getOutFile() {
        return outFile;
    }

    /**
     * Set output file which data would be writed to
     * @param outFile path to file
     */
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }

    /**
     * Set all current mappings for type in input options.
     * Method is applied after providing all relations.
     * @param typeOption current type
     * @throws IllegalArgumentException
     */
    public void setDataType(String typeOption) throws IllegalArgumentException{
        if(dataContainers.containsKey(typeOption)){
            currentContainer = dataContainers.get(typeOption);
            currentComparator = comparators.get(typeOption);
            currentConverter = converters.get(typeOption);
        }else {
            throw new IllegalArgumentException("Input data format option is invalid.");
        }
    }

    /**
     * Set current (specified by user of the class) settings
     * @param args cli options
     * @throws IllegalArgumentException
     */
    public void setCurrentSettings(String[] args) throws IllegalArgumentException{

        // Checking input parameters
        if(args.length < 4){
            throw new IllegalArgumentException("One should use all parameters.");
        }

        setInputFile(args[0]);
        setOutFile(args[1]);

        String formatOption = args[2];
        String orderOption = args[3];

        setDataType(formatOption);
        setComparator(orderOption);

    }

    /**
     * Set ascending/descending order for current comparator
     * @param compType String key represents order type
     * @throws IllegalArgumentException throws in the case of inconsistent option value
     */
    public void setComparator(String compType) throws IllegalArgumentException{
        if(comparatorOrders.containsKey(compType)){
            if(comparatorOrders.get(compType)){
                currentComparator = currentComparator.reversed();
            }
        }else{
            throw new IllegalArgumentException("Order format option is invalid.");
        }
    }

    /**
     * Get container for chosen current element type
     * @return Parametrized data container for current type
     */
    public DataContainer<T> getCurrentTypeContainer(){
        return currentContainer.get();
    }

    /**
     * Return chosen for current type comparator
     * @return Comparator
     */
    public Comparator getCurrentComparator(){
        return currentComparator;
    }

    /**
     * Constructor for the class
     */
    public Options() {
        this.comparatorOrders = new HashMap<>();
        this.comparators = new HashMap<>();
        this.dataContainers = new HashMap<>();
        this.converters = new HashMap<>();
    }
}
