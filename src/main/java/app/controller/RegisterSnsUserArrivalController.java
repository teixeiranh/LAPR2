package app.controller;

import app.domain.model.Company;
import app.domain.model.VaccinationCenter;
import app.domain.model.VaccinationSchedule;
import app.domain.store.SnsUserArrivalStore;
import app.domain.store.VaccinationCenterStore;
import app.domain.store.VaccinationScheduleStore;
import app.domain.store.exception.SnsUserArrivalForSameDateAlreadyExistsInSoreWithAttributeExcepetion;
import app.mappers.VaccinationScheduleMapper;
import app.mappers.dto.SnsUserArrivalDTO;
import app.mappers.dto.VaccinationScheduleDTO;
import app.mappers.dto.VaccinationCenterDTO;
import pt.isep.lei.esoft.auth.AuthFacade;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

public class RegisterSnsUserArrivalController {

    private final App app;
    private final Company company;
    private final AuthFacade authFacade;
    private final SnsUserArrivalStore snsUserArrivalStore;
    private VaccinationScheduleStore vaccinationScheduleStore;
    private VaccinationCenterStore vaccinationCenterStore;

    public RegisterSnsUserArrivalController()
    {
        this.app = App.getInstance();
        this.company = app.getCompany();
        this.authFacade = this.company.getAuthFacade();
        this.snsUserArrivalStore = this.company.snsUserArrivalStore();
        this.vaccinationScheduleStore = this.company.getVaccinationScheduleStore();
        this.vaccinationCenterStore = this.company.getVaccinationCenterStore();

    }


    /**
     * Method to verify if the record creation of SNS User arrival at vaccination center was successful
     * @param snsUserArrivalDTO Data Transfer Object of snsUserArrival
     * @return boolean
     */
    public boolean registerNewSnsUserArrival(SnsUserArrivalDTO snsUserArrivalDTO)
    {
        this.company.registerNewSnsUserArrival(snsUserArrivalDTO);
        return true;
    }


    /**
     * Method that checks if the user already registered today the arrival to vaccination center to be vaccinated
     * @param snsNumber user sns number that receptionist inputs in the system
     * @param arrivalDate date and time the receptionist registers as the user arrival date/time
     * @return boolean
     */
    public boolean checkIfUserAlreadyRegisteredTodayAnArrival(long snsNumber, LocalDateTime arrivalDate) {
        boolean userScheduleAlreadyExistsToday;
        try{
            userScheduleAlreadyExistsToday = this.snsUserArrivalStore.existsSnsUserArrival(snsNumber, arrivalDate);
        }
        catch (SnsUserArrivalForSameDateAlreadyExistsInSoreWithAttributeExcepetion exception)
        {
            String e = exception.getMessage();
            userScheduleAlreadyExistsToday = true;
        }

        return userScheduleAlreadyExistsToday;
    }


    /**
     * Returns true if the check of vaccination center was successful and user is at the right place
     * @param vaccinationSchedule the vaccine schedule info for the SNS user
     * @param vaccinationCenterName Vaccination center where the receptionist is currently working
     * @return boolean
     */
    public boolean checkIfVaccinationCenterIsTheOneOnTheVaccinationSchedule(VaccinationScheduleDTO vaccinationSchedule, String vaccinationCenterName) {
       return this.snsUserArrivalStore.checkIfVaccinationCenterIsTheOneOnTheVaccinationSchedule(vaccinationSchedule, vaccinationCenterName);
    }


    /**
     * To get all the vaccination centers registered within the system
     * @return
     */
    public List<VaccinationCenterDTO> getAllVaccinationCenters(){

        return this.vaccinationCenterStore.getAllDTO();
    }

    /**
     * Get Vaccination Schedule info for the sns user for the arrival day if it exists
     * @param snsNumber user sns number
     * @param arrivalDate date of the sns user arrival to vaccination center
     * @return
     */
    public VaccinationScheduleDTO getSnsUserVaccinationSchedule(long snsNumber, LocalDateTime arrivalDate)
    {
        VaccinationScheduleMapper vaccinationScheduleMapper = new VaccinationScheduleMapper();
        return vaccinationScheduleMapper.toDTO(this.getVaccinationScheduleInfo(snsNumber, arrivalDate));
    }


    public VaccinationSchedule getVaccinationScheduleInfo(long snsNumber, LocalDateTime arrivalDate)
    {
        Optional<VaccinationSchedule> vaccinationSchedule =this.vaccinationScheduleStore.existsVaccinationScheduleForUserOnArrivalDate(snsNumber, arrivalDate);
        if(vaccinationSchedule.isPresent())
        {
            return vaccinationSchedule.get();
        }

        return null;
    }


    /**
     * Returns true if the validation of working hours of vaccination center vs hours SNS User arrived was successfull
     * @param arrivalTime date/time the user arrived at the vaccination center
     * @param vaccinationCenterOfArrival the vaccination center the user arrived
     * @return boolen
     */
    public boolean checkIfUserArrivedWithinVaccinationCenterWorkingHours(LocalDateTime arrivalTime, String vaccinationCenterOfArrival)
    {
        return vaccinationCenterStore.checkIfUserArrivedWithinVaccinationCenterWorkingHours(arrivalTime, vaccinationCenterOfArrival);
    }


    public List<String> getAllVaccinationCentersNameList()
    {
        List<String> vaccinationCenterNames = new ArrayList<>();

        for (VaccinationCenterDTO vaccinationCenter : this.getAllVaccinationCenters())
        {
            vaccinationCenterNames.add(vaccinationCenter.getVaccinationCenterName());
        }
        return vaccinationCenterNames;
    }

    public SnsUserArrivalStore getSnsUserArrivalStore()
    {
        return snsUserArrivalStore;
    }

    public VaccinationScheduleStore getVaccinationScheduleStore()
    {
        return vaccinationScheduleStore;
    }
}
