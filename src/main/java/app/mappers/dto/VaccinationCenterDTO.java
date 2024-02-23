package app.mappers.dto;

import app.domain.model.Employee;
import app.ui.console.utils.AttributesValidationUtils;

public class VaccinationCenterDTO
{

    private String vaccinationCenterName;
    private Long vaccinationCenterPhoneNumber;
    private AddressDTO address;
    private String vaccinationCenterEmailAddress;
    private Long vaccinationCenterFaxNumber;
    private String websiteAddress;
    private String openingHours;
    private String closingHours;
    private int slotDuration;
    private int maxNumVaccines;
    private Employee centerCoordinator;

    public VaccinationCenterDTO(String vaccinationCenterName,
                                Long vaccinationCenterPhoneNumber,
                                AddressDTO address,
                                String vaccinationCenterEmailAddress,
                                Long vaccinationCenterFaxNumber,
                                String websiteAddress,
                                String openingHours,
                                String closingHours,
                                int slotDuration,
                                int maxNumVaccines,
                                Employee centerCoordinator)
    {
        AttributesValidationUtils.validateNonNullAttribute("Vaccination Center Name", vaccinationCenterName);
        this.validateNonNullAndLengthOfPhoneNumber(vaccinationCenterPhoneNumber);
        AttributesValidationUtils.validateNonNullAttribute("Vaccination Center Address", address);
        AttributesValidationUtils.validateNonNullAttribute("Email Address", vaccinationCenterEmailAddress);
        this.validateNonNullAndLengthOfPhoneNumber(vaccinationCenterFaxNumber);
        AttributesValidationUtils.validateNonNullAttribute("Website", websiteAddress);
        AttributesValidationUtils.validateNonNullAttribute("Opening Hours", openingHours);
        AttributesValidationUtils.validateNonNullAttribute("Closing Hours", closingHours);
        AttributesValidationUtils.validateNonNullAttribute("Slot Duration", slotDuration);
        AttributesValidationUtils.validateNonNullAttribute("Max number of vaccines administered per slot", maxNumVaccines);
        AttributesValidationUtils.validateNonNullAttribute("Center Coordiantor", centerCoordinator);

        this.vaccinationCenterName = vaccinationCenterName;
        this.vaccinationCenterPhoneNumber = vaccinationCenterPhoneNumber;
        this.address = address;
        this.vaccinationCenterEmailAddress = vaccinationCenterEmailAddress;
        this.vaccinationCenterFaxNumber = vaccinationCenterFaxNumber;
        this.websiteAddress = websiteAddress;
        this.openingHours = openingHours;
        this.closingHours = closingHours;
        this.slotDuration = slotDuration;
        this.maxNumVaccines = maxNumVaccines;
        this.centerCoordinator = centerCoordinator;
    }

    public String getVaccinationCenterName()
    {
        return vaccinationCenterName;
    }

    public void setVaccinationCenterName(String vaccinationCenterName)
    {
        this.vaccinationCenterName = vaccinationCenterName;
    }

    public Long getVaccinationCenterPhoneNumber()
    {
        return vaccinationCenterPhoneNumber;
    }

    public void setVaccinationCenterPhoneNumber(Long vaccinationCenterPhoneNumber)
    {
        this.vaccinationCenterPhoneNumber = vaccinationCenterPhoneNumber;
    }

    public AddressDTO getAddress()
    {
        return address;
    }

    public void setAddress(AddressDTO address)
    {
        this.address = address;
    }

    public String getVaccinationCenterEmailAddress()
    {
        return vaccinationCenterEmailAddress;
    }

    public void setVaccinationCenterEmailAddress(String vaccinationCenterEmailAddress)
    {
        this.vaccinationCenterEmailAddress = vaccinationCenterEmailAddress;
    }

    public Long getVaccinationCenterFaxNumber()
    {
        return vaccinationCenterFaxNumber;
    }

    public void setVaccinationCenterFaxNumber(Long vaccinationCenterFaxNumber)
    {
        this.vaccinationCenterFaxNumber = vaccinationCenterFaxNumber;
    }

    public String getWebsiteAddress()
    {
        return websiteAddress;
    }

    public void setWebsiteAddress(String websiteAddress)
    {
        this.websiteAddress = websiteAddress;
    }

    public String getOpeningHours()
    {
        return openingHours;
    }

    public void setOpeningHours(String openingHours)
    {
        this.openingHours = openingHours;
    }

    public String getClosingHours()
    {
        return closingHours;
    }

    public void setClosingHours(String closingHours)
    {
        this.closingHours = closingHours;
    }

    public int getSlotDuration()
    {
        return slotDuration;
    }

    public void setSlotDuration(int slotDuration)
    {
        this.slotDuration = slotDuration;
    }

    public int getMaxNumVaccines()
    {
        return maxNumVaccines;
    }

    public void setMaxNumVaccines(int maxNumVaccines)
    {
        this.maxNumVaccines = maxNumVaccines;
    }

    public Employee getCenterCoordinator()
    {
        return centerCoordinator;
    }

    public void setCenterCoordinator(Employee centerCoordinator)
    {
        this.centerCoordinator = centerCoordinator;
    }

    @Override
    public String toString()
    {
        return
                "- Name: " + vaccinationCenterName + '\n' +
                        "- Phone Number: " + vaccinationCenterPhoneNumber + '\n' +
                        "- Email: " + vaccinationCenterEmailAddress + '\n' +
                        "- Opening hours: " + openingHours + '\n' +
                        "- Closing hours: " + closingHours + '\n' +
                        "- " + address + '\n';
    }

    /**
     * Validates if Phone/Fax Number and Fax number have 9 digits
     *
     * @param phoneNumber phone/fax number of vaccination center
     */
    private void validateNonNullAndLengthOfPhoneNumber(long phoneNumber)
    {
        int length = String.valueOf(phoneNumber).length();
        if (length != 9)
        {
            throw new IllegalArgumentException("Phone or fax number information is invalid, please insert a valid phone number.");
        }
    }

    public VaccinationCenterDTO()
    {

    }

}
