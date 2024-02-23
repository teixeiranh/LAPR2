package app.ui.console;

import app.controller.CreateVaccinationCenterController;
import app.domain.model.VaccinationCenter;
import app.ui.console.utils.Utils;

import java.util.ArrayList;

public class ListVaccinationCentersUI implements Runnable{

    private CreateVaccinationCenterController controller;

    public ListVaccinationCentersUI() {
        this.controller = new CreateVaccinationCenterController();
    }


    @Override
    public void run(){
        ArrayList<VaccinationCenter> vaccinationCentersList = controller.getVaccinationCenter();
        Utils.showAndSelectOne(vaccinationCentersList, "Vaccination Centers List");
    }


}
