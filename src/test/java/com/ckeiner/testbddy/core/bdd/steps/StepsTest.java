package com.ckeiner.testbddy.core.bdd.steps;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.aventstack.extentreports.KeywordAccessor;
import com.aventstack.extentreports.gherkin.model.And;
import com.aventstack.extentreports.gherkin.model.Given;
import com.aventstack.extentreports.gherkin.model.Then;
import com.aventstack.extentreports.gherkin.model.When;

public class StepsTest
{
    /**
     * Verifies that {@link Steps} can contain no {@link Step}s
     */
    @Test
    public void canContainNoStep()
    {
        // Create instance of steps
        Steps steps = new Steps();
        // Assert there is an empty list of Step
        Assert.assertTrue(steps.getSteps().isEmpty());
    }

    /**
     * Verifies that {@link Steps} can contain no {@link Step}s
     */
    @Test
    public void canContainEmptyStep()
    {
        // Create list of steps
        List<Step> stepList = new ArrayList<Step>();
        // Create instance of steps with stepList
        Steps steps = new Steps(stepList);
        // Assert there is an empty list of Step
        Assert.assertTrue(steps.getSteps().isEmpty());
    }

    /**
     * Verifies that {@link Steps} can contain a {@link Step}s with
     * {@link Steps#given(String, Runnable)}.
     */
    @Test
    // TODO How to handle this case?
    public void canContainAStepWithNullList()
    {
        // Create instance of steps with null
        Steps steps = new Steps(null);
        Assert.assertNull(steps.getSteps());

        String stepDescription = "A given step";
        // Add a given step
        steps.given(stepDescription, (Runnable) null);
        // Assert that the list isn't null anymore
        Assert.assertTrue(steps.getSteps() != null);
        // Assert the list isn't empty
        Assert.assertFalse(steps.getSteps().isEmpty());
        // Assert the list has one step
        Assert.assertEquals(1, steps.getSteps().size());
        // Get the step
        Step step = steps.getSteps().get(0);
        // Assert the step description is correct
        Assert.assertEquals(stepDescription, step.getDescription());
        // Assert that the keyword is correct
        Assert.assertTrue(KeywordAccessor.getKeyword(step.getKeyword()) instanceof Given);
        // Assert the behavior is null
        Assert.assertNull(step.getBehavior());
        // Assert the status of the step is empty
        Assert.assertTrue(step.getStatus().isEmpty());
    }

    /**
     * Verifies that {@link Steps} can contain a {@link Step}s with
     * {@link Steps#given(String, Runnable)}.
     */
    @Test
    public void canContainAGivenStep()
    {
        String stepDescription = "A given step";
        // Create instance of steps
        Steps steps = new Steps();
        // Add a given step
        steps.given(stepDescription, (Runnable) null);
        // Assert the list isn't empty
        Assert.assertFalse(steps.getSteps().isEmpty());
        // Assert the list has one step
        Assert.assertEquals(1, steps.getSteps().size());
        // Get the step
        Step step = steps.getSteps().get(0);
        // Assert the step description is correct
        Assert.assertEquals(stepDescription, step.getDescription());
        // Assert that the keyword is correct
        Assert.assertTrue(KeywordAccessor.getKeyword(step.getKeyword()) instanceof Given);
        // Assert the behavior is null
        Assert.assertNull(step.getBehavior());
        // Assert the status of the step is empty
        Assert.assertTrue(step.getStatus().isEmpty());
    }

    /**
     * Verifies that {@link Steps} can contain a {@link Step}s with
     * {@link Steps#when(String, Runnable)}.
     */
    @Test
    public void canContainAWhenStep()
    {
        String stepDescription = "A given step";
        // Create instance of steps
        Steps steps = new Steps();
        // Add a when step
        steps.when(stepDescription, (Runnable) null);
        // Assert the list isn't empty
        Assert.assertFalse(steps.getSteps().isEmpty());
        // Assert the list has one step
        Assert.assertEquals(1, steps.getSteps().size());
        // Get the step
        Step step = steps.getSteps().get(0);
        // Assert the step description is correct
        Assert.assertEquals(stepDescription, step.getDescription());
        // Assert that the keyword is correct
        Assert.assertTrue(KeywordAccessor.getKeyword(step.getKeyword()) instanceof When);
        // Assert the behavior is null
        Assert.assertNull(step.getBehavior());
        // Assert the status of the step is empty
        Assert.assertTrue(step.getStatus().isEmpty());
    }

    /**
     * Verifies that {@link Steps} can contain a {@link Step}s with
     * {@link Steps#and(String, Runnable)}.
     */
    @Test
    public void canContainAnAndStep()
    {
        String stepDescription = "A given step";
        // Create instance of steps
        Steps steps = new Steps();
        // Add a and step
        steps.and(stepDescription, (Runnable) null);
        // Assert the list isn't empty
        Assert.assertFalse(steps.getSteps().isEmpty());
        // Assert the list has one step
        Assert.assertEquals(1, steps.getSteps().size());
        // Get the step
        Step step = steps.getSteps().get(0);
        // Assert the step description is correct
        Assert.assertEquals(stepDescription, step.getDescription());
        // Assert that the keyword is correct
        Assert.assertTrue(KeywordAccessor.getKeyword(step.getKeyword()) instanceof And);
        // Assert the behavior is null
        Assert.assertNull(step.getBehavior());
        // Assert the status of the step is empty
        Assert.assertTrue(step.getStatus().isEmpty());
    }

    /**
     * Verifies that {@link Steps} can contain a {@link Step}s with
     * {@link Steps#then(String, Runnable)}.
     */
    @Test
    public void canContainAThenStep()
    {
        String stepDescription = "A given step";
        // Create instance of steps
        Steps steps = new Steps();
        // Add a then step
        steps.then(stepDescription, (Runnable) null);
        // Assert the list isn't empty
        Assert.assertFalse(steps.getSteps().isEmpty());
        // Assert the list has one step
        Assert.assertEquals(1, steps.getSteps().size());
        // Get the step
        Step step = steps.getSteps().get(0);
        // Assert the step description is correct
        Assert.assertEquals(stepDescription, step.getDescription());
        // Assert that the keyword is correct
        Assert.assertTrue(KeywordAccessor.getKeyword(step.getKeyword()) instanceof Then);
        // Assert the behavior is null
        Assert.assertNull(step.getBehavior());
        // Assert the status of the step is empty
        Assert.assertTrue(step.getStatus().isEmpty());
    }

}
