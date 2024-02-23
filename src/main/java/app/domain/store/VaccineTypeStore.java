package app.domain.store;

import app.domain.model.VaccineType;
import app.mappers.VaccineTypeMapper;
import app.mappers.dto.VaccineTypeDTO;
import app.serialization.SerializableStore;
import app.serialization.SerializationUtil;

import java.util.*;

public class VaccineTypeStore implements SerializableStore<VaccineType> {

    public static final String DEFAULT_SERIALIZATION_FILE = "vaccine_types.dat";

    private String serializationFile;
    private Set<VaccineType> vaccineTypeStore;
    private SerializationUtil<VaccineType> serializationUtil;

    public VaccineTypeStore() {
        this.serializationFile = DEFAULT_SERIALIZATION_FILE;
        this.serializationUtil = new SerializationUtil<>();
        this.vaccineTypeStore = this.loadData();
    }

    public VaccineTypeStore(String serializationFile) {
        this.serializationFile = serializationFile;
        this.serializationUtil = new SerializationUtil<>();
        this.vaccineTypeStore = this.loadData();
    }

    public VaccineTypeStore(String baseSerializationFolder,
                            String serializationFile) {
        this.serializationFile = serializationFile;
        this.serializationUtil = new SerializationUtil<>(baseSerializationFolder);
        this.vaccineTypeStore = this.loadData();
    }

    /**
     * Transform DTO into Model with Mapper
     *
     * @param vaccineTypeDTO
     * @return Model
     */
    public VaccineType createVaccineType(VaccineTypeDTO vaccineTypeDTO) {
        VaccineTypeMapper vaccineTypeMapper = new VaccineTypeMapper();
        return vaccineTypeMapper.toModel(vaccineTypeDTO);
    }

    /**
     * Add Vaccine Type to Collection in Store
     *
     * @param vaccineType
     * @return sucess of operation
     */
    public boolean addVaccineType(VaccineType vaccineType) {
        boolean addedSuccessfully = vaccineType != null
                && !this.exists(vaccineType.getCode())
                ? this.vaccineTypeStore.add(vaccineType)
                : false;


/*        if (addedSuccessfully){
            this.saveData();
        }*/


        return addedSuccessfully;
    }

    /**
     * Validates if Vaccine Type with specific code exists
     *
     * @param code
     * @return boolean
     */
    public boolean existsVaccineType(String code) {
        Optional<VaccineType> result = this.getVaccineTypeByCode(code);
        return result.isPresent();
    }

    /**
     * Validates if Vaccine Type exists
     *
     * @param vaccineTypeDTO
     * @return boolean
     */
    public boolean existsVaccineType(VaccineTypeDTO vaccineTypeDTO) {
        return this.existsVaccineType(vaccineTypeDTO.getCode());
    }

    /**
     * Get Vaccine Type with code as a parameter
     *
     * @param code
     * @return object VaccineType
     */
    public Optional<VaccineType> getVaccineTypeByCode(String code) {
        Iterator<VaccineType> iterator = this.vaccineTypeStore.iterator();

        VaccineType vaccineType;
        do {
            if (!iterator.hasNext()) {
                return Optional.empty();
            }

            vaccineType = iterator.next();
        } while (!vaccineType.hasCode(code));

        return Optional.of(vaccineType);
    }

    /**
     * @return full collection of Vaccines Types
     */
    public Set<VaccineType> getAll() {
        return Collections.unmodifiableSet(this.vaccineTypeStore);
    }

    public List<VaccineType> getVaccineType() {
        return (List<VaccineType>) getAll();
    }

    public boolean exists(String code) {
        Optional<VaccineType> result = this.getVaccineTypeByCode(code);
        return result.isPresent();
    }

    public List<VaccineTypeDTO> getAllVaccineTypesDTO() {
        List<VaccineTypeDTO> vaccineTypes = new ArrayList<>();
        VaccineTypeMapper vaccineTypeMapper = new VaccineTypeMapper();

        for (VaccineType vt : this.vaccineTypeStore) {
            vaccineTypes.add(vaccineTypeMapper.toDTO(vt));
        }

        return vaccineTypes;
    }

    @Override
    public Set<VaccineType> dataToSave() {
        return this.vaccineTypeStore;
    }

    @Override
    public String serializationFileName() {
        return this.serializationFile;
    }

    @Override
    public SerializationUtil<VaccineType> serializationUtil() {
        return this.serializationUtil;
    }

}
