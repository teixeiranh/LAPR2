package app.controller;

import app.domain.model.AdverseReaction;
import app.domain.model.Company;
import app.domain.model.SnsUser;
import app.domain.store.AdverseReactionStore;
import app.domain.store.SnsUserStore;
import app.domain.store.VaccineStore;
import app.mappers.SnsUserMapper;
import app.mappers.dto.AdverseReactionDTO;
import app.mappers.dto.SnsUserDTO;
import app.mappers.dto.VaccineDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Class responsible for managing the flow of the SNS User registration process
 */
public class RecordAdverseReactionController {

    private App app;
    private Company company;
    private SnsUserStore snsUserStore;
    private VaccineStore vaccineStore;
    private AdverseReaction adverseReaction;
    private AdverseReactionStore adverseReactionStore;

    public RecordAdverseReactionController()
    {
        this.app = App.getInstance();
        this.company = app.getCompany();
        this.snsUserStore = this.company.getSnsUserStore();
        this.vaccineStore = this.company.getVaccineStore();
        this.adverseReactionStore = this.company.getAdverseReactionStore();
    }

    public AdverseReaction getAdverseReaction()
    {
        return adverseReaction;
    }

    public void setAdverseReaction(AdverseReaction adverseReaction)
    {
        this.adverseReaction = adverseReaction;
    }

    public SnsUserDTO getSnsUserDTOBySnsNumber(long snsNumber)
    {
        SnsUserMapper snsUserMapper = new SnsUserMapper();
        return snsUserMapper.toDTO(this.getSnsUserInfo(snsNumber).get());
    }

    public boolean createAdverseReaction(AdverseReactionDTO adverseReactionDTO)
    {
        this.adverseReaction = this.adverseReactionStore.createAdverseReaction(adverseReactionDTO);
        return this.adverseReaction == null;
    }

    public boolean addAdverseReaction()
    {
        this.adverseReactionStore.addAdverseReaction(this.adverseReaction);
        System.out.println(this.adverseReactionStore.getAllAdverseReactionBySnsNumber(this.adverseReaction.getSnsUser().getSnsNumber().getNumber()));
        return true;
    }

    public List<VaccineDTO> getAllVaccinesDTO()
    {
        return this.vaccineStore.getAllVaccinesDTO();
    }

    private Optional<SnsUser> getSnsUserInfo(Long snsNumber)
    {
        return snsUserStore.getSnsUserBySnsNumber(snsNumber);
    }

    public VaccineDTO getVaccineDTO(String vaccine)
    {
        for (VaccineDTO vaccineDTO : this.getAllVaccinesDTO())
        {
            if (vaccineDTO.getVaccineName().equals(vaccine))
            {
                return vaccineDTO;
            }
        }
        return null;
    }

    public Set<String> getAllVaccinesNames()
    {
        Set<String> vaccinesNames = new HashSet<>();
        for (VaccineDTO vaccine : this.getAllVaccinesDTO())
        {
            vaccinesNames.add(vaccine.getVaccineName());
        }
        return vaccinesNames;
    }

}
