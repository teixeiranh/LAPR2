package app.ui.console;

import app.controller.App;
import app.controller.VaccinationScheduleController;
import app.domain.shared.Constants;
import app.mappers.dto.SnsUserDTO;
import app.mappers.dto.VaccinationCenterDTO;
import app.mappers.dto.VaccinationScheduleDTO;
import app.mappers.dto.VaccineTypeDTO;
import app.ui.console.utils.Utils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

public class VaccinationScheduleUI implements Runnable {

    private VaccinationScheduleController vaccinationScheduleController;

    public VaccinationScheduleUI()
    {
        this.vaccinationScheduleController = new VaccinationScheduleController();
    }

    @Override
    public void run()
    {
        System.out.println("\nPlease insert the vaccination schedule information below");

        boolean endVaccinationScheduleCreation;

        do
        {
            List<VaccineTypeDTO> vaccineTypes = this.vaccinationScheduleController.getAllVaccineTypes();
            Long snsNumber;
            SnsUserDTO snsUser = null;

            if (App.getInstance().getCurrentUserSession().isLoggedInWithRole(Constants.ROLE_SNS_USER))
            {
                snsUser = this.vaccinationScheduleController.getSnsUserDTOInSession();
            } else if (App.getInstance().getCurrentUserSession().isLoggedInWithRole(Constants.ROLE_RECEPTIONIST))
            {
                snsNumber = Utils.readLongFromConsole("SNS number");
                try
                {
                    snsUser = this.vaccinationScheduleController.getSnsUserDTOBySnsNumber(snsNumber);
                } catch (NoSuchElementException e)
                {
                    System.out.println("\nNo SNS User found! " +
                            "\nPlease provide SNS number for registered users only!");
                    break;
                }
            }

            try
            {
                LocalDateTime date = Utils.readDateTimeGreaterThanTodayFromConsole("Schedule date (yyyy-MM-dd HH:mm)");
                VaccineTypeDTO vaccineType = (VaccineTypeDTO) Utils.showAndSelectOne(vaccineTypes, "Vaccination type");

                //TODO: if list is empty?
                List<VaccinationCenterDTO> vaccinationCenters = this.vaccinationScheduleController.getAllVaccinationCentersByVaccineType(vaccineType);
                VaccinationCenterDTO vaccinationCenter = (VaccinationCenterDTO) Utils.showAndSelectOne(vaccinationCenters, "Vaccination center");

                VaccinationScheduleDTO vaccinationScheduleDTO = new VaccinationScheduleDTO(
                        snsUser,
                        date,
                        vaccineType,
                        vaccinationCenter
                );

                boolean vaccinationScheduleCanBeAdd = this.vaccinationScheduleController.createVaccinationSchedule(vaccinationScheduleDTO);

                if (vaccinationScheduleCanBeAdd)
                {
                    System.out.println("Invalid vaccination schedule, the vaccination schedule already exists.\n");
                    endVaccinationScheduleCreation = true;
                } else
                {
                    System.out.println("\n" + vaccinationScheduleDTO);

                    boolean confirmsUserData = Utils.confirm("Confirm vaccination schedule (s/n)?");

                    if (confirmsUserData)
                    {
                        Boolean vaccinationScheduleCreatedSuccessfully = this.vaccinationScheduleController.addVaccinationSchedule();

                        if (vaccinationScheduleCreatedSuccessfully)
                        {
                            System.out.println("Vaccination schedule created successfully.\n");
                            endVaccinationScheduleCreation = true;
                        } else
                        {
                            System.out.println("Vaccination schedule creation failed.\n");
                            endVaccinationScheduleCreation = this.reinputUserInformationUI();
                        }
                    } else
                    {
                        endVaccinationScheduleCreation = this.reinputUserInformationUI();
                    }
                }
                if (App.getInstance().getCurrentUserSession().isLoggedInWithRole(Constants.ROLE_RECEPTIONIST))
                {
                    Utils.showList(this.vaccinationScheduleController.getAllExistingVaccinationSchedule(), "List of Existing Vaccination Schedules:");
                }
            } catch (Exception exception)
            {
                System.out.println("\n" + exception.getMessage());
                endVaccinationScheduleCreation = this.reinputUserInformationUI();
            }
        } while (!endVaccinationScheduleCreation);
    }

    private boolean reinputUserInformationUI()
    {
        boolean shouldReinput = Utils.confirm("Reinput the vaccination schedule information (s/n)?");
        return !shouldReinput;
    }

}
