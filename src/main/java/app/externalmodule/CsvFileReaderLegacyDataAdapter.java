package app.externalmodule;

import app.domain.interfaces.LoadDataFromExternalSource;
import app.mappers.dto.LegacyDataDTO;
import java.io.FileNotFoundException;
import java.util.List;

public class CsvFileReaderLegacyDataAdapter implements LoadDataFromExternalSource
{

    private CsvFileReaderLegacyDataAdaptee csvFileReaderLegacyDataAdaptee;

    public CsvFileReaderLegacyDataAdapter()
    {
        csvFileReaderLegacyDataAdaptee = new CsvFileReaderLegacyDataAdaptee();
    }

    @Override
    public List<LegacyDataDTO> readFileFromExternalSource(String filePath) throws FileNotFoundException
    {
        return csvFileReaderLegacyDataAdaptee.importDataFromCsvFile(filePath);
    }


}
