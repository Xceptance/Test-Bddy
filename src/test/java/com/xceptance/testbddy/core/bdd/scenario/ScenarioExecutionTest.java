package com.xceptance.testbddy.core.bdd.scenario;

import org.junit.Assert;
import org.junit.Test;

import com.xceptance.testbddy.core.bdd.scenario.Scenario;
import com.xceptance.testbddy.core.bdd.steps.Step;
import com.xceptance.testbddy.core.bdd.steps.Steps;
import com.xceptance.testbddy.core.throwables.errors.ScenarioError;
import com.xceptance.testbddy.core.throwables.errors.StepError;
import com.xceptance.testbddy.core.throwables.exceptions.ScenarioException;
import com.xceptance.testbddy.core.throwables.exceptions.StepException;
import com.xceptance.testbddy.util.ExecutionTest;

public class ScenarioExecutionTest extends ExecutionTest
{
    /**
     * Verifies that a {@link Scenario} can execute without {@link Steps}.
     */
    @Test
    public void shouldExecuteWithoutSteps()
    {
        String scenarioDescription = "Scenario Description";
        new Scenario(scenarioDescription).test();
    }

    /**
     * Verifies that a {@link Scenario} cannot execute with <code>null</code>
     * {@link Steps}.
     */
    @Test(expected = ScenarioException.class)
    public void shouldNotExecuteWithNullSteps()
    {
        String scenarioDescription = "Scenario Description";
        new Scenario(scenarioDescription, null).test();
    }

    /**
     * Verifies that a {@link Scenario} executes its {@link Steps} with a single
     * {@link Step}.
     * 
     */
    @Test
    public void shouldExecuteAStep()
    {
        String scenarioDescription = "Scenario Description";
        String stepDescription = "Step Description";
        Steps steps = new Steps().given(stepDescription, () ->
            {
                execution++;
            });
        new Scenario(scenarioDescription, steps).test();
        Assert.assertEquals(1, execution);
    }

    /**
     * Verifies that a {@link Scenario} throws a {@link ScenarioException} when its
     * {@link Steps} throw a {@link StepException}.
     * 
     */
    @Test(expected = ScenarioException.class)
    public void shouldThrowExceptionWhenStepThrowsOne()
    {
        String scenarioDescription = "Scenario Description";
        String stepDescription = "Step Description";
        Steps steps = new Steps().given(stepDescription, () ->
            {
                throw new IllegalArgumentException("Intentional Failure");
            });
        try
        {
            new Scenario(scenarioDescription, steps).test();
        } catch (ScenarioException exception)
        {
            throw exception;
        } catch (Throwable throwable)
        {
            Assert.fail("Unexpected Throwable thrown: " + throwable.getMessage());
        }
    }

    /**
     * Verifies that a {@link Scenario} throws a {@link ScenarioError} when its
     * {@link Steps} throw a {@link StepError}.
     * 
     */
    @Test(expected = ScenarioError.class)
    public void shouldThrowErrorWhenStepThrowsOne()
    {
        String scenarioDescription = "Scenario Description";
        String stepDescription = "Step Description";
        Steps steps = new Steps().given(stepDescription, () ->
            {
                Assert.fail("Intentional Failure");
            });
        try
        {
            new Scenario(scenarioDescription, steps).test();
        } catch (ScenarioError error)
        {
            throw error;
        } catch (Throwable throwable)
        {
            Assert.fail("Unexpected Throwable thrown: " + throwable.getMessage());
        }
    }

}
