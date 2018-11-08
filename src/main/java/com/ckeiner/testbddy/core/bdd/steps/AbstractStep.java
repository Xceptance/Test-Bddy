package com.ckeiner.testbddy.core.bdd.steps;

import java.util.LinkedHashSet;
import java.util.Set;

import com.aventstack.extentreports.GherkinKeyword;
import com.ckeiner.testbddy.core.bdd.status.Status;
import com.ckeiner.testbddy.core.bdd.status.Statusable;
import com.ckeiner.testbddy.core.reporting.ReportElement;
import com.ckeiner.testbddy.core.reporting.ReportInterface;
import com.ckeiner.testbddy.core.throwables.errors.StepError;
import com.ckeiner.testbddy.core.throwables.exceptions.StepException;

/**
 * Describes a step in the BDD Hierarchy.<br>
 * That means, it contains the actual behavior.
 * 
 * @author ckeiner
 *
 * @param <T>
 *            The type of the behavior.
 */
public abstract class AbstractStep<T> implements Statusable
{
    /**
     * Shows the status of the step
     */
    private final Set<Status> status;

    /**
     * The object responsible for reporting.
     */
    private ReportInterface reporter;

    /**
     * Defines the {@link GherkinKeyword}.
     */
    private final GherkinKeyword keyword;

    /**
     * Describes the step in natural language.
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
     *            Contains the actual execution.
     */
    public AbstractStep(final GherkinKeyword keyword, final String description, final T behavior)
    {
        this.keyword = keyword;
        this.description = description;
        this.behavior = behavior;
        this.status = new LinkedHashSet<Status>();
    }

    /**
     * The method called when the step should be executed.<br>
     * If {@link #reporter} was specified, it first prepares the report. Then, it
     * executes the behavior. <br>
     * If {@link #skip} is set, the behavior isn't execute and the report marks the
     * step as skipped.
     * 
     * @throws StepException
     *             If an Exception occurs. The report shows the step as fatal.
     * @throws StepError
     *             If an Error occurs. The report shows the step as failed.
     */
    public void test()
    {
        // If the status contains the Ignore-Status, simply don't show
        if (getStatus().contains(Status.IGNORE))
        {
            return;
        }
        // Create the ReportElement
        ReportElement element = setUpReporter();
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
                if (element != null)
                {
                    element.pass(getDescription());
                }
            }
            // If the step should be skipped
            else
            {
                // Mark the node as skipped
                if (element != null)
                {
                    element.skip(getDescription());
                }
            }
        } catch (Exception e)
        {
            // Mark the node as fatal
            if (element != null)
            {
                element.fatal(e);
            }
            // Throw an Exception
            throw new StepException(e);
        } catch (Error e)
        {
            // Mark the node as failed
            if (element != null)
            {
                element.fail(e);
            }
            // Throw an Error
            throw new StepError(e);
        }
    }

    /**
     * Skips the step
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
     * Defines how a single step is executed.
     */
    protected abstract void executeStep();

    /**
     * Sets the reporter up and shows the {@link Status} defined in {@link #status}.
     * 
     * @return The {@link ReportElement} representing the step.
     */
    protected ReportElement setUpReporter()
    {
        return setUpReporter(true);
    }

    /**
     * Sets the reporter up.
     * 
     * @param showStatus
     *            Whether the status should be shown in the report.
     * @return The ReportElement for a Step. <code>null</code> if
     *         {@link #getReporter()} returns <code>null</code>.
     */
    protected ReportElement setUpReporter(boolean showStatus)
    {
        return setUpReporter(showStatus, getDescription());
    }

    /**
     * Sets the reporter up with a custom description.
     * 
     * @param showStatus
     *            Whether the status should be shown in the report.
     * @param description
     *            The description of the {@link ReportElement}.
     * @return The ReportElement for a Step. <code>null</code> if
     *         {@link #getReporter()} returns <code>null</code>.
     */
    protected ReportElement setUpReporter(boolean showStatus, String description)
    {
        ReportElement element = null;
        if (reporter != null)
        {
            element = reporter.step(keyword, description);
            if (getStatus() != null && showStatus)
            {
                // Assign the status as category
                element.assignCategory(getStatus());
            }
        }
        return element;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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

    @Override
    public AbstractStep<T> skip()
    {
        getStatus().add(Status.SKIP);
        return this;
    }

}