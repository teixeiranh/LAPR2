package app.controller;

import app.domain.model.Company;
import app.domain.model.SnsUser;
import app.domain.shared.Constants;
import app.domain.shared.PasswordGenerator;
import app.domain.store.SnsUserStore;
import app.domain.store.exception.SnsUserAlreadyExistsInStoreWithAttributeException;
import app.mappers.dto.SnsUserDTO;
import pt.isep.lei.esoft.auth.AuthFacade;

/**
 * Class responsible for managing the flow of the SNS User registration process
 */
public class RegisterSnsUserController {

    private final App app;
    private final Company company;
//    private final AuthFacade authFacade;
    private final SnsUserStore snsUserStore;
//    private final PasswordGenerator passwordGenerator;

    public RegisterSnsUserController()
    {
        this.app = App.getInstance();
        this.company = app.getCompany();
//        this.authFacade = this.company.getAuthFacade();
        this.snsUserStore = this.company.getSnsUserStore();
//        this.passwordGenerator = new PasswordGenerator();
    }



    /**
     * Validates if the inputed user information is valid:
     * @param snsUserDTO encapsulates the SNS user inputed information
     * @return true if all the verifications passes, otherwise, returns false
     */
    public boolean checkIfUserAlreadyExists(SnsUserDTO snsUserDTO) {
        return this.snsUserStore.checkIfUserAlreadyExists(snsUserDTO);
    }


//    public boolean checkIfUserAlreadyExists(SnsUserDTO snsUserDTO)
//    {
//        boolean userExists = this.authFacade.existsUser(snsUserDTO.getEmail());
//        if (!userExists)
//        {
//            boolean snsUserExists;
//            try
//            {
//                snsUserExists = this.snsUserStore.existsSnsUser(snsUserDTO);
//            } catch (SnsUserAlreadyExistsInStoreWithAttributeException exception)
//            {
//                System.out.println(exception.getMessage());
//                snsUserExists = true;
//            }
//
//            return snsUserExists;
//        } else
//        {
//            return false;
//        }
//    }

    /**
     * Registers a new SNS user in the system and adds it to the SNS user store
     *
     * @param snsUserDTO encapsulates the SNS user inputed information
     * @return true if registration is successful, otherwise, returns false
     */
    public boolean registerSnsUser(SnsUserDTO snsUserDTO) {
        return this.snsUserStore.registerSnsUser(snsUserDTO);
    }
//    public boolean registerSnsUser(SnsUserDTO snsUserDTO)
//    {
//        String password = this.passwordGenerator.generatePassword();
//
//        System.out.println("Generated password: " + password);
//
//        //add system user
//        boolean userAddedSuccessfully = this.authFacade.addUserWithRole(snsUserDTO.getName(), snsUserDTO.getEmail(), password, Constants.ROLE_SNS_USER);
//        if (userAddedSuccessfully)
//        {
//            SnsUser snsUser = this.snsUserStore.createSnsUser(snsUserDTO);
//            boolean addedSuccessfully = this.snsUserStore.addSnsUser(snsUser);
//
//            //this.snsUserStore.notifyUser(snsUserDTO.getEmail(), password);
//
//            return addedSuccessfully;
//        } else
//        {
//            return false;
//        }
//    }

}
