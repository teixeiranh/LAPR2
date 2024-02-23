package app.ui.console;

import app.controller.SpecifyNewVaccineTypeController;
import app.domain.model.VaccineTechnology;
import app.mappers.dto.VaccineTypeDTO;
import app.ui.console.utils.Utils;

public class SpecifyNewVaccineTypeUI implements Runnable {

    private SpecifyNewVaccineTypeController controller;

    public SpecifyNewVaccineTypeUI()
    {
        controller = new SpecifyNewVaccineTypeController();
    }

    @Override
    public void run()
    {
        System.out.println("\nPlease insert the Vacine Type information:");

        boolean success;

        do {
            String vaccineTypeCode = Utils.readLineFromConsole("Code of Vacine Type: ");
            String vaccineTypeDescription = Utils.readLineFromConsole("Description of the Vacine Type: ");
            VaccineTechnology.getAll();
            VaccineTechnology vaccineTypeTechnology = VaccineTechnology.valueOf(Utils.readLineFromConsole("Choose vaccine type technology (Acronym: LA, IV, SV, TV, VV, MRV)"));

            VaccineTypeDTO vaccineTypeDTO = new VaccineTypeDTO(
                    vaccineTypeCode,
                    vaccineTypeDescription,
                    vaccineTypeTechnology);
            try {
            boolean vaccineTypeAlreadyExists = this.controller.checkIfVaccineTypeAlreadyExists(vaccineTypeDTO);
            success = vaccineTypeAlreadyExists;

            if (vaccineTypeAlreadyExists) {
                System.out.println("Type of Vaccine with that code already exists.\n ");
            } else {
                success = this.controller.specifyNewVaccineType(vaccineTypeDTO);
            }
        }catch (IllegalArgumentException e)
            {
                System.out.println("\n" + e.getMessage());
                success = this.reinsertVaccineTypeInformationUI();
            }

        } while (!success);
    }

    private boolean reinsertVaccineTypeInformationUI()
    {
        boolean shouldReinsert = Utils.confirm("Reinsert the Vaccine Type information (s/n)?");
        return !shouldReinsert;
    }
}

