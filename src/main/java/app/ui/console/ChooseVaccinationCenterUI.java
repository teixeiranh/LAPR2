package app.ui.console;
import app.controller.RegisterSnsUserArrivalController;
import app.mappers.dto.VaccinationCenterDTO;
import app.ui.console.utils.Utils;

import java.util.List;

public class ChooseVaccinationCenterUI implements Runnable{
    private RegisterSnsUserArrivalController RegisterSnsUserArrivalController;
    private VaccinationCenterDTO vaccinationCenterDTOChoice;

    public ChooseVaccinationCenterUI() {
        this.RegisterSnsUserArrivalController = new RegisterSnsUserArrivalController();
        run();
    }

    @Override
    public void run(){
        List<VaccinationCenterDTO> vaccinationCenters = this.RegisterSnsUserArrivalController.getAllVaccinationCenters();
        VaccinationCenterDTO vaccinationCenterDTO = (VaccinationCenterDTO) Utils.showAndSelectOne(vaccinationCenters, "Please choose the Vaccination center where you work");
        vaccinationCenterDTOChoice = vaccinationCenterDTO;
    }

    public VaccinationCenterDTO getVaccinationCenterDTOChoice(){

        return vaccinationCenterDTOChoice;
    }

}

