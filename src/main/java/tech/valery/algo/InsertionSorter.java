package tech.valery.algo;

import java.util.Comparator;
import java.util.List;

/**
 *  The class implements insertion sort and uses an {@link Comparator},
 *  which should be injected before using.
 */
public class InsertionSorter implements Sorter{

    /**
     * @param <T> the class of the objects to be sorted
     * @param tList the array to be sorted
     * @param comparator Comparator to be used for ordering elements
     * @throws ClassCastException if the array contains elements that are not
     *         <i>mutually comparable</i> using the specified comparator.
     * @throws IllegalArgumentException if the comparator is found to violate the
     *         {@link Comparator} contract
     */

    @Override
    public <T> void sort(List<T> tList, Comparator<T> comparator)
            throws ClassCastException, IllegalArgumentException{
        for (int i = 0; i < tList.size(); i++) {
            for (int j = i; j > 0; j--) {
                if(comparator.compare(tList.get(j), tList.get(j - 1)) < 0){
                    T buff = tList.get(j);
                    tList.set(j, tList.get(j - 1));
                    tList.set(j - 1, buff);
                }
            }
        }
    }
}
