package app.controller;

import app.domain.model.Company;
import app.domain.shared.Constants;
import app.domain.store.VaccineTypeStore;
import app.mappers.dto.VaccineTypeDTO;
import pt.isep.lei.esoft.auth.AuthFacade;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;


public class SpecifyNewVaccineTypeController {

    private final App app;
    private final Company company;
    private final AuthFacade authFacade;
    private final VaccineTypeStore vaccineTypeStore;


    public SpecifyNewVaccineTypeController()
    {
        this.app = App.getInstance();
        this.company = app.getCompany();
        this.authFacade = company.getAuthFacade();
        this.vaccineTypeStore = this.company.vaccineTypeStore();
    }

    /**
     * Check if Vaccine Type exists already
     *
     * @param vaccineTypeDTO
     *
     * @return boolean, true if Vaccine Type already exists
     */
    public boolean checkIfVaccineTypeAlreadyExists(VaccineTypeDTO vaccineTypeDTO)
    {
        checkAdministratorRights();
            boolean vaccineTypeExists;
            try
            {
                vaccineTypeExists = this.vaccineTypeStore.existsVaccineType(vaccineTypeDTO);
            } catch (Exception e)
            {
                System.out.println(e.getMessage());
                vaccineTypeExists = true;
            }
            return vaccineTypeExists;
    }

    /**
     * Create a new Vaccine Type
     *
     * @param vaccineTypeDTO
     *
     * @return boolean
     */
    public boolean specifyNewVaccineType(VaccineTypeDTO vaccineTypeDTO)
    {
        checkAdministratorRights();
        this.company.specifyNewVaccineType(vaccineTypeDTO);
        System.out.println("Created with success");
        return true;
    }


    /**
     * Check user Role
     *
     * @return true or false based on user role
     */
    public boolean checkAdministratorRights(){
        UserRoleDTO role = this.authFacade.getCurrentUserSession().getUserRoles().get(0);
        if (role.getDescription() != Constants.ROLE_ADMIN){
            System.out.println("No permission to execute this operation");
            return false;
        }

        return true;
    }


}

