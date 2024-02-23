package app.ui.console;

import app.controller.CreateVaccinationCenterController;
import app.domain.model.Employee;
import app.domain.model.VaccineType;
import app.domain.shared.Role;
import app.mappers.dto.AddressDTO;
import app.mappers.dto.CommunityMassVaccinationCenterDTO;
import app.ui.console.utils.Utils;

public class CommunityMassVaccinationCenterUI implements Runnable {


    private CreateVaccinationCenterController controller;

    public CommunityMassVaccinationCenterUI() {
        this.controller = new CreateVaccinationCenterController();
    }


    @Override
    public void run() {
        System.out.println("Community Mass VC");

        boolean endRegistration;
        do {
            String name = Utils.readLineFromConsole("Community Mass Vaccination Center Name: ");
            long phoneNumber = Utils.readLongFromConsole("Community Mass Vaccination Center Phone Numver: ");
            AddressDTO address = AddressUI.readAddressFromConsole();
            String emailAddress = Utils.readLineFromConsole("Community Mass Vaccination Center Email Address: ");
            long faxNumber = Utils.readIntegerFromConsole("Community Mass Vaccination Center Fax Number: ");
            String websiteAddress = Utils.readLineFromConsole("Community Mass Vaccination Center Web Site Address: ");
            String openingHours = Utils.readLineFromConsole("Community Mass Vaccination Center Opening Hours(eg: 08:00 AM): ");
            String closingHours = Utils.readLineFromConsole("Community Mass Vaccination Center Closing Hours(eg: 07:00 PM): ");
            int slotDuration = Utils.readIntegerFromConsole("Community Mass Vaccination Center Slot Duration (in minutes): ");
            int maxNumVaccines = Utils.readIntegerFromConsole("Community Mass Vaccination Center maximum number of vaccines that can be given per slot ");
            Employee centerCoordinator = (Employee) Utils.showAndSelectOne(controller.getEmployeeByRole(Role.CENTER_COORDINATOR.getDescription()), "\nCenter Coordinator Employee Name: ");
            VaccineType vaccineType = (VaccineType) Utils.showAndSelectOne(controller.getVaccineType(), "\nVaccine Type Codes ");

            try {
                CommunityMassVaccinationCenterDTO communityMassVaccinationCenterDTO = new CommunityMassVaccinationCenterDTO(
                        name,
                        phoneNumber,
                        address,
                        emailAddress,
                        faxNumber,
                        websiteAddress,
                        openingHours,
                        closingHours,
                        slotDuration,
                        maxNumVaccines,
                        centerCoordinator,
                        vaccineType);


                boolean vaccinationCenterAlreadyExists = this.controller.checkIfVaccinationCenterAlreadyExists(communityMassVaccinationCenterDTO);
                endRegistration = vaccinationCenterAlreadyExists;

                if (vaccinationCenterAlreadyExists) {
                    System.out.println("This Vaccination Center Already Exists\n");
                } else {
                    endRegistration = this.registerCommunityMassVaccinationCenter(communityMassVaccinationCenterDTO);
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("\n" + exception.getMessage());
                endRegistration = this.reinputVaccinationCenterInformationUI();
            }
        } while (!endRegistration);
    }


    private boolean registerCommunityMassVaccinationCenter(CommunityMassVaccinationCenterDTO communityMassVaccinationCenterDTO) {
        System.out.println("\n" + communityMassVaccinationCenterDTO);
        boolean confirmVaccinationCenterData = Utils.confirm("Confirm Community Mass Vaccination Center information inserted? (s/n");
        if (confirmVaccinationCenterData) {
            boolean centerSuccessfullyRegistered = this.controller.registerCommunityMassVaccinationCenter(communityMassVaccinationCenterDTO);
            if (centerSuccessfullyRegistered) {
                System.out.println("Community Mass Vaccination Center registered with Success!\n");
                return true;
            } else {
                System.out.println("Community Mass Vaccination Center register failed!\n");
                return this.reinputVaccinationCenterInformationUI();
            }
        } else {
            return this.reinputVaccinationCenterInformationUI();
        }
    }


    private boolean reinputVaccinationCenterInformationUI() {
        boolean shouldReinput = Utils.confirm("Reinput Community Mass Vaccination Center information (s/n)?");
        return !shouldReinput;
    }

}
