package app.controller;

import app.domain.model.*;
import app.domain.shared.Constants;
import app.domain.shared.NotificationHandler;
import app.domain.store.*;
import app.mappers.SnsUserMapper;
import app.mappers.dto.SnsUserDTO;
import app.mappers.dto.VaccinationCenterDTO;
import app.mappers.dto.VaccinationScheduleDTO;
import app.mappers.dto.VaccineTypeDTO;
import pt.isep.lei.esoft.auth.UserSession;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Controller for the VaccinationSchedule class, to handle and process UI information.
 */
public class VaccinationScheduleController {

    private App app;
    private Company company;
    private SnsUserStore snsUserStore;
    private VaccineStore vaccineStore;
    private VaccineTypeStore vaccineTypeStore;
    private VaccinationCenterStore vaccinationCenterStore;
    private VaccinationScheduleStore vaccinationScheduleStore;
    private VaccinationSchedule vaccinationSchedule;
    private VaccineAdministration vaccineAdministration;

    public VaccinationScheduleController()
    {
        this.app = App.getInstance();
        this.company = app.getCompany();
        this.snsUserStore = app.getCompany().getSnsUserStore();
        this.vaccineStore = app.getCompany().getVaccineStore();
        this.vaccineTypeStore = this.company.vaccineTypeStore();
        this.vaccinationCenterStore = this.company.getVaccinationCenterStore();
        this.vaccinationScheduleStore = this.company.getVaccinationScheduleStore();
    }

    /**
     * Returns all Vaccine Types in VaccineTypeStore.
     *
     * @return
     */
    public List<VaccineTypeDTO> getAllVaccineTypes()
    {
        return vaccineTypeStore.getAllVaccineTypesDTO();
    }

    /**
     * Returns all Vaccine Centers by Vaccine Type.
     *
     * @param vaccineType vaccine type pretended
     * @return
     */
    public List<VaccinationCenterDTO> getAllVaccinationCentersByVaccineType(VaccineTypeDTO vaccineType)
    {
        return this.vaccinationCenterStore.getAllDTOByVaccineType(vaccineType);
    }


    /**
     * Returns true if the creation of vaccine is sucessful.
     *
     * @param vaccinationScheduleDTO
     * @return
     */
    public Boolean createVaccinationSchedule(VaccinationScheduleDTO vaccinationScheduleDTO)
    {
        this.company.getSnsUserArrivalStore();

        this.vaccinationSchedule = this.vaccinationScheduleStore.createVaccinationSchedule(vaccinationScheduleDTO);

        Set<VaccineAdministration> vaccineAdministrationStore = this.company.getVaccineAdministrationStore().getVaccineAdministrationStore();
        Iterator<VaccineAdministration> iterator = vaccineAdministrationStore.iterator();
        VaccineAdministration vaccineAdministration;

        while (iterator.hasNext())
        {
            vaccineAdministration = iterator.next();
            if (vaccineAdministration.getSnsUserArrival().getSnsUserNumber().getNumber() == vaccinationScheduleDTO.getSnsUser().getSnsNumber()
                    && vaccineAdministration.getVaccineAdministrationState().equals(VaccineAdministrationState.FULLYVACCINATED))
            {
                return false;
            }
        }

        return this.checkIfScheduleAlreadyExists(vaccinationScheduleDTO)
                && this.vaccinationCenterStore.checkIfScheduleDateIsBetweenVaccinationCenterOpeningHours(vaccinationScheduleDTO.getDate(), vaccinationScheduleDTO.getVaccinationCenter().getVaccinationCenterEmailAddress());

    }

    /**
     * Returns true if the vaccine has all the properties correct.
     *
     * @return
     */
    public Boolean addVaccinationSchedule()
    {
        int userAge = this.vaccinationSchedule.getSnsUser().getAge();
        Long snsUserNumber = this.vaccinationSchedule.getSnsUser().getSnsNumber().getNumber();
        String pretendedVaccineType = this.vaccinationSchedule.getVaccineType().getDescription();
        int numberOfPreviousVaccinations = this.vaccinationScheduleStore.getNumberOfPreviousVaccinations(snsUserNumber, pretendedVaccineType);
        LocalDateTime newVaccinationDate = this.vaccinationSchedule.getDate();
        boolean timeWithinLimit = true;
        boolean ageWithinLimit;

        if (numberOfPreviousVaccinations > 0)
        {
            Vaccine previousVaccine = this.vaccinationScheduleStore.getLastAdministeredVaccinePerSnsUser(snsUserNumber);
            timeWithinLimit = vaccinationScheduleStore.checkIfTimeSinceLastVaccinationIsRight(newVaccinationDate,
                    numberOfPreviousVaccinations, snsUserNumber, previousVaccine);
            ageWithinLimit = vaccineStore.checkIfAgeOfUserIsWithinLimitForVaccine(userAge, previousVaccine);

        } else
        {
            ageWithinLimit = vaccineStore.checkGloballyIfAgeOfUserIsWithinLimit(userAge);
        }


        boolean addedVaccinationScheduleSucessfully;

        if (!ageWithinLimit || !timeWithinLimit)
        {
            addedVaccinationScheduleSucessfully = false;
        }else
        {
            addedVaccinationScheduleSucessfully = this.vaccinationScheduleStore.addVaccinationSchedule(this.vaccinationSchedule);
        }

        if (addedVaccinationScheduleSucessfully)
        {
            String smsMessageContent = "Your vaccination is scheduled for: " + vaccinationSchedule.getDate() + ", at the vaccination center: " + vaccinationSchedule.getVaccinationCenter().getVaccinationCenterName() + "\n";

            if (getSnsUserInSession() == null)
            {
                NotificationHandler.sendSMS(vaccinationSchedule.getSnsUser().getSnsNumber().getNumber(), smsMessageContent);
            } else
            {
                NotificationHandler.sendSMS(getSnsUserInSession().getPhoneNumber().getNumber(), smsMessageContent);
            }
        }

        return addedVaccinationScheduleSucessfully;
    }

    //TODO: check if this method should be here
    public SnsUserDTO getSnsUserDTOInSession()
    {
        SnsUserMapper snsUserMapper = new SnsUserMapper();
        return snsUserMapper.toDTO(getSnsUserInSession()); //TODO: check null
    }

    public SnsUserDTO getSnsUserDTOBySnsNumber(Long snsNumber)
    {
        SnsUserMapper snsUserMapper = new SnsUserMapper();
        return snsUserMapper.toDTO(this.getSnsUserInfo(snsNumber).get()); //TODO: check null
    }

    public List<VaccinationScheduleDTO> getVaccinationSchedulesOfUserInSession()
    {
        return this.vaccinationScheduleStore.getAllVaccinationSchedules(this.getSnsUserDTOInSession().getSnsNumber());
    }

    public List<VaccinationSchedule> getAllExistingVaccinationSchedule()
    {
        return this.vaccinationScheduleStore.getAllExistingVaccinationSchedule();
    }


    private SnsUser getSnsUserInSession()
    {
        UserSession userSession = app.getCurrentUserSession();
        if (userSession.isLoggedInWithRole(Constants.ROLE_SNS_USER))
        {
            Optional<SnsUser> snsUser = this.snsUserStore.getSnsUserById(userSession.getUserId().getEmail());
            if (snsUser.isPresent())
            {
                return snsUser.get();
            }
        }

        return null;
    }

    private Boolean checkIfScheduleAlreadyExists(VaccinationScheduleDTO vaccinationScheduleDTO)
    {
        Optional<VaccinationSchedule> vaccinationScheduleOptional = this.vaccinationScheduleStore.existsVaccinationSchedule(vaccinationScheduleDTO);
        return vaccinationScheduleOptional.isPresent();
    }

    public Optional<SnsUser> getSnsUserInfo(Long snsNumber)
    {
        return snsUserStore.getSnsUserBySnsNumber(snsNumber);
    }

    /**
     * Returns true if the age of the user is within limit for the vaccine.
     *
     * @param vaccinationScheduleDTO
     * @return
     */
    public Boolean checkIfAgeOfUserIsWithinLimit(VaccinationScheduleDTO vaccinationScheduleDTO)
    {
        Long snsNumber = vaccinationScheduleDTO.getSnsUser().getSnsNumber();
        Optional<SnsUser> snsUser = snsUserStore.getSnsUserBySnsNumber(snsNumber);
        int age = snsUser.get().getAge();

        return vaccineStore.checkGloballyIfAgeOfUserIsWithinLimit(age);
    }


}
