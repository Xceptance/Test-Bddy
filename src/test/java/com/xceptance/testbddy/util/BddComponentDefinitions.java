package com.xceptance.testbddy.util;

import java.util.ArrayList;
import java.util.List;

import com.aventstack.extentreports.GherkinKeyword;
import com.xceptance.testbddy.core.bdd.Feature;
import com.xceptance.testbddy.core.bdd.scenario.AbstractScenario;
import com.xceptance.testbddy.core.bdd.scenario.OutlineDescriptor;
import com.xceptance.testbddy.core.bdd.scenario.Scenario;
import com.xceptance.testbddy.core.bdd.scenario.ScenarioOutline;
import com.xceptance.testbddy.core.bdd.steps.Step;
import com.xceptance.testbddy.core.bdd.steps.Steps;
import com.xceptance.testbddy.core.bdd.steps.TypeStep;
import com.xceptance.testbddy.core.bdd.steps.TypeSteps;

/**
 * Contains various definitions for all kinds of BDD components, which include
 * <ul>
 * <li>{@link Step}</li>
 * <li>{@link TypeStep}</li>
 * <li>{@link Steps}</li>
 * <li>{@link TypeSteps}</li>
 * <li>{@link Scenario}</li>
 * <li>{@link ScenarioOutline}</li>
 * <li>{@link OutlineDescriptor}</li>
 * <li>{@link Feature}.</li>
 * </ul>
 * Note, that an empty behaviour means that it has a behaviour but it does
 * nothing.
 * 
 * @author ckeiner
 *
 */
public class BddComponentDefinitions
{
    /**
     * Describes a fully defined {@link Step}, that is with a keyword, description
     * and a behavior.
     * 
     * @return A fully defined Step
     */
    public static Step fullStep()
    {
        try
        {
            return new Step(new GherkinKeyword("Given"), "A fully defined Step", () ->
                {
                });
        } catch (ClassNotFoundException e)
        {
            throw new IllegalArgumentException("Gherkin Keyword undefined.");
        }
    }

    /**
     * Describes a {@link Step} with <code>null</code> as behavior but with a
     * keyword, and description.
     * 
     * @return A Step with <code>null</code> as behavior
     */
    public static Step nullBehaviorStep()
    {
        try
        {
            return new Step(new GherkinKeyword("Given"), "A Step with null behavior", null);
        } catch (ClassNotFoundException e)
        {
            throw new IllegalArgumentException("Gherkin Keyword undefined.");
        }
    }

    /**
     * Describes a fully defined {@link TypeStep}, that is with a keyword,
     * description, a behavior and one test datum.
     * 
     * @return A fully defined TypeStep
     */
    public static TypeStep<Object> fullTypeStepWithData()
    {
        try
        {
            return new TypeStep<Object>(new GherkinKeyword("Given"), "A fully defined Step", (data) ->
                {
                }).withData(new Object());
        } catch (ClassNotFoundException e)
        {
            throw new IllegalArgumentException("Gherkin Keyword undefined.");
        }
    }

    /**
     * Describes a fully defined {@link TypeStep}, that is with a keyword,
     * description and a behavior.
     * 
     * @return A fully defined TypeStep
     */
    public static TypeStep<Object> fullTypeStepWithOutData()
    {
        try
        {
            return new TypeStep<Object>(new GherkinKeyword("Given"), "A fully defined Step", (data) ->
                {
                });
        } catch (ClassNotFoundException e)
        {
            throw new IllegalArgumentException("Gherkin Keyword undefined.");
        }
    }

    /**
     * Describes a {@link TypeStep} with <code>null</code> as behavior and no
     * testdata but with a keyword, and description.
     * 
     * @return A TypeStep with <code>null</code> as behavior
     */
    public static TypeStep<Object> nullBehaviorTypeStepWithoutData()
    {
        try
        {
            return new TypeStep<Object>(new GherkinKeyword("Given"), "A Step with null behavior", null);
        } catch (ClassNotFoundException e)
        {
            throw new IllegalArgumentException("Gherkin Keyword undefined.");
        }
    }

    /**
     * Describes a {@link TypeStep} with <code>null</code> as behavior but with a
     * keyword, description and testdata.
     * 
     * @return A TypeStep with <code>null</code> as behavior
     */
    public static TypeStep<Object> nullBehaviorTypeStepWithData()
    {
        try
        {
            return new TypeStep<Object>(new GherkinKeyword("Given"), "A Step with null behavior", null)
                    .withData(new Object());
        } catch (ClassNotFoundException e)
        {
            throw new IllegalArgumentException("Gherkin Keyword undefined.");
        }
    }

    /**
     * Describes {@link Steps} with a description and empty behavior.
     * 
     * @return Steps with a description and empty behavior.
     */
    public static Steps fullSteps()
    {
        return new Steps().given("A fully defined step", () ->
            {
            });
    }

    /**
     * Describes {@link Steps} with <code>null</code> as steps but with a
     * description.
     * 
     * @return Steps with <code>null</code> as steps and a description
     */
    public static Steps nullSteps()
    {
        return new Steps().given("A fully defined step", null);
    }

    /**
     * Describes {@link TypeSteps} with a description, test data and empty behavior.
     * 
     * @return Steps with a description and empty behavior.
     */
    public static TypeSteps<Object> fullTypeSteps()
    {
        return new TypeSteps<Object>().given("A fully defined step", (data) ->
            {
            }).withData(new Object());
    }

    /**
     * Describes {@link TypeSteps} with <code>null</code> as behavior but with a
     * description.
     * 
     * @return Steps with <code>null</code> as behavior.
     */
    public static TypeSteps<Object> typeStepsWithoutData()
    {
        return new TypeSteps<Object>().given("A fully defined step", (data) ->
            {
            }).withData(null);
    }

    /**
     * Describes {@link TypeSteps} with <code>null</code> as behavior and no test
     * data but with a description.
     * 
     * @return Steps with <code>null</code> as behavior.
     */
    public static TypeSteps<Object> nullTypeStepsWithoutData()
    {
        return new TypeSteps<Object>().withData(null);
    }

    /**
     * Describes {@link TypeSteps} with <code>null</code> as behavior but with a
     * description and data.
     * 
     * @return Steps with <code>null</code> as behavior.
     */
    public static TypeSteps<Object> nullTypeStepsWithData()
    {
        return new TypeSteps<Object>().withData(new Object());
    }

    /**
     * Describes an {@link OutlineDescriptor} with behavior and one test datum.
     * 
     * @return OutlineDescriptor with behavior and one test datum.
     */
    public static OutlineDescriptor<Object> fullDescriptor()
    {
        return new OutlineDescriptor<>(typeStepsWithoutData(), new Object());
    }

    /**
     * Describes an {@link OutlineDescriptor} with behavior and three test data.
     * 
     * @return OutlineDescriptor with behavior and three test data.
     */
    public static OutlineDescriptor<Object> fullDescriptorThreeTestdata()
    {
        return new OutlineDescriptor<>(typeStepsWithoutData(), new Object(), new Object(), new Object());
    }

    /**
     * Describes an {@link OutlineDescriptor} with behavior and no test data
     * defined.
     * 
     * @return OutlineDescriptor with behavior and no test data defined.
     */
    public static OutlineDescriptor<Object> descriptorWithoutDataDefined()
    {
        return new OutlineDescriptor<>(typeStepsWithoutData());
    }

    /**
     * Describes an {@link OutlineDescriptor} with <code>null</code> as behavior and
     * one test datum.
     * 
     * @return OutlineDescriptor without behavior and one test datum.
     */
    public static OutlineDescriptor<Object> nullStepsDescriptor()
    {
        return new OutlineDescriptor<>(null, new Object());
    }

    /**
     * Describes an {@link OutlineDescriptor} with <code>null</code> as behavior and
     * with <code>null</code> as test datum.
     * 
     * @return OutlineDescriptor without behavior and without a test datum.
     */
    public static OutlineDescriptor<Object> nullStepsDescriptorWithoutData()
    {
        return new OutlineDescriptor<>(null, (Object) null);
    }

    /**
     * Describes an {@link OutlineDescriptor} with behavior but with
     * <code>null</code> as test datum.
     * 
     * @return OutlineDescriptor with behavior but without a test datum.
     */
    public static OutlineDescriptor<Object> descriptorWithoutData()
    {
        return new OutlineDescriptor<>(typeStepsWithoutData(), (Object) null);
    }

    /**
     * Describes an {@link OutlineDescriptor} with behavior and three test data
     * whereas the second one is <code>null</code>.
     * 
     * @return OutlineDescriptor with behavior and three test data whereas the
     *         second one is <code>null</code>.
     */
    public static OutlineDescriptor<Object> descriptorContainingOneNullTestData()
    {
        return new OutlineDescriptor<>(typeStepsWithoutData(), new Object(), (Object) null, new Object());
    }

    /**
     * Describes a {@link Scenario} with <code>null</code> as steps but with a
     * keyword, description and testdata.
     * 
     * @return A TypeStep with <code>null</code> as behavior
     */
    public static Scenario fullScenario()
    {
        return new Scenario("A scenario without steps", fullSteps());
    }

    /**
     * Describes a {@link Scenario} with <code>null</code> as steps but with a
     * keyword, description and testdata.
     * 
     * @return A TypeStep with <code>null</code> as behavior
     */
    public static Scenario nullStepsScenario()
    {
        return new Scenario("A scenario without steps", null);
    }

    /**
     * Describes a {@link ScenarioOutline} with a description, steps and one test
     * datum.
     * 
     * @return A {@link ScenarioOutline} with a description, steps and one test
     *         datum.
     */
    public static ScenarioOutline<Object> fullScenarioOutline()
    {
        List<Object> testdata = new ArrayList<>();
        testdata.add(new Object());
        return new ScenarioOutline<Object>("A scenario with Steps and Testdata", typeStepsWithoutData(), testdata);
    }

    /**
     * Describes a {@link ScenarioOutline} with a description, steps and a list of
     * test data containing <code>null</code>.
     * 
     * @return A {@link ScenarioOutline} with a description, steps and a list of
     *         test data containing <code>null</code>.
     */
    public static ScenarioOutline<Object> scenarioOutlineContainingNullTestData()
    {
        List<Object> testdata = new ArrayList<>();
        testdata.add(null);
        return new ScenarioOutline<Object>("A scenario with Steps and Testdata", typeStepsWithoutData(), testdata);
    }

    /**
     * Describes a {@link ScenarioOutline} with a description, steps and
     * <code>null</code> as test data.
     * 
     * @return A {@link ScenarioOutline} with a description, steps and a list of
     *         test data containing <code>null</code>.
     */
    public static ScenarioOutline<Object> scenarioOutlineWithoutData()
    {
        return new ScenarioOutline<Object>("A scenario with Steps and Testdata", typeStepsWithoutData(), null);
    }

    /**
     * Describes a {@link ScenarioOutline} with a description, <code>null</code> as
     * steps and <code>null</code> as test data.
     * 
     * @return A {@link ScenarioOutline} with a description, steps and a list of
     *         test data containing <code>null</code>.
     */
    public static ScenarioOutline<Object> nullStepsScenarioOutlineWithoutData()
    {
        return new ScenarioOutline<Object>("A scenario with Steps and Testdata", null, null);
    }

    /**
     * Defines a {@link Feature} with a single scenario.
     * 
     * @return A Feature with a single scenario.
     */
    public static Feature fullFeature()
    {
        List<AbstractScenario> scenarios = new ArrayList<>();
        scenarios.add(fullScenario());
        return new Feature("A fully defined feature", scenarios);
    }

    /**
     * Defines a {@link Feature} with three scenarios.
     * 
     * @return A Feature with a three scenarios.
     */
    public static Feature fullFeatureWithThreeScenarios()
    {
        List<AbstractScenario> scenarios = new ArrayList<>();
        scenarios.add(fullScenario());
        scenarios.add(fullScenario());
        scenarios.add(fullScenario());
        return new Feature("A fully defined feature", scenarios);
    }

    /**
     * Defines a {@link Feature} with three scenarios whereas the middle one is
     * <code>null</code>.
     * 
     * @return A Feature with a three scenarios whereas the middle one is
     *         <code>null</code>.
     */
    public static Feature featureContainingANullScenario()
    {
        List<AbstractScenario> scenarios = new ArrayList<>();
        scenarios.add(fullScenario());
        scenarios.add(null);
        scenarios.add(fullScenario());
        return new Feature("A fully defined feature", scenarios);
    }

    /**
     * Defines a {@link Feature} with three scenarios whereas the middle one fails.
     * 
     * @return A Feature with a three scenarios whereas the middle one fails.
     */
    public static Feature featureContainingAFailingScenario()
    {
        List<AbstractScenario> scenarios = new ArrayList<>();
        scenarios.add(fullScenario());
        scenarios.add(nullStepsScenario());
        scenarios.add(fullScenario());
        return new Feature("A fully defined feature", scenarios);
    }

    /**
     * Defines a {@link Feature} with <code>null</code> as scenario.
     * 
     * @return A Feature with <code>null</code> as scenario.
     */
    public static Feature nullScenarioFeature()
    {
        return new Feature("A fully defined feature", null);
    }

}
