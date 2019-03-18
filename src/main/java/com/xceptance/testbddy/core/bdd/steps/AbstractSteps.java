package com.xceptance.testbddy.core.bdd.steps;

import java.util.ArrayList;
import java.util.List;

import com.aventstack.extentreports.gherkin.model.And;
import com.aventstack.extentreports.gherkin.model.Given;
import com.aventstack.extentreports.gherkin.model.Then;
import com.aventstack.extentreports.gherkin.model.When;
import com.xceptance.testbddy.core.bdd.status.Statusable;
import com.xceptance.testbddy.core.reporting.ReportInterface;
import com.xceptance.testbddy.core.throwables.errors.StepError;
import com.xceptance.testbddy.core.throwables.exceptions.StepException;

/**
 * Describes multiple steps.
 * 
 * @param <T>
 *            The type of the steps.
 * @author ckeiner
 */
public abstract class AbstractSteps<T extends AbstractStep<?>> implements Statusable
{
    /**
     * The class responsible for reporting.
     */
    private ReportInterface reporter;

    /**
     * A list of all {@link Step}s.
     */
    private final List<T> steps;

    /**
     * Creates an AbstractSteps with an empty list.
     */
    public AbstractSteps()
    {
        this(new ArrayList<>());
    }

    /**
     * Creates AbstractSteps with the specified list of {@link Step}s.
     * 
     * @param steps
     *            The list of steps that specify the behavior.
     */
    public AbstractSteps(final List<T> steps)
    {
        this.steps = steps;
    }

    /**
     * Skips the specified step.
     * 
     * @param step
     *            The step to skip.
     */
    protected abstract void skipStep(T step);

    /**
     * Executes the specified step.
     * 
     * @param step
     *            The step to execute.
     */
    protected abstract void executeStep(T step);

    /**
     * Executes all {@link Step}s.<br>
     * If an exception or error occurs in any step, the steps which weren't executed
     * yet, are set to be skipped and the throwable is re-thrown.<br>
     * Should both, an exception and an error, occur, a StepException is thrown.
     * 
     * @throws StepException
     *             If a StepException occurred in any Step.
     * @throws StepError
     *             If an StepError and no StepExceptions occurred in any Step.
     */
    public void test()
    {
        StepException stepException = null;
        StepError stepError = null;

        // Execute each step
        for (final T step : getSteps())
        {
            if (step.getReporter() == null && this.getReporter() != null)
            {
                step.setReporter(getReporter());
            }
            try
            {
                // If an exception or error occured in a previous step, skip this step
                if (stepException != null || stepError != null)
                {
                    skipStep(step);
                }
                // Otherwise test it
                else
                {
                    executeStep(step);
                }
            } catch (StepException e)
            {
                stepException = e;
            } catch (StepError e)
            {
                stepError = e;
            }
        }

        if (stepException != null)
        {
            throw stepException;
        }
        else if (stepError != null)
        {
            throw stepError;
        }
    }

    /**
     * Doesn't execute the behavior, but shows them in the report as skipped.
     */
    public void skipSteps()
    {
        for (final T step : getSteps())
        {
            if (step.getReporter() == null && this.getReporter() != null)
            {
                step.setReporter(getReporter());
            }
            skipStep(step);
        }
    }

    public ReportInterface getReporter()
    {
        return reporter;
    }

    public void setReporter(ReportInterface reporter)
    {
        this.reporter = reporter;
    }

    public List<T> getSteps()
    {
        return steps;
    }

    /**
     * Adds all steps of the specified {@link Steps} to the {@link #steps}.<br>
     * 
     * @param steps
     *            The {@link Steps} whose {@link Step}s should be added.
     * @return The current Steps.
     */
    public abstract AbstractSteps<T> and(final Steps steps);

    /**
     * Adds the step with the supplied description and runner to the
     * {@link #steps}.<br>
     * The keyword of the Step is {@link And}.
     * 
     * @param description
     *            The description of the step.
     * @param runner
     *            The behavior of the step.
     * @return The current {@link AbstractSteps}.
     */
    public abstract AbstractSteps<T> and(final String description, final Runnable runner);

    /**
     * Adds all steps of the specified {@link Steps} to the {@link #steps}.<br>
     * 
     * @param steps
     *            The {@link Steps} whose {@link Step}s should be added.
     * @return The current Steps.
     */
    public abstract AbstractSteps<T> given(final Steps steps);

    /**
     * Adds the step with the supplied description and runner to the
     * {@link #steps}.<br>
     * The keyword of the Step is {@link Given}.
     * 
     * @param description
     *            The description of the step.
     * @param runner
     *            The behavior of the step.
     * @return The current {@link AbstractSteps}.
     */
    public abstract AbstractSteps<T> given(final String description, final Runnable runner);

    /**
     * Adds all steps of the specified {@link Steps} to the {@link #steps}.<br>
     * 
     * @param steps
     *            The {@link Steps} whose {@link Step}s should be added.
     * @return The current Steps.
     */
    public abstract AbstractSteps<T> then(final Steps steps);

    /**
     * Adds the step with the supplied description and runner to the
     * {@link #steps}.<br>
     * The keyword of the Step is {@link Then}.
     * 
     * @param description
     *            The description of the step.
     * @param runner
     *            The behavior of the step.
     * @return The current {@link AbstractSteps}.
     */
    public abstract AbstractSteps<T> then(final String description, final Runnable runner);

    /**
     * Adds all steps of the specified {@link Steps} to the {@link #steps}.<br>
     * 
     * @param steps
     *            The {@link Steps} whose {@link Step}s should be added.
     * @return The current Steps.
     */
    public abstract AbstractSteps<T> when(final Steps steps);

    /**
     * Adds the step with the supplied description and runner to the
     * {@link #steps}.<br>
     * The keyword of the Step is {@link When}.
     * 
     * @param description
     *            The description of the step.
     * @param runner
     *            The behavior of the step.
     * @return The current {@link AbstractSteps}.
     */
    public abstract AbstractSteps<T> when(final String description, final Runnable runner);

    /**
     * The last added step is ignored.
     */
    @Override
    public abstract AbstractSteps<T> ignore();

    /**
     * The last added step is marked as a work in progress.
     */
    @Override
    public abstract AbstractSteps<T> wip();

    /**
     * The last added step is skipped.
     */
    @Override
    public abstract AbstractSteps<T> skip();

}
