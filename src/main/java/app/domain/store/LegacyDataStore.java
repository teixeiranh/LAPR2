package app.domain.store;

import app.domain.interfaces.LoadDataFromExternalSource;
import app.domain.interfaces.SortingAlgorithm;
import app.domain.shared.Constants;
import app.domain.utils.SelectionSort;
import app.externalmodule.CsvFileReaderLegacyDataAdapter;
import app.mappers.dto.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Class responsible for managing the list of DTO objects imported from a legacy data csv file
 */
public class LegacyDataStore
{

    private List<LegacyDataDTO> registeredLegacyDataDTOList;
    private List<Comparator<LegacyDataDTO>> criteriaList;
    private SortingAlgorithm sortingAlgorithm;
    private LoadDataFromExternalSource externalDataLoader;
    private Properties configProperties;

    private final String DEFAULT_SORTING_ALGORITHM_CLASS = "app.utils.SelectionSort";
    private final String DEFAULT_LEGACY_DATA_READER_CLASS = "app.externalmodule.CsvFileReaderLegacyDataAdapter";


    public LegacyDataStore()
    {
        registeredLegacyDataDTOList = new ArrayList<>();
        criteriaList = new ArrayList<>();
        criteriaList.add(new SortByArrivalTime());
        criteriaList.add(new SortByLeavingTime());

        configProperties = new Properties();
        loadConfigProperties();

    }

    public List<LegacyDataDTO> getRegisteredLegacyDataDTOList()
    {
        return registeredLegacyDataDTOList;
    }

    public List<Comparator<LegacyDataDTO>> getSortingCriteriaList()
    {
        return new ArrayList<>(criteriaList);
    }

    public void setRegisteredLegacyDataDTOList(List<LegacyDataDTO> registeredLegacyDataDTOList)
    {
        this.registeredLegacyDataDTOList = new ArrayList<>(registeredLegacyDataDTOList);
    }

    /**
     * Comparator criterion for sorting legacyDataDTOs by arrival date time
     */
    private class SortByArrivalTime implements Comparator<LegacyDataDTO>
    {
        @Override
        public int compare(LegacyDataDTO dto1, LegacyDataDTO dto2)
        {
            if (dto1.getArrivalDateTime().isBefore(dto2.getArrivalDateTime()))
                return -1;
            if (dto1.getArrivalDateTime().isAfter(dto2.getArrivalDateTime()))
                return 1;
            return 0;
        }

        @Override
        public String toString()
        {
            return String.format("Sort by arrival date");
        }

    }

    /**
     * Comparator criterion for sorting legacyDataDTOs by leaving date time
     */
    private class SortByLeavingTime implements Comparator<LegacyDataDTO>
    {
        @Override
        public int compare(LegacyDataDTO dto1, LegacyDataDTO dto2)
        {
            int res=0;
            if (dto1.getLeavingDateTime().isBefore(dto2.getLeavingDateTime())) {
                res=-1;
            }
            if (dto1.getLeavingDateTime().isAfter(dto2.getLeavingDateTime())) {
                res=1;
            }
            return res;
        }

        @Override
        public String toString()
        {
            return String.format("Sort by leaving date");
        }
    }

    /**
     * Imports a list of legacy data DTO objects from a csv file containing data from a legacy system
     *
     * @param filePath  path to the file to be imported
     * @return  list of DTO objects containing the information about the vaccination process
     * @throws FileNotFoundException
     */
    public List<LegacyDataDTO> importLegacyDataFromExternalSource(String filePath) throws FileNotFoundException
    {
        try
        {
            Class<?> legacyDataReader = Class.forName(configProperties.getProperty(Constants.PARAMS_LEGACY_DATA_READER));
            externalDataLoader = (LoadDataFromExternalSource) legacyDataReader.getDeclaredConstructor().newInstance();
        }
        catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | ClassNotFoundException | InstantiationException e)
        {
            System.out.println(e.getMessage());
            System.out.printf("Failed to instantiate the specified legacy data reader class... Using default csv reader instead.%n");
            externalDataLoader = new CsvFileReaderLegacyDataAdapter();
        }

        List<LegacyDataDTO> importedLegacyDataDTOList = externalDataLoader.readFileFromExternalSource(filePath);

        return importedLegacyDataDTOList;
    }

    /**
     * Sorts the collection of legacy data DTO by a given comparator criterion
     *
     * @param sortingCriterion  comparator that sorts legacy data DTOS by arrival/leaving time
     * @return  list of legacy data DTO sorted according to the provided criterion
     */
    public List<LegacyDataDTO> getSortedLegacyDataDTOList(Comparator<LegacyDataDTO> sortingCriterion)
    {

        try
        {
            Class<?> sortingClass = Class.forName(configProperties.getProperty(Constants.PARAMS_SORTING_ALGORITHM));
            sortingAlgorithm = (SortingAlgorithm) sortingClass.getDeclaredConstructor().newInstance();
        }
        catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | ClassNotFoundException | InstantiationException e) {
            System.out.println(e.getMessage());
            System.out.printf("Failed to instantiate specified sorting algorithm class... Using default sorting class instead.%n");
            sortingAlgorithm = new SelectionSort();
        }

        return sortingAlgorithm.sortData(registeredLegacyDataDTOList, sortingCriterion);
    }

    public List<LegacyDataDTO> getLegacyDataDTOList()
    {
        return new ArrayList<>(registeredLegacyDataDTOList);
    }

    /**
     * Loads the config.properties file that contains information about the csv reader class and sorting algorithm to be used
     *
     */
    private void loadConfigProperties()
    {
        try
        {
            InputStream in = new FileInputStream(Constants.PARAMS_FILENAME);
            configProperties.load(in);
            in.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.out.printf("Failed to load %s file. Fallback to the default sorting algorithm and legacy data csv reader.%n",
                    Constants.PARAMS_FILENAME);
            configProperties.setProperty(Constants.PARAMS_SORTING_ALGORITHM, DEFAULT_SORTING_ALGORITHM_CLASS);
            configProperties.setProperty(Constants.PARAMS_LEGACY_DATA_READER, DEFAULT_LEGACY_DATA_READER_CLASS);
        }
    }

}
