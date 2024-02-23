package app.ui.console;

import app.mappers.dto.VaccinationCenterDTO;
import app.ui.console.utils.Utils;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.ArrayList;
import java.util.List;

public class NurseUI implements Runnable
{

    public NurseUI()
    {

    }


    @Override
    public void run()
    {
        ChooseVaccinationCenterUI chooseVaccinationCenterUI = new ChooseVaccinationCenterUI();
        VaccinationCenterDTO vaccinationCenterDTOChoice = chooseVaccinationCenterUI.getVaccinationCenterDTOChoice();

        Email vcSelectionEmail = new Email(vaccinationCenterDTOChoice.getVaccinationCenterEmailAddress());
        String vaccinationCenterEmail = vaccinationCenterDTOChoice.getVaccinationCenterEmailAddress();

        // Add user story items to the nurse menu
        List<MenuItem> options = new ArrayList<>();
        options.add(new MenuItem("Consult users in the waiting room", new ConsultUsersWaitingRoomUI(vcSelectionEmail)));
        options.add(new MenuItem("Record the administration of a vaccine", new RecordVaccineAdministrationUI(vaccinationCenterEmail)));
        options.add(new MenuItem("Record adverse reactions of a SNS User", new RecordAdverseReactionUI()));

        int option = 0;
        do
        {
            option = Utils.showAndSelectIndex(options,"\n\nNurse Menu:");
            if ((option >= 0) && (option < options.size()))
            {
                options.get(option).run();
            }

        } while (option != -1);
    }

}
