package app.domain.interfaces;

import java.util.Comparator;
import java.util.List;

public interface SortingAlgorithm
{
    <E> List<E> sortData(List<E> list, Comparator<E> c);

}
