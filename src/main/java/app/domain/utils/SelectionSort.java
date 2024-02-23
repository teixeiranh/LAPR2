package app.domain.utils;

import app.domain.interfaces.SortingAlgorithm;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SelectionSort implements SortingAlgorithm
{
    @Override
    public <E> List<E> sortData(List<E> list, Comparator<E> c)
    {
        List<E> sortedList = new ArrayList<>(list);
        int n = list.size();
        E temp;
        for (int i = 0; i < n - 1; i++)
        {
            for (int j = i+1; j < n; j++)
            {
                if (c.compare(sortedList.get(i), sortedList.get(j)) > 0)
                {
                    temp = sortedList.get(i);
                    sortedList.set(i, sortedList.get(j));
                    sortedList.set(j, temp);
                }
            }
        }
        return sortedList;
    }

}
