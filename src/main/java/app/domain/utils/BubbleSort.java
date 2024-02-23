package app.domain.utils;

import app.domain.interfaces.SortingAlgorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BubbleSort implements SortingAlgorithm {

    @Override
    public <E> List<E> sortData(List<E> list, Comparator<E> c) {
        List<E> sortedList = new ArrayList<>(list);
        boolean sorted = false;
        E temp;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < list.size() - 1; i++) {
                if (c.compare(sortedList.get(i), sortedList.get(i+1)) > 0) {
                    temp = sortedList.get(i);
                    sortedList.set(i,sortedList.get(i+1));
                    sortedList.set(i+1, temp);
                    sorted = false;
                }
            }
        }
        return sortedList;
    }

}
