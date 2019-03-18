package com.xceptance.testbddy.dogtests.tests.status;

import static com.xceptance.testbddy.api.BddSuite.feature;
import static com.xceptance.testbddy.api.BddSuite.given;
import static com.xceptance.testbddy.api.BddSuite.scenario;
import static com.xceptance.testbddy.api.BddSuite.with;

import org.junit.Assert;
import org.junit.Test;

import com.xceptance.testbddy.api.PendingConsumer;
import com.xceptance.testbddy.api.PendingRunnable;
import com.xceptance.testbddy.core.bdd.Feature;
import com.xceptance.testbddy.core.bdd.scenario.Scenario;
import com.xceptance.testbddy.core.bdd.scenario.ScenarioOutline;
import com.xceptance.testbddy.core.bdd.status.Status;
import com.xceptance.testbddy.core.bdd.steps.Steps;
import com.xceptance.testbddy.core.bdd.steps.TypeSteps;

public class Pending
{
    Feature feature;

    Scenario scenario;

    ScenarioOutline<Object> scenarioOutline;

    Steps steps;

    TypeSteps<Object> typeSteps;

    @Test
    public void pending()
    {
        //@formatter:off
        feature("The pending status is added correctly",
                /*
                 * Feature
                 */
                () -> scenario("A feature without scenarios is defined",
                        given("A feature with neither scenarios nor reporter is defined", () -> {
                            feature  = featureWithoutScenarios();
                            feature.withReporter(null);
                        })
                        .when("The feature is executed", () -> {
                            feature.test();
                        })
                        .then("The feature should be pending", () -> {
                            Assert.assertTrue(feature.getStatus().contains(Status.PENDING));
                        })
                ),
                /*
                 * Scenario
                 */
                () -> scenario("A scenario without steps is defined",
                        given("A scenario without steps is defined", () -> {
                            scenario = scenarioWithoutSteps();
                            // Set reporter to null so it isn't reported
                            scenario.setReporter(null);
                        })
                        .when("The scenario is run", () -> {
                            scenario.test();
                        })
                        .then("The scenario should be pending", () -> {
                            Assert.assertTrue(scenario.getStatus().contains(Status.PENDING));
                        })
                ),
                () -> scenario("A scenarioOutline without steps is defined",
                        given("A scenarioOutline without steps is defined", () -> {
                            scenarioOutline = outlineWithoutSteps();
                            scenarioOutline.setReporter(null);
                        })
                        .when("The scenarioOutline is run", () -> {
                            scenarioOutline.test();
                        })
                        .then("The scenarioOutline should be pending", () -> {
                            Assert.assertTrue(scenarioOutline.getStatus().contains(Status.PENDING));
                        })
                ),
                () -> scenario("A scenarioOutline without testdata is defined",
                        given("A scenarioOutline without testdata is defined", () -> {
                            scenarioOutline = outlineWithoutTestdata();
                            scenarioOutline.setReporter(null);
                        })
                        .when("The scenarioOutline is run", () -> {
                            scenarioOutline.test();
                        })
                        .then("The scenarioOutline should be pending", () -> {
                            Assert.assertTrue(scenarioOutline.getStatus().contains(Status.PENDING));
                        })
                ),
                /*
                 * Steps
                 */
                () -> scenario("A step with a pending runnable is defined",
                        given("A step with a pending runnable is defined", () -> {
                            steps = stepsWithPendingRunnable();
                            steps.setReporter(null);
                        })
                        .when("The step is run", () -> {
                            steps.test();
                        })
                        .then("The step should be pending", () -> {
                            Assert.assertTrue(steps.getSteps().get(0).getStatus().contains(Status.PENDING));
                        })
                ),
                () -> scenario("A typeStep with a pending runnable is defined",
                        given("A typeStep with a pending runnable is defined", () -> {
                            typeSteps = typeStepsWithPendingRunnable();
                            typeSteps.setReporter(null);
                        })
                        .when("The typeStep is run", () -> {
                            typeSteps.test();
                        })
                        .then("The typeStep should be pending", () -> {
                            Assert.assertTrue(typeSteps.getSteps().get(0).getStatus().contains(Status.PENDING));
                        })
                ),
                () -> scenario("A typeStep with a pending consumer is defined",
                        given("A typeStep with a pending consumer is defined", () -> {
                            typeSteps = typeStepsWithPendingConsumer();
                            typeSteps.setReporter(null);
                        })
                        .when("The typeStep is run", () -> {
                            typeSteps.test();
                        })
                        .then("The typeStep should be pending", () -> {
                            Assert.assertTrue(typeSteps.getSteps().get(0).getStatus().contains(Status.PENDING));
                        })
                )
                // TODO Pending with more than one child or sibling element: two steps etc
        ).test();
        //@formatter:on
    }

    protected Feature featureWithoutScenarios()
    {
        return feature("Feature without Scenarios");
    }

    protected Scenario scenarioWithoutSteps()
    {
        return scenario("Scenario without Steps");
    }

    protected Steps stepsWithPendingRunnable()
    {
        return given("Given without Steps", new PendingRunnable());
    }

    protected ScenarioOutline<Object> outlineWithoutSteps()
    {
        return scenario("Scenario without Steps", with((Object) null));
    }

    protected ScenarioOutline<Object> outlineWithoutTestdata()
    {
        return scenario("Scenario with no Testdata", with().given("Given without Steps", () ->
            {
            }));
    }

    protected TypeSteps<Object> typeStepsWithPendingRunnable()
    {
        return with((Object) null).given("Given without Steps", new PendingRunnable()).getSteps();
    }

    protected TypeSteps<Object> typeStepsWithPendingConsumer()
    {
        return with((Object) null).given("Given without Steps", new PendingConsumer<Object>()).getSteps();
    }

}
