package app.controller;

import app.domain.model.Company;
import app.domain.model.SnsUserArrival;
import app.domain.model.VaccineAdministration;
import app.domain.shared.NotificationHandler;
import app.domain.store.AdverseReactionStore;
import app.domain.store.SnsUserArrivalStore;
import app.domain.store.VaccineAdministrationStore;
import app.domain.store.VaccineStore;
import app.mappers.SnsUserArrivalMapper;
import app.mappers.VaccineAdministrationMapper;
import app.mappers.dto.*;

import java.util.List;
import java.util.Optional;

/**
 * Class responsible for managing the vaccine administration process flow.
 */
public class VaccineAdministrationController {

    private App app;
    private Company company;
    private VaccineAdministrationStore vaccineAdministrationStore;
    private SnsUserArrivalStore snsUserArrivalStore;
    private AdverseReactionStore adverseReactionStore;
    private VaccineAdministration vaccineAdministration;
    private VaccineStore vaccineStore;


    public VaccineAdministrationController() {
        this.app = App.getInstance();
        this.company = app.getCompany();
        this.vaccineAdministrationStore = this.company.getVaccineAdministrationStore();
        this.snsUserArrivalStore = this.company.getSnsUserArrivalStore();
        this.adverseReactionStore = this.company.getAdverseReactionStore();
        this.vaccineStore = this.company.getVaccineStore();
    }


    public VaccineAdministrationStore getVaccineAdministrationStore()
    {
        return vaccineAdministrationStore;
    }

    /**
     *
     * Method to verify if the record creation of vaccine administration was successful
     * @param vaccineAdministrationDTO Data Transfer Object of vaccine administration
     * @return true if registered successful or false if not
     */
    public boolean registerVaccineAdministration(VaccineAdministrationDTO vaccineAdministrationDTO) {
        return this.company.registerVaccineAdministration(vaccineAdministrationDTO);

    }


    public List<SnsUserArrivalDTO> getSnsUsersOnWaitingRoom(String vaccinationCenterEmail) {
        List<SnsUserArrival> snsUserArrivalList = snsUserArrivalStore.getSnsUserArrivalsByVaccinationCenter(vaccinationCenterEmail);
        SnsUserArrivalMapper snsUserArrivalMapper = new SnsUserArrivalMapper();
        return snsUserArrivalMapper.toDTO(snsUserArrivalList);
    }


//    public List<SnsUserArrivalDTO> getSnsUsersOnWaitingRoomByName(String vaccinationCenterName) {
//        List<SnsUserArrival> snsUserArrivalList = snsUserArrivalStore.getSnsUserArrivalsByVaccinationCenterName(vaccinationCenterName);
//        SnsUserArrivalMapper snsUserArrivalMapper = new SnsUserArrivalMapper();
//        return snsUserArrivalMapper.toDTO(snsUserArrivalList);
//    }


    public Optional<SnsUserAdverseReactionsDTO> getAllAdverseReactionBySnsNumberAndVaccineType(Long snsNumber, String vaccineTypeCode) {
        return adverseReactionStore.getAllAdverseReactionBySnsNumberAndVaccineType(snsNumber, vaccineTypeCode);
    }


    public List<VaccineDTO> getAllAvailableVaccinesByVaccineTypeAndAge(int age, VaccineTypeDTO vaccineType) {
        return vaccineStore.getAllAvailableVaccinesByVaccineTypeAndAge(age, vaccineType);
    }

    public int getSnsUserVaccineAgeGroup(int age, VaccineDTO chosenVaccine) {
        return vaccineStore.getSnsUserVaccineAgeGroup(age, chosenVaccine);
    }

    public int getNumberOfDosesForAgeGroup(VaccineDTO chosenVaccine, int age) {
        return vaccineStore.getNumberOfDosesForAgeGroup(chosenVaccine, age);
    }


    public int getNumberOfDosesTakenBySnsUser(VaccineTypeDTO vaccineTypeDTO, long snsNumber) {
        return vaccineAdministrationStore.getNumberOfDosesTakenBySnsUser(vaccineTypeDTO, snsNumber);
    }


    public double getDosageBasedOnVaccineAndDoseNumber(VaccineDTO chosenVaccine, int age, int dosage) {
        return vaccineStore.getDosageBasedOnVaccineAndDoseNumber(chosenVaccine, age, dosage);
    }


    public boolean changeStateOfAdministrationWhenUserIsFullyVaccinated(VaccineAdministrationDTO vaccineAdministrationDTO) {
        int totalDosesNeeded = vaccineStore.getNumberOfDosesForAgeGroup(vaccineAdministrationDTO.getVaccine(), vaccineAdministrationDTO.getSnsUser().getAge());
        int dosesAdministered = vaccineAdministrationDTO.getDoseNumber();

        if (totalDosesNeeded == dosesAdministered) {
            this.vaccineAdministrationStore.changeVaccinationState(vaccineAdministrationDTO);
            return true;
        }
        return false;
    }

    /**
     * To change the vaccine administration schedule state
     * @param vaccineAdministrationDTO data transfer object of vaccine administration
     * @return true if successful, false if not
     */
    public boolean changeVaccinationScheduleState(VaccineAdministrationDTO vaccineAdministrationDTO) {
        return this.vaccineAdministrationStore.changeVaccinationScheduleState(vaccineAdministrationDTO);
    }


    public boolean sendSmsToUser(VaccineAdministrationDTO vaccineAdministrationDTO) {
        VaccineAdministrationMapper vaccineAdministrationMapper = new VaccineAdministrationMapper();
        VaccineAdministration vaccineAdministration1 = vaccineAdministrationMapper.toModel(vaccineAdministrationDTO);
        String message = "Recovery period is over, you can now leave the vaccination center! Thank you.";
        if (this.vaccineAdministrationStore.addVaccineAdministration(vaccineAdministration1)) {
            NotificationHandler.sendSMSForLeavingVaccinationCenter(vaccineAdministration1.getSnsUser().getPhoneNumber().getNumber() ,message);

        }
        return true;
    }

}
