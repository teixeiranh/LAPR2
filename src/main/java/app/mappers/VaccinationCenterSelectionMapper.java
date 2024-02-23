package app.mappers;

import app.domain.model.VaccinationCenter;
import app.mappers.dto.VaccinationCenterSelectionDTO;

import java.util.ArrayList;
import java.util.List;

public class VaccinationCenterSelectionMapper
{
    private AddressMapper addressMapper;

    public VaccinationCenterSelectionMapper()
    {
        this.addressMapper = new AddressMapper();
    }

    public VaccinationCenterSelectionDTO toDTO(VaccinationCenter vc)
    {
        return new VaccinationCenterSelectionDTO(vc.getVaccinationCenterName(), addressMapper.toDTO(vc.getAddress()), vc.getVaccinationCenterEmailAddress());
    }

    public List<VaccinationCenterSelectionDTO> toDTO(List<VaccinationCenter> vcList)
    {
        List<VaccinationCenterSelectionDTO> vcSelectionDTOList = new ArrayList<>();

        for (VaccinationCenter vc : vcList)
        {
            if (vc != null)
            {
                vcSelectionDTOList.add(toDTO(vc));
            }
        }

        return vcSelectionDTOList;
    }

}
