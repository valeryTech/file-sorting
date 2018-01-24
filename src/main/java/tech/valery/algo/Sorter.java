package tech.valery.algo;

import java.util.Comparator;
import java.util.List;

/**
 * Interface represents sorting algorithm
 */
public interface Sorter {
    <T> void sort(List<T> c, Comparator<T> comparator);
}
