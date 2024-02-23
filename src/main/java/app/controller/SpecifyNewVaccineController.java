package app.controller;

import app.domain.model.*;
import app.domain.store.VaccineStore;
import app.mappers.dto.VaccineDTO;
import pt.isep.lei.esoft.auth.AuthFacade;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;
import app.domain.shared.Constants;

import java.util.List;
import java.util.Set;

/**
 * Controller class responsible for the delegation and management of input data.
 * This class is the connector of UI and Domain.
 * ISEP'S Integrative Project 2021-2022, second semester.
 *
 * @author nuno.teixeira <1210860@isep.ipp.pt>
 */
public class SpecifyNewVaccineController
{
   private App app;
   private Company company;
   private AuthFacade authFacade;
   private Vaccine vc;

   /**
    * Default constructor for the Controller.
    */
   public SpecifyNewVaccineController()
   {
      this.app = App.getInstance();
      this.company = app.getCompany();
      this.authFacade = company.getAuthFacade();
   }

   /**
    * Constructor for the Controller.
    *
    * @param company company
    */
   public SpecifyNewVaccineController(Company company)
   {
      this.company = company;
   }

   /**
    * Controller's create vaccine method.
    *
    * @param vaccineType              vaccine type
    * @param vaccineName              vaccine identification name
    * @param vaccineManufacturer      vaccina manufacturer
    * @param vaccineNumberOfAgeGroups quantity of age groups for the vaccine administration process
    * @param minAge                   minimum age of an age group
    * @param maxAge                   maximum age of an age group
    * @param numberOfDoses            number of doses for each age group
    * @param dosage                   dosage per take for each age group
    * @param timeElapsed              time elapsed between vaccine takes
    * @return true if the vaccine creation is successful
    */
   public boolean createVaccine(VaccineType vaccineType, String vaccineName, String vaccineManufacturer,
                                int vaccineNumberOfAgeGroups, List<Integer> minAge, List<Integer> maxAge,
                                List<Integer> numberOfDoses, List<Double> dosage, List<Integer> timeElapsed)
   {

      UserRoleDTO role = this.authFacade.getCurrentUserSession().getUserRoles().get(0);


      if (role.getDescription() != Constants.ROLE_ADMIN)
      {
         System.out.println("No permission to execute this operation!");
      } else
      {
         this.vc = this.company.createVaccine(vaccineType, vaccineName, vaccineManufacturer, vaccineNumberOfAgeGroups, minAge,
                 maxAge, numberOfDoses, dosage, timeElapsed);

         System.out.println(vc.toString());
      }
      return this.company.saveVaccine(this.vc);
   }


   /**
    * Controller's create vaccine method.
    *
    * @param vaccineType              vaccine type
    * @param vaccineName              vaccine identification name
    * @param vaccineManufacturer      vaccina manufacturer
    * @param vaccineNumberOfAgeGroups quantity of age groups for the vaccine administration process
    * @param minAge                   minimum age of an age group
    * @param maxAge                   maximum age of an age group
    * @param numberOfDoses            number of doses for each age group
    * @param dosage                   dosage per take for each age group
    * @param timeElapsed              time elapsed between vaccine takes
    * @return true if the vaccine creation is successful
    */
   public boolean createVaccine(VaccineDTO vaccineDTO)
   {

      UserRoleDTO role = this.authFacade.getCurrentUserSession().getUserRoles().get(0);


      if (role.getDescription() != Constants.ROLE_ADMIN)
      {
         System.out.println("No permission to execute this operation!");
      } else
      {
         this.vc = this.company.createVaccine(vaccineDTO);

         System.out.println(vc.toString());
      }
      return this.company.saveVaccine(this.vc);
   }







   public Set<VaccineType> getListOfVaccineTypes()
   {
      return this.company.getVaccineTypeStore().getAll();
   }

    public List<Vaccine> getListOfAllVacinesDefined()
    {
       return this.company.getVaccineStore().getAll();
    }
}
