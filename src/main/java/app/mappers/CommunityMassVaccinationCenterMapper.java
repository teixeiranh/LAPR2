package app.mappers;

import app.domain.model.CommunityMassVaccinationCenter;
import app.domain.model.PhoneNumber;
import app.mappers.dto.CommunityMassVaccinationCenterDTO;
import pt.isep.lei.esoft.auth.domain.model.Email;

public class CommunityMassVaccinationCenterMapper {

    private AddressMapper addressMapper;

    public CommunityMassVaccinationCenterMapper()
    {
        this.addressMapper = new AddressMapper();
    }

    public CommunityMassVaccinationCenterDTO toDTO(CommunityMassVaccinationCenter model)
    {
        return new CommunityMassVaccinationCenterDTO(
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
                model.getVaccineType());
    }

    public CommunityMassVaccinationCenter toModel(CommunityMassVaccinationCenterDTO dto) {
        return new CommunityMassVaccinationCenter(
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
                dto.getVaccineType());
    }

}
