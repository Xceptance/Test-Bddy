package com.xceptance.testbddy.core.bdd.steps;

import org.junit.Assert;
import org.junit.Test;

import com.xceptance.testbddy.core.bdd.steps.TypeStep;
import com.xceptance.testbddy.core.bdd.steps.TypeSteps;
import com.xceptance.testbddy.core.throwables.errors.StepError;
import com.xceptance.testbddy.core.throwables.exceptions.StepException;
import com.xceptance.testbddy.util.ExecutionTest;

public class TypeStepsExecutionTest extends ExecutionTest
{
    // TODO Null Step added via given(Steps steps) -> given(null)

    /**
     * Verifies that {@link TypeSteps} can execute without {@link TypeStep}s.
     */
    @Test
    public void shouldExecuteWithoutASingleStep()
    {
        // Execute empty steps
        new TypeSteps<Object>().withData(new Object()).test();
    }

    /**
     * Verifies that {@link TypeSteps} executes the supplied {@link TypeStep}.
     */
    @Test(expected = StepException.class)
    public void shouldNotExecuteWithoutTestdata()
    {
        String stepDescription = "A given step";
        // Create instance of steps
        TypeSteps<Object> steps = new TypeSteps<Object>();
        // Add a given step
        steps.given(stepDescription, () ->
            {
            });
        // Execute the step
        steps.test();
    }

    /**
     * Verifies that {@link TypeSteps} executes the supplied {@link TypeStep}.
     */
    @Test
    public void shouldExecuteWithARunnableStep()
    {
        // Create testdata
        Object testdata = new Object();
        String stepDescription = "A given step";
        // Create instance of steps
        TypeSteps<Object> steps = new TypeSteps<Object>();
        // Add a given step
        steps.given(stepDescription, () ->
            {
                execution++;
            });
        // Execute the step
        steps.withData(testdata).test();
        Assert.assertEquals(1, execution);
    }

    /**
     * Verifies that {@link TypeSteps} executes the supplied {@link TypeStep} and
     * verifies that the test data is the same as the supplied one.
     */
    @Test
    public void shouldExecuteWithAConsumerStep()
    {
        Object testdata = new Object();
        String stepDescription = "A given step";
        // Create instance of steps
        TypeSteps<Object> steps = new TypeSteps<Object>();
        // Add a given step
        steps.given(stepDescription, (data) ->
            {
                Assert.assertSame(testdata, data);
                execution++;
            });
        // Execute the step
        steps.withData(testdata).test();
        Assert.assertEquals(1, execution);
    }

    /**
     * Verifies that {@link TypeSteps} executes multiple {@link TypeStep}.
     */
    @Test
    public void shouldExecuteMultipleSteps()
    {
        Object testdata = new Object();
        String stepDescription = "A step";
        // Create instance of steps
        TypeSteps<Object> steps = new TypeSteps<Object>();
        // Add a given step
        steps.given(stepDescription, (data) ->
            {
                Assert.assertSame(testdata, data);
                execution++;
            });
        // Add a when step
        steps.when(stepDescription, () ->
            {
                execution++;
            });
        // Add a then step
        steps.then(stepDescription, (data) ->
            {
                Assert.assertSame(testdata, data);
                execution++;
            });
        // Add an and step
        steps.and(stepDescription, () ->
            {
                execution++;
            });
        steps.withData(testdata).test();
        Assert.assertEquals(4, execution);
    }

    /**
     * Verifies that {@link TypeSteps} stop the execution if one {@link TypeStep}
     * throws an Exception.
     */
    @Test(expected = StepException.class)
    public void shouldStopExecutionIfAnExceptionIsThrown()
    {
        Object testdata = new Object();
        String stepDescription = "A step";
        // Create instance of steps
        TypeSteps<Object> steps = new TypeSteps<Object>();
        // Add a given step
        steps.given(stepDescription, (data) ->
            {
                Assert.assertSame(testdata, data);
                execution++;
            });
        // Add a when step
        steps.when(stepDescription, () ->
            {
                throw new IllegalArgumentException("Intentional Failure");
            });
        // Add a then step
        steps.then(stepDescription, (data) ->
            {
                Assert.assertSame(testdata, data);
                execution++;
            });
        // Add an and step
        steps.and(stepDescription, () ->
            {
                execution++;
            });
        try
        {
            steps.withData(testdata).test();
        } catch (StepException exception)
        {
            Assert.assertEquals(1, execution);
            throw exception;
        } catch (Throwable throwable)
        {
            Assert.fail("Unexpected throwable thrown: " + throwable.getMessage());
        }
    }

    /**
     * Verifies that {@link TypeSteps} stop the execution if one {@link TypeStep}
     * throws an Error.
     */
    @Test(expected = StepError.class)
    public void shouldStopExecutionIfAnErrorIsThrown()
    {
        Object testdata = new Object();
        String stepDescription = "A step";
        // Create instance of steps
        TypeSteps<Object> steps = new TypeSteps<Object>();
        // Add a given step
        steps.given(stepDescription, (data) ->
            {
                Assert.assertSame(testdata, data);
                execution++;
            });
        // Add a when step
        steps.when(stepDescription, () ->
            {
                Assert.fail("Intentional Failure");
            });
        // Add a then step
        steps.then(stepDescription, (data) ->
            {
                Assert.assertSame(testdata, data);
                execution++;
            });
        // Add an and step
        steps.and(stepDescription, () ->
            {
                execution++;
            });
        try
        {
            steps.withData(testdata).test();
        } catch (StepError error)
        {
            Assert.assertEquals(1, execution);
            throw error;
        } catch (Throwable throwable)
        {
            Assert.fail("Unexpected throwable thrown: " + throwable.getMessage());
        }
    }
}
