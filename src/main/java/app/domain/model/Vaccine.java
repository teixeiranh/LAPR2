package app.domain.model;

import java.io.Serializable;
import java.util.List;

/**
 * Class representing a vaccine.
 * It uses the class AdministrationProcess (see package) to store the different
 * administration processes defined by the user.
 * ISEP'S Integrative Project 2021-2022, second semester.
 *
 * @author nuno.teixeira <1210860@isep.ipp.pt>
 */
public class Vaccine implements Serializable
{

    private static final long serialVersionUID = -8662856361686542387L;

    /**
     * Vaccine's identification
     */
    private int vaccineId;
    /**
     * Vaccine's name
     */
    private String vaccineName;
    /**
     * Vaccine's type
     */
    private VaccineType vaccineType;
    /**
     * Vaccine's manufacturer company
     */
    private String vaccineManufacturer;
    /**
     * Vaccine's adminstration process
     * Please the class Adminstration Process
     */
    private AdministrationProcess administrationProcess;

    private static int numberOfVaccines;

    private static final String VACCINE_NAME_DEFAULT_VALUE = "-no name-";
    private static final String MANUFACTURER_DEFAULT_VALUE = "-no name-";
//    private static final String TYPE_DEFAULT_VALUE = "-no type-";

    /**
     * Construtor for the vaccine.
     *
     * @param vaccineType              vaccine type
     * @param vaccineName              vaccine name
     * @param vaccineManufacturer      vaccine manufacturer
     * @param vaccineNumberOfAgeGroups number of age groups for the administration process
     * @param minAge                   minimum age for the age groups
     * @param maxAge                   maximum age for the age groups
     * @param numberOfDoses            number of doses per age group
     * @param dosage                   dosage per take
     * @param timeElapsed              time elpased between dosages
     */
    public Vaccine(VaccineType vaccineType, String vaccineName, String vaccineManufacturer,
                   int vaccineNumberOfAgeGroups, List<Integer> minAge, List<Integer> maxAge,
                   List<Integer> numberOfDoses, List<Double> dosage, List<Integer> timeElapsed)
    {
        numberOfVaccines++;
        this.vaccineId = numberOfVaccines;
        this.vaccineName = vaccineName;
        this.vaccineManufacturer = vaccineManufacturer;
        this.vaccineType = vaccineType;
        this.administrationProcess = new AdministrationProcess(vaccineNumberOfAgeGroups, minAge,
                maxAge, numberOfDoses, dosage, timeElapsed);
    }

    /**
     * Default construtor for the vaccine.
     */
    public Vaccine()
    {
        numberOfVaccines++;
        this.vaccineId = numberOfVaccines;
        this.vaccineName = VACCINE_NAME_DEFAULT_VALUE;
        this.vaccineManufacturer = MANUFACTURER_DEFAULT_VALUE;
        this.administrationProcess = new AdministrationProcess();
    }

    /**
     * Construtor for the vaccine.
     *
     * @param vaccineType         vaccine type
     * @param vaccineName         vaccine name
     * @param vaccineManufacturer vaccine manufacturer
     */
    public Vaccine(VaccineType vaccineType, String vaccineName, String vaccineManufacturer)
    {
        numberOfVaccines++;
        this.vaccineId = numberOfVaccines;
        this.vaccineName = vaccineName;
        this.vaccineManufacturer = vaccineManufacturer;
        this.vaccineType = vaccineType;
        this.administrationProcess = new AdministrationProcess();
    }

    /**
     * Get Vaccine id.
     * @return
     */
    public int getVaccineId()
    {
        return vaccineId;
    }

    /**
     * Get Vaccine name.
     * @return
     */
    public String getVaccineName()
    {
        return vaccineName;
    }

    /**
     * Get Vaccine type.
     * @return
     */
    public VaccineType getVaccineType()
    {
        return vaccineType;
    }

    /**
     * Get Vaccine manufacturer.
     * @return
     */
    public String getVaccineManufacturer()
    {
        return vaccineManufacturer;
    }

    /**
     * Get Vaccine administration process.
     * @return
     */
    public AdministrationProcess getAdministrationProcess()
    {
        return administrationProcess;
    }

    /**
     * Get Vaccine number of age groups.
     * @return
     */
    public int getVaccineNumberOfAgeGroups()
    {
        return this.administrationProcess.getVaccineNumberOfAgeGroups();
    }

    /**
     * Get Vaccine's minimum age list for all age groups.
     * @return
     */
    public List<Integer> getMinAgeList()
    {
        return this.administrationProcess.getMinAge();
    }

    /**
     * Get Vaccine's maximum age list for all age groups.
     * @return
     */
    public List<Integer> getMaxAgeList()
    {
        return this.administrationProcess.getMaxAge();
    }

    /**
     * Get Vaccine's number of doses for all age groups.
     * @return
     */
    public List<Integer> getNumberOfDoses()
    {
        return this.administrationProcess.getNumberOfDoses();
    }

    /**
     * Get Vaccine's dosage per dose.
     * @return
     */
    public List<Double> getDosage()
    {
        return this.administrationProcess.getDosage();
    }

    /**
     * Get Vaccine's time elapsed between doses.
     * @return
     */
    public List<Integer> getTimeElapsed()
    {
        return this.administrationProcess.getTimeElapsed();
    }

    @Override
    public String toString()
    {
        return String.format("Vaccine %s, of type %s, manufactured by %s, with " +
                        "%d age group(s).", vaccineName, vaccineType, vaccineManufacturer,
                administrationProcess.getVaccineNumberOfAgeGroups());
    }

//    public boolean hasProperAge(int age)
//    {
//        int numberOfAgeGroups = this.administrationProcess.getVaccineNumberOfAgeGroups();
//        int minimumAge = this.administrationProcess.getMinAge().get(0);
//        int maximumAge = this.administrationProcess.getMaxAge().get(numberOfAgeGroups - 1);
//
//        return age >= minimumAge && age <= maximumAge ? true : false;
//    }

//    public int returnAgeGroupPerAge(int age)
//    {
//        int numberOfAgeGroups = this.administrationProcess.getVaccineNumberOfAgeGroups();
//        List<Integer> minimumAge = this.administrationProcess.getMinAge();
//        List<Integer> maximumAge = this.administrationProcess.getMaxAge();
//
//        int ageGroupNumber = 0;
//
//        for (int i = 0; i < numberOfAgeGroups - 1; i++)
//        {
//            ageGroupNumber = hasProperAge(age) && age >= minimumAge.get(i) && age <= maximumAge.get(i) ? i + 1 : -1;
//        }
//
//        return ageGroupNumber;
//    }

    /**
     * Checks for this vaccine if the age is between the age groups defined.
     * @param age
     * @return
     */
    public boolean isWithinAge(int age)
    {
        boolean result = false;
        List<Integer> minAgeList = this.getMinAgeList();
        List<Integer> maxAgeList = this.getMaxAgeList();

        for (int i = 0; i < minAgeList.size(); i++)
        {
            result = ((age > minAgeList.get(i) && age < maxAgeList.get(i)) ? true : false);
        }
        return result;
    }





//    public boolean isWithinMinDaysForNextDose(LocalDateTime lastDoseDate, int numberOfDosesTaken)
//    {
//        boolean result = false;
//        List<Integer> listOfTimeElapsed = this.administrationProcess.getTimeElapsed();
//        LocalDateTime today = LocalDateTime.now();
//        long numberOfDaysBetweenTodayAndLastDose = ChronoUnit.DAYS.between(lastDoseDate, today);
//
//        if (numberOfDosesTaken == 0)
//        {
//            result = true;
//        } else
//        {
//            result = (numberOfDaysBetweenTodayAndLastDose >= listOfTimeElapsed.get(numberOfDosesTaken - 1) ? true : false);
//        }
//
//        return result;
//    }

}
