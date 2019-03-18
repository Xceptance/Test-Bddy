package com.xceptance.testbddy.core.bdd.steps;

import java.util.ArrayList;
import java.util.function.Consumer;

import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.gherkin.model.And;
import com.aventstack.extentreports.gherkin.model.Given;
import com.aventstack.extentreports.gherkin.model.Then;
import com.aventstack.extentreports.gherkin.model.When;
import com.xceptance.testbddy.api.PendingConsumer;
import com.xceptance.testbddy.api.PendingRunnable;

/**
 * Describes a scenario with only one data set.<br>
 * Thus, it has the given, when, then, and methods. To execute it, see the
 * {@link #test()} method.
 * 
 * @author ckeiner
 *
 * @param <T>
 *            The type of the Testdata.
 */
public class TypeSteps<T> extends AbstractSteps<TypeStep<T>>
{
    /**
     * The test data.
     */
    private T data;

    /**
     * Creates new steps with an empty list.
     */
    public TypeSteps()
    {
        super(new ArrayList<>());
    }

    @Override
    protected void executeStep(TypeStep<T> step)
    {
        step.withData(data).test();
    }

    @Override
    protected void skipStep(TypeStep<T> step)
    {
        step.withData(data).skipStep();
    }

    /**
     * Adds a new {@link Step} with the specified keyword, description and runner.
     * 
     * @param keyword
     *            The String, that describes the {@link GherkinKeyword}.
     * @param description
     *            The description of the step in a natural language.
     * @param consumer
     *            The behavior of the step.
     * @see GherkinKeyword GherkinKeyword for possible options.
     */
    private void addStep(String keyword, String description, Consumer<T> consumer)
    {
        try
        {
            getSteps().add(new TypeStep<T>(new GherkinKeyword(keyword), description, consumer));
        } catch (ClassNotFoundException e)
        {
            throw new IllegalStateException("Unknown Keyword " + keyword, e);
        }
    }

    /**
     * Adds a new {@link Step} with the specified keyword, description and runner.
     * 
     * @param keyword
     *            The {@link GherkinKeyword} of the step.
     * @param description
     *            The description of the step in a natural language.
     * @param consumer
     *            The behavior of the step.
     * @see GherkinKeyword GherkinKeyword for possible options.
     */
    private void addStep(GherkinKeyword keyword, String description, Consumer<T> consumer)
    {
        getSteps().add(new TypeStep<T>(keyword, description, consumer));
    }

    /**
     * Adds all {@link Step}s of the specified parameter to the steps of this class.
     * 
     * @param steps
     *            The {@link Steps} whose {@link Step}s should be added.
     */
    private void addAllSteps(TypeSteps<T> steps)
    {
        for (TypeStep<T> step : steps.getSteps())
        {
            addStep(step.getKeyword(), step.getDescription(), step.getBehavior());
        }
    }

    @Override
    public TypeSteps<T> and(final Steps scenario)
    {
        for (final Step step : scenario.getSteps())
        {
            addStep(step.getKeyword(), step.getDescription(), runnableToConsumer(step.getBehavior()));
        }
        return this;
    }

    /**
     * Adds all steps of the specified {@link TypeSteps} to the steps.<br>
     * Note, that the generic type of the parameter has to fit the generic type of
     * this class.
     * 
     * @param steps
     *            The {@link TypeSteps} whose {@link Step}s should be added.
     * @return The current TypeSteps.
     */
    public TypeSteps<T> and(final TypeSteps<T> steps)
    {
        addAllSteps(steps);
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
     * @return The current TypeSteps.
     */
    public TypeSteps<T> and(final String description, final Consumer<T> consumer)
    {
        addStep("And", description, consumer);
        return this;
    }

    @Override
    public TypeSteps<T> and(final String description, final Runnable runner)
    {

        addStep("And", description, runnableToConsumer(runner));
        return this;
    }

    @Override
    public TypeSteps<T> given(final Steps scenario)
    {
        for (final Step step : scenario.getSteps())
        {
            addStep(step.getKeyword(), step.getDescription(), runnableToConsumer(step.getBehavior()));
        }
        return this;
    }

    /**
     * Adds all steps of the specified {@link TypeSteps} to the steps.<br>
     * Note, that the generic type of the parameter has to fit the generic type of
     * this class.
     * 
     * @param steps
     *            The {@link TypeSteps} whose {@link Step}s should be added.
     * @return The current TypeSteps.
     */
    public TypeSteps<T> given(final TypeSteps<T> steps)
    {
        addAllSteps(steps);
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
     * @return The current TypeSteps.
     */
    public TypeSteps<T> given(final String description, final Consumer<T> consumer)
    {
        addStep("Given", description, consumer);
        return this;
    }

    @Override
    public TypeSteps<T> given(final String description, final Runnable runner)
    {
        addStep("Given", description, runnableToConsumer(runner));

        return this;
    }

    @Override
    public TypeSteps<T> then(final Steps scenario)
    {
        for (final Step step : scenario.getSteps())
        {
            addStep(step.getKeyword(), step.getDescription(), runnableToConsumer(step.getBehavior()));
        }
        return this;
    }

    /**
     * Adds all steps of the specified {@link TypeSteps} to the steps.<br>
     * Note, that the generic type of the parameter has to fit the generic type of
     * this class.
     * 
     * @param steps
     *            The {@link TypeSteps} whose {@link Step}s should be added.
     * @return The current TypeSteps.
     */
    public TypeSteps<T> then(final TypeSteps<T> steps)
    {
        addAllSteps(steps);
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
     * @return The current TypeSteps.
     */
    public TypeSteps<T> then(final String description, final Consumer<T> consumer)
    {
        addStep("Then", description, consumer);
        return this;
    }

    @Override
    public TypeSteps<T> then(final String description, final Runnable runner)
    {
        addStep("Then", description, runnableToConsumer(runner));
        return this;
    }

    @Override
    public TypeSteps<T> when(final Steps scenario)
    {
        for (final Step step : scenario.getSteps())
        {
            addStep(step.getKeyword(), step.getDescription(), runnableToConsumer(step.getBehavior()));
        }
        return this;
    }

    /**
     * Adds all steps of the specified {@link TypeSteps} to the steps.<br>
     * Note, that the generic type of the parameter has to fit the generic type of
     * this class.
     * 
     * @param steps
     *            The {@link TypeSteps} whose {@link Step}s should be added.
     * @return The current TypeSteps.
     */
    public TypeSteps<T> when(final TypeSteps<T> steps)
    {
        addAllSteps(steps);
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
     * @return The current TypeSteps.
     */
    public TypeSteps<T> when(final String description, final Consumer<T> consumer)
    {
        addStep("When", description, consumer);
        return this;
    }

    @Override
    public TypeSteps<T> when(final String description, final Runnable runner)
    {
        addStep("When", description, runnableToConsumer(runner));
        return this;
    }

    /**
     * Specifies the data used during the execution.
     * 
     * @param data
     *            The test data for the scenario.
     * @return The current TypeSteps.
     */
    public TypeSteps<T> withData(final T data)
    {
        this.data = data;
        return this;
    }

    /**
     * Creates a {@link Consumer} out of the specified {@link Runnable}.
     * 
     * @param runner
     *            The Runnable to transform.
     * @return <code>null</code> if the Runnable was null.<br>
     *         A {@link PendingConsumer} if the Runnable was a
     *         {@link PendingRunnable}.<br>
     *         Otherwise, a Consumer that runs the Runnable.
     */
    private Consumer<T> runnableToConsumer(final Runnable runner)
    {
        Consumer<T> consumer;
        // If the runnable was null, return a null consumer
        if (runner == null)
        {
            consumer = null;
        }
        // If the runnable was a pending runnable, create a pending consumer
        else if (runner instanceof PendingRunnable)
        {
            consumer = new PendingConsumer<T>();
        }
        // If the runnable was something else, create a consumer out of the runner
        else
        {
            consumer = new Consumer<T>()
                {
                    @Override
                    public void accept(final T t)
                    {
                        runner.run();
                    }
                };
        }
        // Return the created consumer
        return consumer;
    }

    public T getData()
    {
        return data;
    }

    @Override
    public TypeSteps<T> ignore()
    {
        getSteps().get(getSteps().size() - 1).ignore();
        return this;
    }

    @Override
    public TypeSteps<T> wip()
    {
        getSteps().get(getSteps().size() - 1).wip();
        return this;
    }

    @Override
    public TypeSteps<T> skip()
    {
        getSteps().get(getSteps().size() - 1).skip();
        return this;
    }

}
