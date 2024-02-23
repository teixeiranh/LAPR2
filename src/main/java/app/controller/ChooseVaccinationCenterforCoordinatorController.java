package app.controller;

import app.domain.model.Company;
import app.domain.model.Employee;
import app.domain.model.VaccinationCenter;
import app.domain.store.EmployeeStore;
import app.domain.store.VaccinationCenterStore;
import app.mappers.dto.VaccinationCenterDTO;
import pt.isep.lei.esoft.auth.AuthFacade;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ChooseVaccinationCenterforCoordinatorController
{
    private  App app;
    private  Company company;
    private  AuthFacade authFacade;
    private VaccinationCenterStore vaccinationCenterStore;
    private EmployeeStore employeeStore;

    public ChooseVaccinationCenterforCoordinatorController()
    {
        this.app = App.getInstance();
        this.company = app.getCompany();
        this.authFacade = this.company.getAuthFacade();
        this.vaccinationCenterStore = this.company.getVaccinationCenterStore();
        this.employeeStore = this.company.getEmployeeStore();
    }

    /**
     * To get all the vaccination centers registered within the system
     * @return
     */
    public List<VaccinationCenterDTO> getAllVaccinationCenters(){

        return this.vaccinationCenterStore.getAllDTO();
    }

    public List<VaccinationCenter> getVaccinationCenters(){
        Set<VaccinationCenter> vaccinationCenterSet = this.vaccinationCenterStore.getAllVaccinationCenter();
        List<VaccinationCenter> vaccinationCenterList = new ArrayList<>(vaccinationCenterSet);
        return vaccinationCenterList;
    }


    public Optional<Employee> getEmployeeById(String currentCoordinatorId)
    {
        return this.employeeStore.getEmployeeByEmail(currentCoordinatorId);
    }
}
