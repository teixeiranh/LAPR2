package app.domain.interfaces;

import app.domain.model.VaccineAdministration;

import java.util.Set;

/**
 * Interface to be used by all the adapters that pretend some sort of Statistical
 * analysis.
 * It has a single method.
 */
public interface IExportStatisticsToExternalSource
{
    void exportStatistics(Set<VaccineAdministration> vaccineAdministrationList,
                          String nameOfFile);
}
