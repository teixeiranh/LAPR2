package app.mappers;

import app.domain.model.AdverseReaction;
import app.mappers.dto.AdverseReactionDTO;

public class AdverseReactionMapper {

    private SnsUserMapper snsUserMapper;
    private VaccineMapper vaccineMapper;

    public AdverseReactionMapper()
    {
        this.snsUserMapper = new SnsUserMapper();
        this.vaccineMapper = new VaccineMapper();
    }

    public AdverseReactionDTO toDTO(AdverseReaction model)
    {
        return new AdverseReactionDTO(
                this.snsUserMapper.toDTO(model.getSnsUser()),
                model.getAdverseReaction(),
                model.getDate(),
                this.vaccineMapper.toDTO(model.getVaccine()));
    }

    public AdverseReaction toModel(AdverseReactionDTO dto)
    {
        return new AdverseReaction(
                this.snsUserMapper.toModel(dto.getSnsUser()),
                dto.getAdverseReaction(),
                dto.getDate(),
                this.vaccineMapper.toModel(dto.getVaccine()));
    }

}
