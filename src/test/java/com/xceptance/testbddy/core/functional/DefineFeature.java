package com.xceptance.testbddy.core.functional;

import static com.xceptance.testbddy.api.BddSuite.feature;
import static com.xceptance.testbddy.api.BddSuite.given;
import static com.xceptance.testbddy.api.BddSuite.scenario;
import static com.xceptance.testbddy.api.BddSuite.with;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.xceptance.testbddy.core.bdd.Feature;
import com.xceptance.testbddy.core.bdd.scenario.AbstractScenario;
import com.xceptance.testbddy.core.bdd.scenario.Scenario;
import com.xceptance.testbddy.core.bdd.scenario.ScenarioOutline;
import com.xceptance.testbddy.core.bdd.steps.Step;
import com.xceptance.testbddy.core.bdd.steps.TypeStep;

public class DefineFeature
{
    /**
     * Verifies the description of Feature, Scenario, Given, When, And, Then is
     * correct
     */
    @Test
    public void verifyScenarioDescriptionsAreCorrect()
    {
        final String clazz = this.getClass().getName();
        final String featureDescription = "Define a feature";
        final String scenarioDescription = "Define a scenario";
        final String givenDescription = "Define a given step";
        final String whenDescription = "Define a when step";
        final String andDescription = "Define an and step";
        final String thenDescription = "Define a then step";

        Feature definedFeature = feature(featureDescription,
                () -> scenario(scenarioDescription, given(givenDescription, () ->
                    {
                        System.out.println("Given I define some step");
                    }).when(whenDescription, () ->
                        {
                            System.out.println("I define a when step");
                        }).and(andDescription, () ->
                            {
                                System.out.println("I define an and step");
                            }).then(thenDescription, () ->
                                {
                                    System.out.println("I should define a then step");
                                })));
        Assert.assertEquals(featureDescription, definedFeature.getDescription());
        Assert.assertEquals(clazz, definedFeature.getClassFeatureDefinedIn());
        List<AbstractScenario> scenarios = definedFeature.getScenarios();
        Assert.assertEquals(1, scenarios.size());
        Assert.assertTrue(scenarios.get(0) instanceof Scenario);
        Scenario scenario = (Scenario) scenarios.get(0);
        Assert.assertEquals(scenarioDescription, scenario.getDescription());
        List<Step> steps = scenario.getSteps().getSteps();
        Assert.assertEquals(givenDescription, steps.get(0).getDescription());
        Assert.assertEquals(whenDescription, steps.get(1).getDescription());
        Assert.assertEquals(andDescription, steps.get(2).getDescription());
        Assert.assertEquals(thenDescription, steps.get(3).getDescription());
    }

    /**
     * Verifies the description of Feature, Scenario, Given, When, And, Then is
     * correct
     */
    @Test
    public void verifyScenarioOutlineDescriptionsAreCorrect()
    {
        final String clazz = this.getClass().getName();
        final String featureDescription = "Define a feature";
        final String scenarioDescription = "Define a scenario";
        final String givenDescription = "Define a given step";
        final String whenDescription = "Define a when step";
        final String andDescription = "Define an and step";
        final String thenDescription = "Define a then step";

        Feature definedFeature = feature(featureDescription,
                () -> scenario(scenarioDescription, with("Some Data").given(givenDescription, () ->
                    {
                        System.out.println("Given I define some step");
                    }).when(whenDescription, () ->
                        {
                            System.out.println("I define a when step");
                        }).and(andDescription, () ->
                            {
                                System.out.println("I define an and step");
                            })
                        .then(thenDescription, () ->
                            {
                                System.out.println("I should define a then step");
                            })));
        Assert.assertEquals(featureDescription, definedFeature.getDescription());
        Assert.assertEquals(clazz, definedFeature.getClassFeatureDefinedIn());
        List<AbstractScenario> scenarios = definedFeature.getScenarios();
        Assert.assertEquals(1, scenarios.size());
        Assert.assertTrue(scenarios.get(0) instanceof ScenarioOutline<?>);
        @SuppressWarnings("unchecked")
        ScenarioOutline<String> scenario = (ScenarioOutline<String>) scenarios.get(0);
        Assert.assertEquals(scenarioDescription, scenario.getDescription());
        List<TypeStep<String>> steps = scenario.getSteps().getSteps();
        Assert.assertEquals(givenDescription, steps.get(0).getDescription());
        Assert.assertEquals(whenDescription, steps.get(1).getDescription());
        Assert.assertEquals(andDescription, steps.get(2).getDescription());
        Assert.assertEquals(thenDescription, steps.get(3).getDescription());
    }
}
