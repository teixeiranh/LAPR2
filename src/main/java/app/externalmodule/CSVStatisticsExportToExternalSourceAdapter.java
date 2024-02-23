package app.externalmodule;

import app.domain.interfaces.IExportStatisticsToExternalSource;
import app.domain.model.VaccineAdministration;
import app.ui.console.utils.Utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * Adapter to get a csv file printed.
 */
public class CSVStatisticsExportToExternalSourceAdapter implements IExportStatisticsToExternalSource

{
    private final String CSV_EXTENSION = ".csv";

    /**
     * Method to export statistics.
     * This method get the list of users fully vaccinated and the corresponding date has a List,
     * then it uses that list in a constructor of a Set to get a list with unique entries.
     * Then it counts the repetions of the Set List in the firts list.
     * After this, it exports a file with the final list.
     *
     * @param vaccineAdministrationList
     * @param nameOfFile
     */
    @Override
    public void exportStatistics(Set<VaccineAdministration> vaccineAdministrationList,
                                 String nameOfFile)
    {
        List<LocalDate> vaccineAdministrationListOfDates = new ArrayList<>();
        Iterator<VaccineAdministration> iterator = vaccineAdministrationList.iterator();
        VaccineAdministration vc;

        while (iterator.hasNext())
        {
            vc = iterator.next();
            vaccineAdministrationListOfDates.add(Utils.convertToDateViaInstant(vc.getLeavingDate()));
        }

        List<LocalDate> vaccineAdministrationListOfUniqueDates = Utils.removeDuplicatesFromList(vaccineAdministrationListOfDates);
        Collections.sort(vaccineAdministrationListOfUniqueDates);
        Collections.sort(vaccineAdministrationListOfDates);

        List<Integer> numberOfTimes = new ArrayList<>();
        int count = 0;

        for (int i = 0; i < vaccineAdministrationListOfUniqueDates.size(); i++)
        {
            for (int j = 0; j < vaccineAdministrationListOfDates.size(); j++)
            {
                if (vaccineAdministrationListOfUniqueDates.get(i).equals(vaccineAdministrationListOfDates.get(j)))
                {
                    count++;
                }
            }
            numberOfTimes.add(count);
            count = 0;
        }

        try
        {
            BufferedWriter writer =
                    new BufferedWriter(new FileWriter(nameOfFile + CSV_EXTENSION));

            if (vaccineAdministrationListOfUniqueDates.size() != 0)
            {
                for (int i = 0; i < vaccineAdministrationListOfUniqueDates.size(); i++)
                {
                    writer.write(vaccineAdministrationListOfUniqueDates.get(i).toString()
                            + "," + numberOfTimes.get(i).toString() + "\n");
                }
            } else
            {
                writer.write("No fully vaccinated users found between dates!");
            }
            writer.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
