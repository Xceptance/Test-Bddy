package com.xceptance.testbddy.core.bdd.steps;

import org.junit.Assert;
import org.junit.Test;

import com.aventstack.extentreports.GherkinKeyword;
import com.xceptance.testbddy.core.bdd.steps.Step;

public class StepDefinitionTest
{
    /**
     * Verifies that a {@link Step} can contain no behavior.
     * 
     * @throws ClassNotFoundException
     *             If the {@link GherkinKeyword} does not exist.
     */
    @Test
    public void canContainNoBehavior() throws ClassNotFoundException
    {
        // Create String containing the keyword
        String keywordString = "Given";
        // Create a GherkinKeyword for the step
        GherkinKeyword keyword = new GherkinKeyword(keywordString);
        // Create the description for a step
        String stepDescription = "Step Description";
        // Create Step with the keyword, description and no behavior
        Step step = new Step(keyword, stepDescription, null);
        // Assert the keyword is correct
        Assert.assertEquals(keyword, step.getKeyword());
        // Assert the description is correct
        Assert.assertEquals(stepDescription, step.getDescription());
        // Assert the behavior is correct
        Assert.assertNull(step.getBehavior());
        // Assert that the status is empty
        Assert.assertTrue(step.getStatus().isEmpty());
    }

    /**
     * Verifies that a {@link Step} can contain behavior.
     * 
     * @throws ClassNotFoundException
     *             If the {@link GherkinKeyword} does not exist.
     */
    @Test
    public void canContainBehavior() throws ClassNotFoundException
    {
        String keywordString = "Given";
        // Create instance of steps
        GherkinKeyword keyword = new GherkinKeyword(keywordString);
        String stepDescription = "Step Description";
        Runnable runner = () ->
            {
            };
        Step step = new Step(keyword, stepDescription, runner);
        // Assert the keyword is correct
        Assert.assertEquals(keyword, step.getKeyword());
        // Assert the description is correct
        Assert.assertEquals(stepDescription, step.getDescription());
        // Assert the behavior is correct
        Assert.assertEquals(runner, step.getBehavior());
        // Assert that the status is empty
        Assert.assertTrue(step.getStatus().isEmpty());
    }
}
