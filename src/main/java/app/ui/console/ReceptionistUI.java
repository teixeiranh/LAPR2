package app.ui.console;

import app.domain.model.VaccinationCenter;
import app.mappers.dto.VaccinationCenterDTO;
import app.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ReceptionistUI implements Runnable {

    private ChooseVaccinationCenterUI chooseVaccinationCenterUI;

    @Override
    public void run()
    {

        ChooseVaccinationCenterUI chooseVaccinationCenterUI = new ChooseVaccinationCenterUI();

        VaccinationCenterDTO vaccinationCenterDTOChoice = chooseVaccinationCenterUI.getVaccinationCenterDTOChoice();

        if (vaccinationCenterDTOChoice != null)
        {
            do
            {
                try
                {
                    List<MenuItem> options = new ArrayList<MenuItem>();
                    options.add(new MenuItem("Register a SNS User", new RegisterSnsUserUI()));
                    options.add(new MenuItem("Schedule a Vaccination", new VaccinationScheduleUI()));
                    options.add(new MenuItem("Registar an Arrival of a SNS User", new RegisterSnsUserArrivalUI(vaccinationCenterDTOChoice)));
                    int option = 0;
                    do
                    {
                        option = Utils.showAndSelectIndex(options, "\n\nReceptionist Menu:");

                        if ((option >= 0) && (option < options.size()))
                        {
                            options.get(option).run();
                        }
                    }
                    while (option != -1);
                } catch (IllegalArgumentException e)
                {
                    System.out.println("Invalid Vaccination Center, please try again!");
                }
            } while (vaccinationCenterDTOChoice == null);
        }
    }

}
