package app.mappers.dto;

import app.ui.console.utils.AttributesValidationUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class AdverseReactionDTO {

    private SnsUserDTO snsUser;
    private String adverseReaction;
    private LocalDateTime date;
    private VaccineDTO vaccine;

    public AdverseReactionDTO(String adverseReaction,
                              SnsUserDTO snsUser,
                              VaccineDTO vaccine)
    {
        validateNonNullAndLenghtAttribute(adverseReaction);
        AttributesValidationUtils.validateNonNullAttribute("vaccine", vaccine);
        AttributesValidationUtils.validateNonNullAttribute("snsUser", snsUser);
        Calendar today = Calendar.getInstance();
        this.date = LocalDateTime.now();
        this.adverseReaction = adverseReaction;
        this.snsUser = snsUser;
        this.vaccine = vaccine;
    }

    public AdverseReactionDTO(SnsUserDTO snsUser,
                              String adverseReaction,
                              LocalDateTime date,
                              VaccineDTO vaccine)
    {
        validateNonNullAndLenghtAttribute(adverseReaction);
        AttributesValidationUtils.validateNonNullAttribute("vaccine", vaccine);
        AttributesValidationUtils.validateNonNullAttribute("snsUser", snsUser);
        AttributesValidationUtils.validateNonNullAttribute("date", date);
        this.snsUser = snsUser;
        this.adverseReaction = adverseReaction;
        this.date = date;
        this.vaccine = vaccine;
    }

    public SnsUserDTO getSnsUser()
    {
        return snsUser;
    }

    public String getAdverseReaction()
    {
        return adverseReaction;
    }

    public LocalDateTime getDate()
    {
        return date;
    }

    public VaccineDTO getVaccine()
    {
        return vaccine;
    }

    public static void validateNonNullAndLenghtAttribute(String description)
    {
        if (description == null)
        {
            throw new IllegalArgumentException("Adverse reaction cannot be null");
        }
        if (description.length() > 300)
        {
            throw new IllegalArgumentException("Adverse reaction cannot have more than 300 characters");
        }
        if (description.isBlank())
        {
            throw new IllegalArgumentException("Adverse reaction cannot be blank.");
        }
    }

    @Override
    public String toString()
    {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        return "SNS User number: " + snsUser.getSnsNumber() + '\n' +
                "SNS User name: " + snsUser.getName() + '\n' +
                "SNS User age: " + snsUser.getAge() + '\n' +
                "Date: " + this.date.format(df) + '\n' +
                "Vaccine type: " + vaccine.getVaccineType() + '\n' +
                "Vaccine: " + vaccine.getVaccineName() + '\n' +
                "Adverse reaction: " + adverseReaction + '\n';
    }

}
