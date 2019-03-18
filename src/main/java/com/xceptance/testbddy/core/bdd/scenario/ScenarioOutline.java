package com.xceptance.testbddy.core.bdd.scenario;

import java.util.ArrayList;
import java.util.List;

import com.xceptance.testbddy.core.bdd.status.Status;
import com.xceptance.testbddy.core.bdd.steps.Steps;
import com.xceptance.testbddy.core.bdd.steps.TypeSteps;
import com.xceptance.testbddy.core.reporting.ReportElement;
import com.xceptance.testbddy.core.throwables.MultipleScenarioWrapperException;
import com.xceptance.testbddy.core.throwables.errors.ScenarioError;
import com.xceptance.testbddy.core.throwables.errors.StepError;
import com.xceptance.testbddy.core.throwables.exceptions.ScenarioException;
import com.xceptance.testbddy.core.throwables.exceptions.StepException;

/**
 * Represents a Scenario Outline in the BDD Hierarchy.<br>
 * It is the same as a {@link Scenario} except that it has test data. This
 * means, that the scenario gets executed once with each test data. To execute
 * it, see the {@link #test()} method.
 *
 * @author ckeiner
 *
 * @param <T>
 *            The type of the <code>Testdata</code>.
 */
public class ScenarioOutline<T> extends AbstractScenario
{
    /**
     * The steps to execute.
     */
    private TypeSteps<T> steps;

    /**
     * The list of test data
     */
    private final List<T> testdata;

    /**
     * Creates a new ScenarioOutline with the specified description, steps and test
     * data.
     * 
     * @param description
     *            The description of the Scenario.
     * @param steps
     *            The {@link Steps} of the scenario.
     * @param testdata
     *            The list of test data.
     */
    public ScenarioOutline(String description, TypeSteps<T> steps, List<T> testdata)
    {
        super(description);
        this.steps = steps;
        this.testdata = testdata;
    }

    /**
     * Executes the scenario outline.<br>
     * Exceptions and errors get caught and the scenario is executed with the rest
     * of the test data. In the end, the exceptions and errors are re-thrown as
     * {@link ScenarioException} and {@link ScenarioError} respectively.<br>
     * Should both, an exception and an error, occur, a ScenarioException is thrown.
     */
    @Override
    public void test()
    {
        if (canAndShouldExecuteScenario())
        {
            // Initialize needed variables
            List<ScenarioException> scenarioExceptions = new ArrayList<>();
            List<ScenarioError> scenarioErrors = new ArrayList<>();
            System.out.println("================\nScenarioOutline: " + getDescription() + "\n================");
            for (final T testdatum : this.testdata)
            {
                doSingleTest(testdatum, scenarioExceptions, scenarioErrors);
            }
            System.out.println("\n\n");
            finishScenario(scenarioExceptions, scenarioErrors);
        }
    }

    /**
     * Verifies if the {@link ScenarioOutline} should and can be executed. A
     * ScenarioOutline should not be executed if it is ignored, and cannot be
     * executed if it has no steps, test data, or either is empty.
     * 
     * @return True if the ScenarioOutline can be executed, otherwise false.
     */
    protected boolean canAndShouldExecuteScenario()
    {
        boolean executeScenario = true;
        if (getStatus().contains(Status.IGNORE))
        {
            executeScenario = false;
        }

        if (getSteps() == null)
        {
            throw new ScenarioException("Scenario " + getDescription() + " failed.",
                    new IllegalStateException("Null steps found"));
        }

        if (getTestdata() == null)
        {
            throw new ScenarioException("Scenario " + getDescription() + " failed.",
                    new IllegalStateException("Null Testdata found"));
        }

        if (getTestdata().isEmpty())
        {
            // Add the pending status to the list of stati
            getStatus().add(Status.PENDING);
            // Set up reporting
            final ReportElement scenarioReporter = setUpReporter(getSteps(), null);
            // If a scenarioReporter could be built
            if (scenarioReporter != null)
            {
                // And there are also no steps
                if (getSteps().getSteps().isEmpty())
                {
                    // Report that both a not found
                    scenarioReporter.pending("No Testdata and Steps were defined");
                }
                // Otherwise
                else
                { // Report that no test data was defined
                    scenarioReporter.pending("No Testdata were defined");
                }
            }
            // End execution of feature
            executeScenario = false;
        }

        // If there are no steps, then the scenario is pending
        else if (getSteps().getSteps().isEmpty())
        {
            // Add the pending status to the list of stati
            getStatus().add(Status.PENDING);
            for (T testdatum : testdata)
            {
                // Set up reporting
                final ReportElement scenarioReporter = setUpReporter(getSteps(), testdatum);
                if (scenarioReporter != null)
                {// Set pending for the reporter
                    scenarioReporter.pending("No steps were defined");
                }
            }
            // End execution of feature
            executeScenario = false;
        }

        return executeScenario;
    }

    /**
     * Executes a test with a single test datum.
     * 
     * @param testdatum
     *            The test datum to execute the steps with.
     * @param scenarioExceptions
     *            The list of {@link ScenarioException}s.
     * @param scenarioErrors
     *            The list of {@link ScenarioError}s.
     */
    private void doSingleTest(final T testdatum, final List<ScenarioException> scenarioExceptions,
            final List<ScenarioError> scenarioErrors)
    {
        // Set the testdata
        TypeSteps<T> typeSteps = getSteps().withData(testdatum);

        // If there are no steps, then the scenario is pending
        if (getSteps() == null || getSteps().getSteps().isEmpty())
        {
            // Add the pending status to the list of stati
            getStatus().add(Status.PENDING);
            // Set up reporting
            final ReportElement scenarioReporter = setUpReporter(typeSteps, testdatum);
            // Set pending for the reporter
            scenarioReporter.pending("No steps found");
            // End execution of feature
            return;
        }

        // Tell the reporter the scenario starts
        ReportElement scenarioReporter = setUpReporter(typeSteps, testdatum);

        System.out.println("Using testdata:\n" + testdatum.toString());
        try
        {
            executeScenario(scenarioReporter, typeSteps);
        } catch (StepException exception)
        {
            scenarioExceptions.add(scenarioException(testdatum, exception, scenarioReporter));
        } catch (StepError exception)
        {
            scenarioErrors.add(scenarioError(testdatum, exception, scenarioReporter));
        }
        System.out.println("\n");
    }

    /**
     * Creates and logs a {@link ScenarioError}.
     * 
     * @param testdatum
     *            The used test datum.
     * @param stepError
     *            The {@link StepError} that occurred.
     * @param scenarioReporter
     *            The reporter for reporting.
     * @return The ScenarioError with a proper description and the causing
     *         StepError.
     */
    private ScenarioError scenarioError(T testdatum, StepError stepError, ReportElement scenarioReporter)
    {
        // Create a ScenarioError
        ScenarioError scenarioError = new ScenarioError(
                "Scenario \"" + getDescription() + "\" failed with data:\n" + testdatum.toString(), stepError);
        if (scenarioReporter != null)
        {
            // Mark the scenario as failed with the error
            scenarioReporter.fail(scenarioError);
        }
        return scenarioError;
    }

    /**
     * Creates and logs a {@link ScenarioException}.
     * 
     * @param testdatum
     *            The used test datum.
     * @param stepException
     *            The {@link StepException} that occurred.
     * @param scenarioReporter
     *            The reporter for reporting.
     * @return The ScenarioException with a proper description and the causing
     *         StepException.
     */
    private ScenarioException scenarioException(T testdatum, StepException stepException,
            ReportElement scenarioReporter)
    {
        // Create a ScenarioError
        ScenarioException scenarioException = new ScenarioException(
                "Scenario \"" + getDescription() + "\" failed with data:\n" + testdatum.toString(), stepException);
        if (scenarioReporter != null)
        {
            // Mark the scenario as fatal with the exception
            scenarioReporter.fatal(scenarioException);
        }
        return scenarioException;
    }

    @Override
    public void skipScenario()
    {
        for (T testdatum : testdata)
        {
            // Get the step definition
            TypeSteps<T> typeSteps = getSteps();
            // Set the testdata
            typeSteps = typeSteps.withData(testdatum);
            // Set up the report for this element
            ReportElement scenarioReporter = setUpReporter(typeSteps, testdatum, false);
            // Set up the reporter for the bddSteps if none was supplied
            if (typeSteps.getReporter() == null && this.getReporter() != null)
            {
                typeSteps.setReporter(this.getReporter());
            }
            // Skip the steps
            typeSteps.skipSteps();
            scenarioReporter.skip(getDescription());
        }
    }

    /**
     * Creates a {@link ReportElement} for the scenario if a reporter is set.<br>
     * Also assigns the scenario's status as the report element's category.
     * 
     * @param typeSteps
     *            The {@link TypeSteps} for which to create the reporter.
     * @param testdatum
     *            The test datum to execute the steps with.
     * @return The ReportElement for the Scenario specified by typeSteps.
     *         <code>null</code> if {@link #getReporter()} returns
     *         <code>null</code>.
     */
    protected ReportElement setUpReporter(TypeSteps<T> typeSteps, T testdatum)
    {
        return setUpReporter(typeSteps, testdatum, true);
    }

    /**
     * Creates a {@link ReportElement} for the scenario if a reporter is set.<br>
     * Also assigns the scenario's status as the report element's category if
     * reportStatus is true.
     * 
     * @param typeSteps
     *            The {@link TypeSteps} for which to create the reporter.
     * @param testdatum
     *            The test datum to execute the steps with.
     * @param reportStatus
     *            Whether the status should be reported or not.
     * 
     * @return The ReportElement for the Scenario specified by typeSteps.
     *         <code>null</code> if {@link #getReporter()} returns
     *         <code>null</code>.
     */
    protected ReportElement setUpReporter(TypeSteps<T> typeSteps, T testdatum, boolean reportStatus)
    {
        ReportElement scenarioReporter = null;
        if (getReporter() != null)
        {
            scenarioReporter = getReporter().scenarioOutline(this.getDescription(), testdatum);
            if (typeSteps != null && typeSteps.getReporter() == null)
            {
                typeSteps.setReporter(this.getReporter());
            }
            if (this.getStatus() != null && reportStatus)
            {
                scenarioReporter.assignCategory(getStatus());
            }
        }
        return scenarioReporter;
    }

    /**
     * Executes the supplied steps.<br>
     * 
     * @param scenarioReporter
     *            The ReportElement of the scenario.
     * @param typeSteps
     *            The {@link TypeSteps} to execute.
     */
    protected void executeScenario(ReportElement scenarioReporter, TypeSteps<T> typeSteps)
    {
        if (getStatus().contains(Status.SKIP))
        {
            if (scenarioReporter != null)
            {
                scenarioReporter.skip(getDescription());
            }
        }
        else
        {
            typeSteps.test();
            if (scenarioReporter != null)
            {
                scenarioReporter.pass(getDescription());
            }
        }
    }

    /**
     * Throws {@link ScenarioException} or {@link ScenarioError} if the respective
     * parameter isn't empty.<br>
     * If both, an exception and error, occur, a ScenarioException is thrown.
     * 
     * @param scenarioExceptions
     *            The list of scenarioExceptions that happened during the scenario.
     * @param scenarioErrors
     *            The list of scenarioErrors that happened during the scenario.
     */
    private void finishScenario(List<ScenarioException> scenarioExceptions, List<ScenarioError> scenarioErrors)
    {
        // Then we collect the throwables
        MultipleScenarioWrapperException scenarioWrapperException = new MultipleScenarioWrapperException(
                scenarioExceptions, scenarioErrors);
        // Throw a scenario exception if an exception or exception and error occured
        if (scenarioExceptions != null && !scenarioExceptions.isEmpty())
        {
            throw new ScenarioException(scenarioWrapperException);
        }
        // Throw a scenario error if an error occured
        else if (scenarioErrors != null && !scenarioErrors.isEmpty())
        {
            throw new ScenarioError(scenarioWrapperException);
        }
    }

    public List<T> getTestdata()
    {
        return testdata;
    }

    public TypeSteps<T> getSteps()
    {
        return steps;
    }

    public void setSteps(TypeSteps<T> steps)
    {
        this.steps = steps;
    }

    @Override
    public ScenarioOutline<T> ignore()
    {
        super.ignore();
        return this;
    }

    @Override
    public ScenarioOutline<T> wip()
    {
        super.wip();
        return this;
    }

    @Override
    public ScenarioOutline<T> skip()
    {
        super.skip();
        return this;
    }

}
