package app.mappers.dto;

import app.domain.model.VaccineTechnology;
import app.ui.console.utils.AttributesValidationUtils;


public class VaccineTypeDTO {

    private String code;
    private String description;
    private VaccineTechnology technology;

    public VaccineTypeDTO(String code, String description, VaccineTechnology technology) {

        AttributesValidationUtils.validateNonNullAttribute("code", code);
        AttributesValidationUtils.validateNonNullAttribute("description", description);
        AttributesValidationUtils.validateNonNullAttribute("technology", technology);

        this.validateNonNullAndLengthAndAlphaNumericCode(code);

        this.code = code;
        this.description = description;
        this.technology = technology;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public VaccineTechnology getTechnology() {
        return technology;
    }

    @Override
    public String toString()
    {
        return "Vaccine Type information:\n" +
                "- Code: " + code + '\n' +
                "- Description: " + description + '\n' +
                "- Technology: " + technology ;
    }

    private void validateNonNullAndLengthAndAlphaNumericCode(String code)
    {
        if (code == null)
        {
            throw new IllegalArgumentException("Code cannot be null.");
        }
        if (code.length() != 5)
        {
            throw new IllegalArgumentException("Code size can´t extend 5 characters.");
        }

        if (code.matches("^(\\d*[a-zA-Z]\\d*)+$") != true){
            throw new IllegalArgumentException("Code must contain numbers and letters.");
        }
     }
}
