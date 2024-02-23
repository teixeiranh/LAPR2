package app.ui.console;

import app.controller.RegisterSnsUserController;
import app.domain.shared.Gender;
import app.mappers.dto.AddressDTO;
import app.mappers.dto.SnsUserDTO;
import app.ui.console.utils.Utils;

import java.util.Date;
import java.util.List;

public class RegisterSnsUserUI implements Runnable {

    private static final String DATE_FORMAT = "DD-MM-YYYY";

    private final RegisterSnsUserController controller;

    public RegisterSnsUserUI()
    {
        this.controller = new RegisterSnsUserController();
    }

    @Override
    public void run()
    {
        System.out.println("\nPlease insert the user information below");

        boolean endSnsUserRegistration;

        do
        {
            String name = Utils.readLineFromConsole("Name");
            String email = Utils.readLineFromConsole("Email");
            Gender gender = (Gender) Utils.showAndSelectOneFromEnum(List.of(Gender.values()), "\nGender (optional)");
            Date birthDate = Utils.readDateFromConsole("Birth date (" + DATE_FORMAT + ")");
            long snsNumber = Utils.readLongFromConsole("SNS number");
            long phoneNumber = Utils.readLongFromConsole("Phone number");
            String citizenCardNumber = Utils.readLineFromConsole("Citizen card number (DDDDDDDD)");
            AddressDTO address = AddressUI.readAddressFromConsole();

            try
            {
                SnsUserDTO snsUserDTO = new SnsUserDTO(
                        name,
                        email,
                        gender,
                        birthDate,
                        snsNumber,
                        address,
                        phoneNumber,
                        citizenCardNumber);

                boolean userAlreadyExists = this.controller.checkIfUserAlreadyExists(snsUserDTO);
                endSnsUserRegistration = userAlreadyExists;

                if (userAlreadyExists)
                {
                    System.out.println("Invalid SNS User, the SNS user already exists.\n");
                } else
                {
                    endSnsUserRegistration = this.registerSnsUserUI(snsUserDTO);
                }
            } catch (IllegalArgumentException exception)
            {
                System.out.println("\n" + exception.getMessage());
                endSnsUserRegistration = this.reinputUserInformationUI();
            }

        } while (!endSnsUserRegistration);
    }

    private boolean registerSnsUserUI(SnsUserDTO snsUserDTO)
    {
        System.out.println("\n" + snsUserDTO);
        boolean confirmsUserData = Utils.confirm("Confirm SNS user information (s/n)?");
        if (confirmsUserData)
        {
            boolean registeredSuccessfully = this.controller.registerSnsUser(snsUserDTO);
            if (registeredSuccessfully)
            {
                System.out.println("SNS user registered successfully.\n");
                return true;
            } else
            {
                System.out.println("SNS user registered failed.\n");
                return this.reinputUserInformationUI();
            }
        } else
        {
            return this.reinputUserInformationUI();
        }
    }

    private boolean reinputUserInformationUI()
    {
        boolean shouldReinput = Utils.confirm("Reinput the SNS user information (s/n)?");
        return !shouldReinput;
    }

}
