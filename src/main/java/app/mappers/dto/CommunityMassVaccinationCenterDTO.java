package app.mappers.dto;

import app.domain.model.Employee;
import app.domain.model.VaccineType;
import app.ui.console.utils.AttributesValidationUtils;

public class CommunityMassVaccinationCenterDTO extends VaccinationCenterDTO {

    private VaccineType vaccineType;

    public CommunityMassVaccinationCenterDTO(String vaccinationCenterName,
                                             long vaccinationCenterPhoneNumber,
                                             AddressDTO address,
                                             String vaccinationCenterEmailAddress,
                                             long vaccinationCenterFaxNumber,
                                             String websiteAddress,
                                             String openingHours,
                                             String closingHours,
                                             int slotDuration,
                                             int maxNumVaccines,
                                             Employee centerCoordinator,
                                             VaccineType vaccineType)
    {
        super(
                vaccinationCenterName,
                vaccinationCenterPhoneNumber,
                address,
                vaccinationCenterEmailAddress,
                vaccinationCenterFaxNumber,
                websiteAddress,
                openingHours,
                closingHours,
                slotDuration,
                maxNumVaccines,
                centerCoordinator
        );

        AttributesValidationUtils.validateNonNullAttribute("Vaccine Type administrated", vaccineType);

        this.vaccineType = vaccineType;
    }

    public VaccineType getVaccineType()
    {
        return vaccineType;
    }

    @Override
    public String toString()
    {
        return "CM Vaccination Center information:\n" +
                super.toString() +
                "- Vaccine Type: " + vaccineType + '\n';
    }

}




