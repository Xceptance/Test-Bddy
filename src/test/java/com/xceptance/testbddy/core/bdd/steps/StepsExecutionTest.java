package com.xceptance.testbddy.core.bdd.steps;

import org.junit.Assert;
import org.junit.Test;

import com.xceptance.testbddy.core.bdd.steps.Step;
import com.xceptance.testbddy.core.bdd.steps.Steps;
import com.xceptance.testbddy.core.throwables.errors.StepError;
import com.xceptance.testbddy.core.throwables.exceptions.StepException;
import com.xceptance.testbddy.util.ExecutionTest;

public class StepsExecutionTest extends ExecutionTest
{
    // TODO Null Step added via given(Steps steps) -> given(null)

    /**
     * Verifies that {@link Steps} can execute without {@link Step}s
     */
    @Test
    public void shouldExecuteWithoutASingleStep()
    {
        // Execute empty steps
        new Steps().test();
    }

    /**
     * Verifies that {@link Steps} should execute the supplied {@link Step}.
     */
    @Test
    public void shouldExecuteOneStep()
    {
        String stepDescription = "A given step";
        // Create instance of steps
        Steps steps = new Steps();
        // Add a given step
        steps.given(stepDescription, () ->
            {
                execution++;
            });
        // Execute the steps
        steps.test();
        Assert.assertEquals(1, execution);
    }

    /**
     * Verifies that {@link Steps} should execute all {@link Step}s.
     */
    @Test
    public void shouldExecuteAllStep()
    {
        String stepDescription = "A step";
        // Create instance of steps
        Steps steps = new Steps();
        // Add a given step
        steps.given(stepDescription, () ->
            {
                execution++;
            });
        steps.when(stepDescription, () ->
            {
                execution++;
            });
        steps.and(stepDescription, () ->
            {
                execution++;
            });
        steps.then(stepDescription, () ->
            {
                execution++;
            });
        // Execute the steps
        steps.test();
        Assert.assertEquals(4, execution);
    }

    /**
     * Verifies that {@link Steps} should stop the execution if one {@link Step}
     * throws an exception.
     */
    @Test(expected = StepException.class)
    public void shouldStopExecutionIfAnExceptionIsThrown()
    {
        String stepDescription = "A step";
        // Create instance of steps
        Steps steps = new Steps();
        // Add a given step
        steps.given(stepDescription, () ->
            {
                execution++;
            });
        steps.when(stepDescription, () ->
            {
                throw new IllegalArgumentException("Intentional Failure");
            });
        steps.and(stepDescription, () ->
            {
                execution++;
            });
        steps.then(stepDescription, () ->
            {
                execution++;
            });
        // Execute the steps
        try
        {
            steps.test();
        } catch (StepException exception)
        {
            // Verify only one step was executed
            Assert.assertEquals(1, execution);
            throw exception;
        } catch (Throwable throwable)
        {
            Assert.fail("Unexpected throwable thrown: " + throwable);
        }
    }

    /**
     * Verifies that {@link Steps} should stop the execution if one {@link Step}
     * throws an error.
     */
    @Test(expected = StepError.class)
    public void shouldStopExecutionIfAnErrorIsThrown()
    {
        String stepDescription = "A step";
        // Create instance of steps
        Steps steps = new Steps();
        // Add a given step
        steps.given(stepDescription, () ->
            {
                execution++;
            });
        steps.when(stepDescription, () ->
            {
                Assert.fail("Intentional Failure");
            });
        steps.and(stepDescription, () ->
            {
                execution++;
            });
        steps.then(stepDescription, () ->
            {
                execution++;
            });
        // Execute the steps
        try
        {
            steps.test();
        } catch (StepError error)
        {
            // Verify only one step was executed
            Assert.assertEquals(1, execution);
            throw error;
        } catch (Throwable throwable)
        {
            Assert.fail("Unexpected throwable thrown: " + throwable.getMessage());
        }
    }

}
