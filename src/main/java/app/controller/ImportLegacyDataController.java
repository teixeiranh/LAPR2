package app.controller;

import app.domain.model.*;
import app.domain.store.*;
import app.domain.store.exception.VaccineDoesNotExistException;
import app.mappers.SnsUserArrivalMapper;
import app.mappers.SnsUserMapper;
import app.mappers.VaccineMapper;
import app.mappers.VaccineTypeMapper;
import app.mappers.dto.*;


import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ImportLegacyDataController
{

    private LegacyDataStore legacyDataStore;

    private Company company;
    private App app;
    private VaccinationCenterStore vaccinationCenterStore;
    private SnsUserStore snsUserStore;
    private VaccinationScheduleStore vaccinationScheduleStore;
    private SnsUserArrivalStore snsUserArrivalStore;
    private VaccineAdministrationStore vaccineAdministrationStore;
    private VaccineStore vaccineStore;

    private SnsUserMapper snsUserMapper;
    private VaccineMapper vaccineMapper;

    private VaccineTypeMapper vaccineTypeMapper;

    private SnsUserArrivalMapper snsUserArrivalMapper;

    public ImportLegacyDataController ()
    {
        app = App.getInstance();
        company = app.getCompany();
        legacyDataStore = company.getLegacyDataStore();
        vaccinationCenterStore = company.getVaccinationCenterStore();
        snsUserStore = company.getSnsUserStore();
        vaccinationScheduleStore = company.getVaccinationScheduleStore();
        snsUserArrivalStore = company.getSnsUserArrivalStore();
        vaccineAdministrationStore = company.getVaccineAdministrationStore();
        vaccineStore = company.getVaccineStore();
        snsUserMapper = new SnsUserMapper();
        vaccineMapper = new VaccineMapper();
        vaccineTypeMapper = new VaccineTypeMapper();


    }

    public boolean importLegacyDataFromExternalSource(String filePath, String vcEmail) throws FileNotFoundException
    {
        List<LegacyDataDTO> legacyDataDTOList = legacyDataStore.importLegacyDataFromExternalSource(filePath);

        boolean importSuccess = false;
        if (!legacyDataDTOList.isEmpty())
        {
            importSuccess = registerLegacyDataFromExternalSource(legacyDataDTOList, vcEmail);
        }
        return importSuccess;
    }

    public List<Comparator<LegacyDataDTO>> getSortingCriteriaList()
    {
        return legacyDataStore.getSortingCriteriaList();
    }

    public List<LegacyDataDTO> getSortedLegacyDataDTOList(Comparator<LegacyDataDTO> sortingCriterion)
    {
        return legacyDataStore.getSortedLegacyDataDTOList(sortingCriterion);
    }

    public List<LegacyDataDTO> getLegacyDataDTOList()
    {
        return legacyDataStore.getRegisteredLegacyDataDTOList();
    }


    private boolean registerLegacyDataFromExternalSource(List<LegacyDataDTO> legacyDataDTOList, String vcEmail)
    {
        List<LegacyDataDTO> registeredLegacyDataDTOList = new ArrayList<>();
        boolean registeredWithSuccess = false;

        VaccinationCenterDTO vaccinationCenterDTO = vaccinationCenterStore.getVaccinationCenterDTOByEmail(vcEmail);

        for (LegacyDataDTO legacyDataDTO : legacyDataDTOList)
        {
            long snsUserNumber = legacyDataDTO.getSnsNumber();

            if (snsUserStore.existsSnsUser(snsUserNumber))
            {
                SnsUser snsUser = snsUserStore.getSnsUserBySnsNumber(snsUserNumber).get();
                SnsUserDTO snsUserDTO = snsUserMapper.toDTO(snsUser);

                VaccinationScheduleDTO vaccinationScheduleDTO = createVaccinationScheduleDTOFromLegacyDataDTO(legacyDataDTO, vaccinationCenterDTO, snsUserDTO);

                if (!vaccinationScheduleStore.existsVaccinationSchedule(vaccinationScheduleDTO).isPresent())
                {
                    VaccinationSchedule vaccinationSchedule = vaccinationScheduleStore.createVaccinationSchedule(vaccinationScheduleDTO);
                    vaccinationScheduleStore.addVaccinationSchedule(vaccinationSchedule);
                    //vaccinationSchedule.setState(VaccinationScheduleState.FINALIZED);

                    SnsUserArrivalDTO arrivalDTO = createSnsUserArrivalDTOFromLegacyDataDTO(legacyDataDTO, vaccinationScheduleDTO);
                    SnsUserArrival snsUserArrival = snsUserArrivalStore.createSnsUserArrival(arrivalDTO);
                    snsUserArrivalStore.addSnsUserArrival(snsUserArrival);

                    VaccineAdministrationDTO vaccineAdministrationDTO = createVaccineAdministrationDTOFromLegacyDataDTO(legacyDataDTO, snsUserDTO, arrivalDTO);
                    VaccineAdministration vaccineAdministration = vaccineAdministrationStore.createVaccineAdministration(vaccineAdministrationDTO);
                    vaccineAdministration.setVaccineAdministrationState(VaccineAdministrationState.FULLYVACCINATED);
                    vaccineAdministrationStore.addVaccineAdministration(vaccineAdministration);

                    legacyDataDTO.setSnsUserName(snsUserDTO.getName());
                    legacyDataDTO.setVaccineTypeDescription(vaccineAdministration.getVaccine().getVaccineType().getDescription());
                    registeredLegacyDataDTOList.add(legacyDataDTO);
                }
                else
                {
                    System.out.printf("SnsUser number %d already has an appointment registered in the system!!%n%n", snsUserNumber);
                }
            }
            else
            {
                System.out.printf("SnsUser number %d is not registered in the system!!%n%n", snsUserNumber);
            }

        }

        if (!registeredLegacyDataDTOList.isEmpty())
        {
            legacyDataStore.setRegisteredLegacyDataDTOList(registeredLegacyDataDTOList);
            registeredWithSuccess = true;
        }

        return registeredWithSuccess;
    }

    private VaccinationScheduleDTO createVaccinationScheduleDTOFromLegacyDataDTO(LegacyDataDTO legacyDataDTO, VaccinationCenterDTO vcDTO, SnsUserDTO userDTO)
    {
        LocalDateTime vaccineScheduleDate = legacyDataDTO.getScheduledDateTime();
        VaccineTypeDTO vaccineTypeDTO = createVaccineTypeDTOFromLegacyDataDTO(legacyDataDTO);
        VaccinationScheduleDTO vaccinationScheduleDTO = new VaccinationScheduleDTO(
                userDTO, vaccineScheduleDate, vaccineTypeDTO, vcDTO
        );
        return vaccinationScheduleDTO;
    }

    private SnsUserArrivalDTO createSnsUserArrivalDTOFromLegacyDataDTO(LegacyDataDTO legacyDataDTO, VaccinationScheduleDTO scheduleDTO)
    {
        LocalDateTime arrivalDateTime = legacyDataDTO.getArrivalDateTime();
        SnsUserArrivalDTO snsUserArrivalDTO = new SnsUserArrivalDTO(
                arrivalDateTime, scheduleDTO.getSnsUser().getSnsNumber(), scheduleDTO
        );
        return snsUserArrivalDTO;
    }

    private VaccineDTO createVaccineDTOFromLegacyDataDTO(LegacyDataDTO legacyDataDTO) throws VaccineDoesNotExistException
    {
        if (!vaccineStore.getVaccineByName(legacyDataDTO.getVaccineName()).isPresent())
        {
            throw new VaccineDoesNotExistException("The vaccine does not exist in the system!!");
        }
        Vaccine vaccine = vaccineStore.getVaccineByName(legacyDataDTO.getVaccineName()).get();

        VaccineDTO vaccineDTO = vaccineMapper.toDTO(vaccine);
        return vaccineDTO;
    }

    private VaccineTypeDTO createVaccineTypeDTOFromLegacyDataDTO(LegacyDataDTO legacyDataDTO)
    {

        VaccineDTO vaccineDTO = createVaccineDTOFromLegacyDataDTO(legacyDataDTO);
        VaccineType vaccineType = vaccineDTO.getVaccineType();
        VaccineTypeDTO vaccineTypeDTO = vaccineTypeMapper.toDTO(vaccineType);
        return vaccineTypeDTO;
    }

    private VaccineAdministrationDTO createVaccineAdministrationDTOFromLegacyDataDTO(
            LegacyDataDTO legacyDataDTO, SnsUserDTO userDTO, SnsUserArrivalDTO arrivalDTO
    )
    {
        VaccineDTO vaccineDTO = createVaccineDTOFromLegacyDataDTO(legacyDataDTO);
        VaccineAdministrationDTO vaccineAdministrationDTO = new VaccineAdministrationDTO(
                arrivalDTO, userDTO, vaccineDTO, legacyDataDTO.getDose(), legacyDataDTO.getLotNumber(),
                legacyDataDTO.getNurseAdministrationDateTime(), legacyDataDTO.getLeavingDateTime()
        );
        return vaccineAdministrationDTO;
    }


}
