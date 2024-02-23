package app.mappers.dto;

import app.ui.console.utils.AttributesValidationUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class LegacyDataDTO
{
    private Long snsNumber;

    private String vaccineName;

    private int dose;

    private String lotNumber;

    private LocalDateTime scheduledDateTime;

    private LocalDateTime arrivalDateTime;

    private LocalDateTime nurseAdministrationDateTime;

    private LocalDateTime leavingDateTime;
    private String snsUserName;
    private String vaccineTypeDescription;

    private final String DEFAULT_SNS_USER_NAME = "N/A";
    private final String DEFAULT_VACCINE_TYPE_DESCRIPTION = "N/A";


    public LegacyDataDTO(
        Long snsNumber,
        String vaccineName,
        int dose,
        String lotNumber,
        LocalDateTime scheduledDateTime,
        LocalDateTime arrivalDateTime,
        LocalDateTime nurseAdministrationDateTime,
        LocalDateTime leavingDateTime)
        {
            AttributesValidationUtils.validateNonNullAttribute("Sns Number", snsNumber);
            AttributesValidationUtils.validateNonNullAttribute("Vaccine Name", vaccineName);
            AttributesValidationUtils.validateNonNullAttribute("Dosage", dose);
            AttributesValidationUtils.validateNonNullAttribute("Lot Number", lotNumber);
            AttributesValidationUtils.validateNonNullAttribute("Scheduled DateTime", scheduledDateTime);
            AttributesValidationUtils.validateNonNullAttribute("Arrival DateTime", arrivalDateTime);
            AttributesValidationUtils.validateNonNullAttribute("Nurse Administration DateTime", nurseAdministrationDateTime);
            AttributesValidationUtils.validateNonNullAttribute("Leaving DateTime", leavingDateTime);

            validateScheduledDateTimeSmallerthenArrivalDateTime(scheduledDateTime, arrivalDateTime);
            validateNurseAdministrationDateTimeIsBiggerThenLeavingDateTime(nurseAdministrationDateTime, leavingDateTime);

            this.snsNumber = snsNumber;
            this.vaccineName = vaccineName;
            this.dose = dose;
            this.lotNumber = lotNumber;
            this.scheduledDateTime = scheduledDateTime;
            this.arrivalDateTime = arrivalDateTime;
            this.nurseAdministrationDateTime = nurseAdministrationDateTime;
            this.leavingDateTime = leavingDateTime;
            snsUserName = DEFAULT_SNS_USER_NAME;
            vaccineTypeDescription = DEFAULT_VACCINE_TYPE_DESCRIPTION;
        }

        public Long getSnsNumber()
        {
            return snsNumber;
        }

        public String getVaccineName()
        {
            return vaccineName;
        }

        public int getDose()
        {
            return dose;
        }

        public String getLotNumber()
        {
            return lotNumber;
        }

        public LocalDateTime getScheduledDateTime()
        {
            return scheduledDateTime;
        }

        public LocalDateTime getArrivalDateTime()
        {
            return arrivalDateTime;
        }

        public LocalDateTime getNurseAdministrationDateTime()
        {
            return nurseAdministrationDateTime;
        }

        public LocalDateTime getLeavingDateTime()
        {
            return leavingDateTime;
        }

        public String getSnsUserName() {return snsUserName;}

        public String getVaccineTypeDescription() {return vaccineTypeDescription;}

        public void setSnsUserName(String snsUserName)
        {
            this.snsUserName = snsUserName;
        }

        public void setVaccineTypeDescription(String vaccineTypeDescription)
        {
            this.vaccineTypeDescription = vaccineTypeDescription;
        }

    /**
     * Validates if Scheduled DateTime is samller then the Arrival DateTime
     * @param scheduledDateTime Scheduled DateTime
     * @param arrivalDateTime Arrival DateTime
     */
    private void validateScheduledDateTimeSmallerthenArrivalDateTime(LocalDateTime scheduledDateTime, LocalDateTime arrivalDateTime)
    {
/*        if (scheduledDateTime.isAfter(arrivalDateTime))
        {
            throw new IllegalArgumentException("Scheduled DateTime can't be bigger then then the Arrival DateTime.");
        }
        if (scheduledDateTime.isEqual(arrivalDateTime))
        {
            throw new IllegalArgumentException("Scheduled DateTime can't be equal to Arrival DateTime. ");
        }*/
    }

    /**
     * Validates if Nurse Administration DateTime is bigger then the Leaving DateTime
     * @param nurseAdministrationDateTime Nurse Administration DateTime
     * @param leavingDateTime Leaving DateTime
     */
    private void validateNurseAdministrationDateTimeIsBiggerThenLeavingDateTime(LocalDateTime nurseAdministrationDateTime, LocalDateTime leavingDateTime)
    {
        if (nurseAdministrationDateTime.isAfter(leavingDateTime))
        {
            throw new IllegalArgumentException("Nurse Administration DateTime can't be bigger then Leaving DateTime.");
        }
        if (nurseAdministrationDateTime.isEqual(leavingDateTime))
        {
            throw new IllegalArgumentException("Nurse Administration DateTime can't be equal to Leaving DateTime. ");
        }
    }

    @Override
    public String toString()
    {
        String DATE_FORMATTER= "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        return String.format("%s, %d, arrived at %s, was vaccinated with vaccine type %s on %s, and left at %s",
                getSnsUserName(), getSnsNumber(), getArrivalDateTime().format(formatter), getVaccineTypeDescription(),
                getNurseAdministrationDateTime().format(formatter), getLeavingDateTime().format(formatter));
    }

}
