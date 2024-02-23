package app.externalmodule;

import app.domain.model.SnsNumber;
import app.domain.shared.DoseNumber;
import app.mappers.LegacyDataMapper;
import app.mappers.dto.LegacyDataDTO;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class CsvFileReaderLegacyDataAdaptee
{
    private LegacyDataMapper legacyDataMapper;

    private final String[] FILE_HEADER = {
            "SNSUserNumber", "VaccineName", "Dose", "LotNumber", "ScheduledDateTime",
            "ArrivalDateTime", "NurseAdministrationDateTime", "LeavingDateTime"
    };

    private final String SEPARATOR = ";";


    public CsvFileReaderLegacyDataAdaptee() {legacyDataMapper = new LegacyDataMapper();}

    public List<LegacyDataDTO> importDataFromCsvFile(String filePath)
            throws FileNotFoundException, HeaderNotMatchException, InvalidTypeFileException
    {
        validateFileExtension(filePath);
        Scanner csvReader = new Scanner(new File(filePath));
        List<LegacyDataDTO> legacyDataDTOList = new ArrayList<>();
        String line = csvReader.nextLine();
        String[] lineItems = line.split(SEPARATOR);
        validateHeader(lineItems);
        int lineCount = 0;
        while (csvReader.hasNextLine())
        {
            line = csvReader.nextLine();
            lineItems = line.split(SEPARATOR);
            lineCount++;
            try
            {
                LegacyDataDTO legacyDataDTO = createLegacyDataDTOFromCsvLine(lineItems);
                legacyDataDTOList.add(legacyDataDTO);
            }
            catch (DateTimeParseException e)
            {
                System.out.println(e.getMessage());
                System.out.printf("Invalid date format on line %d. Current line discarded, reading next line...%n", lineCount);
            }
            catch (RuntimeException e)
            {
                System.out.println(e.getMessage());
                System.out.printf("Invalid data on line %d. Current line discarded, reading next line...%n", lineCount);
            }
        }
        csvReader.close();
        return legacyDataDTOList;
    }

    public LegacyDataDTO createLegacyDataDTOFromCsvLine(String[] lineItems) throws DateTimeParseException
    {
        SnsNumber snsUserNumber = new SnsNumber((long) Integer.parseUnsignedInt(lineItems[0]));
        String vaccineName = lineItems[1];
        int vaccineDose = DoseNumber.getDoseNumberIfExists(lineItems[2]);
        String lotNumber = lineItems[3];
        LocalDateTime vaccineScheduleDateTime = readDateTime(lineItems[4]);
        LocalDateTime arrivalDateTime = readDateTime(lineItems[5]);
        LocalDateTime vaccineAdministrationDateTime = readDateTime(lineItems[6]);
        LocalDateTime leavingDateTime = readDateTime(lineItems[7]);
        return legacyDataMapper.toDTO(
                snsUserNumber, vaccineName, vaccineDose, lotNumber, vaccineScheduleDateTime,
                arrivalDateTime, vaccineAdministrationDateTime, leavingDateTime
        );
    }

    public boolean validateHeader(String[] header) throws HeaderNotMatchException
    {
        if (header.length != FILE_HEADER.length)
        {
            throw new HeaderNotMatchException("Invalid header size!");
        }

        for (int i = 0; i < header.length; i++)
        {

            if (!header[i].trim().equalsIgnoreCase(FILE_HEADER[i]))
            {
                throw new HeaderNotMatchException("Invalid file header!");
            }
        }

        return true;
    }

    public LocalDateTime readDateTime(String dateTimeString) throws DateTimeParseException
    {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("[M/d/yyyy H:m]")
                .appendPattern("[M-d-yyyy H:m]")
                .toFormatter();
        return LocalDateTime.parse(dateTimeString, formatter);
    }


    public boolean validateFileExtension(String filePath) throws InvalidTypeFileException {
        boolean validExtension = filePath.matches(".+\\.csv$");

        if (!validExtension) {
            throw new InvalidTypeFileException("The file is not a .csv file.");
        }
        return validExtension;
    }


}
