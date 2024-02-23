package app.mappers.dto;

import app.ui.console.utils.AttributesValidationUtils;

import java.time.LocalDateTime;

public class VaccineAdministrationDTO {

    private SnsUserArrivalDTO snsUserArrival;
    private SnsUserDTO snsUser;
    private VaccineDTO vaccine;
    private int doseNumber;
    private String lotNumber;
    private LocalDateTime administrationDate;
    private LocalDateTime leavingDate;

   public VaccineAdministrationDTO(SnsUserArrivalDTO snsUserArrival,
                                   SnsUserDTO snsUser,
                                   VaccineDTO vaccine,
                                   int doseNumber,
                                   String lotNumber,
                                   LocalDateTime administrationDate,
                                   LocalDateTime leavingDate)

   {
       AttributesValidationUtils.validateNonNullAttribute("SNS User Arrival", snsUserArrival);
       AttributesValidationUtils.validateNonNullAttribute("Sns Number", snsUser);
       AttributesValidationUtils.validateNonNullAttribute("Vaccine", vaccine);
       validateZeroOrNulllAttribute("Vaccine Dose Number", doseNumber);
       AttributesValidationUtils.validateNonNullAttribute("Lot Number", lotNumber);
       AttributesValidationUtils.validateNonNullAttribute("Date|Hour", administrationDate);
       AttributesValidationUtils.validateNonNullAttribute("Date|Hour", leavingDate);


       this.snsUserArrival = snsUserArrival;
       this.snsUser = snsUser;
       this.vaccine = vaccine;
       this.doseNumber = doseNumber;
       this.lotNumber = lotNumber;
       this.administrationDate = administrationDate;
       this.leavingDate = leavingDate;

   }

    public SnsUserArrivalDTO getSnsUserArrival()
    {
        return this.snsUserArrival;
    }

    public SnsUserDTO getSnsUser()
    {
        return this.snsUser;
    }

    public VaccineDTO getVaccine()
    {
        return this.vaccine;
    }

   public int getDoseNumber()
   {
       return this.doseNumber;
   }

   public String getLotNumber()
   {
       return this.lotNumber;
   }

    public LocalDateTime getAdministrationDate()
    {
        return this.administrationDate;
    }

    public LocalDateTime getLeavingDate()
    {
        return this.leavingDate;
    }

    @Override
    public String toString()
    {
         return  "\nSNS User number: " + snsUser.getSnsNumber() +
                 "\nSNS User name: " + snsUser.getName() +
                 "\nSNS User age: " + snsUser.getAge() +
                 "\n Vaccine: " + vaccine.getVaccineName() +
                 "\n Dose Number: " + getDoseNumber() +
                 "\n Lot Number: " + getLotNumber() +
                 "\n Administration date: " + getAdministrationDate();

    }












    /**
     * Verifies if a String attribute is not null and not blank
     *
     * @param attribute      attribute being validated
     * @param attributeValue attribute value
     */
    static public void validateZeroOrNulllAttribute(String attribute, int attributeValue)
    {
        if (attributeValue == 0)
        {
            throw new IllegalArgumentException("User cannot have the attribute " + attribute + " as zero");
        }

    }
}
