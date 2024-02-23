package app.controller;

import app.domain.model.Company;
import app.domain.model.Employee;
import app.domain.model.VaccinationCenter;
import app.domain.model.VaccineType;
import app.domain.store.EmployeeStore;
import app.domain.store.VaccinationCenterStore;
import app.domain.store.VaccineTypeStore;
import app.mappers.dto.CommunityMassVaccinationCenterDTO;
import app.mappers.dto.HealthCareVaccinationCenterDTO;
import app.mappers.dto.VaccinationCenterDTO;

import java.util.ArrayList;
import java.util.List;


/**
 * Controller class responsible for the flow of tasks.
 *
 */
public class CreateVaccinationCenterController {

    private final App app;
    private final Company company;
    private final VaccinationCenterStore vaccinationCenterStore;
    private EmployeeStore employeeStore;
    private VaccineTypeStore vaccineTypeStore;

    public CreateVaccinationCenterController()
    {
        this.app = App.getInstance();
        this.company = app.getCompany();
        this.vaccinationCenterStore = this.company.getVaccinationCenterStore();
        this.employeeStore = company.getEmployeeStore();
        this.vaccineTypeStore = company.vaccineTypeStore();
    }


    /**
     * Check if Community Mass Vaccination Center exists already
     *
     * @param communityMassVaccinationCenterDTO
     *
     * @return boolean true if Community Mass Vaccination Center already exists
     */
    public boolean checkIfVaccinationCenterAlreadyExists(CommunityMassVaccinationCenterDTO communityMassVaccinationCenterDTO)
    {
        boolean vaccinationCenterExists;
        try
        {
            vaccinationCenterExists = this.vaccinationCenterStore.existsCommunityMassVaccinationCenter(communityMassVaccinationCenterDTO);
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            vaccinationCenterExists = true;
        }
        return vaccinationCenterExists;
    }



    /**
     * Check if Health Care Vaccination Center exists already
     *
     * @param healthCareVaccinationCenterDTO
     *
     * @return boolean true if Community Mass Vaccination Center already exists
     */
    public boolean checkIfVaccinationCenterAlreadyExists(HealthCareVaccinationCenterDTO healthCareVaccinationCenterDTO)
    {
        boolean vaccinationCenterExists;
        try
        {
            vaccinationCenterExists = this.vaccinationCenterStore.existsHealthCareVaccinationCenter(healthCareVaccinationCenterDTO);
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            vaccinationCenterExists = true;
        }
        return vaccinationCenterExists;
    }


    /**
     * Registers HC Vaccination Center, company delegates it
     * @param healthCareVaccinationCenterDTO encapsulates the HCVaccination Center inputed information
     * @return
     */
    public boolean registerHealthcareVaccinationCenter(HealthCareVaccinationCenterDTO healthCareVaccinationCenterDTO)
    {
        return this.company.registerHealthcareVaccinationCenter(healthCareVaccinationCenterDTO);
    }


    /**
     * Registers CM Vaccination Center, company delegates it
     * @param communityMassVaccinationCenterDTO encapsulates the CMVaccination Center inputed information
     * @return
     */
    public boolean registerCommunityMassVaccinationCenter(CommunityMassVaccinationCenterDTO communityMassVaccinationCenterDTO)
    {
        return this.company.registerCommunityMassVaccinationCenter(communityMassVaccinationCenterDTO);
    }

    public List<Employee> getEmployeeByRole(String role){
        return employeeStore.getListEmployeeByRole(role);
    }

    public List<VaccineType> getVaccineType() {

        ArrayList<VaccineType> vaccineTypesList = new ArrayList<>(this.vaccineTypeStore.getAll());
        return vaccineTypesList;
    }

    public ArrayList<VaccinationCenter> getVaccinationCenter(){
        ArrayList<VaccinationCenter> vaccinationCentersList = new ArrayList<>(this.vaccinationCenterStore.getAll());
        return vaccinationCentersList;
    }

    public ArrayList<VaccinationCenterDTO> getVaccinationCenterDTO(){
        ArrayList<VaccinationCenterDTO> vaccinationCentersDTOList = new ArrayList<>(this.vaccinationCenterStore.getAllDTO());
        return vaccinationCentersDTOList;
    }


}
