package com.xceptance.testbddy.core.bdd;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.xceptance.testbddy.core.bdd.scenario.AbstractScenario;
import com.xceptance.testbddy.core.bdd.status.Status;
import com.xceptance.testbddy.core.bdd.status.Statusable;
import com.xceptance.testbddy.core.reporting.ReportElement;
import com.xceptance.testbddy.core.reporting.ReportInterface;
import com.xceptance.testbddy.core.reporting.extentreports.ExtentReportInterface;
import com.xceptance.testbddy.core.throwables.MultipleScenarioWrapperException;
import com.xceptance.testbddy.core.throwables.errors.FeatureError;
import com.xceptance.testbddy.core.throwables.errors.ScenarioError;
import com.xceptance.testbddy.core.throwables.exceptions.FeatureException;
import com.xceptance.testbddy.core.throwables.exceptions.ScenarioException;

/**
 * Represents a feature in the BDD Hierarchy.<br>
 * Therefore, this is the entry point for every test. To execute it, see the
 * {@link #test()} method.
 *
 * @author ckeiner
 */
public class Feature implements Statusable
{
    /**
     * The reporter responsible for reporting.
     */
    public ReportInterface reporter;

    /**
     * The description of the feature.
     */
    private final String description;

    /**
     * The list of scenarios to execute.
     */
    private final List<AbstractScenario> scenarios;

    /**
     * The status of the feature.
     */
    private final Set<Status> status;

    /**
     * The class the feature is defined in.
     */
    private final String classFeatureDefinedIn;

    /**
     * Creates a Feature with the specified description and an empty list of
     * scenarios.
     *
     * @param description
     *            String that describes the Feature.
     */
    public Feature(final String description)
    {
        this(description, new ArrayList<>());
    }

    /**
     * Creates a Feature with the specified description and scenarios.
     *
     * @param description
     *            String that describes the Feature.
     * @param scenarios
     *            List of {@link AbstractScenario}s that the feature should execute.
     */
    public Feature(final String description, final List<AbstractScenario> scenarios)
    {
        this(description, scenarios, null);
    }

    /**
     * Creates a Feature with the specified description and scenarios. Afterwards,
     * the reporter is initialized.
     *
     * @param description
     *            String that describes the Feature.
     * @param scenarios
     *            List of {@link AbstractScenario}s that the feature should execute.
     * @param classFeatureDefinedIn
     *            The class the feature is defined in.
     */
    public Feature(final String description, final List<AbstractScenario> scenarios, String classFeatureDefinedIn)
    {
        this.description = description;
        this.scenarios = scenarios;
        status = new LinkedHashSet<Status>();
        this.classFeatureDefinedIn = classFeatureDefinedIn;
        initReporter();
    }

    /**
     * Initializes the reporter.
     */
    protected void initReporter()
    {
        this.reporter = ExtentReportInterface.getInstance();
    }

    /**
     * Executes the feature.<br>
     * Every exception and error is bundled and thrown in a {@link FeatureException}
     * or {@link FeatureError} respectively.<br>
     * Should both, an exception and an error, occur, a FeatureException is thrown.
     */
    public void test()
    {
        // Execute only if it should be executed
        if (canAndShouldExecuteFeature())
        {
            // Set up list of exceptions and error
            final List<ScenarioException> scenarioExceptions = new ArrayList<ScenarioException>();
            final List<ScenarioError> scenarioErrors = new ArrayList<ScenarioError>();
            // Set up reporting
            final ReportElement featureReport = setUpReporter();
            // Print some information to the console
            printToConsole();

            // For each scenario
            for (final AbstractScenario scenario : getScenarios())
            {
                // If the scenario is not null
                if (scenario != null)
                {
                    // Set the reporter of the scenario if the feature has one
                    if (scenario.getReporter() == null && reporter != null)
                    {
                        scenario.setReporter(reporter);
                    }
                    // Execute the scenario and catch all exceptions and errors
                    try
                    {
                        // Execute the feature
                        executeScenario(scenario);
                    } catch (final ScenarioException e)
                    {
                        scenarioExceptions.add(e);
                    } catch (final ScenarioError e)
                    {
                        scenarioErrors.add(e);
                    }
                }
                else
                {
                    scenarioExceptions.add(new ScenarioException(new IllegalStateException("Scenario is null")));
                }
            }

            try
            {
                // Finish the test with proper reporting, and exception, error throwing
                finishTest(featureReport, scenarioExceptions, scenarioErrors);
            } finally
            {
                if (reporter != null)
                {
                    reporter.finishReport();
                }
            }
        }
    }

    /**
     * Verifies that the feature can and should be executed.<br>
     * A feature is executable if it is neither ignored, has not a scenario list or
     * an empty one.
     */
    private boolean canAndShouldExecuteFeature()
    {
        boolean shouldDoExecution = true;
        // If we ignore the feature, we neither want to execute nor log it
        if (getStatus().contains(Status.IGNORE))
        {
            shouldDoExecution = false;
        }

        if (getScenarios() == null)
        {
            throw new FeatureException(classFeatureDefinedIn + ".Feature \"" + description + "\" failed.",
                    new IllegalStateException("Scenarios are null"));
        }

        // If there is no scenario, then the feature is pending
        if (getScenarios().isEmpty())
        {
            // Add the pending status to the list of stati
            getStatus().add(Status.PENDING);
            // Set up reporting
            final ReportElement featureReport = setUpReporter();
            if (featureReport != null)
            {
                // Set pending for the reporter
                featureReport.pending("No scenarios were defined");
            }
            // End execution of feature
            shouldDoExecution = false;
        }

        return shouldDoExecution;
    }

    /**
     * Prints some basic information to the console.
     */
    protected void printToConsole()
    {
        // Print some information to the console
        String logging = classFeatureDefinedIn + ".Feature: " + description;
        if (getStatus() != null)
        {
            logging = getStatus().toString() + logging;
        }
        System.out.println(logging);
    }

    /**
     * Executes the specified scenario.
     * 
     * @param scenario
     *            The {@link AbstractScenario} that should be executed.
     */
    public void executeScenario(AbstractScenario scenario)
    {
        if (getStatus().contains(Status.SKIP))
        {
            scenario.skipScenario();
        }
        else
        {
            scenario.test();
        }
    }

    /**
     * Creates a {@link ReportElement} for the feature if a reporter is set.<br>
     * Also assigns the feature's status as the report element's category.
     *
     * @return {@link ReportElement} if a reporter was set, else <code>null</code>.
     */
    private ReportElement setUpReporter()
    {
        // Define ReportElement featureReport
        ReportElement featureReport = null;
        // If a reporter exists
        if (reporter != null)
        {
            // Describe the feature
            featureReport = reporter.feature(classFeatureDefinedIn + ": " + description);
            // If the status isn't null
            if (getStatus() != null)
            {
                // Assign the Status as category
                featureReport.assignCategory(getStatus());
            }
        }
        // Return the featureReport
        return featureReport;
    }

    /**
     * Bundles and reports any occurred exceptions and errors and re-throws them in
     * a {@link FeatureException} or {@link FeatureError}.
     *
     * @param featureReport
     *            The {@link ReportElement} generated by the test.
     * @param scenarioExceptions
     *            The {@link ScenarioException}s thrown.
     * @param scenarioErrors
     *            The {@link ScenarioError}s thrown.
     */
    private void finishTest(final ReportElement featureReport, final List<ScenarioException> scenarioExceptions,
            final List<ScenarioError> scenarioErrors)
    {
        // Find out if an exception and/or error occured
        final boolean exception = scenarioExceptions != null && !scenarioExceptions.isEmpty();
        final boolean error = scenarioErrors != null && !scenarioErrors.isEmpty();

        // If an exception or error happened
        if (exception || error)
        {
            String errorMessage = classFeatureDefinedIn + ".Feature \"" + description + "\" failed.";
            // Gather all errors and exceptions
            final MultipleScenarioWrapperException scenarioWrapperException = new MultipleScenarioWrapperException(
                    scenarioExceptions, scenarioErrors);
            // Print the stack trace
            scenarioWrapperException.printStackTrace();

            // If an exception happened
            if (exception)
            {
                // Report the feature as fatal
                if (featureReport != null)
                {
                    featureReport.fatal(scenarioWrapperException);
                }
                // Throw the FeatureException with the wrapper for all exceptions and errors
                throw new FeatureException(errorMessage, scenarioWrapperException);
            }
            // If no exception but an error happened
            else if (error)
            {
                if (featureReport != null)
                {
                    // Report the feature as failed
                    featureReport.fail(scenarioWrapperException);
                }
                // Throw the FeatureError with the wrapper for all exceptions and errors
                throw new FeatureError(errorMessage, scenarioWrapperException);
            }
        }
        // If no errors occured and a featureReport exists
        else if (featureReport != null)
        {
            // If the Status contains SKIP, then report the feature as skipped.
            if (getStatus().contains(Status.SKIP))
            {
                featureReport.skip(description);
            }
            // If the Status doesn't contain skipped, then report the feature as passed.
            else
            {
                featureReport.pass(description);
            }
        }
    }

    public String getDescription()
    {
        return description;
    }

    /**
     * Returns all scenarios.
     *
     * @return Returns all {@link AbstractScenario} of this feature.
     */
    public List<AbstractScenario> getScenarios()
    {
        return scenarios;
    }

    public Set<Status> getStatus()
    {
        return status;
    }

    public String getClassFeatureDefinedIn()
    {
        return classFeatureDefinedIn;
    }

    @Override
    public Feature ignore()
    {
        getStatus().add(Status.IGNORE);
        return this;
    }

    @Override
    public Feature wip()
    {
        getStatus().add(Status.WIP);
        return this;
    }

    @Override
    public Feature skip()
    {
        getStatus().add(Status.SKIP);
        return this;
    }

    public Feature withReporter(ReportInterface reporter)
    {
        this.reporter = reporter;
        return this;
    }
}
