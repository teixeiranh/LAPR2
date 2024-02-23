package app.ui.console;

import app.controller.CreateVaccinationCenterController;
import app.domain.model.Employee;
import app.domain.model.VaccineType;
import app.domain.shared.Role;
import app.mappers.dto.AddressDTO;
import app.mappers.dto.HealthCareVaccinationCenterDTO;
import app.ui.console.utils.Utils;

import java.util.ArrayList;

public class HealthcareVaccinationCenterUI implements Runnable{

    private CreateVaccinationCenterController controller;


    public HealthcareVaccinationCenterUI()
    {
       this.controller = new CreateVaccinationCenterController();
    }




    @Override
    public void run()
    {
        System.out.println("Health Care VC");
        boolean endRegistration;
        do
        {
            String name = Utils.readLineFromConsole(" Health Care Vaccination Center Name: ");
            long phoneNumber = Utils.readIntegerFromConsole("Health Care Vaccination Center Phone Number: ");
            AddressDTO address = AddressUI.readAddressFromConsole();
            String emailAddress = Utils.readLineFromConsole("Health Care Vaccination Center Email Address(eg: vaccinationCenter@gmail.com): ");
            long faxNumber = Utils.readIntegerFromConsole("Health Care Vaccination Center Fax Number: ");
            String websiteAddress = Utils.readLineFromConsole("Health Care Vaccination Center Web Site Address: ");
            String openingHours = Utils.readLineFromConsole("Health Care Vaccination Center Opening Hours(eg: 8:00 AM): ");
            String closingHours = Utils.readLineFromConsole("Health Care Vaccination Center Closing Hours(eg: 8:00 PM): ");
            int slotDuration = Utils.readIntegerFromConsole("Health Care Vaccination Center Slot Duration (in minutes): ");
            int maxNumVaccines = Utils.readIntegerFromConsole("Health Care Vaccination Center maximum number of vaccines that can be given per slot ");
            Employee centerCoordinator = (Employee) Utils.showAndSelectOne(controller.getEmployeeByRole(Role.CENTER_COORDINATOR.getDescription()), "\nCenter Coordinator Employee Name: ");
            String ars = Utils.readLineFromConsole("Associated ARS: ");
            String ages = Utils.readLineFromConsole("Associated AGES: ");
            ArrayList<VaccineType> vaccineType = readVaccinesFromConsole();

            try{
            HealthCareVaccinationCenterDTO healthCareVaccinationCenterDTO = new HealthCareVaccinationCenterDTO(name,
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
                    ars,
                    ages,
                    vaccineType);

                boolean vaccinationCenterAlreadyExists = this.controller.checkIfVaccinationCenterAlreadyExists(healthCareVaccinationCenterDTO);
                endRegistration = vaccinationCenterAlreadyExists;

                if (vaccinationCenterAlreadyExists) {
                    System.out.println("This Vaccination Center Already Exists\n");
                } else {
                    endRegistration = this.registerHealthcareVaccinationCenter(healthCareVaccinationCenterDTO);
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("\n" + exception.getMessage());
                endRegistration = this.reinputVaccinationCenterInformationUI();
            }
        } while (!endRegistration);
    }



    private boolean registerHealthcareVaccinationCenter(HealthCareVaccinationCenterDTO healthCareVaccinationCenterDTO) {
        System.out.println("\n" + healthCareVaccinationCenterDTO);
        boolean confirmVaccinationCenterData = Utils.confirm("Confirm Health Care Vaccination Center information inserted? (s/n");
        if (confirmVaccinationCenterData) {
            boolean centerSuccessfullyRegistered = this.controller.registerHealthcareVaccinationCenter(healthCareVaccinationCenterDTO);
            if (centerSuccessfullyRegistered) {
                System.out.println("Health Care Vaccination Center registered with Success!\n");
                return true;
            } else {
                System.out.println("Health Care Vaccination Center register failed!\n");
                return this.reinputVaccinationCenterInformationUI();
            }
        } else {
            return this.reinputVaccinationCenterInformationUI();
        }
    }


    private boolean reinputVaccinationCenterInformationUI() {
        boolean shouldReinput = Utils.confirm("Reinput Health Care Vaccination Center information (s/n)?");
        return !shouldReinput;
    }


    public ArrayList<VaccineType> readVaccinesFromConsole()
    {

        ArrayList<VaccineType> vaccineTypeList = new ArrayList<VaccineType>();
        int i = 1;
        do {
            VaccineType vaccineType =  (VaccineType) Utils.showAndSelectOne(controller.getVaccineType(), "\nVaccine Type Codes, please select one ");
            vaccineTypeList.add(vaccineType);
            System.out.println("\nAdd another vaccine to VC?");
            System.out.println("0 - No ");
            System.out.println("1 - yes ");
            i = Utils.readIntegerFromConsole("");

        } while(i!=0);

        return vaccineTypeList;
    }

}
