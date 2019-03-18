package com.xceptance.testbddy.api;

import static com.xceptance.testbddy.api.BddSuite.and;
import static com.xceptance.testbddy.api.BddSuite.feature;
import static com.xceptance.testbddy.api.BddSuite.given;
import static com.xceptance.testbddy.api.BddSuite.scenario;
import static com.xceptance.testbddy.api.BddSuite.then;
import static com.xceptance.testbddy.api.BddSuite.when;
import static com.xceptance.testbddy.api.BddSuite.with;

import java.util.function.Supplier;

import org.junit.Assert;
import org.junit.Test;

import com.aventstack.extentreports.KeywordAccessor;
import com.aventstack.extentreports.gherkin.model.And;
import com.aventstack.extentreports.gherkin.model.Given;
import com.aventstack.extentreports.gherkin.model.Then;
import com.aventstack.extentreports.gherkin.model.When;
import com.xceptance.testbddy.api.BddSuite;
import com.xceptance.testbddy.core.bdd.Feature;
import com.xceptance.testbddy.core.bdd.scenario.AbstractScenario;
import com.xceptance.testbddy.core.bdd.scenario.OutlineDescriptor;
import com.xceptance.testbddy.core.bdd.scenario.Scenario;
import com.xceptance.testbddy.core.bdd.scenario.ScenarioOutline;
import com.xceptance.testbddy.core.bdd.steps.Step;
import com.xceptance.testbddy.core.bdd.steps.Steps;
import com.xceptance.testbddy.core.bdd.steps.TypeSteps;

public class BddSuiteTest
{

    /**
     * Verifies that {@link Feature}s are correctly created via the API.
     */
    @Test
    public void canCreateFeatures()
    {
        String clazz = this.getClass().getName();
        Steps steps = new Steps();
        String scenarioDescription = "Scenario Description";
        Scenario scenario = new Scenario(scenarioDescription, steps);
        Supplier<AbstractScenario> scenarioSupplier = () -> scenario;
        String featureDescription = "Feature Description";
        Feature feature = feature(featureDescription, scenarioSupplier);

        // Verify the description has the class added
        Assert.assertEquals(featureDescription, feature.getDescription());
        Assert.assertEquals(clazz, feature.getClassFeatureDefinedIn());
        // Verify the feature has no status
        Assert.assertTrue(feature.getStatus().isEmpty());
        // Verify the list of scenarios is correct
        Assert.assertEquals(1, feature.getScenarios().size());
        Assert.assertEquals(scenario, feature.getScenarios().get(0));
    }

    /**
     * Verifies that {@link Scenario}s are correctly created via the API.
     */
    @Test
    public void canCreateScenario()
    {
        Steps steps = new Steps();
        String scenarioDescription = "Scenario Description";
        Scenario scenario = scenario(scenarioDescription, steps);

        // Verify the description is correct
        Assert.assertEquals(scenarioDescription, scenario.getDescription());
        // Verify there is no status
        Assert.assertTrue(scenario.getStatus().isEmpty());
        // Verify the steps are the same
        Assert.assertEquals(steps, scenario.getSteps());
    }

    /**
     * Verifies that {@link ScenarioOutline}s are correctly created via the API.
     */
    @Test
    public void canCreateScenarioOutline()
    {
        TypeSteps<Object> steps = new TypeSteps<>();
        Object testdata = new Object();
        OutlineDescriptor<Object> descriptor = new OutlineDescriptor<>(steps, testdata);
        String scenarioDescription = "Scenario Description";
        ScenarioOutline<Object> scenario = scenario(scenarioDescription, descriptor);

        // Verify the description is correct
        Assert.assertEquals(scenarioDescription, scenario.getDescription());
        // Verify there is no status
        Assert.assertTrue(scenario.getStatus().isEmpty());
        // Verify the steps are the same
        Assert.assertEquals(steps, scenario.getSteps());
        // Assert there is one test datum
        Assert.assertEquals(1, scenario.getTestdata().size());
        // Assert the test datum is correct
        Assert.assertEquals(testdata, scenario.getTestdata().get(0));
    }

    /**
     * Verifies that {@link BddSuite#with(Object...)} creates an
     * {@link OutlineDescriptor} with the specified test data.
     */
    @Test
    public void canCreateOutlineDescriptor()
    {
        Object testdata = new Object();
        OutlineDescriptor<Object> descriptor = with(testdata);

        // Assert that the size is correct
        Assert.assertEquals(1, descriptor.getTestdata().size());
        // Assert the test data is still the same
        Assert.assertEquals(testdata, descriptor.getTestdata().get(0));
        // Assert there aren't any steps
        Assert.assertTrue(descriptor.getSteps().getSteps().isEmpty());
    }

    /*
     * GWAT
     */

    /**
     * Verifies that {@link BddSuite#and(Steps)} creates {@link Steps} with the
     * specified keyword. Also verifies that Steps keep their keyword.
     */
    @Test
    public void shouldCreateStepWithAnd_ButKeepItsKeyword()
    {
        String stepDescription = "Step Description";
        Runnable runner = () ->
            {
            };
        Steps keywordSteps = new Steps();
        Steps steps = and(keywordSteps);
        // Assert nothing was added
        Assert.assertTrue(steps.getSteps().isEmpty());

        // Verify it works if the steps have a step
        keywordSteps = new Steps().given(stepDescription, runner);
        steps = and(keywordSteps);
        // Assert one step was added
        Assert.assertEquals(1, steps.getSteps().size());
        // Get the step
        Step step = steps.getSteps().get(0);
        // Assert the keyword is the correct
        // TODO should it keep its keyword?
        Assert.assertTrue(KeywordAccessor.getKeyword(step.getKeyword()) instanceof Given);
        // Assert the description is correct
        Assert.assertEquals(stepDescription, step.getDescription());
        // Assert the behavior is the same
        Assert.assertEquals(runner, step.getBehavior());
    }

    /**
     * Verifies that {@link BddSuite#and(String, Runnable)} creates {@link Steps}
     * with the specified keyword
     */
    @Test
    public void canCreateAndBehavior()
    {
        String stepDescription = "Step Description";
        Runnable runner = () ->
            {
            };
        Steps steps = and(null, null);
        // Assert nothing was added
        Assert.assertEquals(1, steps.getSteps().size());
        Step step = steps.getSteps().get(0);
        Assert.assertNull(step.getDescription());
        Assert.assertNull(step.getBehavior());

        // Verify it works if the steps have a step
        steps = and(stepDescription, runner);
        // Assert one step was added
        Assert.assertEquals(1, steps.getSteps().size());
        // Get the step
        step = steps.getSteps().get(0);
        // Assert the keyword is the correct
        Assert.assertTrue(KeywordAccessor.getKeyword(step.getKeyword()) instanceof And);
        // Assert the description is correct
        Assert.assertEquals(stepDescription, step.getDescription());
        // Assert the behavior is the same
        Assert.assertEquals(runner, step.getBehavior());
    }

    /**
     * Verifies that {@link BddSuite#given(Steps)} creates {@link Steps} with the
     * specified keyword. Also verifies that Steps keep their keyword.
     */
    @Test
    public void shouldCreateStepWithGiven_ButKeepItsKeyword()
    {
        String stepDescription = "Step Description";
        Runnable runner = () ->
            {
            };
        Steps keywordSteps = new Steps();
        Steps steps = given(keywordSteps);
        // Assert nothing was added
        Assert.assertTrue(steps.getSteps().isEmpty());

        // Verify it works if the steps have a step
        keywordSteps = new Steps().given(stepDescription, runner);
        steps = given(keywordSteps);
        // Assert one step was added
        Assert.assertEquals(1, steps.getSteps().size());
        // Get the step
        Step step = steps.getSteps().get(0);
        // Assert the keyword is the correct
        // TODO should it keep its keyword?
        Assert.assertTrue(KeywordAccessor.getKeyword(step.getKeyword()) instanceof Given);
        // Assert the description is correct
        Assert.assertEquals(stepDescription, step.getDescription());
        // Assert the behavior is the same
        Assert.assertEquals(runner, step.getBehavior());
    }

    /**
     * Verifies that {@link BddSuite#given(String, Runnable)} creates {@link Steps}
     * with the specified keyword
     */
    @Test
    public void canCreateGivenBehavior()
    {
        String stepDescription = "Step Description";
        Runnable runner = () ->
            {
            };
        Steps steps = given(null, null);
        // Assert nothing was added
        Assert.assertEquals(1, steps.getSteps().size());
        Step step = steps.getSteps().get(0);
        Assert.assertNull(step.getDescription());
        Assert.assertNull(step.getBehavior());

        // Verify it works if the steps have a step
        steps = given(stepDescription, runner);
        // Assert one step was added
        Assert.assertEquals(1, steps.getSteps().size());
        // Get the step
        step = steps.getSteps().get(0);
        // Assert the keyword is the correct
        Assert.assertTrue(KeywordAccessor.getKeyword(step.getKeyword()) instanceof Given);
        // Assert the description is correct
        Assert.assertEquals(stepDescription, step.getDescription());
        // Assert the behavior is the same
        Assert.assertEquals(runner, step.getBehavior());
    }

    /**
     * Verifies that {@link BddSuite#when(Steps)} creates {@link Steps} with the
     * specified keyword. Also verifies that Steps keep their keyword.
     */
    @Test
    public void shouldCreateStepWithWhen_ButKeepItsKeyword()
    {
        String stepDescription = "Step Description";
        Runnable runner = () ->
            {
            };
        Steps keywordSteps = new Steps();
        Steps steps = when(keywordSteps);
        // Assert nothing was added
        Assert.assertTrue(steps.getSteps().isEmpty());

        // Verify it works if the steps have a step
        keywordSteps = new Steps().given(stepDescription, runner);
        steps = when(keywordSteps);
        // Assert one step was added
        Assert.assertEquals(1, steps.getSteps().size());
        // Get the step
        Step step = steps.getSteps().get(0);
        // Assert the keyword is the correct
        // TODO should it keep its keyword?
        Assert.assertTrue(KeywordAccessor.getKeyword(step.getKeyword()) instanceof Given);
        // Assert the description is correct
        Assert.assertEquals(stepDescription, step.getDescription());
        // Assert the behavior is the same
        Assert.assertEquals(runner, step.getBehavior());
    }

    /**
     * Verifies that {@link BddSuite#when(String, Runnable)} creates {@link Steps}
     * with the specified keyword.
     */
    @Test
    public void canCreateWhenBehavior()
    {
        String stepDescription = "Step Description";
        Runnable runner = () ->
            {
            };
        Steps steps = when(null, null);
        // Assert nothing was added
        Assert.assertEquals(1, steps.getSteps().size());
        Step step = steps.getSteps().get(0);
        Assert.assertNull(step.getDescription());
        Assert.assertNull(step.getBehavior());

        // Verify it works if the steps have a step
        steps = when(stepDescription, runner);
        // Assert one step was added
        Assert.assertEquals(1, steps.getSteps().size());
        // Get the step
        step = steps.getSteps().get(0);
        // Assert the keyword is the correct
        Assert.assertTrue(KeywordAccessor.getKeyword(step.getKeyword()) instanceof When);
        // Assert the description is correct
        Assert.assertEquals(stepDescription, step.getDescription());
        // Assert the behavior is the same
        Assert.assertEquals(runner, step.getBehavior());
    }

    /**
     * Verifies that {@link BddSuite#then(Steps)} creates {@link Steps} with the
     * specified keyword. Also verifies that Steps keep their keyword.
     */
    @Test
    public void shouldCreateStepWithThen_ButKeepItsKeyword()
    {
        String stepDescription = "Step Description";
        Runnable runner = () ->
            {
            };
        Steps keywordSteps = new Steps();
        Steps steps = then(keywordSteps);
        // Assert nothing was added
        Assert.assertTrue(steps.getSteps().isEmpty());

        // Verify it works if the steps have a step
        keywordSteps = new Steps().given(stepDescription, runner);
        steps = then(keywordSteps);
        // Assert one step was added
        Assert.assertEquals(1, steps.getSteps().size());
        // Get the step
        Step step = steps.getSteps().get(0);
        // Assert the keyword is the correct
        // TODO should it keep its keyword?
        Assert.assertTrue(KeywordAccessor.getKeyword(step.getKeyword()) instanceof Given);
        // Assert the description is correct
        Assert.assertEquals(stepDescription, step.getDescription());
        // Assert the behavior is the same
        Assert.assertEquals(runner, step.getBehavior());
    }

    /**
     * Verifies that {@link BddSuite#then(String, Runnable)} creates {@link Steps}
     * with the specified keyword
     */
    @Test
    public void canCreateThenBehavior()
    {
        String stepDescription = "Step Description";
        Runnable runner = () ->
            {
            };
        Steps steps = then(null, null);
        // Assert nothing was added
        Assert.assertEquals(1, steps.getSteps().size());
        Step step = steps.getSteps().get(0);
        Assert.assertNull(step.getDescription());
        Assert.assertNull(step.getBehavior());

        // Verify it works if the steps have a step
        steps = then(stepDescription, runner);
        // Assert one step was added
        Assert.assertEquals(1, steps.getSteps().size());
        // Get the step
        step = steps.getSteps().get(0);
        // Assert the keyword is the correct
        Assert.assertTrue(KeywordAccessor.getKeyword(step.getKeyword()) instanceof Then);
        // Assert the description is correct
        Assert.assertEquals(stepDescription, step.getDescription());
        // Assert the behavior is the same
        Assert.assertEquals(runner, step.getBehavior());
    }
}
