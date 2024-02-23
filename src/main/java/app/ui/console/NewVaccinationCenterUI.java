package app.ui.console;

import app.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class NewVaccinationCenterUI implements Runnable {

    @Override
    public void run()
    {
        List <MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Health Care Vaccination Center", new HealthcareVaccinationCenterUI()));
        options.add(new MenuItem("Community Mass Vaccination Center", new CommunityMassVaccinationCenterUI()));
        options.add(new MenuItem("List of existing Vaccination Centers", new ListVaccinationCentersUI()));

        int option = 0;

        do
        {
            option = Utils.showAndSelectIndex(options, "\n\nTypes of Vaccination Centers available for creation:");

            if ((option >= 0) && (option < options.size()))
            {
                options.get(option).run();
            }
        }
        while (option != -1);
    }

}
