package com.xceptance.testbddy.dogtests.tests.status;

import static com.xceptance.testbddy.api.BddSuite.feature;
import static com.xceptance.testbddy.api.BddSuite.given;
import static com.xceptance.testbddy.api.BddSuite.scenario;

import org.junit.Assert;
import org.junit.Test;

import com.xceptance.testbddy.core.bdd.Feature;
import com.xceptance.testbddy.core.bdd.scenario.AbstractScenario;
import com.xceptance.testbddy.core.bdd.scenario.Scenario;
import com.xceptance.testbddy.core.bdd.scenario.ScenarioOutline;
import com.xceptance.testbddy.core.bdd.status.Status;
import com.xceptance.testbddy.core.bdd.steps.Step;
import com.xceptance.testbddy.core.bdd.steps.Steps;
import com.xceptance.testbddy.core.bdd.steps.TypeStep;
import com.xceptance.testbddy.core.bdd.steps.TypeSteps;

public class Wip extends StatusDefinitions
{
    Feature feature;

    Scenario scenario;

    ScenarioOutline<Object> scenarioOutline;

    Steps steps;

    TypeSteps<Object> typeSteps;

    @Test
    public void wip()
    {
        //@formatter:off
        feature("The WIP status is appended to the BDD item",
                /*
                 * Feature
                 */
                () -> scenario("A feature is wip", 
                        given("A feature is wip", () -> {
                            feature  = featureWithEmtpyBehavior().wip();
                            feature.withReporter(null);
                        })
                        .when("The feature is executed", () -> {
                            feature.test();
                        })
                        .then("Only the feature should be wip", () -> {
                            // Feature has WIP
                            Assert.assertTrue(feature.getStatus().contains(Status.WIP));
                            // Get the scenario
                            AbstractScenario scenario = feature.getScenarios().get(0);
                            // AbstractScenario hasn't WIP
                            Assert.assertFalse(scenario.getStatus().contains(Status.WIP));
                            // AbstractScenario is a scenario
                            Assert.assertTrue(scenario instanceof Scenario);
                            // Get the Step
                            Step step = ((Scenario) scenario).getSteps().getSteps().get(0);
                            // Step hasn't WIP
                            Assert.assertFalse(step.getStatus().contains(Status.WIP));
                        })
                ),
                /*
                 * Scenario
                 */
                () -> scenario("A scenario is wip", 
                        given("A scenario is wip", () -> {
                            scenario  = scenarioWithEmtpyBehavior().wip();
                        })
                        .when("The scenario is executed", () -> {
                            scenario.test();
                        })
                        .then("Only the scenario should be wip", () -> {
                            // Feature has WIP
                            Assert.assertTrue(scenario.getStatus().contains(Status.WIP));
                            // Get the Step
                            Step step = scenario.getSteps().getSteps().get(0);
                            // Step hasn't WIP
                            Assert.assertFalse(step.getStatus().contains(Status.WIP));
                        })
                ),
                () -> scenario("A scenario outline is wip", 
                        given("A scenario outline is wip", () -> {
                            scenarioOutline  = outlineWithEmtpyBehavior().wip();
                        })
                        .when("The feature is executed", () -> {
                            scenarioOutline.test();
                        })
                        .then("Only the feature should be wip", () -> {
                            // Feature has WIP
                            Assert.assertTrue(scenarioOutline.getStatus().contains(Status.WIP));
                            // Get the Step
                            TypeStep<Object> typeStep = scenarioOutline.getSteps().getSteps().get(0);
                            // Step hasn't WIP
                            Assert.assertFalse(typeStep.getStatus().contains(Status.WIP));
                        })
                ),
                /*
                 * Step
                 */
                () -> scenario("A step is wip", 
                        given("A step is wip", () -> {
                            steps  = stepsWithEmtpyBehavior().wip();
                        })
                        .when("The step is executed", () -> {
                            steps.test();
                        })
                        .then("Only the step should be wip", () -> {
                            Assert.assertTrue(steps.getSteps().get(0).getStatus().contains(Status.WIP));
                        })
                ),
                () -> scenario("A typeStep is wip", 
                        given("A typeStep is wip", () -> {
                            typeSteps  = typeStepsWithEmtpyBehavior().wip();
                        })
                        .when("The typeStep is executed", () -> {
                            typeSteps.withData(new Object()).test();
                        })
                        .then("Only the typeStep should be wip", () -> {
                            Assert.assertTrue(typeSteps.getSteps().get(0).getStatus().contains(Status.WIP));
                        })
                )
                // TODO Wip with multiple sibling and childs: two steps, etc
        ).test();
        //@formatter:on
    }

}
