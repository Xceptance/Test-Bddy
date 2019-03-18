package com.xceptance.testbddy.core.bdd.scenario;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Test;

import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.KeywordAccessor;
import com.aventstack.extentreports.gherkin.model.Given;
import com.aventstack.extentreports.gherkin.model.When;
import com.xceptance.testbddy.core.bdd.scenario.ScenarioOutline;
import com.xceptance.testbddy.core.bdd.steps.TypeStep;
import com.xceptance.testbddy.core.bdd.steps.TypeSteps;

public class ScenarioOutlineDefinitionTest
{
    /**
     * Verifies that a {@link ScenarioOutline} can contain no steps
     */
    @Test
    public void canContainNoStepsAndNoTestdata()
    {
        String scenarioDescription = "Scenario Description";
        ScenarioOutline<Object> scenario = new ScenarioOutline<Object>(scenarioDescription, null, null);
        Assert.assertEquals(scenarioDescription, scenario.getDescription());
        Assert.assertNull(scenario.getTestdata());
        Assert.assertNull(scenario.getSteps());
        Assert.assertTrue(scenario.getStatus().isEmpty());
    }

    /**
     * Verifies that a {@link ScenarioOutline} can contain a step with
     * {@link Runnable} that is <code>null</code> and doesn't change its values
     * 
     * @throws ClassNotFoundException
     *             If the {@link GherkinKeyword} does not exist.
     */
    @Test
    public void canContainARunnableStep() throws ClassNotFoundException
    {
        String scenarioDescription = "Scenario Description";
        String stepDescription = "Step Description";
        // TODO is this evil? -> Null immer Fehler, also schöner das zu def'fen
        TypeSteps<Object> steps = new TypeSteps<Object>().given(stepDescription, (Runnable) null);
        Object testdata = new Object();
        List<Object> testdataList = new ArrayList<>();
        testdataList.add(testdata);
        ScenarioOutline<Object> scenario = new ScenarioOutline<Object>(scenarioDescription, steps, testdataList);

        Assert.assertEquals(scenarioDescription, scenario.getDescription());
        Assert.assertTrue(scenario.getStatus().isEmpty());
        // Assert there is one test datum
        Assert.assertEquals(1, scenario.getTestdata().size());
        // Assert the test datum is correct
        Assert.assertEquals(testdata, scenario.getTestdata().get(0));
        Assert.assertEquals(steps, scenario.getSteps());
        Assert.assertEquals(1, scenario.getSteps().getSteps().size());
        TypeStep<Object> step = scenario.getSteps().getSteps().get(0);
        Assert.assertEquals(stepDescription, step.getDescription());
        Assert.assertNull(step.getBehavior());
        Assert.assertTrue(KeywordAccessor.getKeyword(step.getKeyword()) instanceof Given);
        Assert.assertTrue(step.getStatus().isEmpty());
    }

    /**
     * Verifies that a {@link ScenarioOutline} can contain a step with
     * {@link Consumer} that is <code>null</code> and doesn't change its values
     * 
     * @throws ClassNotFoundException
     *             If the {@link GherkinKeyword} does not exist.
     */
    @Test
    public void canContainAConsumerStep() throws ClassNotFoundException
    {
        String scenarioDescription = "Scenario Description";
        String stepDescription = "Step Description";
        // TODO is this evil?
        TypeSteps<Object> steps = new TypeSteps<Object>().given(stepDescription, (Consumer<Object>) null);
        ScenarioOutline<Object> scenario = new ScenarioOutline<Object>(scenarioDescription, steps, null);

        Assert.assertEquals(scenarioDescription, scenario.getDescription());
        Assert.assertTrue(scenario.getStatus().isEmpty());
        Assert.assertEquals(steps, scenario.getSteps());
        Assert.assertEquals(1, scenario.getSteps().getSteps().size());
        TypeStep<Object> step = scenario.getSteps().getSteps().get(0);
        Assert.assertEquals(stepDescription, step.getDescription());
        Assert.assertNull(step.getBehavior());
        Assert.assertNull(step.getTestdata());
        Assert.assertTrue(KeywordAccessor.getKeyword(step.getKeyword()) instanceof Given);
        Assert.assertTrue(step.getStatus().isEmpty());
    }

    /**
     * Verifies that a feature can contain multiple scenarios
     */
    @Test
    public void canContainMultipleSteps()
    {
        String scenarioDescription = "Scenario Description";
        String stepDescription = "Step Description";
        String anotherStepDescription = "Another Step Description";
        TypeSteps<Object> steps = new TypeSteps<Object>().given(stepDescription, (Runnable) null)
                .when(anotherStepDescription, (Consumer<Object>) null);
        ScenarioOutline<Object> scenario = new ScenarioOutline<Object>(scenarioDescription, steps, null);

        Assert.assertEquals(scenarioDescription, scenario.getDescription());
        Assert.assertTrue(scenario.getStatus().isEmpty());
        Assert.assertEquals(steps, scenario.getSteps());
        Assert.assertEquals(2, scenario.getSteps().getSteps().size());

        TypeStep<Object> step = scenario.getSteps().getSteps().get(0);
        Assert.assertEquals(stepDescription, step.getDescription());
        Assert.assertNull(step.getBehavior());
        Assert.assertNull(step.getTestdata());
        Assert.assertTrue(KeywordAccessor.getKeyword(step.getKeyword()) instanceof Given);
        Assert.assertTrue(step.getStatus().isEmpty());

        TypeStep<Object> anotherStep = scenario.getSteps().getSteps().get(1);
        Assert.assertEquals(anotherStepDescription, anotherStep.getDescription());
        Assert.assertNull(anotherStep.getBehavior());
        Assert.assertNull(anotherStep.getTestdata());
        Assert.assertTrue(KeywordAccessor.getKeyword(anotherStep.getKeyword()) instanceof When);
        Assert.assertTrue(anotherStep.getStatus().isEmpty());
    }

}
