package app.mappers.dto;

import app.domain.model.Employee;
import app.domain.model.VaccineType;
import app.ui.console.utils.AttributesValidationUtils;

import java.util.ArrayList;

public class HealthCareVaccinationCenterDTO extends VaccinationCenterDTO {

    private String ars;
    private String ages;
    private ArrayList<VaccineType> vaccineTypes;

    public HealthCareVaccinationCenterDTO(String vaccinationCenterName,
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
                                          String ars,
                                          String ages,
                                          ArrayList<VaccineType> vaccineTypes)
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
        AttributesValidationUtils.validateNonNullAttribute("Associated Administração Regional de Saúde", ars);
        AttributesValidationUtils.validateNonNullAttribute("Associated Agrupamentos de Centros de Saúde", ages);
        AttributesValidationUtils.validateNonNullAttribute("Vaccine Types", vaccineTypes);


        this.ars = ars;
        this.ages = ages;
        this.vaccineTypes = vaccineTypes;
    }

    public String getArs()
    {
        return ars;
    }

    public String getAges()
    {
        return ages;
    }

    public ArrayList<VaccineType> getVaccineTypes()
    {
        return vaccineTypes;
    }

    @Override
    public String toString()
    {
        return "HC Vaccination Center information:\n" +
                super.toString() +
                "- Associated Administração Regional de Saúde: " + ars + '\n' +
                "- Associated Agrupamentos de Centros de Saúde: " + ages + '\n' +
                "- Vaccine Types: " + vaccineTypes + '\n';
    }


    /**
     * Validates if Phone/Fax Number and Fax number have 9 digits
     *
     * @param phoneNumber phone/faz number of vaccination center
     */
    private void validateNonNullAndLengthOfPhoneNumber(long phoneNumber)
    {
        int length = String.valueOf(phoneNumber).length();
        if (length != 9)
        {
            throw new IllegalArgumentException("Phone or fax number information is invalid, please insert a valid phone number.");
        }
    }


}
