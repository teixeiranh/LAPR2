package app.domain.tasks;

import app.domain.shared.Constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class VaccinationCenterStatsRecorderRecurringTask {

    public static final String DEFAULT_STATS_TIMER = "23:00";
    public static final String DEFAULT_STATS_ADAPTER = "app.domain.tasks.VaccinationCenterStatsDailyRecorderAdapter";

    private final LocalTime vaccinationCenterStatsTimer;
    private final String vaccinationCenterStatsRecorderAdapterToUse;

    public VaccinationCenterStatsRecorderRecurringTask()
    {
        Properties props = getProperties();

        this.vaccinationCenterStatsTimer = this.parseVaccinationCenterStatsTimer(
                props.getProperty(Constants.PARAMS_VACCINATION_CENTER_STATS_TIMER));

        this.vaccinationCenterStatsRecorderAdapterToUse = props.getProperty(
                Constants.PARAMS_VACCINATION_CENTER_STATS_RECORDER_ADAPTER);

        this.configureVaccinationCenterStatsRecorder(this.vaccinationCenterStatsTimer, this.vaccinationCenterStatsRecorderAdapterToUse);
    }

    private Properties getProperties()
    {
        Properties props = new Properties();

        // Add default properties and values
        props.setProperty(Constants.PARAMS_VACCINATION_CENTER_STATS_TIMER, DEFAULT_STATS_TIMER);
        props.setProperty(Constants.PARAMS_VACCINATION_CENTER_STATS_RECORDER_ADAPTER, DEFAULT_STATS_ADAPTER);

        // Read configured values
        try
        {
            InputStream in = new FileInputStream(Constants.PARAMS_FILENAME);
            props.load(in);
            in.close();
        } catch (IOException ignored)
        {

        }

        return props;
    }

    private LocalTime parseVaccinationCenterStatsTimer(String vaccinationCenterStatsTimerProperty)
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(vaccinationCenterStatsTimerProperty, dateTimeFormatter);
    }

    private void configureVaccinationCenterStatsRecorder(LocalTime vaccinationCenterStatsTimer,
                                                         String vaccinationCenterStatsRecorderAdapterToUse)
    {
        try
        {
            Calendar runningTime = Calendar.getInstance();
            runningTime.set(Calendar.HOUR_OF_DAY, vaccinationCenterStatsTimer.getHour());
            runningTime.set(Calendar.MINUTE, vaccinationCenterStatsTimer.getMinute());
            runningTime.set(Calendar.SECOND, 0);

            Class<?> reflectionClass = Class.forName(vaccinationCenterStatsRecorderAdapterToUse);
            TimerTask adapter = (TimerTask) reflectionClass.getDeclaredConstructor().newInstance();

            Timer timer = new Timer(true);
            timer.scheduleAtFixedRate(adapter, runningTime.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e)
        {
            throw new RuntimeException(e); //TODO
        }
    }

}
