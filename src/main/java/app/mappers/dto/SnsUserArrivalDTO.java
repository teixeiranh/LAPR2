package app.mappers.dto;

import app.ui.console.utils.AttributesValidationUtils;
import java.time.LocalDateTime;

public class SnsUserArrivalDTO {
    private LocalDateTime arrivalDate;
    private Long snsNumber;
    private VaccinationScheduleDTO vaccineSchedule;

    /**
     * Constructs an instance of SNS User DTO (Data Transfer Object)
     *
     * @param arrivalDate       Arrival DateTime of SNS User(required)
     * @param snsNumber         SNS Number of the SNS User (required,unique)
     * @param vaccineSchedule   SNS User Scheduling
     */

    public SnsUserArrivalDTO(
              LocalDateTime arrivalDate,
              long snsNumber,
              VaccinationScheduleDTO vaccineSchedule
    )
    {
        AttributesValidationUtils.validateNonNullAttribute("Arrival Date", arrivalDate);
        AttributesValidationUtils.validateNonNullAttribute("SNS number", snsNumber);
        AttributesValidationUtils.validateNonNullAttribute("Vaccine Schedule", vaccineSchedule);

        this.arrivalDate = arrivalDate;
        this.snsNumber = snsNumber;
        this.vaccineSchedule = vaccineSchedule;
    }

    public SnsUserArrivalDTO() {

    }
    
    public LocalDateTime getArrivalDate()
    {
        return arrivalDate;
    }

    public long getSnsNumber()
    {
        return snsNumber;
    }

    public VaccinationScheduleDTO getVaccineSchedule()
    {
        return vaccineSchedule;
    }

    public VaccinationScheduleDTO getVaccinationSchedule() {return vaccineSchedule;}

    @Override
    public String toString()
    {
        return "SNS user Schedule information:\n" +
                "- Arrival Date: " + arrivalDate + '\n' +
                "- Sns Number: " + snsNumber + '\n' +
                "- Vaccine Schedule: " + vaccineSchedule + '\n';
    }

}
