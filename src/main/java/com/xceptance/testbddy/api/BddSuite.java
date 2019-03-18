package com.xceptance.testbddy.api;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.aventstack.extentreports.gherkin.model.And;
import com.aventstack.extentreports.gherkin.model.Given;
import com.aventstack.extentreports.gherkin.model.Then;
import com.aventstack.extentreports.gherkin.model.When;
import com.xceptance.testbddy.core.bdd.Feature;
import com.xceptance.testbddy.core.bdd.scenario.AbstractScenario;
import com.xceptance.testbddy.core.bdd.scenario.OutlineDescriptor;
import com.xceptance.testbddy.core.bdd.scenario.Scenario;
import com.xceptance.testbddy.core.bdd.scenario.ScenarioOutline;
import com.xceptance.testbddy.core.bdd.steps.Step;
import com.xceptance.testbddy.core.bdd.steps.Steps;

// TODO Examples for each API method would be nice
public class BddSuite
{

    /**
     * Creates a {@link Feature} with the supplied description and each scenario
     * specified in the array of {@link Supplier}s.<br>
     * A supplier describes an {@link AbstractScenario} which is either a
     * {@link Scenario} or {@link ScenarioOutline}. Each Supplier then adds exactly
     * one Scenario or ScenarioOutline to the Feature.
     * 
     * @param description
     *            The description of the Feature.
     * @param scenarioSuppliers
     *            The array of Suppliers that contain an AbstractScenario.
     * @return Feature, that is described by the description and has the
     *         {@link AbstractScenario}s of the supplier.
     */
    @SafeVarargs
    public static Feature feature(final String description, final Supplier<AbstractScenario>... scenarioSuppliers)
    {
        // Convert Array of AbstractScenario Suppliers to a list of AbstractScenarios
        List<AbstractScenario> scenarios = new ArrayList<>();
        for (Supplier<AbstractScenario> scenarioSupplier : scenarioSuppliers)
        {
            scenarios.add(scenarioSupplier.get());
        }

        /*
         * Add the defining class to the name of the feature. The defining class is
         * either the second or third element on the stack, depending on where the
         * feature is defined.
         */
        String currentClass = Thread.currentThread().getStackTrace()[2].getClassName();
        if (currentClass.equals(BddSuite.class.getName()))
        {
            currentClass = Thread.currentThread().getStackTrace()[3].getClassName();
        }

        // Create the feature and return it
        return new Feature(description, scenarios, currentClass);
    }

    /**
     * Creates a {@link ScenarioOutline} with the specified description and
     * descriptor.<br>
     * The behavior and test data of the ScenarioOutline is contained in the
     * {@link OutlineDescriptor}.
     * 
     * @param description
     *            The description of the scenario.
     * @param descriptor
     *            The OutlineDescriptor with the behavior and test data of the
     *            scenario.
     * @param <T>
     *            The type of the test data.
     * @return A ScenarioOutline with the test data and behavior specified in the
     *         descriptor.
     */
    public static <T> ScenarioOutline<T> scenario(String description, OutlineDescriptor<T> descriptor)
    {
        ScenarioOutline<T> outline;

        /*
         * If the descriptor wasn't null, create the ScenarioOutline with the content of
         * the descriptor. Otherwise initialize it with null.
         */
        if (descriptor != null)
        {
            outline = new ScenarioOutline<T>(description, descriptor.getSteps(), descriptor.getTestdata());
        }
        else
        {
            outline = new ScenarioOutline<T>(description, null, null);
        }
        return outline;
    }

    /**
     * Creates a {@link Scenario} with the specified description and steps.
     * 
     * @param description
     *            The description of the Scenario.
     * @param steps
     *            The {@link Steps} of the scenario.
     * @return A Scenario with the specified description and steps.
     */
    public static Scenario scenario(String description, Steps steps)
    {
        return new Scenario(description, steps);
    }

    /**
     * Creates a pending {@link Scenario} with the specified description.
     * 
     * @param description
     *            The description of the Scenario.
     * @return A pending scenario with the specified description.
     */
    public static Scenario scenario(String description)
    {
        return new Scenario(description);
    }

    /**
     * Creates an {@link OutlineDescriptor} with the specified test data.
     * 
     * @param testdata
     *            The test data for the OutlineDescriptor.
     * @param <T>
     *            The type of the test data.
     * @return An OutlineDescriptor with the specified test data.
     */
    @SafeVarargs
    public static <T> OutlineDescriptor<T> with(T... testdata)
    {
        return new OutlineDescriptor<T>(testdata);
    }

    /**
     * Creates an {@link OutlineDescriptor} without any test data.
     * 
     * @param clazz
     *            The class of the test datum.
     * @param <T>
     *            The type of the test datum.
     * @return An OutlineDescriptor without test data.
     */
    public static <T> OutlineDescriptor<T> withTypeOf(Class<T> clazz)
    {
        return new OutlineDescriptor<T>();
    }

    /*
     * GWAT
     */

    /**
     * Creates a {@link Steps} instance and adds every {@link Step} of the parameter
     * to it.
     * 
     * @param steps
     *            The {@link Steps} whose {@link Step}s should be added.
     * 
     * @return New {@link Steps} with the specified {@link Step}s inside.
     */
    public static Steps and(Steps steps)
    {
        return new Steps().and(steps);
    }

    /**
     * Creates a new instance of {@link Steps} which contains the {@link Step}
     * specified with the description and runnable. <br>
     * The keyword of the Step is {@link And}.
     * 
     * @param description
     *            The description of the Step.
     * @param runner
     *            The behavior of the Step.
     * @return New {@link Steps} with the specified {@link Step}s inside.
     */
    public static Steps and(String description, Runnable runner)
    {
        return new Steps().and(description, runner);
    }

    /**
     * Creates a {@link Steps} instance and adds every {@link Step} of the parameter
     * to it.
     * 
     * @param steps
     *            The {@link Steps} whose {@link Step}s should be added.
     * 
     * @return New {@link Steps} with the specified {@link Step}s inside.
     */
    public static Steps given(Steps steps)
    {
        return new Steps().given(steps);
    }

    /**
     * Creates a new instance of {@link Steps} which contains the {@link Step}
     * specified with the description and runnable. <br>
     * The keyword of the Step is {@link Given}.
     * 
     * @param description
     *            The description of the step.
     * @param runner
     *            The behavior of the step.
     * @return New {@link Steps} with the specified {@link Step}s inside.
     */
    public static Steps given(String description, Runnable runner)
    {
        return new Steps().given(description, runner);
    }

    /**
     * Creates a {@link Steps} instance and adds every {@link Step} of the parameter
     * to it.
     * 
     * @param steps
     *            The {@link Steps} whose {@link Step}s should be added.
     * 
     * @return New {@link Steps} with the specified {@link Step}s inside.
     */
    public static Steps then(Steps steps)
    {
        return new Steps().then(steps);
    }

    /**
     * Creates a new instance of {@link Steps} which contains the {@link Step}
     * specified with the description and runnable. <br>
     * The keyword of the Step is {@link Then}.
     * 
     * @param description
     *            The description of the step.
     * @param runner
     *            The behavior of the step.
     * @return New {@link Steps} with the specified {@link Step}s inside.
     */
    public static Steps then(String description, Runnable runner)
    {
        return new Steps().then(description, runner);
    }

    /**
     * Creates a {@link Steps} instance and adds every {@link Step} of the parameter
     * to it.
     * 
     * @param steps
     *            The {@link Steps} whose {@link Step}s should be added.
     * 
     * @return New {@link Steps} with the specified {@link Step}s inside.
     */
    public static Steps when(Steps steps)
    {
        return new Steps().when(steps);
    }

    /**
     * Creates a new instance of {@link Steps} which contains the {@link Step}
     * specified with the description and runnable. <br>
     * The keyword of the Step is {@link When}.
     * 
     * @param description
     *            The description of the step.
     * @param runner
     *            The behavior of the step.
     * @return New {@link Steps} with the specified {@link Step}s inside.
     */
    public static Steps when(String description, Runnable runner)
    {
        return new Steps().when(description, runner);
    }

}
