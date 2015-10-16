package io.sprint0.cli.jobs;

import io.sprint0.cli.activities.Activity;
import io.sprint0.cli.activities.ActivityResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.sprint0.cli.configuration.ConfigurationStore;
import io.sprint0.cli.configuration.ConfigurationStoreHolder;
import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class Job implements ConfigurationStoreHolder {

    private ConfigurationStore configurationStore;

    public enum Status {
        SUCCESS, FAILURE
    }

    private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<Activity> activities = new ArrayList <> ();

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    public Status execute(CommandLine commandLine) {

        List<ActivityResult> results = new ArrayList <>();
        for (Activity activity : activities) {
            String activityName = activity.toString();
            logger.info("Activity : {} .....started.", activityName);
            ActivityResult result = activity.go(commandLine);
            results.add(result);
            if (ActivityResult.Status.FAILURE.equals(result.getStatus())) {
                logger.info("Activity : {} .....failed.", activityName);
                return Status.FAILURE;
            }
            logger.info("Activity : {} .....succeeded.", activityName);

        }
        logger.info("All activities succeeded.");

        return Status.SUCCESS;
    }

    public List<Activity> getActivities() {
        return Collections.unmodifiableList(activities);
    }

    @Override
    public void setConfigurationStore(ConfigurationStore configurationStore) {
        this.configurationStore = configurationStore;
    }
}
