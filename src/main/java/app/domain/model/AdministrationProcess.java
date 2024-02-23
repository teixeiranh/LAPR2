package app.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing an administration process.
 * This class is used by Vaccine (composition) to help store all the different
 * administration processes associated with the vaccine.
 * ISEP'S Integrative Project 2021-2022, second semester.
 * @author nuno.teixeira <1210860@isep.ipp.pt>
 */
public class AdministrationProcess implements Serializable
{

    private static final long serialVersionUID = 1940861604667894908L;

    /**
     * Administration process identification
     */
    private int administrationProcessId;
    /**
     * Number of age groups for the adminstration process
     */
    private int vaccineNumberOfAgeGroups;
    /**
     * List of minimum ages for the adminstration process
     */
    private List<Integer> minAge;
    /**
     * List of maximum ages for the adminstration process
     */
    private List<Integer> maxAge;
    /**
     * Number of doses per age group
     * The list has 1 element per age group defined in the administration process.
     */
    private List<Integer> numberOfDoses;
    /**
     * Dosage per dose
     * The list has same number of elements has the list for the number of doses.
     */
    private List<Double> dosage;
    /**
     * Time elapsed between doses
     */
    private List<Integer> timeElapsed;

    private static int numberOfAdministrationProcesses;

    private static final int NUMBER_OF_AGE_GROUPS_DEFAULT_VALUE = 1;

    /**
     * Constructor for the administration process.
     * @param vaccineNumberOfAgeGroups number of age groups for the administration process
     * @param minAge minimum age for the age groups
     * @param maxAge maximum age for the age groups
     * @param numberOfDoses number of doses per age group
     * @param dosage dosage per take
     * @param timeElapsed time elpased between dosages
     */
    public AdministrationProcess(int vaccineNumberOfAgeGroups, List<Integer> minAge,
                                 List<Integer> maxAge,List<Integer> numberOfDoses,
                                 List<Double> dosage,List<Integer> timeElapsed)
    {
        numberOfAdministrationProcesses++;
        this.administrationProcessId = numberOfAdministrationProcesses;
        this.vaccineNumberOfAgeGroups = vaccineNumberOfAgeGroups;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.numberOfDoses = numberOfDoses;
        this.dosage = dosage;
        this.timeElapsed = timeElapsed;
    }

    /**
     * Default constructor for the administration process.
     */
    public AdministrationProcess()
    {
        numberOfAdministrationProcesses++;
        this.administrationProcessId = numberOfAdministrationProcesses;
        this.vaccineNumberOfAgeGroups = NUMBER_OF_AGE_GROUPS_DEFAULT_VALUE;
        this.minAge = new ArrayList<Integer>();
        this.maxAge = new ArrayList<Integer>();
        this.numberOfDoses = new ArrayList<Integer>();
        this.dosage = new ArrayList<Double>();
        this.timeElapsed = new ArrayList<Integer>();
    }

    /**
     * Get administration process number of age groups.
     * @return
     */
   public int getVaccineNumberOfAgeGroups()
    {
        return vaccineNumberOfAgeGroups;
    }

    /**
     * Get administration process minimum age.
     * @return
     */
   public List<Integer> getMinAge()
    {
        return minAge;
    }

    /**
     * Get vaccine's maximum age.
     * @return
     */
   public List<Integer> getMaxAge()
    {
        return maxAge;
    }

    /**
     * Get administration process number of doses
     * @return
     */
   public List<Integer> getNumberOfDoses()
    {
        return numberOfDoses;
    }

    /**
     * Get administration process dosage per dose
     * @return
     */
   public List<Double> getDosage()
    {
        return dosage;
    }

    /**
     * Get time elapsed between doses
     * @return
     */
   public List<Integer> getTimeElapsed()
    {
        return timeElapsed;
    }

   @Override
    public String toString()
    {
        return String.format("Administration process number %d with %d age group(s).",
                administrationProcessId,vaccineNumberOfAgeGroups);
    }

}
