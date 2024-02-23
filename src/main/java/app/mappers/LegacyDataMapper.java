package app.mappers;

import app.domain.model.SnsNumber;
import app.mappers.dto.LegacyDataDTO;
import java.time.LocalDateTime;

public class LegacyDataMapper {

    /**
     * Maps from model entity to DTO
     * @param snsNumber sns Number
     * @param vaccineName Vaccine Name
     * @param dose dosage
     * @param lotNumber lot Number
     * @param snsNumber sns Number
     * @param scheduledDateTime Scheduled DateTime
     * @param arrivalDateTime Arrival DateTime
     * @param nurseAdministrationDateTime Nurse Adminsitration DateTime
     * @param leavingDateTime Leaving DateTime
     * @return the LegacyDataDTO
     */

    public LegacyDataDTO toDTO(SnsNumber snsNumber, String vaccineName, int dose, String lotNumber, LocalDateTime scheduledDateTime, LocalDateTime arrivalDateTime, LocalDateTime nurseAdministrationDateTime, LocalDateTime leavingDateTime)
    {
        return new LegacyDataDTO(
                snsNumber.getNumber(),
                vaccineName,
                dose,
                lotNumber,
                scheduledDateTime,
                arrivalDateTime,
                nurseAdministrationDateTime,
                leavingDateTime);
    }
}
