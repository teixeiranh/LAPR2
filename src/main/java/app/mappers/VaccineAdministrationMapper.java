package app.mappers;

import app.domain.model.VaccineAdministration;
import app.mappers.dto.VaccineAdministrationDTO;

public class VaccineAdministrationMapper {

    private  SnsUserArrivalMapper snsUserArrivalMapper;
    private SnsUserMapper snsUserMapper;
    private VaccineMapper vaccineMapper;

    public VaccineAdministrationMapper()
    {
        this.snsUserArrivalMapper = new SnsUserArrivalMapper();
        this.snsUserMapper = new SnsUserMapper();
        this.vaccineMapper = new VaccineMapper();
    }

    /**
     * Maps from model entity to DTO
     * @param model the model to convert
     * @return VaccineAdministrationDTO
     */
    public VaccineAdministrationDTO toDTO(VaccineAdministration model)
    {
        return new VaccineAdministrationDTO(
                this.snsUserArrivalMapper.toDTO(model.getSnsUserArrival()),
                this.snsUserMapper.toDTO(model.getSnsUser()),
                this.vaccineMapper.toDTO(model.getVaccine()),
                model.getDoseNumber(),
                model.getLotNumber(),
                model.getAdministrationDate(),
                model.getLeavingDate());
    }


    public VaccineAdministration toModel(VaccineAdministrationDTO dto)
    {
        return new VaccineAdministration(
                this.snsUserArrivalMapper.toModel(dto.getSnsUserArrival()),
                this.snsUserMapper.toModel(dto.getSnsUser()),
                this.vaccineMapper.toModel(dto.getVaccine()),
                dto.getDoseNumber(),
                dto.getLotNumber(),
                dto.getAdministrationDate(),
                dto.getLeavingDate());

    }


}
