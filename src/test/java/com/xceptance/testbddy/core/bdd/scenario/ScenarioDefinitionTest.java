package com.xceptance.testbddy.core.bdd.scenario;

import org.junit.Assert;
import org.junit.Test;

import com.aventstack.extentreports.KeywordAccessor;
import com.aventstack.extentreports.gherkin.model.Given;
import com.aventstack.extentreports.gherkin.model.When;
import com.xceptance.testbddy.core.bdd.scenario.Scenario;
import com.xceptance.testbddy.core.bdd.steps.Step;
import com.xceptance.testbddy.core.bdd.steps.Steps;

public class ScenarioDefinitionTest
{
    /**
     * Verifies that a {@link Scenario} can contain no {@link Steps}.
     */
    @Test
    public void canContainNoSteps()
    {
        String scenarioDescription = "Scenario Description";
        Scenario scenario = new Scenario(scenarioDescription, null);

        Assert.assertEquals(scenarioDescription, scenario.getDescription());
        Assert.assertNull(scenario.getSteps());
        Assert.assertTrue(scenario.getStatus().isEmpty());
    }

    /**
     * Verifies that a {@link Scenario} can contain a {@link Steps} with a single
     * {@link Step}.
     * 
     */
    @Test
    public void canContainAStep()
    {
        String scenarioDescription = "Scenario Description";
        String stepDescription = "Step Description";
        Steps steps = new Steps().given(stepDescription, null);
        Scenario scenario = new Scenario(scenarioDescription, steps);

        // Verify the description is correct
        Assert.assertEquals(scenarioDescription, scenario.getDescription());
        // Verify there is no status
        Assert.assertTrue(scenario.getStatus().isEmpty());
        // Verify the steps are the same
        Assert.assertEquals(steps, scenario.getSteps());
        Assert.assertEquals(1, scenario.getSteps().getSteps().size());
        Step step = scenario.getSteps().getSteps().get(0);
        Assert.assertEquals(stepDescription, step.getDescription());
        Assert.assertNull(step.getBehavior());
        Assert.assertTrue(KeywordAccessor.getKeyword(step.getKeyword()) instanceof Given);
        Assert.assertTrue(step.getStatus().isEmpty());
    }

    /**
     * Verifies that a {@link Scenario} can contain {@link Steps} with multiple
     * {@link Step}s.
     */
    @Test
    public void canContainMultipleSteps()
    {
        String scenarioDescription = "Scenario Description";
        String stepDescription = "Step Description";
        String anotherStepDescription = "Another Step Description";
        Steps steps = new Steps().given(stepDescription, null).when(anotherStepDescription, null);
        Scenario scenario = new Scenario(scenarioDescription, steps);

        Assert.assertEquals(scenarioDescription, scenario.getDescription());
        Assert.assertTrue(scenario.getStatus().isEmpty());
        Assert.assertEquals(steps, scenario.getSteps());
        Assert.assertEquals(2, scenario.getSteps().getSteps().size());

        Step step = scenario.getSteps().getSteps().get(0);
        Assert.assertEquals(stepDescription, step.getDescription());
        Assert.assertNull(step.getBehavior());
        Assert.assertTrue(KeywordAccessor.getKeyword(step.getKeyword()) instanceof Given);
        Assert.assertTrue(step.getStatus().isEmpty());

        Step anotherStep = scenario.getSteps().getSteps().get(1);
        Assert.assertEquals(anotherStepDescription, anotherStep.getDescription());
        Assert.assertNull(anotherStep.getBehavior());
        Assert.assertTrue(KeywordAccessor.getKeyword(anotherStep.getKeyword()) instanceof When);
        Assert.assertTrue(anotherStep.getStatus().isEmpty());
    }

}
