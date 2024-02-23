package app.mappers.dto;

import app.domain.model.VaccineType;
import app.ui.console.utils.AttributesValidationUtils;

import java.util.List;

public class VaccineDTO {
        private VaccineType vaccineType;
        private String vaccineName;
        private String vaccineManufacturer;

        private int numberOfAgeGroups;
        private List<Integer> minAge;
        private List<Integer> maxAge;
        private List<Integer> numberOfDoses;
        private List<Double> dosage;
        private List<Integer> timeElapsed;

//    private AdministrationProcess administrationProcess;

        public VaccineDTO(VaccineType vaccineType,
                          String vaccineName,
                          String vaccineManufacturer,
                          int numberOfAgeGroups,
                          List<Integer> minAge,
                          List<Integer> maxAge,
                          List<Integer> numberOfDoses,
                          List<Double> dosage,
                          List<Integer> timeElapsed)
        {

            AttributesValidationUtils.validateNonNullAttribute("type",vaccineType);
            AttributesValidationUtils.validateNonNullAttribute("name",vaccineName);
            AttributesValidationUtils.validateNonNullAttribute("manufacturer",vaccineManufacturer);
            AttributesValidationUtils.validateNonNullAttribute("number of age groups",numberOfAgeGroups);
            AttributesValidationUtils.validateNonNullAttribute("minimum age",minAge);
            AttributesValidationUtils.validateNonNullAttribute("maximum age",maxAge);
            AttributesValidationUtils.validateNonNullAttribute("number of doses",numberOfDoses);
            AttributesValidationUtils.validateNonNullAttribute("dosage",dosage);
            AttributesValidationUtils.validateNonNullAttribute("time elapsed",timeElapsed);


            this.vaccineType = vaccineType;
            this.vaccineName = vaccineName;
            this.vaccineManufacturer = vaccineManufacturer;
            this.numberOfAgeGroups = numberOfAgeGroups;
            this.minAge = minAge;
            this.maxAge = maxAge;
            this.numberOfDoses = numberOfDoses;
            this.dosage = dosage;
            this.timeElapsed = timeElapsed;
        }

        public VaccineDTO(VaccineType vaccineType, String vaccineName, String vaccineManufacturer)
        {
            AttributesValidationUtils.validateNonNullAttribute("type",vaccineType);
            AttributesValidationUtils.validateNonNullAttribute("name",vaccineName);
            AttributesValidationUtils.validateNonNullAttribute("manufacturer",vaccineManufacturer);

            this.vaccineType = vaccineType;
            this.vaccineName = vaccineName;
            this.vaccineManufacturer = vaccineManufacturer;
        }

        public VaccineType getVaccineType()
        {
            return vaccineType;
        }

        public String getVaccineName()
        {
            return vaccineName;
        }

        public String getVaccineManufacturer()
        {
            return vaccineManufacturer;
        }

        public int getNumberOfAgeGroups()
        {
            return numberOfAgeGroups;
        }

        public List<Integer> getMinAge()
        {
            return minAge;
        }

        public List<Integer> getMaxAge()
        {
            return maxAge;
        }

        public List<Integer> getNumberOfDoses()
        {
            return numberOfDoses;
        }

        public List<Double> getDosage()
        {
            return dosage;
        }

        public List<Integer> getTimeElapsed()
        {
            return timeElapsed;
        }

        @Override
        public String toString()
        {
            return "Vaccine Type: " + vaccineType + "\n" +
                    "Vaccine Name: " + vaccineName + "\n" +
                    "Manufacturer: " + vaccineManufacturer;
        }
    }

