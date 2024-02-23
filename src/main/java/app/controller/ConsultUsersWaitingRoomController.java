package app.controller;

import app.domain.model.Company;
import app.domain.model.SnsUserArrival;
import app.domain.store.SnsUserArrivalStore;
import app.mappers.SnsUserArrivalMapper;
import app.mappers.dto.SnsUserArrivalDTO;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.List;

public class ConsultUsersWaitingRoomController
{
    private App app;
    private Company company;
    private SnsUserArrivalStore snsUserArrivalStore;
    private Email vcEmail;

    /**
     * Constructs an instance of ConsultUsersWaitingRoomController class
     *
     * @param vcEmail  email class of the selected vaccination center after the nurse logged into the system
     */
    public ConsultUsersWaitingRoomController(Email vcEmail)
    {
        this.app = App.getInstance();
        this.company = app.getCompany();
        this.snsUserArrivalStore = company.getSnsUserArrivalStore();
        this.vcEmail = vcEmail;
    }


    /**
     * Returns a list of SnsUserArrivalDTO objects
     *
     * @return  list of SnsUserArrivalDTO objects with the relevant data to show to the user
     */
    public List<SnsUserArrivalDTO> getSnsUserArrivalsList()
    {
        // Get the collection class responsible for the Users in the waiting room
        List<SnsUserArrival> arrivalsList = snsUserArrivalStore.getSnsUserArrivalsByVaccinationCenter(vcEmail.getEmail());

        // Return list of SnsUserArrivalDTO objects
        SnsUserArrivalMapper snsUserArrivalMapper = new SnsUserArrivalMapper();
        return snsUserArrivalMapper.toDTO(arrivalsList);

    }


}
