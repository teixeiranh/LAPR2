package app.mappers.dto;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SnsUserAdverseReactionsDTO {

    private SnsUserDTO snsUser;
    private List<AdverseReactionDTO> adverseReactions;

    public SnsUserAdverseReactionsDTO(SnsUserDTO snsUser,
                                      List<AdverseReactionDTO> adverseReactions)
    {
        this.snsUser = snsUser;
        this.adverseReactions = adverseReactions;
    }

    public SnsUserDTO getSnsUser()
    {
        return snsUser;
    }

    public List<AdverseReactionDTO> getAdverseReactions()
    {
        return adverseReactions;
    }

    @Override
    public String toString()
    {
        String snsUserInfo = "";
        if (this.snsUser != null)
        {
            snsUserInfo = "SNS User number: " + this.snsUser.getSnsNumber() + '\n' +
                    "SNS User name: " + this.snsUser.getName() + '\n' +
                    "SNS User age: " + this.snsUser.getAge() + '\n';
        }

        StringBuilder adverseReactions = new StringBuilder();

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        for (AdverseReactionDTO adverseReactionDTO : this.adverseReactions)
        {
            adverseReactions.append('\n')
                    .append("Date: ")
                    .append(df.format(adverseReactionDTO.getDate()))
                    .append('\n')
                    .append("Vaccine type: ")
                    .append(adverseReactionDTO.getVaccine().getVaccineType())
                    .append('\n')
                    .append("Vaccine: ")
                    .append(adverseReactionDTO.getVaccine().getVaccineName())
                    .append('\n')
                    .append("Adverse reaction: ")
                    .append(adverseReactionDTO.getAdverseReaction())
                    .append('\n');
        }

        return snsUserInfo + adverseReactions;
    }
}
