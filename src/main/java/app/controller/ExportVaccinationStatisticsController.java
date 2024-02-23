package app.controller;

import app.domain.model.Company;
import app.domain.model.VaccineAdministration;
import app.domain.store.*;
import app.externalmodule.CSVStatisticsExportToExternalSourceAdapter;

import java.util.Date;
import java.util.Set;

/**
 * Controller for the VaccinationSchedule class, to handle and process UI information.
 */
public class ExportVaccinationStatisticsController
{

    private App app;
    private Company company;
    private VaccineAdministrationStore vaccineAdministrationStore;
    private CSVStatisticsExportToExternalSourceAdapter csvStatisticsExportToExternalSourceAdapter;


    public ExportVaccinationStatisticsController()
    {
        this.app = App.getInstance();
        this.company = app.getCompany();
        this.vaccineAdministrationStore = this.company.getVaccineAdministrationStore();
        this.csvStatisticsExportToExternalSourceAdapter = new CSVStatisticsExportToExternalSourceAdapter();
    }

    /**
     * Method to obtain list of fully vaccinated users.
     * @param startDate start date for the analysis
     * @param endDate end date for the analysis
     * @return
     */
    public Set<VaccineAdministration> getListOfFullyVaccinatedUsersByDate(Date startDate, Date endDate)
    {
        return this.vaccineAdministrationStore.getListFullyVaccinatedUsersBetweenDatesOfUserCenter(startDate, endDate);
    }

    /**
     * Method to print the list of users pretended by the user of the app.
     * @param listOfFullyVaccinatedUsersByDate list of users fully vaccinated
     * @param fileName name of the file pretended
     */
    public void printListOfUsers(Set<VaccineAdministration> listOfFullyVaccinatedUsersByDate,
                                 String fileName)
    {
        this.csvStatisticsExportToExternalSourceAdapter.exportStatistics(listOfFullyVaccinatedUsersByDate,fileName);
    }





}
