package app.mappers;

import app.domain.model.SnsNumber;
import app.domain.model.SnsUserArrival;
import app.mappers.dto.SnsUserArrivalDTO;

import java.util.ArrayList;
import java.util.List;

public class SnsUserArrivalMapper {

    private VaccinationScheduleMapper vaccineSchedule;



    public SnsUserArrivalMapper(){
        this.vaccineSchedule = new VaccinationScheduleMapper();
    }


    /**
     * Method maps from model to DTO
     * @param model the model to map
     * @return the SnsUserArrivalDTO
     */
    public SnsUserArrivalDTO toDTO(SnsUserArrival model)
    {
        return new SnsUserArrivalDTO(
                model.getArrivalDate(),
                model.getSnsUserNumber().getNumber(),
                this.vaccineSchedule.toDTO(model.getVaccineSchedule())
        );

    }

    public List<SnsUserArrivalDTO> toDTO(List<SnsUserArrival> snsUserArrivalsList)
    {
        List<SnsUserArrivalDTO> snsUserArrivalsDTOList = new ArrayList<>();

        for (SnsUserArrival arrival : snsUserArrivalsList)
        {
            snsUserArrivalsDTOList.add(this.toDTO(arrival));
        }
        return snsUserArrivalsDTOList;
    }


    /**
     * Method maps from DTO to Model
     * @param dto the DTO to map
     * @return SnsUserArrival
     */
    public SnsUserArrival toModel(SnsUserArrivalDTO dto)
    {
        return new SnsUserArrival(
                dto.getArrivalDate(),
                new SnsNumber(dto.getSnsNumber()),
                this.vaccineSchedule.toModel(dto.getVaccineSchedule())
        );
    }



}
