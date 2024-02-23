package app.mappers;

import app.domain.model.HealthcareVaccinationCenter;
import app.domain.model.PhoneNumber;
import app.mappers.dto.HealthCareVaccinationCenterDTO;
import pt.isep.lei.esoft.auth.domain.model.Email;


public class HealthCareVaccinationCenterMapper {

    private AddressMapper addressMapper;

    public HealthCareVaccinationCenterMapper()
    {
        this.addressMapper = new AddressMapper();
    }

    public HealthCareVaccinationCenterDTO toDTO(HealthcareVaccinationCenter model)
    {
        return new HealthCareVaccinationCenterDTO(
                model.getVaccinationCenterName(),
                model.getVaccinationCenterPhoneNumber().getNumber(),
                this.addressMapper.toDTO(model.getAddress()),
                model.getVaccinationCenterEmailAddress().getEmail(),
                model.getVaccinationCenterFaxNumber().getNumber(),
                model.getWebsiteAddress(),
                model.getOpeningHours(),
                model.getClosingHours(),
                model.getSlotDuration(),
                model.getMaxNumVaccines(),
                model.getCenterCoordinator(),
                model.getArs(),
                model.getAges(),
                model.getVaccineTypes());
    }

    public HealthcareVaccinationCenter toModel(HealthCareVaccinationCenterDTO dto)
    {
        return new HealthcareVaccinationCenter(
                dto.getVaccinationCenterName(),
                new PhoneNumber(dto.getVaccinationCenterPhoneNumber()),
                this.addressMapper.toModel(dto.getAddress()),
                new Email(dto.getVaccinationCenterEmailAddress()),
                new PhoneNumber(dto.getVaccinationCenterFaxNumber()),
                dto.getWebsiteAddress(),
                dto.getOpeningHours(),
                dto.getClosingHours(),
                dto.getSlotDuration(),
                dto.getMaxNumVaccines(),
                dto.getCenterCoordinator(),
                dto.getArs(),
                dto.getAges(),
                dto.getVaccineTypes());
    }


}
