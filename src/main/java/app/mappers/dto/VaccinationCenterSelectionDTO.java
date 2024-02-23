package app.mappers.dto;

import pt.isep.lei.esoft.auth.domain.model.Email;

public class VaccinationCenterSelectionDTO
{
    private String name;
    private AddressDTO addressDTO;
    private Email email;

    public VaccinationCenterSelectionDTO(String name, AddressDTO addressDTO, Email email)
    {
        this.name = name;
        this.addressDTO = addressDTO;
        this.email = email;
    }

    public String getName()
    {
        return name;
    }

    public AddressDTO getAddressDTO()
    {
        return addressDTO;
    }

    public Email getEmail() {return email;}

    @Override
    public String toString()
    {
        return String.format("%s - %s, %s%n", name, addressDTO.getStreet(), addressDTO.getCity());
    }
}
