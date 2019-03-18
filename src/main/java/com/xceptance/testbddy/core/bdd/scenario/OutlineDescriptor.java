package com.xceptance.testbddy.core.bdd.scenario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import com.aventstack.extentreports.gherkin.model.And;
import com.aventstack.extentreports.gherkin.model.Given;
import com.aventstack.extentreports.gherkin.model.Then;
import com.aventstack.extentreports.gherkin.model.When;
import com.xceptance.testbddy.core.bdd.status.Statusable;
import com.xceptance.testbddy.core.bdd.steps.Step;
import com.xceptance.testbddy.core.bdd.steps.Steps;
import com.xceptance.testbddy.core.bdd.steps.TypeSteps;

/**
 * Describes a {@link ScenarioOutline}.<br>
 * Thus, it has information about every test data used in the scenario and the
 * behavior of it.
 * 
 * @author ckeiner
 *
 * @param <T>
 *            The type of the test data.
 */
public class OutlineDescriptor<T> implements Statusable
{
    /**
     * The steps containing the behavior of the {@link ScenarioOutline}.
     */
    private final TypeSteps<T> steps;

    /**
     * The list of test data
     */
    private final List<T> testdata;

    /**
     * Creates a new instance of {@link OutlineDescriptor} with empty
     * {@link TypeSteps} and the specified array of test data.
     * 
     * @param testdata
     *            The test data for each execution. One element is exactly one test
     *            datum.
     */
    @SafeVarargs
    public OutlineDescriptor(T... testdata)
    {
        this(new TypeSteps<T>(), testdata);
    }

    /**
     * Creates a new instance of {@link OutlineDescriptor} with the supplied
     * {@link TypeSteps} and the specified array of test data.
     * 
     * @param steps
     *            The {@link Steps} of the scenario.
     * @param testdata
     *            The test data for each execution. One element is exactly one test
     *            datum.
     */
    @SafeVarargs
    public OutlineDescriptor(TypeSteps<T> steps, T... testdata)
    {
        this.steps = steps;
        this.testdata = new ArrayList<>();
        // If test data was specified, add it to the list
        if (testdata != null && testdata.length > 0)
        {
            this.testdata.addAll(Arrays.asList(testdata));
        }
    }

    public TypeSteps<T> getSteps()
    {
        return steps;
    }

    public List<T> getTestdata()
    {
        return testdata;
    }

    /**
     * Adds all steps of the specified {@link Steps} to the {@link #steps}.<br>
     * 
     * @param steps
     *            The {@link Steps} whose {@link Step}s should be added.
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> and(final Steps steps)
    {
        getSteps().and(steps);
        return this;
    }

    /**
     * Adds all steps of the specified {@link TypeSteps} to the {@link #steps}.<br>
     * Note, that the generic type of the parameter has to fit the generic type of
     * this class.<br>
     * 
     * @param steps
     *            The {@link TypeSteps} whose {@link Step}s should be added.
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> and(final TypeSteps<T> steps)
    {
        getSteps().and(steps);
        return this;
    }

    /**
     * Adds the step specified by the description and consumer to the
     * {@link #steps}.<br>
     * The keyword of the TypeStep is {@link And}.
     * 
     * @param description
     *            The description of the step in a natural language.
     * @param consumer
     *            The behavior of this step.
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> and(final String description, final Consumer<T> consumer)
    {
        getSteps().and(description, consumer);
        return this;
    }

    /**
     * Adds the step specified by the description and runner to the
     * {@link #steps}.<br>
     * The keyword of the TypeStep is {@link And}.
     * 
     * @param description
     *            The description of the step in a natural language.
     * @param runner
     *            The behavior of this step.
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> and(final String description, final Runnable runner)
    {

        getSteps().and(description, runner);
        return this;
    }

    /**
     * Adds all steps of the specified {@link Steps} to the {@link #steps}.<br>
     * 
     * @param steps
     *            The {@link Steps} whose {@link Step}s should be added.
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> given(final Steps steps)
    {
        getSteps().given(steps);
        return this;
    }

    /**
     * Adds all steps of the specified {@link TypeSteps} to the {@link #steps}.<br>
     * Note, that the generic type of the parameter has to fit the generic type of
     * this class.<br>
     * 
     * @param steps
     *            The {@link TypeSteps} whose {@link Step}s should be added.
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> given(final TypeSteps<T> steps)
    {
        getSteps().given(steps);
        return this;
    }

    /**
     * Adds the step specified by the description and consumer to the
     * {@link #steps}.<br>
     * The keyword of the TypeStep is {@link Given}.
     * 
     * @param description
     *            The description of the step in a natural language.
     * @param consumer
     *            The behavior of this step.
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> given(final String description, final Consumer<T> consumer)
    {
        getSteps().given(description, consumer);
        return this;
    }

    /**
     * Adds the step specified by the description and runner to the
     * {@link #steps}.<br>
     * The keyword of the TypeStep is {@link Given}.
     * 
     * @param description
     *            The description of the step in a natural language.
     * @param runner
     *            The behavior of this step.
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> given(final String description, final Runnable runner)
    {
        getSteps().given(description, runner);
        return this;
    }

    /**
     * Adds all steps of the specified {@link Steps} to the {@link #steps}.<br>
     * 
     * @param steps
     *            The {@link Steps} whose {@link Step}s should be added.
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> then(final Steps steps)
    {
        getSteps().then(steps);
        return this;
    }

    /**
     * Adds all steps of the specified {@link TypeSteps} to the {@link #steps}.<br>
     * Note, that the generic type of the parameter has to fit the generic type of
     * this class.<br>
     * 
     * @param steps
     *            The {@link TypeSteps} whose {@link Step}s should be added.
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> then(final TypeSteps<T> steps)
    {
        getSteps().then(steps);
        return this;
    }

    /**
     * Adds the step specified by the description and consumer to the
     * {@link #steps}.<br>
     * The keyword of the TypeStep is {@link Then}.
     * 
     * @param description
     *            The description of the step in a natural language.
     * @param consumer
     *            The behavior of this step.
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> then(final String description, final Consumer<T> consumer)
    {
        getSteps().then(description, consumer);
        return this;
    }

    /**
     * Adds the step specified by the description and runner to the
     * {@link #steps}.<br>
     * The keyword of the TypeStep is {@link Then}.
     * 
     * @param description
     *            The description of the step in a natural language.
     * @param runner
     *            The behavior of this step.
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> then(final String description, final Runnable runner)
    {
        getSteps().then(description, runner);
        return this;
    }

    /**
     * Adds all steps of the specified {@link Steps} to the {@link #steps}.<br>
     * 
     * @param steps
     *            The {@link Steps} whose {@link Step}s should be added.
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> when(final Steps steps)
    {
        getSteps().when(steps);
        return this;
    }

    /**
     * Adds all steps of the specified {@link TypeSteps} to the {@link #steps}.<br>
     * Note, that the generic type of the parameter has to fit the generic type of
     * this class.<br>
     * 
     * @param steps
     *            The {@link TypeSteps} whose {@link Step}s should be added.
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> when(final TypeSteps<T> steps)
    {
        getSteps().when(steps);
        return this;
    }

    /**
     * Adds the step specified by the description and consumer to the
     * {@link #steps}.<br>
     * The keyword of the TypeStep is {@link When}.
     * 
     * @param description
     *            The description of the step in a natural language.
     * @param consumer
     *            The behavior of this step.
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> when(final String description, final Consumer<T> consumer)
    {
        getSteps().when(description, consumer);
        return this;
    }

    /**
     * Adds the step specified by the description and runner to the
     * {@link #steps}.<br>
     * The keyword of the TypeStep is {@link When}.
     * 
     * @param description
     *            The description of the step in a natural language.
     * @param runner
     *            The behavior of this step.
     * @return The current OutlineDescriptor.
     */
    public OutlineDescriptor<T> when(final String description, final Runnable runner)
    {
        getSteps().when(description, runner);
        return this;
    }

    @Override
    public OutlineDescriptor<T> ignore()
    {
        getSteps().ignore();
        return this;
    }

    @Override
    public OutlineDescriptor<T> wip()
    {
        getSteps().wip();
        return this;
    }

    @Override
    public OutlineDescriptor<T> skip()
    {
        getSteps().skip();
        return this;
    }

}
