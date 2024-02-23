package app.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Vaccination schedule to register vaccination appointments.
 */
public class VaccinationSchedule implements Serializable
{
    private static final long serialVersionUID = -7234604135542964361L;
    private static int vaccinationScheduleCount = 0;
    private static final String ID_TEMPLATE = "VAC-SCHEDULE-";

    /**
     * Identification for the vaccination schedule
     */
    private String id;

    /**
     * SNS User number
     */
    private SnsUser snsUser;

    /**
     * Date for the vaccination schedule
     */
    private LocalDateTime date;

    public void setVaccine(Vaccine vaccine)
    {
        this.vaccine = vaccine;
    }

    /**
     * Vaccine administred or to be adminstred in the appointment
     */
    private Vaccine vaccine;

    /**
     * Vaccine Type pretended
     */
    private VaccineType vaccineType;

    /**
     * Vaccination center pretended
     */
    private VaccinationCenter vaccinationCenter;

    /**
     * State of the vaccination schedule
     * <p>
     * It has 3 different states: CREATED, IN_PROGRESS, FINALIZED:
     * - CREATED - first state, after schedulint it with the app or with a recepcionist;
     * - IN_PROGRESS - as soon as the user arrives at the Vaccination Center;
     * - FINALIZED - as soon as the user lefts the Vaccination Center with the vaccine taken.
     */
    private VaccinationScheduleState state;

    /**
     * Construtor for the Vaccination Schedule
     *
     * @param snsUser
     * @param date
     * @param vaccineType
     * @param vaccinationCenter
     */
    public VaccinationSchedule(SnsUser snsUser,
                               LocalDateTime date,
                               VaccineType vaccineType,
                               VaccinationCenter vaccinationCenter)
    {
        this.snsUser = snsUser;
        this.date = date;
        this.vaccineType = vaccineType;
        this.vaccinationCenter = vaccinationCenter;
        this.state = VaccinationScheduleState.CREATED;
        vaccinationScheduleCount++;
        this.id = String.format("%s%d", ID_TEMPLATE, vaccinationScheduleCount);
    }
    public VaccinationSchedule(SnsUser snsUser,
                               LocalDateTime date,
                               VaccineType vaccineType,
                               VaccinationCenter vaccinationCenter,
                               Vaccine vaccine)
    {
        this.snsUser = snsUser;
        this.date = date;
        this.vaccineType = vaccineType;
        this.vaccinationCenter = vaccinationCenter;
        this.state = VaccinationScheduleState.CREATED;
        vaccinationScheduleCount++;
        this.id = String.format("%s%d", ID_TEMPLATE, vaccinationScheduleCount);
        this.vaccine = vaccine;
    }

    public SnsUser getSnsUser()
    {
        return snsUser;
    }

    public LocalDateTime getDate()
    {
        return date;
    }

    public VaccineType getVaccineType()
    {
        return vaccineType;
    }

    public VaccinationCenter getVaccinationCenter()
    {
        return vaccinationCenter;
    }

    public VaccinationScheduleState getState()
    {
        return state;
    }

    public void setState(VaccinationScheduleState state)
    {
        this.state = state;
    }

    public Vaccine getVaccine()
    {
        return vaccine;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "VaccinationSchedule - " +
                "snsNumber=" + snsUser.getSnsNumber().getNumber() +
                ", date=" + date +
                ", vaccineType=" + vaccineType.getDescription() +
                ", vaccinationCenter=" + vaccinationCenter.getVaccinationCenterName() +
                ", state=" + state.toString();
    }

}
