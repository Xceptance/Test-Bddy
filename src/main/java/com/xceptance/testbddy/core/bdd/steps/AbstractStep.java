package com.xceptance.testbddy.core.bdd.steps;

import java.util.LinkedHashSet;
import java.util.Set;

import com.aventstack.extentreports.GherkinKeyword;
import com.xceptance.testbddy.api.PendingConsumer;
import com.xceptance.testbddy.api.PendingRunnable;
import com.xceptance.testbddy.core.bdd.status.Status;
import com.xceptance.testbddy.core.bdd.status.Statusable;
import com.xceptance.testbddy.core.reporting.ReportElement;
import com.xceptance.testbddy.core.reporting.ReportInterface;
import com.xceptance.testbddy.core.throwables.errors.StepError;
import com.xceptance.testbddy.core.throwables.exceptions.StepException;

/**
 * Describes a step in the BDD Hierarchy.<br>
 * Hence, it contains the actual behavior.
 * 
 * @author ckeiner
 *
 * @param <T>
 *            The type of the behavior. This is typically a functional interface
 *            like {@link Runnable}.
 */
public abstract class AbstractStep<T> implements Statusable
{
    /**
     * The status of the step
     */
    private final Set<Status> status;

    /**
     * The reporter responsible for reporting.
     */
    private ReportInterface reporter;

    /**
     * Defines the {@link GherkinKeyword}.
     */
    private final GherkinKeyword keyword;

    /**
     * Describes the step in a natural language.
     */
    private String description;

    /**
     * Contains the behavior of the step.
     */
    private final T behavior;

    /**
     * Creates an AbstractStep with the specified keyword, description and behavior.
     * 
     * @param keyword
     *            The {@link GherkinKeyword} describing whether its a given, when,
     *            then or and step.
     * @param description
     *            A String describing what this step does.
     * @param behavior
     *            The behavior of the step.
     */
    public AbstractStep(final GherkinKeyword keyword, final String description, final T behavior)
    {
        this.keyword = keyword;
        this.description = description;
        this.behavior = behavior;
        this.status = new LinkedHashSet<Status>();
    }

    /**
     * Executes the step.<br>
     * It first verifies if the step can and should be run. Then sets up the
     * reporter If {@link #reporter} was specified, it first prepares the report. If
     * {@link #skip} is set, the {@link #skipStep()} method is called, otherwise
     * {@link #executeStep()} is called. <br>
     * If an Exception or Error occurs, it is re-thrown as {@link StepException} and
     * {@link StepError} respectively.
     * 
     * @throws StepException
     *             If an Exception occurs. The report shows the step as fatal.
     * @throws StepError
     *             If an Error occurs. The report shows the step as failed.
     */
    public void test()
    {
        if (canAndShouldExecuteStep())
        {
            // Create the ReportElement
            ReportElement stepReporter = setUpReporter();
            // Print the description of the step
            System.out.println(getDescription());
            try
            {
                // If it shouldn't be skipped
                if (!getStatus().contains(Status.SKIP))
                {
                    // Execute it
                    executeStep();
                    // Mark the node as passed if it exists
                    if (stepReporter != null)
                    {
                        stepReporter.pass(getDescription());
                    }
                }
                // If the step should be skipped
                else
                {
                    // Mark the node as skipped
                    if (stepReporter != null)
                    {
                        stepReporter.skip(getDescription());
                    }
                }
            } catch (Exception e)
            {
                // Mark the node as fatal
                if (stepReporter != null)
                {
                    stepReporter.fatal(e);
                }
                // Throw an Exception
                throw new StepException("Step " + getDescription() + " failed.", e);
            } catch (Error e)
            {
                // Mark the node as failed
                if (stepReporter != null)
                {
                    stepReporter.fail(e);
                }
                // Throw an Error
                throw new StepError("Step " + getDescription() + " failed.", e);
            }
        }
    }

    /**
     * Verifies if the step can and should be executed. If a step is ignores, has a
     * null behavior or the behavior is either {@link PendingRunnable} or
     * {@link PendingConsumer}, it is not executed.
     * 
     * @return True if it should be executed. Otherwise false.
     */
    protected boolean canAndShouldExecuteStep()
    {
        boolean executeStep = true;
        // If the status contains the Ignore-Status, simply don't show
        if (getStatus().contains(Status.IGNORE))
        {
            executeStep = false;
        }

        if (behavior == null)
        {
            throw new StepException("Step " + getDescription() + " failed.",
                    new IllegalStateException("Null behavior found"));
        }

        // If the behavior is either a pending runnable or pending consumer
        if (behavior instanceof PendingRunnable || behavior instanceof PendingConsumer<?>)
        {
            // Add the pending status to the list of stati
            getStatus().add(Status.PENDING);
            // Set up reporting
            final ReportElement stepReporter = setUpReporter();
            if (stepReporter != null)
            {
                // Set pending for the reporter
                stepReporter.pending("No behavior was defined");
            }
            // End execution of feature
            executeStep = false;
        }
        return executeStep;
    }

    /**
     * Skips the step.
     */
    public void skipStep()
    {
        // Create the ReportElement
        ReportElement element = setUpReporter(true);
        // Mark the node as skipped
        if (element != null)
        {
            element.skip(description);
        }
    }

    /**
     * Executes a single step.
     */
    protected abstract void executeStep();

    /**
     * Creates a {@link ReportElement} for the step if a reporter is set.<br>
     * Also assigns the step's status as the report element's category.
     * 
     * @return The {@link ReportElement} representing the step.
     */
    protected ReportElement setUpReporter()
    {
        return setUpReporter(true);
    }

    /**
     * Creates a {@link ReportElement} for the step if a reporter is set.<br>
     * Also assigns the step's status as the report element's category if
     * reportStatus is true.
     * 
     * @param reportStatus
     *            Whether the status should be shown in the report.
     * @return The ReportElement for a Step. <code>null</code> if
     *         {@link #getReporter()} returns <code>null</code>.
     */
    protected ReportElement setUpReporter(boolean reportStatus)
    {
        return setUpReporter(reportStatus, getDescription());
    }

    /**
     * Creates a {@link ReportElement} with a custom description for the step if a
     * reporter is set.<br>
     * Also assigns the step's status as the report element's category if
     * reportStatus is true.
     * 
     * @param reportStatus
     *            Whether the status should be shown in the report.
     * @param description
     *            The description of the {@link ReportElement}.
     * @return The ReportElement for a Step. <code>null</code> if
     *         {@link #getReporter()} returns <code>null</code>.
     */
    protected ReportElement setUpReporter(boolean reportStatus, String description)
    {
        ReportElement element = null;
        if (reporter != null)
        {
            element = reporter.step(keyword, description);
            if (getStatus() != null && reportStatus)
            {
                // Assign the status as category
                element.assignCategory(getStatus());
            }
        }
        return element;
    }

    protected void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public T getBehavior()
    {
        return behavior;
    }

    public GherkinKeyword getKeyword()
    {
        return keyword;
    }

    public ReportInterface getReporter()
    {
        return this.reporter;
    }

    public void setReporter(ReportInterface reporter)
    {
        this.reporter = reporter;
    }

    public Set<Status> getStatus()
    {
        return status;
    }

    @Override
    public AbstractStep<T> ignore()
    {
        getStatus().add(Status.IGNORE);
        return this;
    }

    @Override
    public AbstractStep<T> wip()
    {
        getStatus().add(Status.WIP);
        return this;
    }

    /**
     * Skip the step.<br>
     * This means, it appears in the report, but is not executed. The following
     * steps are executed regardless.
     */
    @Override
    public AbstractStep<T> skip()
    {
        getStatus().add(Status.SKIP);
        return this;
    }

}
