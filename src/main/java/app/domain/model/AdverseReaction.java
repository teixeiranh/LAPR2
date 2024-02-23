package app.domain.model;

import app.ui.console.utils.AttributesValidationUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AdverseReaction implements Serializable {

    private static final long serialVersionUID = -7517386663155231149L;
    private SnsUser snsUser;
    private String adverseReaction;
    private LocalDateTime date;
    private Vaccine vaccine;

    public AdverseReaction(SnsUser snsUser,
                           String adverseReaction,
                           LocalDateTime date,
                           Vaccine vaccine)
    {
        validateNonNullAndLenghtAttribute(adverseReaction);
        AttributesValidationUtils.validateNonNullAttribute("vaccine", vaccine);
        this.snsUser = snsUser;
        this.adverseReaction = adverseReaction;
        this.date = date;
        this.vaccine = vaccine;
    }

    public String getAdverseReaction()
    {
        return adverseReaction;
    }

    public LocalDateTime getDate()
    {
        return date;
    }

    public Vaccine getVaccine()
    {
        return vaccine;
    }

    public SnsUser getSnsUser()
    {
        return snsUser;
    }

    @Override
    public String toString()
    {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        return "SNS User number: " + snsUser.getSnsNumber().getNumber() + '\n' +
                "SNS User name: " + snsUser.getName() + '\n' +
                "SNS User age: " + snsUser.getAge() + '\n' +
                "Date: " + df.format(this.date) + '\n' +
                "Vaccine type: " + vaccine.getVaccineType() + '\n' +
                "Vaccine: " + vaccine.getVaccineName() + '\n' +
                "Adverse reaction: " + adverseReaction + '\n';
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
            throw new IllegalArgumentException("Adverse reaction cannot be blank");
        }
    }

}
