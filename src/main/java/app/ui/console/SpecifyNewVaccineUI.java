package app.ui.console;

import app.domain.model.VaccineType;
import app.mappers.dto.VaccineDTO;
import app.ui.console.utils.Utils;

import java.util.List;

import app.controller.SpecifyNewVaccineController;
import jdk.jshell.execution.Util;

import java.util.ArrayList;
import java.util.Set;

/**
 * UI class responsible for the dealing with user's input.
 * ISEP'S Integrative Project 2021-2022, second semester.
 *
 * @author nuno.teixeira <1210860@isep.ipp.pt>
 */
public class SpecifyNewVaccineUI implements Runnable
{

    private SpecifyNewVaccineController controller;

    /**
     * Controller's definition.
     */
    public SpecifyNewVaccineUI()
    {
        controller = new SpecifyNewVaccineController();
    }

    /**
     * Run method for the US-013.
     */
    @Override
    public void run()
    {
        System.out.println("\nNew Vaccine Data:");

        boolean success;

        Set<VaccineType> setOfVaccineTypes = this.controller.getListOfVaccineTypes();
        List<VaccineType> listOfVaccineTypes = new ArrayList<>(setOfVaccineTypes);

        do
        {
            String vaccineName = Utils.readLineFromConsole("Name of Vaccine: ");
            VaccineType vaccineType = (VaccineType) Utils.showAndSelectOne(listOfVaccineTypes, "Select vaccine type: ");
            String vaccineManufacturer = Utils.readLineFromConsole("Manufacturer of Vaccine: ");
            int vaccineNumberOfAgeGroups = Utils.readGreaterThan0IntegerFromConsole("How many Age Groups: ");
            List<Integer> minAge = new ArrayList<>();
            List<Integer> maxAge = new ArrayList<>();
            List<Integer> numberOfDoses = new ArrayList<>();
            List<Double> dosage = new ArrayList<>();
            List<Integer> timeElapsed = new ArrayList<>();

            for (int i = 0; i < vaccineNumberOfAgeGroups; i++)
            {
                int minAgeAux;

                // minimum age value definition
                if (i == 0)
                {
                    minAge.add(Utils.readPositiveIntegerFromConsole("Minimum age of age group " + Math.addExact(i, 1) + "?"));
                } else
                {
                    minAgeAux = Utils.readPositiveIntegerFromConsole("Minimum age of age group " + Math.addExact(i, 1) + "?"
                            + "(>" + maxAge.get(i - 1) + ")");
                    while (minAgeAux <= maxAge.get(i - 1))
                    {
                        minAgeAux = Utils.readPositiveIntegerFromConsole("Minimum age of age group " + Math.addExact(i, 1) + "?"
                                + "(>" + maxAge.get(i - 1) + ")");
                    }
                    minAge.add(minAgeAux);
                }

                // maximum age value definition
                int maxAgeValue = Utils.readPositiveIntegerFromConsole("Maximum age of age group "
                        + Math.addExact(i, 1) + "?" + "(>" + minAge.get(i) + ")");
                while (maxAgeValue < minAge.get(i))
                {
                    System.out.println("The upper limit has to be higher than the lower limit.");
                    maxAgeValue = Utils.readPositiveIntegerFromConsole("Maximum age of age group "
                            + Math.addExact(i, 1) + "?" + "(>" + minAge.get(i) + ")");
                }
                maxAge.add(maxAgeValue);

                // dosage definition
                numberOfDoses.add(Utils.readGreaterThan0IntegerFromConsole("Number of doses of age group " + Math.addExact(i, 1) + "?"));
                for (int j = 0; j < numberOfDoses.get(i); j++)
                {
                    double dosagei;
                    dosagei = Utils.readGreaterThan0DoubleFromConsole("Dosage " + Math.addExact(j, 1) + " (ml)?");
                    dosage.add(dosagei);
                }

                // time elapsed definition
                if (numberOfDoses.get(i) > 1)
                {
                    for (int j = 0; j < numberOfDoses.get(i) - 1; j++)
                    {
                        String messageDaysElapsed = "Time Elapsed (days) between Dosage " + Math.addExact(j, 1)
                                + " and Dosage " + Math.addExact(j, 2) + "?";

                        int daysElapsed = Utils.readGreaterThan0IntegerFromConsole(messageDaysElapsed);

                        while (daysElapsed <= 0)
                        {
                            System.out.println("Number of days has to be greater than zero.");
                            daysElapsed = Utils.readGreaterThan0IntegerFromConsole(messageDaysElapsed);
                        }
                        timeElapsed.add(daysElapsed);
                    }
                }
            }

            VaccineDTO vaccineDTO = new VaccineDTO
                    (
                            vaccineType,
                            vaccineName,
                            vaccineManufacturer,
                            vaccineNumberOfAgeGroups,
                            minAge,
                            maxAge,
                            numberOfDoses,
                            dosage,
                            timeElapsed
                    );

            // sends information to the controller
            success = this.controller.createVaccine(vaccineDTO);

            boolean confirmation;
            confirmation = Utils.confirmEN("Do you confirm this operation? (y/n)");

            if (!success || !confirmation)
            {
                failure();
            } else
            {
                result();
            }
        } while (!success);
        Utils.showList(this.controller.getListOfAllVacinesDefined(),"\nList of vaccines defined:");
    }

    /**
     * Prints a message.
     */
    public static void result()
    {
        System.out.println("\n" + "Thank you! Have a nice day!");
    }

    /**
     * Prints a message.
     */
    public static void failure()
    {
        System.out.println("Please try again.");
    }

}
