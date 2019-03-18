package com.xceptance.testbddy.core.bdd.scenario;

import org.junit.Assert;
import org.junit.Test;

import com.aventstack.extentreports.KeywordAccessor;
import com.aventstack.extentreports.gherkin.model.And;
import com.aventstack.extentreports.gherkin.model.Given;
import com.aventstack.extentreports.gherkin.model.Then;
import com.aventstack.extentreports.gherkin.model.When;
import com.xceptance.testbddy.core.bdd.scenario.OutlineDescriptor;
import com.xceptance.testbddy.core.bdd.steps.TypeStep;
import com.xceptance.testbddy.core.bdd.steps.TypeSteps;

public class OutlineDescriptorTest
{
    /**
     * Verifies the {@link OutlineDescriptor} can contain test data.
     */
    @Test
    public void canContainTestData()
    {
        // Create test data object
        Object testdata = new Object();
        // Create descriptor with the test data
        OutlineDescriptor<Object> descriptor = new OutlineDescriptor<>(testdata);
        // Assert that the size is correct
        Assert.assertEquals(1, descriptor.getTestdata().size());
        // Assert the test data is still the same
        Assert.assertEquals(testdata, descriptor.getTestdata().get(0));
        // Assert there aren't any steps
        Assert.assertTrue(descriptor.getSteps().getSteps().isEmpty());
    }

    /**
     * Verifies the {@link OutlineDescriptor} can contain steps.
     */
    @Test
    public void canContainSteps()
    {
        // Create steps
        TypeSteps<Object> steps = new TypeSteps<Object>();
        // Create descriptor with the steps
        OutlineDescriptor<Object> descriptor = new OutlineDescriptor<>(steps);
        // Verify there are no test data
        Assert.assertTrue(descriptor.getTestdata().isEmpty());
        // Verify the steps are the same
        Assert.assertEquals(steps, descriptor.getSteps());
    }

    /**
     * Verifies the {@link OutlineDescriptor} can contain test data and steps.
     */
    @Test
    public void canContainStepsAndTestData()
    {
        // Create test data object
        Object testdata = new Object();
        // Create steps
        TypeSteps<Object> steps = new TypeSteps<Object>();
        // Create descriptor with the steps and test data
        OutlineDescriptor<Object> descriptor = new OutlineDescriptor<>(steps, testdata);
        // Assert that the size is correct
        Assert.assertEquals(1, descriptor.getTestdata().size());
        // Assert the test data is still the same
        Assert.assertEquals(testdata, descriptor.getTestdata().get(0));
        // Assert the step is the same
        Assert.assertEquals(steps, descriptor.getSteps());
    }

    /**
     * Verifies the {@link OutlineDescriptor} can contain a given Step.
     */
    @Test
    public void canContainGivenStep()
    {
        // Create step description
        String stepDescription = "Step Description";
        // Create test data object
        Object testdata = new Object();
        // Create steps
        TypeSteps<Object> steps = new TypeSteps<Object>();
        // Create descriptor with the steps and test data
        OutlineDescriptor<Object> descriptor = new OutlineDescriptor<>(steps, testdata);
        // Call the given method
        descriptor.given(stepDescription, (Runnable) null);
        // Assert that the size is correct
        Assert.assertEquals(1, descriptor.getTestdata().size());
        // Assert the test data is still the same
        Assert.assertEquals(testdata, descriptor.getTestdata().get(0));
        // Verify the steps remain the same
        Assert.assertEquals(steps, descriptor.getSteps());
        // Verify there is one step
        Assert.assertEquals(1, steps.getSteps().size());
        // Get the step
        TypeStep<Object> step = steps.getSteps().get(0);
        // Verify the description
        Assert.assertEquals(stepDescription, step.getDescription());
        // Verify the behavior
        Assert.assertNull(step.getBehavior());
        // Verify the test data
        Assert.assertNull(step.getTestdata());
        // Verify the keyword
        Assert.assertTrue(KeywordAccessor.getKeyword(step.getKeyword()) instanceof Given);
    }

    /**
     * Verifies the {@link OutlineDescriptor} can contain a And Step.
     */
    @Test
    public void canContainAndStep()
    {
        // Create step description
        String stepDescription = "Step Description";
        // Create test data object
        Object testdata = new Object();
        // Create steps
        TypeSteps<Object> steps = new TypeSteps<Object>();
        // Create descriptor with the steps and test data
        OutlineDescriptor<Object> descriptor = new OutlineDescriptor<>(steps, testdata);
        // Call the given method
        descriptor.and(stepDescription, (Runnable) null);
        // Assert that the size is correct
        Assert.assertEquals(1, descriptor.getTestdata().size());
        // Assert the test data is still the same
        Assert.assertEquals(testdata, descriptor.getTestdata().get(0));
        // Verify the steps remain the same
        Assert.assertEquals(steps, descriptor.getSteps());
        // Verify there is one step
        Assert.assertEquals(1, steps.getSteps().size());
        // Get the step
        TypeStep<Object> step = steps.getSteps().get(0);
        // Verify the description
        Assert.assertEquals(stepDescription, step.getDescription());
        // Verify the behavior
        Assert.assertNull(step.getBehavior());
        // Verify the test data
        Assert.assertNull(step.getTestdata());
        // Verify the keyword
        Assert.assertTrue(KeywordAccessor.getKeyword(step.getKeyword()) instanceof And);
    }

    /**
     * Verifies the {@link OutlineDescriptor} can contain a given Step.
     */
    @Test
    public void canContainWhenStep()
    {
        // Create step description
        String stepDescription = "Step Description";
        // Create test data object
        Object testdata = new Object();
        // Create steps
        TypeSteps<Object> steps = new TypeSteps<Object>();
        // Create descriptor with the steps and test data
        OutlineDescriptor<Object> descriptor = new OutlineDescriptor<>(steps, testdata);
        // Call the given method
        descriptor.when(stepDescription, (Runnable) null);
        // Assert that the size is correct
        Assert.assertEquals(1, descriptor.getTestdata().size());
        // Assert the test data is still the same
        Assert.assertEquals(testdata, descriptor.getTestdata().get(0));
        // Verify the steps remain the same
        Assert.assertEquals(steps, descriptor.getSteps());
        // Verify there is one step
        Assert.assertEquals(1, steps.getSteps().size());
        // Get the step
        TypeStep<Object> step = steps.getSteps().get(0);
        // Verify the description
        Assert.assertEquals(stepDescription, step.getDescription());
        // Verify the behavior
        Assert.assertNull(step.getBehavior());
        // Verify the test data
        Assert.assertNull(step.getTestdata());
        // Verify the keyword
        Assert.assertTrue(KeywordAccessor.getKeyword(step.getKeyword()) instanceof When);
    }

    /**
     * Verifies the {@link OutlineDescriptor} can contain a given Step.
     */
    @Test
    public void canContainThenStep()
    {
        // Create step description
        String stepDescription = "Step Description";
        // Create test data object
        Object testdata = new Object();
        // Create steps
        TypeSteps<Object> steps = new TypeSteps<Object>();
        // Create descriptor with the steps and test data
        OutlineDescriptor<Object> descriptor = new OutlineDescriptor<>(steps, testdata);
        // Call the given method
        descriptor.then(stepDescription, (Runnable) null);
        // Assert that the size is correct
        Assert.assertEquals(1, descriptor.getTestdata().size());
        // Assert the test data is still the same
        Assert.assertEquals(testdata, descriptor.getTestdata().get(0));
        // Verify the steps remain the same
        Assert.assertEquals(steps, descriptor.getSteps());
        // Verify there is one step
        Assert.assertEquals(1, steps.getSteps().size());
        // Get the step
        TypeStep<Object> step = steps.getSteps().get(0);
        // Verify the description
        Assert.assertEquals(stepDescription, step.getDescription());
        // Verify the behavior
        Assert.assertNull(step.getBehavior());
        // Verify the test data
        Assert.assertNull(step.getTestdata());
        // Verify the keyword
        Assert.assertTrue(KeywordAccessor.getKeyword(step.getKeyword()) instanceof Then);
    }

}
