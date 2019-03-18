package com.xceptance.testbddy.core.bdd.steps;

import com.aventstack.extentreports.GherkinKeyword;

/**
 * Describes a BDD Step without test data. <br>
 * To execute it, see the {@link #test()} method.
 * 
 * @author ckeiner
 */
public class Step extends AbstractStep<Runnable>
{

    /**
     * Creates a Step with the specified keyword, description and behavior.
     * 
     * @param keyword
     *            The {@link GherkinKeyword} of the step.
     * @param description
     *            A String describing what this step does.
     * @param behavior
     *            A Runnable containing the behavior.
     * @see AbstractStep#AbstractStep(GherkinKeyword, String, Object)
     * @see GherkinKeyword
     */
    public Step(GherkinKeyword keyword, String description, Runnable behavior)
    {
        super(keyword, description, behavior);
    }

    /**
     * Runs the Runnable.
     */
    @Override
    protected void executeStep()
    {
        getBehavior().run();
    }

}
