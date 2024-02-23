package app.domain.tasks;

import app.controller.App;
import app.domain.model.VaccinationCenter;
import app.domain.store.VaccinationCenterStore;
import app.domain.store.VaccinationScheduleStore;
import app.ui.console.utils.Utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VaccinationCenterStatsDailyRecorderAdapter extends TimerTask {

    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final String VACCINATION_CENTER_STATS_FILE = "vaccination_center_stats.txt";

    private String vaccinationCenterStatsFile;

    public VaccinationCenterStatsDailyRecorderAdapter()
    {
        this.vaccinationCenterStatsFile = VACCINATION_CENTER_STATS_FILE;
    }

    public VaccinationCenterStatsDailyRecorderAdapter(String vaccinationCenterStatsFile)
    {
        this.vaccinationCenterStatsFile = vaccinationCenterStatsFile;
    }

    @Override
    public void run()
    {
        VaccinationCenterStore vaccinationCenterStore = App.getInstance().getCompany().getVaccinationCenterStore();
        VaccinationScheduleStore vaccinationScheduleStore = App.getInstance().getCompany().getVaccinationScheduleStore();

        int totalNumberOfVaccinations;
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        for (VaccinationCenter vc : vaccinationCenterStore.getAll())
        {
            totalNumberOfVaccinations = vaccinationScheduleStore.getNumberOfVaccinations(vc);
            this.writeAndAppendToFile(vc.getVaccinationCenterName() + " - " + dateFormat.format(today) + " - " + totalNumberOfVaccinations);
        }
    }

    private void writeAndAppendToFile(String content)
    {
        try
        {
            FileWriter fileWriter = new FileWriter(this.vaccinationCenterStatsFile, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(content);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (Exception ex)
        {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
