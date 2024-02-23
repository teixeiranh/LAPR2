package app.mappers;

import app.domain.model.Vaccine;
import app.mappers.dto.VaccineDTO;

public class VaccineMapper
{
    public VaccineDTO toDTO(Vaccine model)
    {
        return new VaccineDTO
                (
                        model.getVaccineType(),
                        model.getVaccineName(),
                        model.getVaccineManufacturer(),
//                        model.getAdministrationProcess(),
                        model.getVaccineNumberOfAgeGroups(),
                        model.getMinAgeList(),
                        model.getMaxAgeList(),
                        model.getNumberOfDoses(),
                        model.getDosage(),
                        model.getTimeElapsed()
                );
    }

    public Vaccine toModel(VaccineDTO dto)
    {
        return new Vaccine
                (
                        dto.getVaccineType(),
                        dto.getVaccineName(),
                        dto.getVaccineManufacturer(),
//                        dto.getAdministrationProcess()
                        dto.getNumberOfAgeGroups(),
                        dto.getMinAge(),
                        dto.getMaxAge(),
                        dto.getNumberOfDoses(),
                        dto.getDosage(),
                        dto.getTimeElapsed()
                );
    }

}
