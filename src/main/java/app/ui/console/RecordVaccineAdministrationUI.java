package app.ui.console;


import app.controller.VaccineAdministrationController;
import app.mappers.dto.*;
import app.ui.console.utils.Utils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class RecordVaccineAdministrationUI implements Runnable {

    private VaccineAdministrationController vaccineAdministrationController;
    private  String vaccinationCenterEmail;

    public RecordVaccineAdministrationUI(String vaccinationCenterEmail) {
        this.vaccineAdministrationController = new VaccineAdministrationController();
        this.vaccinationCenterEmail = vaccinationCenterEmail;

    }


    @Override
    public void run() {

        boolean endVaccinationAdministration = true;


        do {

            List<SnsUserArrivalDTO> snsUsersWaiting = this.vaccineAdministrationController.getSnsUsersOnWaitingRoom(vaccinationCenterEmail);
            if(snsUsersWaiting.isEmpty()){
                System.out.println("Please try another Vaccination Center!");
                while (!endVaccinationAdministration);
            }else {
                SnsUserArrivalDTO snsUserArrivalDTO = (SnsUserArrivalDTO) Utils.showAndSelectOne(snsUsersWaiting, "Users On Waiting Room, please select the next one to be vaccinated");
                System.out.println("Please check below the registered vaccines' adverse reactions for the selected SNS User:");
                int i = 0;
                do {
                    Optional<SnsUserAdverseReactionsDTO> snsUserAdverseReactionsDTO = this.vaccineAdministrationController.getAllAdverseReactionBySnsNumberAndVaccineType(snsUserArrivalDTO.getSnsNumber(), snsUserArrivalDTO.getVaccineSchedule().getVaccineType().getCode());
                    if (snsUserAdverseReactionsDTO.isEmpty()) {
                        System.out.println("No adverse reactions Registered");
                    } else {
                        System.out.println("\n" + snsUserAdverseReactionsDTO);
                    }
                    System.out.println("1 - Continue");
                    i = Utils.readIntegerFromConsole("");
                } while (i != 1 && !endVaccinationAdministration);


                List<VaccineDTO> vaccinesAvailable = this.vaccineAdministrationController.getAllAvailableVaccinesByVaccineTypeAndAge(snsUserArrivalDTO.getVaccinationSchedule().getSnsUser().getAge(), snsUserArrivalDTO.getVaccinationSchedule().getVaccineType());
                VaccineDTO chosenVaccine = (VaccineDTO) Utils.showAndSelectOne(vaccinesAvailable, "Please choose vaccine to be administered ");


                LocalDateTime administrationDate = LocalDateTime.now();
                int doseNumber = (this.vaccineAdministrationController.getNumberOfDosesTakenBySnsUser(snsUserArrivalDTO.getVaccineSchedule().getVaccineType(), snsUserArrivalDTO.getVaccinationSchedule().getSnsUser().getSnsNumber()) + 1);
                int k = 0;
                do {
                    double dosage = this.vaccineAdministrationController.getDosageBasedOnVaccineAndDoseNumber(chosenVaccine, snsUserArrivalDTO.getVaccineSchedule().getSnsUser().getAge(), doseNumber);
                    System.out.printf("The user is taking the dose number %d and the dosage to administer is %.2f ml \n", doseNumber, dosage);
                    System.out.println("\n1 - Continue");
                    k = Utils.readIntegerFromConsole("");
                } while (k != 1);
                String lotNumber = Utils.readLineFromConsole("Please insert vaccine's lot number");

                LocalDateTime leavingDate = administrationDate.plusMinutes(30);


                VaccineAdministrationDTO vaccineAdministrationDTO = new VaccineAdministrationDTO(
                        snsUserArrivalDTO,
                        snsUserArrivalDTO.getVaccinationSchedule().getSnsUser(),
                        chosenVaccine,
                        doseNumber,
                        lotNumber,
                        administrationDate,
                        leavingDate

                );


                endVaccinationAdministration = this.registerVaccineAdministrationUI(vaccineAdministrationDTO);
                boolean vaccinationScheduleChangeOfState = this.vaccineAdministrationController.changeVaccinationScheduleState(vaccineAdministrationDTO);
                if (vaccinationScheduleChangeOfState) {
                    System.out.println("Vaccination Schedule is finalized");
                }

                if (this.vaccineAdministrationController.changeStateOfAdministrationWhenUserIsFullyVaccinated(vaccineAdministrationDTO)) {
                    System.out.println("User is fully vaccinated, all doses administered");
                    this.vaccineAdministrationController.sendSmsToUser(vaccineAdministrationDTO);
                }
            }
        } while (!endVaccinationAdministration);
    }


    private boolean registerVaccineAdministrationUI(VaccineAdministrationDTO vaccineAdministrationDTO) {
        System.out.println("\n" + vaccineAdministrationDTO);
        boolean confirmsData = Utils.confirm("Confirm vaccine administration information (s/n)?");
        if (confirmsData) {
            boolean registeredSuccessfully = this.vaccineAdministrationController.registerVaccineAdministration(vaccineAdministrationDTO);
            if (registeredSuccessfully) {
                System.out.println("Vaccine administration registered successfully.\n");
                return true;
            } else {
                System.out.println("Vaccine administration register failed.\n");
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