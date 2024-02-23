package app.ui.console;

import app.controller.ExportVaccinationStatisticsController;
import app.domain.model.VaccinationSchedule;
import app.domain.model.VaccineAdministration;
import app.mappers.dto.VaccinationCenterDTO;
import app.ui.console.utils.Utils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ExportVaccinationStatisticsUI implements Runnable
{

    private ExportVaccinationStatisticsController exportVaccinationStatisticsController;

    public ExportVaccinationStatisticsUI()
    {
        this.exportVaccinationStatisticsController = new ExportVaccinationStatisticsController();
    }

    @Override
    public void run()
    {
        System.out.println("\nPlease insert the Export Information below");

        Date startDate = Utils.readDateFromConsoleYearMonthDay("Start date for analysis (yyyy-MM-dd)");
        Date endDate = Utils.readDateFromConsoleYearMonthDay("End date for analysis (yyyy-MM-dd)");
        String fileName = Utils.readLineFromConsole("What is the csv file name?");

        Set<VaccineAdministration> listOfFullyVaccinatedUsersByDate =
                this.exportVaccinationStatisticsController
                        .getListOfFullyVaccinatedUsersByDate(startDate,endDate);

        this.exportVaccinationStatisticsController.
                printListOfUsers(listOfFullyVaccinatedUsersByDate,fileName);
    }
}
