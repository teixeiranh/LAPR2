package app.mappers;

import app.domain.model.*;
import app.mappers.dto.*;

public class VaccinationScheduleMapper {

    private SnsUserMapper snsUserMapper;
    private VaccineTypeMapper vaccineTypeMapper;
    private HealthCareVaccinationCenterMapper healthCareVaccinationCenterMapper;
    private CommunityMassVaccinationCenterMapper communityMassVaccinationCenterMapper;

    public VaccinationScheduleMapper()
    {
        this.snsUserMapper = new SnsUserMapper();
        this.vaccineTypeMapper = new VaccineTypeMapper();
        this.healthCareVaccinationCenterMapper = new HealthCareVaccinationCenterMapper();
        this.communityMassVaccinationCenterMapper = new CommunityMassVaccinationCenterMapper();
    }

    /**
     * Maps from model entity to DTO
     *
     * @param model the model to map/convert
     * @return the vaccinationScheduleDTO
     */
    public VaccinationScheduleDTO toDTO(VaccinationSchedule model)
    {
        VaccinationCenterDTO vaccinationCenterDTO;
        if (model.getVaccinationCenter() instanceof HealthcareVaccinationCenter)
        {
            vaccinationCenterDTO = this.healthCareVaccinationCenterMapper.toDTO((HealthcareVaccinationCenter) model.getVaccinationCenter());
        } else
        {
            vaccinationCenterDTO = this.communityMassVaccinationCenterMapper.toDTO((CommunityMassVaccinationCenter) model.getVaccinationCenter());
        }

        return new VaccinationScheduleDTO(
                this.snsUserMapper.toDTO(model.getSnsUser()),
                model.getDate(),
                this.vaccineTypeMapper.toDTO(model.getVaccineType()),
                vaccinationCenterDTO);
    }

    /**
     * Maps from DTO to model entity
     *
     * @param dto the DTO to map/convert
     * @return the vaccinationSchedule
     */
    public VaccinationSchedule toModel(VaccinationScheduleDTO dto)
    {
        VaccinationCenter vaccinationCenterMapper;

        if (dto.getVaccinationCenter() instanceof HealthCareVaccinationCenterDTO)
        {
            vaccinationCenterMapper = this.healthCareVaccinationCenterMapper.toModel((HealthCareVaccinationCenterDTO) dto.getVaccinationCenter());

        } else
        {
            vaccinationCenterMapper = this.communityMassVaccinationCenterMapper.toModel((CommunityMassVaccinationCenterDTO) dto.getVaccinationCenter());
        }

        return new VaccinationSchedule(
                this.snsUserMapper.toModel(dto.getSnsUser()),
                dto.getDate(),
                this.vaccineTypeMapper.toModel(dto.getVaccineType()),
                vaccinationCenterMapper);
    }

}
