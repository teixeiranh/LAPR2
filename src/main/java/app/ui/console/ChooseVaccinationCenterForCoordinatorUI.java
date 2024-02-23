package app.ui.console;
import app.controller.App;
import app.controller.ChooseVaccinationCenterforCoordinatorController;
import app.controller.RegisterSnsUserArrivalController;
import app.domain.model.Employee;
import app.domain.model.VaccinationCenter;
import app.mappers.dto.VaccinationCenterDTO;
import app.ui.console.utils.Utils;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.List;

public class ChooseVaccinationCenterForCoordinatorUI implements Runnable{
//    private RegisterSnsUserArrivalController RegisterSnsUserArrivalController;
    private ChooseVaccinationCenterforCoordinatorController chooseVaccinationCenterforCoordinatorController;
    private VaccinationCenter vaccinationCenterChoice;
    private String currentCoordinatorId=App.getInstance().getCurrentUserSession().getUserId().getEmail();;

    public ChooseVaccinationCenterForCoordinatorUI() {
        this.chooseVaccinationCenterforCoordinatorController = new ChooseVaccinationCenterforCoordinatorController();
        run();
    }

    @Override
    public void run(){
        List<VaccinationCenter> vaccinationCenters = this.chooseVaccinationCenterforCoordinatorController.getVaccinationCenters();

        for (VaccinationCenter vc : vaccinationCenters)
        {
            if (vc.getCenterCoordinator().getEmail().equalsIgnoreCase(this.currentCoordinatorId))
            {
                this.vaccinationCenterChoice = vc;
                this.chooseVaccinationCenterforCoordinatorController.getEmployeeById(this.currentCoordinatorId).get().setVaccinationCenter(vc);
                break;
            }
        }
    }

//    public VaccinationCenterDTO getVaccinationCenterDTOChoice(){
//
//        return vaccinationCenterDTOChoice;
//
//    }

    public boolean isCoordinatorAssigned()
    {
        try
        {
            VaccinationCenter vc = this.chooseVaccinationCenterforCoordinatorController.
                    getEmployeeById(this.currentCoordinatorId).get().getVaccinationCenter();
            return true;
        } catch (Exception e)
        {
            return false;
        }

//        if (vc != null)
//        {
//            return true;
//        }
//        return false;
    }

}

