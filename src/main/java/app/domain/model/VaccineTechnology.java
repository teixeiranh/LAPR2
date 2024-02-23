package app.domain.model;

public enum VaccineTechnology {

    LA("Live-attenuated"),
    IV("Inactivated vaccine"),
    SV("Subunit vaccine"),
    TV("Toxoid vaccine"),
    VV("Viral vector vaccine"),
    MRV("Messenger RNA vaccine");

    private String description;

    VaccineTechnology(String description)
    {
            this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static void getAll(){
        for (VaccineTechnology technology : VaccineTechnology.values()) {
            System.out.println(technology + " - " + technology.getDescription());
        }
    }

}
