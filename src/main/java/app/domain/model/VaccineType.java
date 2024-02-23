package app.domain.model;

import java.io.Serializable;

public class VaccineType implements Serializable
{

    private static final long serialVersionUID = -4355299913412356459L;

    private String code;
    private String description;
    private VaccineTechnology technology;

    /**
     * Default constructor for the new vaccine.
     **/
    public VaccineType() {

    }

    /**
     * Constructor for the new vaccine .
     * @param code code name of the vaccine type
     * @param description brand or company responsible for the production of the vaccine
     * @param technology type of technology used in the vaccine
     */
    public VaccineType(String code, String description, VaccineTechnology technology) {
        this.validateNonNullAttribute("code", code);
        this.validateNoAlphaNumericCode("code", code);
        this.validateNonNullAttribute("description", description);
        this.validateNonNullAttribute("VaccineTechnology", technology);

        this.code = code;
        this.description = description;
        this.technology = technology;
    }

    /**
     * Getter do code
     *
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * Getter do description
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter do getTechnology
     *
     * @return technology
     */
    public VaccineTechnology getTechnology() {
        return technology;
    }

    /**
     * Setter do code
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Setter do description
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Setter do technology
     *
     * @param technology
     */
    public void setTechnology( VaccineTechnology technology ) { this.technology = technology; }

    /**
     * método toString que faz override do metodo toString Object
     * @return string com o code e designatio e rendimento OR
     */
    @Override
    public String toString() {
        return String.format("The code of the Vaccine is %s the description is %s and the technology is %s",
                code, description, technology);
    }

    public boolean hasCode(String code) {
        return this.code.equals(code);
    }

    private void validateNonNullAttribute(String attribute, Object attributeValue)
    {
        if (attributeValue == null)
        {
            throw new IllegalArgumentException("User cannot have a null " + attribute + ".");
        }
    }

    static public void validateNonNullAttribute(String attribute, String attributeValue)
    {
        if (attributeValue == null)
        {
            throw new IllegalArgumentException("User cannot have the attribute " + attribute + " as null");
        }

        if (attributeValue.isBlank())
        {
            throw new IllegalArgumentException("User cannot have the attribute " + attribute + " as blank.");
        }
    }

    static public void validateNoAlphaNumericCode(String attribute, String attributeValue)
    {
        if (attributeValue.matches("^(\\d*[a-zA-Z]\\d*)+$") != true)
        {
            throw new IllegalArgumentException("User cannot have the attribute " + attribute + " containing only numbers or only letters.");
        }
    }

}
