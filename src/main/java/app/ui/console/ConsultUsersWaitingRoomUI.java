package app.ui.console;

import app.controller.ConsultUsersWaitingRoomController;
import app.mappers.dto.SnsUserArrivalDTO;
import app.mappers.dto.SnsUserDTO;
import app.mappers.dto.VaccinationCenterDTO;
import app.ui.console.utils.Utils;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.List;

public class ConsultUsersWaitingRoomUI implements Runnable
{

    private ConsultUsersWaitingRoomController controller;
    private VaccinationCenterDTO vaccinationCenterDTO;

    public ConsultUsersWaitingRoomUI(Email vcEmail)
    {
        this.controller = new ConsultUsersWaitingRoomController(vcEmail);
    }

    @Override
    public void run()
    {

        // Get list of SnsUsersDTO
        List<SnsUserArrivalDTO> snsUserArrivalsDTOList = this.controller.getSnsUserArrivalsList();

        if (!snsUserArrivalsDTOList.isEmpty())
        {
            printUsersWaitingRoom(snsUserArrivalsDTOList, "Users in the waiting room: ");
        }
        else
        {
            System.out.printf("%nThere are no Users in the waiting room.%n");
        }

        Utils.readLineFromConsole("Press Enter to return to Main Menu...");
    }

    /**
     * Prints a list of Users in the waiting room of the vaccination center
     *
     * @param userList  list of SnsUserArrivalDTO objects containing the time of arrival, schedule information and SnsUser
     *                  information
     * @param header  header message
     */
    public void printUsersWaitingRoom(List<SnsUserArrivalDTO> userList, String header)
    {
        System.out.println();
        System.out.println(header);
        System.out.println("---");
        int c = 1;
        for (SnsUserArrivalDTO arrival : userList)
        {
            SnsUserDTO userDTO = arrival.getVaccinationSchedule().getSnsUser();
            System.out.printf("%d. %s, %s, birth date:%s, user no: %d, phone no: %d, admission time: %s%n", c,
                    userDTO.getName(),
                    userDTO.getGender(),
                    userDTO.getBirthdate(),
                    userDTO.getSnsNumber(),
                    userDTO.getPhoneNumber(),
                    arrival.getArrivalDate());
            c++;
        }

    }
}
