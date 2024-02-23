package app.ui.console;

import app.controller.RecordAdverseReactionController;
import app.mappers.dto.AdverseReactionDTO;
import app.mappers.dto.SnsUserDTO;
import app.mappers.dto.VaccineDTO;
import app.ui.console.utils.Utils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

public class RecordAdverseReactionUI implements Runnable {

    private final RecordAdverseReactionController recordAdverseReactionController;

    public RecordAdverseReactionUI()
    {
        this.recordAdverseReactionController = new RecordAdverseReactionController();
    }

    @Override
    public void run()
    {
        System.out.println("\nPlease insert the adverse reaction information");

        boolean endAdverseReactionRecord = false;

        do
        {
            try
            {
                long snsNumber = Utils.readLongFromConsole("SNS number");
                SnsUserDTO snsUser;

                try
                {
                    snsUser = this.recordAdverseReactionController.getSnsUserDTOBySnsNumber(snsNumber);
                } catch (NoSuchElementException e)
                {
                    System.out.printf("User with SNS number %s not found", snsNumber);
                    break;
                }

                List<VaccineDTO> vaccines = this.recordAdverseReactionController.getAllVaccinesDTO();
                VaccineDTO vaccine;
                try
                {
                    vaccine = (VaccineDTO) Utils.showAndSelectOne(vaccines, "Vaccine");
                } catch (NumberFormatException exception)
                {
                    System.out.println("Please insert a valid option!");
                    break;
                }

                String adverseReaction = Utils.readLineFromConsole("Adverse Reaction");

                LocalDateTime date = Utils.readDateTimeFromConsole("AdverseReaction date (yyyy-MM-dd HH:mm)");

                AdverseReactionDTO adverseReactionDTO = new AdverseReactionDTO(
                        snsUser,
                        adverseReaction,
                        date,
                        vaccine
                );

                boolean adverseReactionCanBeRecorded = this.recordAdverseReactionController.createAdverseReaction(adverseReactionDTO);

                if (adverseReactionCanBeRecorded)
                {
                    System.out.println("An adverse reaction cannot be created\n");
                    endAdverseReactionRecord = true;
                } else
                {
                    System.out.println("\n" + adverseReactionDTO);

                    boolean confirmsUserData = Utils.confirm("Confirm adverse reaction (s/n)?");

                    if (confirmsUserData)
                    {

                        boolean adverseReactionRecordedSuccessfully = this.recordAdverseReactionController.addAdverseReaction();

                        if (adverseReactionRecordedSuccessfully)
                        {
                            System.out.println("Adverse Reaction recorded successfully.\n");
                            endAdverseReactionRecord = true;
                        } else
                        {
                            System.out.println("Adverse Reaction record failed.\n");
                            endAdverseReactionRecord = this.reinputUserInformationUI();
                        }
                    }
                }
            } catch (IllegalArgumentException exception)
            {
                System.out.println(exception.getMessage());
                break;
            }
        } while (!endAdverseReactionRecord);
    }

    private boolean reinputUserInformationUI()
    {
        boolean shouldReinput = Utils.confirm("Reinput the adverse reaction (s/n)?");
        return !shouldReinput;
    }

}
