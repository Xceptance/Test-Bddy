package com.xceptance.testbddy.dogtests.tests.status;

import static com.xceptance.testbddy.api.BddSuite.feature;
import static com.xceptance.testbddy.api.BddSuite.scenario;
import static com.xceptance.testbddy.api.BddSuite.with;

import org.junit.Assert;
import org.junit.Test;

import com.xceptance.testbddy.core.bdd.scenario.AbstractScenario;
import com.xceptance.testbddy.core.bdd.scenario.Scenario;
import com.xceptance.testbddy.core.bdd.status.Status;
import com.xceptance.testbddy.core.bdd.steps.Step;
import com.xceptance.testbddy.core.bdd.steps.TypeStep;

public class Skip extends StatusDefinitions
{

    @Test
    public void skip()
    {
        //@formatter:off
        feature("The SKIP status is appended to the BDD item",
                /*
                 * Feature
                 */
                () -> scenario("A feature is skipped", 
                        with(featureWithEmtpyBehavior(), featureThatThrowsAnError())
                        .given("A <data.getDescription()> is skipped", data -> {
                            data.skip();
                            data.withReporter(null);
                        })
                        .when("The <data.getDescription()> is executed", data -> {
                            data.test();
                        })
                        .then("Only the <data.getDescription()> should be skipped", data -> {
                            // Feature has WIP
                            Assert.assertTrue(data.getStatus().contains(Status.SKIP));
                            // Get the scenario
                            AbstractScenario scenario = data.getScenarios().get(0);
                            // AbstractScenario hasn't WIP
                            Assert.assertFalse(scenario.getStatus().contains(Status.SKIP));
                            // AbstractScenario is a scenario
                            Assert.assertTrue(scenario instanceof Scenario);
                            // Get the Step
                            Step step = ((Scenario) scenario).getSteps().getSteps().get(0);
                            // Step hasn't WIP
                            Assert.assertFalse(step.getStatus().contains(Status.SKIP));
                        })
                ),
                /*
                 * Scenario
                 */
                () -> scenario("A scenario is skipped",
                        with(scenarioWithEmtpyBehavior(), scenarioThatThrowsAnError())
                        .given("A <data.getDescription()> is skipped", data -> {
                            data.skip();
                            data.setReporter(null);
                        })
                        .when("The <data.getDescription()> is executed", data -> {
                            data.test();
                        })
                        .then("Only the <data.getDescription()> should be skipped", data -> {
                            // Feature has WIP
                            Assert.assertTrue(data.getStatus().contains(Status.SKIP));
                            // Get the Step
                            Step step = data.getSteps().getSteps().get(0);
                            // Step hasn't WIP
                            Assert.assertFalse(step.getStatus().contains(Status.SKIP));
                        })
                ),
                () -> scenario("A scenario outline is skipped",
                        with(outlineWithEmtpyBehavior(), outlineThatThrowsAnError())
                        .given("A <data.getDescription()> is skipped", data -> {
                            data.skip();
                            data.setReporter(null);
                        })
                        .when("The <data.getDescription()> is executed", data -> {
                            data.test();
                        })
                        .then("Only the <data.getDescription()> should be skipped", data -> {
                            // Feature has WIP
                            Assert.assertTrue(data.getStatus().contains(Status.SKIP));
                            // Get the Step
                            TypeStep<Object> typeStep = data.getSteps().getSteps().get(0);
                            // Step hasn't WIP
                            Assert.assertFalse(typeStep.getStatus().contains(Status.SKIP));
                        })
                ),
                /*
                 * Step
                 */
                () -> scenario("A step is skipped",
                        with(stepsWithEmtpyBehavior(), stepsThatThrowsAnError())
                        .given("A <data.getSteps().get(0).getDescription()> is skipped", data -> {
                            data.skip();
                            data.setReporter(null);
                        })
                        .when("The <data.getSteps().get(0).getDescription()> is executed", data -> {
                            data.test();
                        })
                        .then("Only the <data.getSteps().get(0).getDescription()> should be skipped", data -> {
                            Assert.assertTrue(data.getSteps().get(0).getStatus().contains(Status.SKIP));
                        })
                ),
                () -> scenario("A typeStep is skipped",
                        with(typeStepsWithEmtpyBehavior(), typeStepsThatThrowsAnError())
                        .given("A <data.getSteps().get(0).getDescription()> is skipped", data -> {
                            data.skip();
                            data.setReporter(null);
                        })
                        .when("The <data.getSteps().get(0).getDescription()> is executed", data -> {
                            data.withData(new Object()).test();
                        })
                        .then("Only the <data.getSteps().get(0).getDescription()> should be skipped", data -> {
                            Assert.assertTrue(data.getSteps().get(0).getStatus().contains(Status.SKIP));
                        })
                )
                // TODO Skip with multiple sibling and childs: two steps, etc
        ).test();
        //@formatter:on
    }

}
