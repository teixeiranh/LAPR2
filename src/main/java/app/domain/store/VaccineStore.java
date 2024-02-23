package app.domain.store;

import app.domain.model.Vaccine;
import app.domain.model.VaccineType;
import app.mappers.VaccineMapper;
import app.mappers.VaccineTypeMapper;
import app.mappers.dto.VaccineDTO;
import app.mappers.dto.VaccineTypeDTO;
import app.serialization.SerializableStore;
import app.serialization.SerializationUtil;

import java.util.*;

/**
 * Store class responsible for creating and storing the different vaccines.
 * ISEP'S Integrative Project 2021-2022, second semester.
 *
 * @author nuno.teixeira <1210860@isep.ipp.pt>
 */
public class VaccineStore implements SerializableStore<Vaccine>
{

    public static final String DEFAULT_SERIALIZATION_FILE = "vaccines.dat";

    private String serializationFile;
    private List<Vaccine> vaccineStore;
    private SerializationUtil<Vaccine> serializationUtil;

    public VaccineStore()
    {
        this.serializationFile = DEFAULT_SERIALIZATION_FILE;
        this.serializationUtil = new SerializationUtil<>();
        this.vaccineStore = new ArrayList<>(this.loadData());
    }

    public VaccineStore(String serializationFile)
    {
        this.serializationFile = serializationFile;
        this.serializationUtil = new SerializationUtil<>();
        this.vaccineStore = new ArrayList<>(this.loadData());
    }

    public VaccineStore(String baseSerializationFolder,
                        String serializationFile)
    {
        this.serializationFile = serializationFile;
        this.serializationUtil = new SerializationUtil<>(baseSerializationFolder);
        this.vaccineStore = new ArrayList<>(this.loadData());
    }

    /**
     * Vaccine Store's create vaccine method.
     *
     * @param vaccineType              vaccine type
     * @param vaccineName              vaccine identification name
     * @param vaccineManufacturer      vaccina manufacturer
     * @param vaccineNumberOfAgeGroups quantity of age groups for the vaccine administration process
     * @param minAge                   minimum age of an age group
     * @param maxAge                   maximum age of an age group
     * @param numberOfDoses            number of doses for each age group
     * @param dosage                   dosage per take for each age group
     * @param timeElapsed              time elapsed between vaccine takes
     * @return true if the vaccine creation is successful
     */
    public Vaccine createVaccine(VaccineType vaccineType, String vaccineName, String vaccineManufacturer,
                                 int vaccineNumberOfAgeGroups, List<Integer> minAge, List<Integer> maxAge,
                                 List<Integer> numberOfDoses, List<Double> dosage, List<Integer> timeElapsed)
    {
        return new Vaccine(vaccineType, vaccineName, vaccineManufacturer,
                vaccineNumberOfAgeGroups, minAge, maxAge, numberOfDoses, dosage, timeElapsed);
    }

    public Vaccine createVaccine(VaccineDTO vaccineDTO)
    {
        VaccineMapper vaccineMapper = new VaccineMapper();
        return vaccineMapper.toModel(vaccineDTO);
    }


    /**
     * Verifies if the vaccine is already defined.
     *
     * @param vc Vaccine
     * @return true if the vaccine was not defined and it is stored in the Store's List
     */
    public boolean validateVaccine(Vaccine vc)
    {
        if (vc == null)
        {
            return false;
        }
        return !this.vaccineStore.contains(vc);
    }

    /**
     * Saves the vaccine.
     *
     * @param vc Vaccine
     * @return true if the vaccine is saved
     */
    public boolean saveVaccine(Vaccine vc)
    {
        if (!validateVaccine(vc))
        {
            return false;
        }

        boolean addedSuccessfully = this.vaccineStore.add(vc);

/*        if (addedSuccessfully){
            this.saveData();
        }*/

        return addedSuccessfully;
    }

    public Optional<Vaccine> getVaccineByName(String vaccineName)
    {
        Iterator<Vaccine> iterator = this.vaccineStore.iterator();
        Vaccine vaccine;
        do
        {
            if (!iterator.hasNext())
            {
                return Optional.empty();
            }
            vaccine = iterator.next();
        } while (!vaccine.getVaccineName().equals(vaccineName));

        return Optional.of(vaccine);
    }

    public List<Vaccine> getAll()
    {
        return Collections.unmodifiableList(this.vaccineStore);
    }


    public Boolean checkGloballyIfAgeOfUserIsWithinLimit(int age)
    {
        boolean result = false;
        for (int i = 0; i < vaccineStore.size(); i++)
        {
            result = (vaccineStore.get(i).isWithinAge(age));
            if (result == true) break;
        }
        return result;
    }

    public List<VaccineDTO> getAllVaccinesDTO()
    {
        List<VaccineDTO> vaccines = new ArrayList<>();
        VaccineMapper vaccineMapper = new VaccineMapper();

        for (Vaccine vaccine : this.vaccineStore)
        {
            vaccines.add(vaccineMapper.toDTO(vaccine));
        }
        return vaccines;
    }

    public Boolean checkIfAgeOfUserIsWithinLimitForVaccine(int age, Vaccine previousVaccine) throws NullPointerException
    {
        boolean result;

        result = (previousVaccine.isWithinAge(age)) ? true : false;

        return result;
    }


    /**
     * Method that returns a list of vaccines available for the SNS User's age and scheduled vaccine type vaccination
     *
     * @param age         sns user age
     * @param vaccineType schedule vaccine type administration
     * @return list of vaccines available
     */
    public List<VaccineDTO> getAllAvailableVaccinesByVaccineTypeAndAge(int age, VaccineTypeDTO vaccineType)
    {
        List<VaccineDTO> vaccinesAvailable = new ArrayList<>();
        VaccineMapper vaccineMapper = new VaccineMapper();
        VaccineTypeMapper vaccineTypeMapper = new VaccineTypeMapper();
        VaccineType vaccineType1 = vaccineTypeMapper.toModel(vaccineType);
        String vaccineType1String = vaccineType1.getDescription();
        boolean result = false;

        for (Vaccine vaccine : this.vaccineStore)
        {
            if (vaccine.isWithinAge(age) && vaccine.getVaccineType().getDescription().equals(vaccineType1String))
            {
                vaccinesAvailable.add(vaccineMapper.toDTO(vaccine));
            }

        }
        return vaccinesAvailable;
    }


    /**
     * Method to get the index of the age group where the sns user fits
     *
     * @param age           sns user age
     * @param chosenVaccine vaccine chosen to be administered based on vaccine type scheduled
     * @return index of the age group where sns user is included
     */
    public int getSnsUserVaccineAgeGroup(int age, VaccineDTO chosenVaccine)
    {
        VaccineMapper vaccineMapper = new VaccineMapper();
        Vaccine vaccine = vaccineMapper.toModel(chosenVaccine);
        int numberOfAgeGroups = vaccine.getVaccineNumberOfAgeGroups();
        List<Integer> minAgeList = vaccine.getMinAgeList();
        List<Integer> maxAgeList = vaccine.getMaxAgeList();
        int ageGroup = 50000;
        for (int i = 0; i < numberOfAgeGroups; i++)
        {
            if (age >= minAgeList.get(i) && age <= maxAgeList.get(i))
            {
                ageGroup = i;
            }
        }
        return ageGroup;
    }


    /**
     * Method to get the number of doses to be administered until the user is fully vaccinated
     *
     * @param chosenVaccine vaccine chosen to be administered based on vaccine type scheduled
     * @param age           age sns user
     * @return
     */
    public int getNumberOfDosesForAgeGroup(VaccineDTO chosenVaccine, int age)
    {
        VaccineMapper vaccineMapper = new VaccineMapper();
        Vaccine vaccine = vaccineMapper.toModel(chosenVaccine);

        int ageGroup = getSnsUserVaccineAgeGroup(age, chosenVaccine);
        List<Integer> numberOfDosesList = vaccine.getNumberOfDoses();
        return numberOfDosesList.get(ageGroup);
    }

    /**
     * Method that will return the number of doses to be administered so the sns users for those age groups are fully vaccinated
     *
     * @param chosenVaccine vaccine chosen to be administered based on vaccine type scheduled
     * @param age           age sns user
     * @return
     */
    public int getNumberOfDosesForPreviousAgeGroups(VaccineDTO chosenVaccine, int age)
    {
        VaccineMapper vaccineMapper = new VaccineMapper();
        Vaccine vaccine = vaccineMapper.toModel(chosenVaccine);
        int ageGroup = getSnsUserVaccineAgeGroup(age, chosenVaccine);
        int totalDoses = 0;
        List<Integer> numberOfDosesList = vaccine.getNumberOfDoses();
        if (ageGroup != 0)
        {
            for (int i = 0; i < ageGroup; i++)
            {
                totalDoses += numberOfDosesList.get(i);
            }
        }
        return totalDoses;
    }


    /**
     * Method that will return the number of doses to be administered for the previous and current age group, so the sns users are fully vaccinated
     *
     * @param chosenVaccine vaccine chosen to be administered based on vaccine type scheduled
     * @param age           age sns user
     * @return
     */
    public int getNumberOfDosesForAgeGroupsIncludingUserAgeGroup(VaccineDTO chosenVaccine, int age)
    {
        VaccineMapper vaccineMapper = new VaccineMapper();
        Vaccine vaccine = vaccineMapper.toModel(chosenVaccine);
        int ageGroup = getSnsUserVaccineAgeGroup(age, chosenVaccine);
        int totalDoses = 0;
        List<Integer> numberOfDosesList = vaccine.getNumberOfDoses();

        for (int i = 0; i <= ageGroup; i++)
        {
            totalDoses += numberOfDosesList.get(i);
        }

        return totalDoses;
    }


    /**
     * Method to get a list with dosages per dose number of a specific vaccine for a specific age group
     *
     * @param chosenVaccine vaccine chosen to be administered based on vaccine type scheduled
     * @param age           age sns user
     * @return
     */
    public List<Double> getDosageListForAgeGroup(VaccineDTO chosenVaccine, int age)
    {
        VaccineMapper vaccineMapper = new VaccineMapper();
        Vaccine vaccine = vaccineMapper.toModel(chosenVaccine);
        int ageGroup = getSnsUserVaccineAgeGroup(age, chosenVaccine);
        List<Double> dosageList = vaccine.getDosage();
        List<Double> dosageListForSpecificAgeGroup = new ArrayList<>();
        int minIndex = (getNumberOfDosesForPreviousAgeGroups(chosenVaccine, age) - 1);
        int maxIndex = (getNumberOfDosesForAgeGroupsIncludingUserAgeGroup(chosenVaccine, age) - 1);
        for (int i = (minIndex + 1); i <= maxIndex; i++)
        {
            dosageListForSpecificAgeGroup.add(dosageList.get(i));
        }
        return dosageListForSpecificAgeGroup;
    }


    public double getDosageBasedOnVaccineAndDoseNumber(VaccineDTO chosenVaccine, int age, int doseNumber)
    {
        List<Double> dosageListForSpecificAgeGroup = getDosageListForAgeGroup(chosenVaccine, age);
        return dosageListForSpecificAgeGroup.get((doseNumber));
    }

    @Override
    public Set<Vaccine> dataToSave()
    {
        return new HashSet<>(this.vaccineStore);
    }

    @Override
    public String serializationFileName()
    {
        return this.serializationFile;
    }

    @Override
    public SerializationUtil<Vaccine> serializationUtil()
    {
        return this.serializationUtil;
    }

}


