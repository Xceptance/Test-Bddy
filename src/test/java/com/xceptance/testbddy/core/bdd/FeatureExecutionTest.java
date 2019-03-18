package com.xceptance.testbddy.core.bdd;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.xceptance.testbddy.core.bdd.Feature;
import com.xceptance.testbddy.core.bdd.scenario.AbstractScenario;
import com.xceptance.testbddy.core.bdd.scenario.Scenario;
import com.xceptance.testbddy.core.bdd.steps.Steps;
import com.xceptance.testbddy.core.throwables.errors.FeatureError;
import com.xceptance.testbddy.core.throwables.exceptions.FeatureException;
import com.xceptance.testbddy.util.ExecutionTest;

public class FeatureExecutionTest extends ExecutionTest
{

    // TODO Null Scenario added in between

    /**
     * Verifies that a features fails during the execution if it the
     * {@link Feature#getScenarios()} is <code>null</code>.
     */
    @Test(expected = FeatureException.class)
    public void shouldNotExecuteWithNullScenario()
    {
        String featureDescription = "Feature Description";
        // Create feature consisting only of the description
        new Feature(featureDescription, null).test();
    }

    /**
     * Verifies that a feature can execute a scenario.
     */
    @Test
    public void shouldExecuteAScenario()
    {
        String stepDescription = "Step Description";
        String scenarioDescription = "Scenario Description";
        String featureDescription = "Feature Description";

        // Create steps
        Steps steps = new Steps().given(stepDescription, () ->
            {
                execution++;
            });
        // Create Scenario with steps and a description
        Scenario scenario = new Scenario(scenarioDescription, steps);
        // Create list to give to the feature
        List<AbstractScenario> scenarioList = new ArrayList<AbstractScenario>();
        scenarioList.add(scenario);

        // TODO blöcke kommentieren vllt auch nice, dann hat man keine kommentare
        // dazwischen
        // TODO Assert no execution happened yet; precondition
        Assert.assertEquals(0, execution);

        // Create feature consisting of the description and the scenarioList
        new Feature(featureDescription, scenarioList).test();

        // Assert one execution happened
        Assert.assertEquals(1, execution);
    }

    /**
     * Verifies that a feature can execute multiple scenarios.
     */
    @Test
    public void shouldExecuteMultipleScenarios()
    {
        String stepDescription = "Step Description";
        String scenarioDescription = "Scenario Description";
        String anotherScenarioDescription = "Another Scenario Description";
        String featureDescription = "Feature Description";
        Steps steps = new Steps().given(stepDescription, () ->
            {
                execution++;
            });
        List<AbstractScenario> scenarioList = new ArrayList<AbstractScenario>();
        Scenario scenario = new Scenario(scenarioDescription, steps);
        Scenario anotherScenario = new Scenario(anotherScenarioDescription, steps);
        scenarioList.add(scenario);
        scenarioList.add(anotherScenario);
        new Feature(featureDescription, scenarioList).test();
        // Verify two executions happened
        Assert.assertEquals(2, execution);
    }

    /**
     * Verifies that a feature executes all scenarios even if one fails with an
     * error.
     */
    @Test(expected = FeatureError.class)
    public void shouldExecuteMultipleScenariosEvenIfOneThrowsError()
    {
        String stepDescription = "Step Description";
        String scenarioDescription = "Scenario Description";
        String anotherScenarioDescription = "Another Scenario Description";
        String featureDescription = "Feature Description";
        Steps steps = new Steps().given(stepDescription, () ->
            {
                execution++;
            });
        Steps errorSteps = new Steps().given(stepDescription, () ->
            {
                Assert.fail("Intentional failure");
            });
        List<AbstractScenario> scenarioList = new ArrayList<AbstractScenario>();
        Scenario scenario = new Scenario(scenarioDescription, steps);
        Scenario errorScenario = new Scenario(anotherScenarioDescription, errorSteps);
        scenarioList.add(scenario);
        scenarioList.add(errorScenario);
        scenarioList.add(scenario);
        try
        {
            new Feature(featureDescription, scenarioList).test();
        } catch (FeatureError error)
        {
            // Verify two executions happened
            Assert.assertEquals(2, execution);
            throw error;
        } catch (Throwable throwable)
        {
            Assert.fail("Unexpected throwable thrown: " + throwable.getMessage());
        }
    }

    /**
     * Verifies that a feature executes all scenarios even if one fails with an
     * exception.
     */
    @Test(expected = FeatureException.class)
    public void shouldExecuteMultipleScenariosEvenIfOneThrowsException()
    {
        String stepDescription = "Step Description";
        String scenarioDescription = "Scenario Description";
        String anotherScenarioDescription = "Another Scenario Description";
        String featureDescription = "Feature Description";
        Steps steps = new Steps().given(stepDescription, () ->
            {
                execution++;
            });
        Steps exceptionSteps = new Steps().given(stepDescription, () ->
            {
                throw new IllegalArgumentException("Intentional failure");
            });
        List<AbstractScenario> scenarioList = new ArrayList<AbstractScenario>();
        Scenario scenario = new Scenario(scenarioDescription, steps);
        Scenario exceptionScenario = new Scenario(anotherScenarioDescription, exceptionSteps);
        scenarioList.add(scenario);
        scenarioList.add(exceptionScenario);
        scenarioList.add(scenario);
        try
        {
            new Feature(featureDescription, scenarioList).test();
        } catch (FeatureException exception)
        {
            // Verify two executions happened
            Assert.assertEquals(2, execution);
            throw exception;
        } catch (Throwable throwable)
        {
            Assert.fail("Unexpected throwable thrown: " + throwable.getMessage());
        }
    }

}
