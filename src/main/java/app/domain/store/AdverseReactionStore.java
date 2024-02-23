package app.domain.store;

import app.domain.model.AdverseReaction;
import app.mappers.AdverseReactionMapper;
import app.mappers.SnsUserMapper;
import app.mappers.dto.AdverseReactionDTO;
import app.mappers.dto.SnsUserAdverseReactionsDTO;
import app.mappers.dto.SnsUserDTO;
import app.serialization.SerializableStore;
import app.serialization.SerializationUtil;

import java.util.*;

public class AdverseReactionStore implements SerializableStore<AdverseReaction> {

    public static final String DEFAULT_SERIALIZATION_FILE = "adverse_reactions.dat";
    private List<AdverseReaction> adverseReactions;
    private AdverseReactionMapper adverseReactionMapper;

    private final String serializationFile;
    private final SerializationUtil<AdverseReaction> serializationUtil;

    public AdverseReactionStore()
    {
        this.serializationFile = DEFAULT_SERIALIZATION_FILE;
        this.adverseReactionMapper = new AdverseReactionMapper();
        this.serializationUtil = new SerializationUtil<>();
        this.adverseReactions = new ArrayList<>(this.loadData());
    }

    public AdverseReactionStore(String serializationFile)
    {
        this.serializationFile = serializationFile;
        this.adverseReactionMapper = new AdverseReactionMapper();
        this.serializationUtil = new SerializationUtil<>();
        this.adverseReactions = new ArrayList<>(this.loadData());
    }

    public AdverseReactionStore(String serializationFile, String baseSerializationFolder)
    {
        this.serializationFile = serializationFile;
        this.adverseReactionMapper = new AdverseReactionMapper();
        this.serializationUtil = new SerializationUtil<>(baseSerializationFolder);
        this.adverseReactions = new ArrayList<>(this.loadData());
    }

    public AdverseReaction createAdverseReaction(AdverseReactionDTO adverseReactionDTO)
    {
        return this.adverseReactionMapper.toModel(adverseReactionDTO);
    }

    public boolean addAdverseReaction(AdverseReaction adverseReaction)
    {
        boolean addedSuccessfully = adverseReaction != null
                && this.adverseReactions.add(adverseReaction);

        if (addedSuccessfully)
        {
            this.saveData();
        }

        return addedSuccessfully;
    }

    public Optional<SnsUserAdverseReactionsDTO> getAllAdverseReactionBySnsNumber(Long snsNumber)
    {
        List<AdverseReaction> snsUserAdverseReactionList = new ArrayList<>();
        for (AdverseReaction adverseReaction : this.adverseReactions)
        {
            if (adverseReaction.getSnsUser().getSnsNumber().getNumber() == snsNumber)
            {
                snsUserAdverseReactionList.add(adverseReaction);
            }
        }

        if (snsUserAdverseReactionList.isEmpty())
        {
            return Optional.empty();
        }

        return Optional.of(this.mapAdverseReactionsToSnsUserAdverseReactionsDTO(snsUserAdverseReactionList));
    }

    public Optional<SnsUserAdverseReactionsDTO> getAllAdverseReactionBySnsNumberAndVaccineType(Long snsNumber,
                                                                                               String vaccineTypeCode)
    {
        List<AdverseReaction> snsUserAdverseReactionList = new ArrayList<>();
        for (AdverseReaction adverseReaction : this.adverseReactions)
        {
            if (adverseReaction.getSnsUser().getSnsNumber().getNumber() == snsNumber
                    && adverseReaction.getVaccine().getVaccineType().getCode().equals(vaccineTypeCode))
            {
                snsUserAdverseReactionList.add(adverseReaction);
            }
        }

        if (snsUserAdverseReactionList.isEmpty())
        {
            return Optional.empty();
        }

        return Optional.of(this.mapAdverseReactionsToSnsUserAdverseReactionsDTO(snsUserAdverseReactionList));
    }

    public Optional<SnsUserAdverseReactionsDTO> getAllAdverseReactionBySnsNumberAndVaccineName(Long snsNumber,
                                                                                               String vaccineName)
    {
        List<AdverseReaction> snsUserAdverseReactionList = new ArrayList<>();
        for (AdverseReaction adverseReaction : this.adverseReactions)
        {
            if (adverseReaction.getSnsUser().getSnsNumber().getNumber() == snsNumber
                    && adverseReaction.getVaccine().getVaccineName().equals(vaccineName))
            {
                snsUserAdverseReactionList.add(adverseReaction);
            }
        }

        if (snsUserAdverseReactionList.isEmpty())
        {
            return Optional.empty();
        }

        return Optional.of(this.mapAdverseReactionsToSnsUserAdverseReactionsDTO(snsUserAdverseReactionList));
    }

    public Optional<SnsUserAdverseReactionsDTO> getAllAdverseReactionBySnsNumberAndVaccineTypeAndVaccineName(Long snsNumber,
                                                                                                             String vaccineTypeCode,
                                                                                                             String vaccineName)
    {
        List<AdverseReaction> snsUserAdverseReactionList = new ArrayList<>();
        for (AdverseReaction adverseReaction : this.adverseReactions)
        {
            if (adverseReaction.getSnsUser().getSnsNumber().getNumber() == snsNumber
                    && adverseReaction.getVaccine().getVaccineType().getCode().equals(vaccineTypeCode)
                    && adverseReaction.getVaccine().getVaccineName().equals(vaccineName))
            {
                snsUserAdverseReactionList.add(adverseReaction);
            }
        }

        if (snsUserAdverseReactionList.isEmpty())
        {
            return Optional.empty();
        }

        return Optional.of(this.mapAdverseReactionsToSnsUserAdverseReactionsDTO(snsUserAdverseReactionList));
    }

    private SnsUserAdverseReactionsDTO mapAdverseReactionsToSnsUserAdverseReactionsDTO(List<AdverseReaction> adverseReactions)
    {
        SnsUserDTO snsUser = null;
        if (!adverseReactions.isEmpty())
        {
            snsUser = new SnsUserMapper().toDTO(adverseReactions.get(0).getSnsUser());
        }

        List<AdverseReactionDTO> adverseReactionsDTO = new ArrayList<>();
        for (AdverseReaction adverseReaction : adverseReactions)
        {
            adverseReactionsDTO.add(this.adverseReactionMapper.toDTO(adverseReaction));
        }

        return new SnsUserAdverseReactionsDTO(snsUser, adverseReactionsDTO);
    }

    @Override
    public Set<AdverseReaction> dataToSave()
    {
        return new HashSet<>(this.adverseReactions);
    }

    @Override
    public String serializationFileName()
    {
        return this.serializationFile;
    }

    @Override
    public SerializationUtil<AdverseReaction> serializationUtil()
    {
        return this.serializationUtil;
    }

}
