package app.ui.console;


import app.controller.RegisterSnsUserArrivalController;
import app.domain.store.exception.SnsUserArrivalForClosedVaccinationCenterWithAttributeExcepetion;
import app.domain.store.exception.SnsUserArrivalForIncorretVaccinationCenterWithAttributeExcepetion;
import app.domain.store.exception.SnsUserArrivalForNotHavingaScheduleWithAttributeException;
import app.domain.store.exception.SnsUserArrivalForSameDateAlreadyExistsInSoreWithAttributeExcepetion;
import app.mappers.dto.SnsUserArrivalDTO;
import app.mappers.dto.VaccinationCenterDTO;
import app.mappers.dto.VaccinationScheduleDTO;
import app.ui.console.utils.Utils;

import java.time.LocalDateTime;
import java.util.List;

public class RegisterSnsUserArrivalUI implements Runnable {

    private VaccinationCenterDTO vaccinationCenterDTOChoice;
    private final RegisterSnsUserArrivalController controller;

    private VaccinationCenterDTO vaccinationCenterDTO;

    public RegisterSnsUserArrivalUI(VaccinationCenterDTO vaccinationCenterDTOChoice) {
        this.controller = new RegisterSnsUserArrivalController();
        this.vaccinationCenterDTO = vaccinationCenterDTOChoice;
    }

    @Override
    public void run() {
        System.out.println("\nPlease insert the Sns user information below");

        boolean endSnsUserArrivalRegistration;

        do {

            Long snsNumber = Utils.readLongFromConsole("SNS number");
            LocalDateTime arrivalDate = LocalDateTime.now();
            String vaccinationCenterName = vaccinationCenterDTO.getVaccinationCenterName();

            try {
                VaccinationScheduleDTO vaccineSchedule = this.controller.getSnsUserVaccinationSchedule(snsNumber, arrivalDate);
                boolean userScheduleCheck = false;
                if (vaccineSchedule == null ? userScheduleCheck == true : userScheduleCheck == false) ;

                if (userScheduleCheck) {
                    throw new SnsUserArrivalForNotHavingaScheduleWithAttributeException("Sns Number", String.valueOf(snsNumber));
                }
                if (!this.controller.checkIfUserAlreadyRegisteredTodayAnArrival(snsNumber, arrivalDate)) {
                    throw new SnsUserArrivalForSameDateAlreadyExistsInSoreWithAttributeExcepetion("Sns Number", String.valueOf(snsNumber), "arrival time", String.valueOf(arrivalDate));
                }
                if (!this.controller.checkIfVaccinationCenterIsTheOneOnTheVaccinationSchedule(vaccineSchedule, vaccinationCenterName)) {
                    throw new SnsUserArrivalForIncorretVaccinationCenterWithAttributeExcepetion("Sns Number", String.valueOf(snsNumber), "Vaccination Center", String.valueOf(vaccinationCenterName));
                }
                if (!this.controller.checkIfUserArrivedWithinVaccinationCenterWorkingHours(arrivalDate, vaccinationCenterName)) {
                    throw new SnsUserArrivalForClosedVaccinationCenterWithAttributeExcepetion("Sns Number", String.valueOf(snsNumber), "Vaccination Center", String.valueOf(vaccinationCenterName));
                } else {
                    SnsUserArrivalDTO snsUserArrivalDTO = new SnsUserArrivalDTO(
                            arrivalDate,
                            snsNumber,
                            vaccineSchedule);

                    endSnsUserArrivalRegistration = this.registerSnsUserArrivalUI(snsUserArrivalDTO);
                }

            } catch (SnsUserArrivalForNotHavingaScheduleWithAttributeException |
                     SnsUserArrivalForIncorretVaccinationCenterWithAttributeExcepetion |
                     SnsUserArrivalForClosedVaccinationCenterWithAttributeExcepetion |
                     SnsUserArrivalForSameDateAlreadyExistsInSoreWithAttributeExcepetion e) {
                endSnsUserArrivalRegistration = this.reinputUserInformationUI();
            }

        } while (!endSnsUserArrivalRegistration);
    }

    private boolean registerSnsUserArrivalUI(SnsUserArrivalDTO snsUserArrivalDTO) {
        System.out.println("\n" + snsUserArrivalDTO);
        boolean confirmsUserData = Utils.confirm("Confirm SNS user information (s/n)?");
        if (confirmsUserData) {
            boolean registeredSuccessfully = this.controller.registerNewSnsUserArrival(snsUserArrivalDTO);
            if (registeredSuccessfully) {
                System.out.println("SNS user Arrival registered successfully.\n");
                return true;
            } else {
                System.out.println("SNS user Arrival registered failed.\n");
                return this.reinputUserInformationUI();
            }
        } else {
            return this.reinputUserInformationUI();
        }
    }

    private boolean reinputUserInformationUI() {
        boolean shouldReinput = Utils.confirm("Reinput the SNS user information (s/n)?");
        return !shouldReinput;
    }
}
