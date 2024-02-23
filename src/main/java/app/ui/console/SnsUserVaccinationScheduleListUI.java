package app.ui.console;

import app.controller.VaccinationScheduleController;
import app.mappers.dto.VaccinationScheduleDTO;

import java.util.List;

public class SnsUserVaccinationScheduleListUI implements Runnable {

    private VaccinationScheduleController vaccinationScheduleController;

    public SnsUserVaccinationScheduleListUI()
    {
        this.vaccinationScheduleController = new VaccinationScheduleController();
    }

    @Override
    public void run()
    {
        System.out.println("\nHere are your schedules");

        List<VaccinationScheduleDTO> vaccinationSchedules = this.vaccinationScheduleController.getVaccinationSchedulesOfUserInSession();

        if (vaccinationSchedules.isEmpty())
        {
            System.out.println("N/A");
        } else
        {
            for (VaccinationScheduleDTO vaccinationSchedule : vaccinationSchedules)
            {
                System.out.println(vaccinationSchedule + "\n");
            }
        }
    }

}
