package app.mappers;

import app.domain.model.SnsUser;
import app.domain.model.VaccineType;
import app.mappers.dto.SnsUserDTO;
import app.mappers.dto.VaccineTypeDTO;

public class VaccineTypeMapper {

    public VaccineTypeDTO toDTO(VaccineType model)
    {
        return new VaccineTypeDTO(
                model.getCode(),
                model.getDescription(),
                model.getTechnology());
    }

    public VaccineType toModel(VaccineTypeDTO dto)
    {
        return new VaccineType(
                dto.getCode(),
                dto.getDescription(),
                dto.getTechnology());
    }

}
