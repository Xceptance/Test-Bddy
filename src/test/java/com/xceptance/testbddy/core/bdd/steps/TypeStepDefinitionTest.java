package com.xceptance.testbddy.core.bdd.steps;

import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Test;

import com.aventstack.extentreports.GherkinKeyword;
import com.xceptance.testbddy.core.bdd.steps.Step;
import com.xceptance.testbddy.core.bdd.steps.TypeStep;

public class TypeStepDefinitionTest
{
    /**
     * Verifies that a {@link TypeStep} can contain no behavior.
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
        TypeStep<Object> step = new TypeStep<Object>(keyword, stepDescription, null);
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
        Consumer<Object> consumer = (data) ->
            {
            };
        TypeStep<Object> step = new TypeStep<Object>(keyword, stepDescription, consumer);
        // Assert the keyword is correct
        Assert.assertEquals(keyword, step.getKeyword());
        // Assert the description is correct
        Assert.assertEquals(stepDescription, step.getDescription());
        // Assert the behavior is correct
        Assert.assertEquals(consumer, step.getBehavior());
        // Assert that the status is empty
        Assert.assertTrue(step.getStatus().isEmpty());
    }

    /**
     * Verifies that a {@link TypeStep} can contain test data.
     * 
     * @throws ClassNotFoundException
     *             If the {@link GherkinKeyword} does not exist.
     */
    @Test
    public void canContainTestdata() throws ClassNotFoundException
    {
        // Create String containing the keyword
        String keywordString = "Given";
        // Create a GherkinKeyword for the step
        GherkinKeyword keyword = new GherkinKeyword(keywordString);
        // Create the description for a step
        String stepDescription = "Step Description";
        // Create Step with the keyword, description and no behavior
        TypeStep<Object> step = new TypeStep<Object>(keyword, stepDescription, null);
        // Assert there is no test data
        Assert.assertNull(step.getTestdata());
        // Add test data
        Object someObject = new Object();
        step.withData(someObject);
        // Assert the test data is set
        Assert.assertEquals(someObject, step.getTestdata());

        // Create new object
        someObject = new Object();
        // Replace old test data with the new one
        step.withData(someObject);
        // Assert the new test data replaced the old one
        Assert.assertEquals(someObject, step.getTestdata());
    }
}
