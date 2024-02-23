package app.ui.console;

import app.controller.RegisterEmployeeController;
import app.domain.model.CitizenCard;
import app.domain.model.PhoneNumber;
import app.domain.shared.Address;
import app.domain.shared.Role;
import app.mappers.AddressMapper;
import app.mappers.dto.AddressDTO;
import app.ui.console.utils.Utils;
import pt.isep.lei.esoft.auth.domain.model.Email;

public class RegisterEmployeeUI implements Runnable
{
    private RegisterEmployeeController controller;

    public RegisterEmployeeUI() {
        this.controller = new RegisterEmployeeController();
    }

    @Override
    public void run()
    {
        boolean exit = false;
        while(!exit)
        {

            boolean keepRegistering = true;
            while(keepRegistering)
            {
                // Show list of available roles and request selection
                System.out.println("### Register new Employee");
                Role empRole = (Role) Utils.showAndSelectOne(controller.getRoleList(), "Select employee role:");
                if (empRole != null)
                {

                    // Request employee name
                    String name = Utils.readLineFromConsole("Employee name: ");

                    // Request email
                    Email empEmail = readEmailFromConsole();

                    // Request phone number
                    PhoneNumber empPhoneNumber = readPhoneNumberFromConsole();

                    // Request citizen card number
                    CitizenCard empCitizenCard = readCitizenCardFromConsole();

                    // Request address
                    Address empAddress = readAddressFromConsole();

                    // Create employee
                    boolean success = controller.createEmployee(name, empEmail, empAddress, empPhoneNumber, empCitizenCard, empRole);

                    if (success)
                    {
                        // Show data and request user confirmation
                        controller.printEmployeeData();

                        if (Utils.confirm("Is the employee data correct? "))
                        {
                            // Save employee
                            if (!controller.saveEmployee())
                            {
                                System.out.println("Unable to save employee registry. Please try again.");
                            }
                            else
                            {
                                System.out.println("Employee registered successfully!");
                            }
                        }
                        else
                        {
                            System.out.println("Aborting employee registering process...");
                        }

                    }
                    else
                    {
                        System.out.println("[Error] Employee already exists!");
                    }

                    keepRegistering = Utils.confirm("Do you want to register another employee?(s/n)");
                }
                else
                {
                    System.out.println();
                    System.out.println("[System]: Employee registry cancelled.");
                    keepRegistering = false;
                }

            }

            exit = Utils.confirm("Exit to Main menu?(s/n)");

        }

    }

    /**
     * Method to read and validate the email provided by the user
     *
     * @return  Email instance
     */
    private Email readEmailFromConsole()
    {
        Email empEmail = null;
        boolean validInput = false;
        while (!validInput)
        {
            try
            {
                String inputEmail = Utils.readLineFromConsole("Employee email: ");
                empEmail = new Email(inputEmail);
                validInput = true;
            }
            catch(IllegalArgumentException e)
            {
                System.out.println("Invalid email address, please try again!");
            }
        }

        return empEmail;
    }

    /**
     * Method to read and validate the employee address provided by the user
     *
     * @return  Address instance
     */
    private Address readAddressFromConsole()
    {
        Address empAddress = null;
        boolean validInput = false;
        while (!validInput)
        {
            try
            {
                AddressDTO inputAddress = AddressUI.readAddressFromConsole();
                AddressMapper addressMapper = new AddressMapper();
                empAddress = addressMapper.toModel(inputAddress);
                validInput = true;
            }
            catch(IllegalArgumentException e)
            {
                System.out.println("Invalid address, please try again!");
            }
        }

        return empAddress;

    }

    /**
     * Method to read and validate the employee citizen card number provided by the user
     *
     * @return  CitizenCard instance
     */
    private CitizenCard readCitizenCardFromConsole()
    {
        CitizenCard citizenCard = null;
        boolean validInput = false;
        while (!validInput)
        {
            try
            {
                String inputCitizenCardNumber = Utils.readLineFromConsole("Citizen card number (e.g. 000000000ZZ4): ");
                citizenCard = new CitizenCard(inputCitizenCardNumber);
                validInput = true;
            }
            catch(IllegalArgumentException e)
            {
                System.out.println("Invalid citizen card number, please try again!");
            }
        }
        return citizenCard;
    }

    /**
     * Method to read and validate the employee phone number provided by the user
     *
     * @return  PhoneNumber instance
     */
    private PhoneNumber readPhoneNumberFromConsole()
    {
        PhoneNumber phoneNumber = null;
        boolean validInput = false;
        while (!validInput)
        {
            try
            {
                long inputPhoneNumber = Utils.readLongFromConsole("Employee phone number: ");
                phoneNumber = new PhoneNumber(inputPhoneNumber);
                validInput = true;
            }
            catch(IllegalArgumentException e)
            {
                System.out.println("Invalid phone number, please try again!");
            }

        }
        return phoneNumber;
    }

}
