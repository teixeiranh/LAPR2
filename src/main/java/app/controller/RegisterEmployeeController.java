package app.controller;

import app.domain.model.*;
import app.domain.shared.Address;
import app.domain.shared.Role;
import app.domain.store.EmployeeStore;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.List;

public class RegisterEmployeeController
{

    private EmployeeStore empStore;
    private Employee emp;

    private App app;
    private Company company;


    public RegisterEmployeeController()
    {
        this.app = App.getInstance();
        this.company = app.getCompany();
        this.empStore = company.getEmployeeStore();
    }


    public boolean createEmployee(String name, Email email, Address address, PhoneNumber phoneNumber, CitizenCard citizenCardNumber, Role role)
    {
        this.emp = empStore.createEmployee(name, email, address, phoneNumber, citizenCardNumber, role);
        return !empStore.existsEmployee(emp);
    }

    public void printEmployeeData()
    {
        System.out.println();
        System.out.println(emp);
    }

    public boolean saveEmployee()
    {
        return empStore.saveEmployee(emp);
    }

    public List<Role> getRoleList()
    {
        return empStore.getRoleList();
    }

}
