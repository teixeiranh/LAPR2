package app.mappers.dto;

import app.ui.console.utils.AttributesValidationUtils;

import java.time.LocalDateTime;

public class VaccinationScheduleDTO {

    private SnsUserDTO snsUser;
    private LocalDateTime date;
    private VaccineTypeDTO vaccineType;
    private VaccinationCenterDTO vaccinationCenter;

    public VaccinationScheduleDTO(SnsUserDTO snsUser,
                                  LocalDateTime date,
                                  VaccineTypeDTO vaccineType,
                                  VaccinationCenterDTO vaccinationCenter)
    {
        AttributesValidationUtils.validateNonNullAttribute("SNS Number", snsUser);
        AttributesValidationUtils.validateNonNullAttribute("Data|Hora", date);
        AttributesValidationUtils.validateNonNullAttribute("vaccine type", vaccineType);
        AttributesValidationUtils.validateNonNullAttribute("Vaccination Center", vaccinationCenter);

        this.snsUser = snsUser;
        this.date = date; // a data tem de ser posterior à data de hoje
        this.vaccineType = vaccineType;
        this.vaccinationCenter = vaccinationCenter;
    }

    public SnsUserDTO getSnsUser()
    {
        return this.snsUser;
    }

    public LocalDateTime getDate()
    {
        return this.date;
    }

    public VaccineTypeDTO getVaccineType()
    {
        return this.vaccineType;
    }

    public VaccinationCenterDTO getVaccinationCenter()
    {
        return this.vaccinationCenter;
    }


    @Override
    public String toString()
    {
        return
                "\nSNS number:          " + this.snsUser.getSnsNumber() +
                        "\nVaccination date:    " + this.date +
                        "\nVaccine type:        " + this.vaccineType.getDescription() +
                        "\nVaccination center:  " + this.vaccinationCenter.getVaccinationCenterName();
    }

}
