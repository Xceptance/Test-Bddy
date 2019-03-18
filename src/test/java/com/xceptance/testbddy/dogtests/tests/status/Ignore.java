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

public class Ignore extends StatusDefinitions
{
    @Test
    public void ignore()
    {
        //@formatter:off
        feature("The IGNORE status is appended to the BDD item",
                /*
                 * Feature
                 */
                () -> scenario("A feature is ignored", 
                        with(featureWithEmtpyBehavior(), featureThatThrowsAnError())
                        .given("<data.getDescription()> is ignored", data -> {
                            data.ignore();
                            data.withReporter(null);
                        })
                        .when("<data.getDescription()> is executed", data -> {
                            data.test();
                        })
                        .then("Only the <data.getDescription()> should be ignored", data -> {
                            // Feature has WIP
                            Assert.assertTrue(data.getStatus().contains(Status.IGNORE));
                            // Get the scenario
                            AbstractScenario scenario = data.getScenarios().get(0);
                            // AbstractScenario hasn't WIP
                            Assert.assertFalse(scenario.getStatus().contains(Status.IGNORE));
                            // AbstractScenario is a scenario
                            Assert.assertTrue(scenario instanceof Scenario);
                            // Get the Step
                            Step step = ((Scenario) scenario).getSteps().getSteps().get(0);
                            // Step hasn't WIP
                            Assert.assertFalse(step.getStatus().contains(Status.IGNORE));
                        })
                ),
                /*
                 * Scenario
                 */
                () -> scenario("A scenario is ignored",
                        with(scenarioWithEmtpyBehavior(), scenarioThatThrowsAnError())
                        .given("A <data.getDescription()> is ignored", data -> {
                            data.ignore();
                            data.setReporter(null);
                        })
                        .when("The <data.getDescription()> is executed", data -> {
                            data.test();
                        })
                        .then("Only the <data.getDescription()> should be ignored", data -> {
                            // Feature has WIP
                            Assert.assertTrue(data.getStatus().contains(Status.IGNORE));
                            // Get the Step
                            Step step = data.getSteps().getSteps().get(0);
                            // Step hasn't WIP
                            Assert.assertFalse(step.getStatus().contains(Status.IGNORE));
                        })
                ),
                () -> scenario("A scenario outline is ignored",
                        with(outlineWithEmtpyBehavior(), outlineThatThrowsAnError())
                        .given("A <data.getDescription()> is ignored", data -> {
                            data.ignore();
                            data.setReporter(null);
                        })
                        .when("The <data.getDescription()> is executed", data -> {
                            data.test();
                        })
                        .then("Only the <data.getDescription()> should be ignored", data -> {
                            // Feature has WIP
                            Assert.assertTrue(data.getStatus().contains(Status.IGNORE));
                            // Get the Step
                            TypeStep<Object> typeStep = data.getSteps().getSteps().get(0);
                            // Step hasn't WIP
                            Assert.assertFalse(typeStep.getStatus().contains(Status.IGNORE));
                        })
                ),
                /*
                 * Step
                 */
                () -> scenario("A step is ignored",
                        with(stepsWithEmtpyBehavior(), stepsThatThrowsAnError())
                        .given("A <data.getSteps().get(0).getDescription()> is ignored", data -> {
                            data.ignore();
                            data.setReporter(null);
                        })
                        .when("The <data.getSteps().get(0).getDescription()> is executed", data -> {
                            data.test();
                        })
                        .then("Only the <data.getSteps().get(0).getDescription()> should be ignored", data -> {
                            Assert.assertTrue(data.getSteps().get(0).getStatus().contains(Status.IGNORE));
                        })
                ),
                () -> scenario("A typeStep is ignored",
                        with(typeStepsWithEmtpyBehavior(), typeStepsThatThrowsAnError())
                        .given("A <data.getSteps().get(0).getDescription()> is ignored", data -> {
                            data.ignore();
                            data.setReporter(null);
                        })
                        .when("The <data.getSteps().get(0).getDescription()> is executed", data -> {
                            data.withData(new Object()).test();
                        })
                        .then("Only the <data.getSteps().get(0).getDescription()> should be ignored", data -> {
                            Assert.assertTrue(data.getSteps().get(0).getStatus().contains(Status.IGNORE));
                        })
                )
                // TODO Ignore with multiple sibling and childs: two steps, etc
        ).test();
        //@formatter:on
    }

}
