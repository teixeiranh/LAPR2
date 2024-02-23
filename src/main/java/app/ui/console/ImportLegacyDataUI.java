package app.ui.console;

import app.controller.ImportLegacyDataController;
import app.domain.model.Vaccine;
import app.domain.store.exception.VaccineDoesNotExistException;
import app.externalmodule.HeaderNotMatchException;
import app.externalmodule.InvalidTypeFileException;
import app.mappers.dto.LegacyDataDTO;
import app.ui.console.utils.Utils;

import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ImportLegacyDataUI implements Runnable
{

    private ImportLegacyDataController importLegacyDataController;

    public ImportLegacyDataUI() {
        this.importLegacyDataController = new ImportLegacyDataController();
    }
    @Override
    public void run()
    {
        String vcEmail = "vaccinationPorto@email.com"; // temporario enquanto não está definida a estrategia para ir buscar o centro
        boolean exit = false;
        while (!exit)
        {
            boolean askAgain;
            do
            {
                System.out.println("Insert the path of the Legacy Data file: ");
                Scanner in = new Scanner(System.in);
                String pathFile = in.nextLine();
                try
                {
                    boolean success = importLegacyDataController.importLegacyDataFromExternalSource(pathFile, vcEmail);
                    if (success)
                    {
                        List<Comparator<LegacyDataDTO>> sortingCriteriaList = importLegacyDataController.getSortingCriteriaList();
                        Comparator sortCriterion = (Comparator) Utils.showAndSelectOne(
                                sortingCriteriaList,
                                "Choose sorting criterion to visualize the imported data:");
                        if (sortCriterion != null)
                        {
                            List<LegacyDataDTO> sortedLegacyDataDTOList = importLegacyDataController.getSortedLegacyDataDTOList(sortCriterion);
                            Utils.showList(sortedLegacyDataDTOList, "List of imported data:");
                        }
                        else
                        {
                            System.out.println("Visualization of imported data cancelled!");
                        }
                    }
                    else
                    {
                        System.out.printf("The data in %s already exists in the system. Exiting...", pathFile);
                    }

                }
                catch (FileNotFoundException | HeaderNotMatchException | InvalidTypeFileException | VaccineDoesNotExistException e)
                {
                    System.out.println(e.getMessage());
                }
                finally
                {
                    askAgain = Utils.confirm("Do you want to load again? (s/n)");
                    if (!askAgain)
                    {
                        exit = true;
                    }
                }
            } while ((askAgain));
        }
    }
}
