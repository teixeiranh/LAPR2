package app.domain.store;

import app.domain.model.VaccinationCenter;
import app.domain.model.VaccinationSchedule;
import app.domain.model.VaccinationScheduleState;
import app.domain.model.Vaccine;
import app.domain.utils.DateUtils;
import app.mappers.VaccinationScheduleMapper;
import app.mappers.dto.VaccinationScheduleDTO;
import app.serialization.SerializableStore;
import app.serialization.SerializationUtil;

import java.time.LocalDateTime;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Store for the VaccinationSchedule class instances.
 */
public class VaccinationScheduleStore implements SerializableStore<VaccinationSchedule> {

    public static final String DEFAULT_SERIALIZATION_FILE = "vaccination_schedules.dat";

    private String serializationFile;
    private Set<VaccinationSchedule> vaccinationScheduleStore;
    private VaccinationScheduleMapper vaccinationScheduleMapper;
    private SerializationUtil<VaccinationSchedule> serializationUtil;

    public VaccinationScheduleStore()
    {
        this.serializationFile = DEFAULT_SERIALIZATION_FILE;
        this.serializationUtil = new SerializationUtil<>();
        this.vaccinationScheduleStore = this.loadData();
        this.vaccinationScheduleMapper = new VaccinationScheduleMapper();
    }

    public VaccinationScheduleStore(String serializationFile)
    {
        this.serializationFile = serializationFile;
        this.serializationUtil = new SerializationUtil<>();
        this.vaccinationScheduleStore = this.loadData();
        this.vaccinationScheduleMapper = new VaccinationScheduleMapper();
    }

    public VaccinationScheduleStore(String baseSerializationFolder,
                                    String serializationFile)
    {
        this.serializationFile = serializationFile;
        this.serializationUtil = new SerializationUtil<>(baseSerializationFolder);
        this.vaccinationScheduleStore = this.loadData();
        this.vaccinationScheduleMapper = new VaccinationScheduleMapper();
    }

    /**
     * Returns a list with previous vaccine schedules for a user.
     *
     * @param vaccinationScheduleDTO data transfer object
     * @return
     */
    public Optional<VaccinationSchedule> existsVaccinationSchedule(VaccinationScheduleDTO vaccinationScheduleDTO)
    {
        return this.existsVaccinationSchedule(
                vaccinationScheduleDTO.getSnsUser().getSnsNumber(),
                vaccinationScheduleDTO.getVaccineType().getCode());
    }

    /**
     * Creates a vaccinationSchedule instance.
     *
     * @param vaccinationScheduleDTO
     * @return
     */
    public VaccinationSchedule createVaccinationSchedule(VaccinationScheduleDTO vaccinationScheduleDTO)
    {
        return this.vaccinationScheduleMapper.toModel(vaccinationScheduleDTO);
    }

    public boolean addVaccinationSchedule(VaccinationSchedule vaccinationSchedule)
    {
        boolean addedSuccessfully = vaccinationSchedule != null
                && !this.existsVaccinationSchedule(vaccinationSchedule.getSnsUser().getSnsNumber().getNumber(), vaccinationSchedule.getVaccineType().getCode()).isPresent()
                && this.vaccinationScheduleStore.add(vaccinationSchedule);

/*       if (addedSuccessfully)
        {
            this.saveData();
        }*/

        return addedSuccessfully;
    }

    /**
     * Returns a list with previous vaccine schedules for a user.
     *
     * @param snsNumber       sns user number
     * @param vaccineTypeCode type code for the vaccine
     * @return
     */
    private Optional<VaccinationSchedule> existsVaccinationSchedule(long snsNumber,
                                                                    String vaccineTypeCode)
    {
        Iterator<VaccinationSchedule> iterator = this.vaccinationScheduleStore.iterator();

        VaccinationSchedule vaccinationSchedule;
        do
        {
            if (!iterator.hasNext())
            {
                return Optional.empty();
            }

            vaccinationSchedule = iterator.next();
        } while ((vaccinationSchedule.getSnsUser().getSnsNumber().getNumber() != snsNumber)
                || (!vaccinationSchedule.getVaccineType().getCode().equals(vaccineTypeCode))
                || vaccinationSchedule.getState().equals(VaccinationScheduleState.FINALIZED));

        return Optional.of(vaccinationSchedule);
    }

    public Optional<VaccinationSchedule> existsVaccinationScheduleForUserOnArrivalDate(long snsNumber,
                                                                                       LocalDateTime arrivalDate)
    {
        Iterator<VaccinationSchedule> iterator = this.vaccinationScheduleStore.iterator();

        VaccinationSchedule vaccinationSchedule;
        boolean findOne = false;
        do
        {
            if (!iterator.hasNext() && !findOne)
            {
                return Optional.empty();
            }
            findOne = true;
            vaccinationSchedule = iterator.next();
        } while ((vaccinationSchedule.getSnsUser().getSnsNumber().getNumber() != snsNumber)
                && (vaccinationSchedule.getDate().getDayOfYear() != arrivalDate.getDayOfYear())
                && vaccinationSchedule.getState() == VaccinationScheduleState.CREATED
                && !findOne);

        return Optional.of(vaccinationSchedule);
    }

    /**
     * Get a list of all vaccination schedule of the user in session.
     *
     * @param snsNumberOfUserInSession user logged in
     * @return list of all vaccination schedule
     */

    public List<VaccinationScheduleDTO> getAllVaccinationSchedules(Long snsNumberOfUserInSession)
    {
        List<VaccinationScheduleDTO> vaccinationSchedules = new ArrayList<>();

        for (VaccinationSchedule vaccinationSchedule : this.vaccinationScheduleStore)
        {
            if (vaccinationSchedule.getSnsUser().getSnsNumber().getNumber() == snsNumberOfUserInSession)
            {
                vaccinationSchedules.add(this.vaccinationScheduleMapper.toDTO(vaccinationSchedule));
            }
        }

        return vaccinationSchedules;
    }

    public List<VaccinationSchedule> getAllExistingVaccinationSchedule()
    {
        List<VaccinationSchedule> existingVaccinationSchedules = new ArrayList<>();
        for (VaccinationSchedule vaccinationSchedule : this.vaccinationScheduleStore)
        {
            existingVaccinationSchedules.add(vaccinationSchedule);
        }
        return existingVaccinationSchedules;
    }

    /**
     * Determine the number of previous vaccination appointments.
     *
     * @param SnsUser sns user number
     * @return
     */
    public int getNumberOfPreviousVaccinations(Long SnsUser,
                                               String pretendedVaccineType)
    {
        List<VaccinationSchedule> listOfVaccinationSchedule = new ArrayList<>(this.vaccinationScheduleStore);

        int counterOfPreviousVaccinations = 0;
        for (int i = 0; i < listOfVaccinationSchedule.size(); i++)
        {
            if (listOfVaccinationSchedule.get(i).getSnsUser().getSnsNumber().getNumber() == SnsUser &&
                    listOfVaccinationSchedule.get(i).getVaccineType().getDescription().equals(pretendedVaccineType))
            {
                counterOfPreviousVaccinations++;
            }
        }

        return counterOfPreviousVaccinations;
    }

    /**
     * Determine the last vaccination date for a SNS User
     *
     * @param snsUser sns user number
     * @return
     */
    public LocalDateTime getLastVaccinationDatePerSnsUser(Long snsUser)
    {
        List<VaccinationSchedule> listOfVaccinationSchedule = new ArrayList<>(this.vaccinationScheduleStore);
        List<VaccinationSchedule> listOfVaccinationSchedulePerSnsUser = new ArrayList<>();

        for (int i = 0; i < listOfVaccinationSchedule.size(); i++)
        {
            if (listOfVaccinationSchedule.get(i).getSnsUser().getSnsNumber().getNumber() == snsUser)
            {
                listOfVaccinationSchedulePerSnsUser.add(listOfVaccinationSchedule.get(i));
            }
        }

        LocalDateTime olderVaccineDate = listOfVaccinationSchedulePerSnsUser.get(0).getDate();
        for (int i = 1; i < listOfVaccinationSchedulePerSnsUser.size(); i++)
        {
            if (listOfVaccinationSchedulePerSnsUser.get(i).getDate().isAfter(olderVaccineDate))
            {
                olderVaccineDate = listOfVaccinationSchedulePerSnsUser.get(i).getDate();
            }

        }

        return olderVaccineDate;
    }

    /**
     * Determine the last adminsitred vaccine for a SNS User.
     *
     * @param snsUser sns user number
     * @return
     */
    public Vaccine getLastAdministeredVaccinePerSnsUser(Long snsUser)
    {
        List<VaccinationSchedule> listOfVaccinationSchedule = new ArrayList<>(this.vaccinationScheduleStore);
        List<VaccinationSchedule> listOfVaccinationSchedulePerSnsUser = new ArrayList<>();

        for (int i = 0; i < listOfVaccinationSchedule.size(); i++)
        {
            if (listOfVaccinationSchedule.get(i).getSnsUser().getSnsNumber().getNumber() == snsUser)
            {
                listOfVaccinationSchedulePerSnsUser.add(listOfVaccinationSchedule.get(i));
            }
        }

        LocalDateTime olderVaccineDate = listOfVaccinationSchedulePerSnsUser.get(0).getDate();
        Vaccine olderVaccine = listOfVaccinationSchedulePerSnsUser.get(0).getVaccine();
        for (int i = 1; i < listOfVaccinationSchedulePerSnsUser.size(); i++)
        {
            if (listOfVaccinationSchedulePerSnsUser.get(i).getDate().isAfter(olderVaccineDate))
            {
                olderVaccineDate = listOfVaccinationSchedulePerSnsUser.get(i).getDate();
                olderVaccine = listOfVaccinationSchedulePerSnsUser.get(i).getVaccine();
            }
        }

        return olderVaccine;
    }

    /**
     * Check if the time passed since last vaccination and the new one is properly
     * defined.
     *
     * @param nextVaccinationDate next vaccination date
     * @param numberOfDoses       number of doses already taken
     * @param SnsUser             SNS User number
     * @param vaccine             vaccine to be taken
     * @return
     */
    public Boolean checkIfTimeSinceLastVaccinationIsRight(LocalDateTime nextVaccinationDate,
                                                          int numberOfDoses,
                                                          Long SnsUser,
                                                          Vaccine vaccine)
    {
        boolean result = false;

        if (vaccine == null)
        {
            return true;
        }

        List<Integer> timeElapsedList = vaccine.getAdministrationProcess().getTimeElapsed();

        if (timeElapsedList.size() < numberOfDoses)
        {
            return result;
        }
        LocalDateTime lastVaccineationDate = getLastVaccinationDatePerSnsUser(SnsUser);

        int timeElapsedForDose = timeElapsedList.get(numberOfDoses - 1);

        long daysBetween = DAYS.between(lastVaccineationDate, nextVaccinationDate);

        return daysBetween >= timeElapsedForDose;

    }

    public int getNumberOfVaccinations(VaccinationCenter vc)
    {
        int counter = 0;

        for (VaccinationSchedule vs : this.vaccinationScheduleStore)
        {
            VaccinationScheduleState state = vs.getState();
            String vaccinationCenterName = vs.getVaccinationCenter().getVaccinationCenterName();
            LocalDateTime vaccinationScheduleDate = vs.getDate();

            if ((state == VaccinationScheduleState.FINALIZED)
                    && (vaccinationCenterName.equalsIgnoreCase(vc.getVaccinationCenterName()))
                    && (DateUtils.isToday(vaccinationScheduleDate)))
            {
                counter++;
            }
        }

        return counter;
    }

    public Optional<VaccinationSchedule> getVaccinationScheduleById(String appointment1)
    {
        Iterator<VaccinationSchedule> iterator = this.vaccinationScheduleStore.iterator();
        VaccinationSchedule vaccinationSchedule;
        do
        {
            if (!iterator.hasNext())
            {
                return Optional.empty();
            }
            vaccinationSchedule = iterator.next();
        } while (!vaccinationSchedule.getId().equals(appointment1));
        return Optional.of(vaccinationSchedule);

    }

    @Override
    public Set<VaccinationSchedule> dataToSave()
    {
        return this.vaccinationScheduleStore;
    }

    @Override
    public String serializationFileName()
    {
        return this.serializationFile;
    }

    @Override
    public SerializationUtil<VaccinationSchedule> serializationUtil() {
        return this.serializationUtil;
    }

    public Set<VaccinationSchedule> getVaccinationScheduleStore()
    {
        return vaccinationScheduleStore;
    }
}


