package com.xceptance.testbddy.core.bdd.steps;

import java.util.ArrayList;

import com.aventstack.extentreports.GherkinKeyword;

/**
 * Describes a BDD Scenario.<br>
 * Thus, it has the given, when, then, and methods. To execute it, see the
 * {@link #test()} method.
 * 
 * @author ckeiner
 *
 */
public class Steps extends AbstractSteps<Step>
{
    /**
     * Creates new steps with an empty list.
     */
    public Steps()
    {
        super(new ArrayList<>());
    }

    @Override
    protected void executeStep(Step step)
    {
        step.test();
    }

    @Override
    protected void skipStep(Step step)
    {
        step.skipStep();
    }

    /**
     * Adds a new {@link Step} with the specified keyword, description and runner.
     * 
     * @param keyword
     *            The String, that describes the {@link GherkinKeyword}.
     * @param description
     *            The description of the step in a natural language.
     * @param runner
     *            The behavior of the step.
     * @see GherkinKeyword GherkinKeyword for possible options.
     */
    private void addStep(String keyword, String description, Runnable runner)
    {
        try
        {
            getSteps().add(new Step(new GherkinKeyword(keyword), description, runner));
        } catch (ClassNotFoundException e)
        {
            throw new IllegalStateException("Unknown Keyword " + keyword, e);
        }
    }

    /**
     * Adds a new {@link Step} with the specified keyword, description and runner.
     * 
     * @param keyword
     *            The {@link GherkinKeyword} of the step.
     * @param description
     *            The description of the step in a natural language.
     * @param runner
     *            The behavior of the step.
     * @see GherkinKeyword GherkinKeyword for possible options.
     */
    private void addStep(GherkinKeyword keyword, String description, Runnable runner)
    {
        getSteps().add(new Step(keyword, description, runner));
    }

    /**
     * Adds all {@link Step}s of the specified parameter to the steps of this class.
     * 
     * @param steps
     *            The {@link Steps} whose {@link Step}s should be added.
     */
    private void addAllSteps(Steps steps)
    {
        for (Step step : steps.getSteps())
        {
            addStep(step.getKeyword(), step.getDescription(), step.getBehavior());
        }
    }

    @Override
    public Steps and(final Steps steps)
    {
        addAllSteps(steps);
        return this;
    }

    @Override
    public Steps and(final String description, final Runnable runner)
    {
        addStep("And", description, runner);
        return this;
    }

    @Override
    public Steps given(final Steps steps)
    {
        addAllSteps(steps);
        return this;
    }

    @Override
    public Steps given(final String description, final Runnable runner)
    {
        addStep("Given", description, runner);
        return this;
    }

    @Override
    public Steps then(final Steps steps)
    {
        addAllSteps(steps);
        return this;
    }

    @Override
    public Steps then(final String description, final Runnable runner)
    {
        addStep("Then", description, runner);
        return this;
    }

    @Override
    public Steps when(final Steps steps)
    {
        addAllSteps(steps);
        return this;
    }

    @Override
    public Steps when(final String description, final Runnable runner)
    {
        addStep("When", description, runner);
        return this;
    }

    @Override
    public Steps ignore()
    {
        getSteps().get(getSteps().size() - 1).ignore();
        return this;
    }

    @Override
    public Steps wip()
    {
        getSteps().get(getSteps().size() - 1).wip();
        return this;
    }

    @Override
    public Steps skip()
    {
        getSteps().get(getSteps().size() - 1).skip();
        return this;
    }

}
