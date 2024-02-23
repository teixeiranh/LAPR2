package app.ui.console;

import app.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SnsUserUI implements Runnable {

    @Override
    public void run()
    {
        List<MenuItem> options = new ArrayList<>();
        options.add(new MenuItem("Schedule a vaccination", new VaccinationScheduleUI()));
        options.add(new MenuItem("List my vaccination schedules", new SnsUserVaccinationScheduleListUI()));
        int option = 0;
        do
        {
            option = Utils.showAndSelectIndex(options, "\n\nSNS User menu:");

            if ((option >= 0) && (option < options.size()))
            {
                options.get(option).run();
            }
        }
        while (option != -1);
    }

}
