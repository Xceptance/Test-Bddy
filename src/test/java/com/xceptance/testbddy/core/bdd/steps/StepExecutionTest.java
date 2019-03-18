package com.xceptance.testbddy.core.bdd.steps;

import org.junit.Assert;
import org.junit.Test;

import com.aventstack.extentreports.GherkinKeyword;
import com.xceptance.testbddy.core.bdd.steps.Step;
import com.xceptance.testbddy.core.throwables.exceptions.StepException;
import com.xceptance.testbddy.util.ExecutionTest;

public class StepExecutionTest extends ExecutionTest
{

    /**
     * Verifies that a {@link Step} cannot execute without behavior.
     * 
     * @throws ClassNotFoundException
     *             If the {@link GherkinKeyword} does not exist.
     */
    @Test(expected = StepException.class)
    public void shouldNotExecuteWithoutBehavior() throws ClassNotFoundException
    {
        // Create String containing the keyword
        String keywordString = "Given";
        // Create a GherkinKeyword for the step
        GherkinKeyword keyword = new GherkinKeyword(keywordString);
        // Create the description for a step
        String stepDescription = "Step Description";
        // Create Step with the keyword, description and no behavior
        new Step(keyword, stepDescription, null).test();

    }

    /**
     * Verifies that a {@link Step} executes the behavior.
     * 
     * @throws ClassNotFoundException
     *             If the {@link GherkinKeyword} does not exist.
     */
    @Test
    public void shouldExecuteBehavior() throws ClassNotFoundException
    {
        String keywordString = "Given";
        // Create instance of steps
        GherkinKeyword keyword = new GherkinKeyword(keywordString);
        String stepDescription = "Step Description";
        Runnable runner = () ->
            {
                execution++;
            };
        // Create and execute Step with the keyword, description and the behavior
        new Step(keyword, stepDescription, runner).test();
        // Assert that the runnable was executed
        Assert.assertEquals(1, execution);
    }

}
