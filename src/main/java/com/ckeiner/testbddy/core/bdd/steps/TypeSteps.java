package com.ckeiner.testbddy.core.bdd.steps;

import java.util.ArrayList;
import java.util.function.Consumer;

import com.aventstack.extentreports.GherkinKeyword;
import com.ckeiner.testbddy.api.PendingConsumer;
import com.ckeiner.testbddy.api.PendingRunnable;

/**
 * Describes a scenario with only one data set.<br>
 * Thus, it has the given, when, then, and methods.
 * 
 * @author ckeiner
 *
 * @param <T>
 *            The type of the Testdata.
 */
public class TypeSteps<T> extends AbstractSteps<TypeStep<T>>
{
    /**
     * The used test data.
     */
    private T data;

    /**
     * Creates a BddTypeScenario.
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
     * Adds a step to the list of BddSteps.
     * 
     * @param keyword
     *            The {@link GherkinKeyword} of the step.
     * @param description
     *            The description of the step in a natural language.
     * @param consumer
     *            The behavior of the step.
     */
    private void addStep(GherkinKeyword keyword, String description, Consumer<T> consumer)
    {
        getSteps().add(new TypeStep<T>(keyword, description, consumer));
    }

    /**
     * Adds a step to the list of BddSteps.
     * 
     * @param keyword
     *            The String, that describes the {@link GherkinKeyword}.
     * @param description
     *            The description of the step in a natural language.
     * @param consumer
     *            The behavior of the step.
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
     * Adds all steps of the specified scenario to the steps of this BddScenario.
     * 
     * @param steps
     *            The BddScenario which steps should be added to the steps of this
     *            class.
     */
    private void addAllSteps(TypeSteps<T> steps)
    {
        for (TypeStep<T> step : steps.getSteps())
        {
            addStep(step.getKeyword(), step.getDescription(), step.getBehavior());
        }
    }

    /**
     * Adds all steps of the specified scenario to the steps of this
     * BddTypeScenario.
     * 
     * @param scenario
     *            The {@link Steps} which steps should be added to the steps of this
     *            class.
     * @return The current BddTypeScenario.
     */
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
     * Adds all steps of the specified scenario to the steps of this
     * BddTypeScenario.<br>
     * Note, that the generic type of the parameter has to fit the generic type of
     * this class.
     * 
     * @param scenario
     *            The BddTypeScenario which steps should be added to the steps of
     *            this class.
     * @return The current BddTypeScenario.
     */
    public TypeSteps<T> and(final TypeSteps<T> scenario)
    {
        addAllSteps(scenario);
        return this;
    }

    /**
     * Adds the step specified by the description and consumer to this scenario.<br>
     * The {@link GherkinKeyword} of the step is "And".
     * 
     * @param description
     *            The description of the step in a natural language.
     * @param consumer
     *            The behavior of this step.
     * @return The current BddTypeScenario.
     */
    public TypeSteps<T> and(final String description, final Consumer<T> consumer)
    {
        addStep("And", description, consumer);
        return this;
    }

    /**
     * Adds the step specified by the description and runner to this scenario.<br>
     * The {@link GherkinKeyword} of the step is "And".
     * 
     * @param description
     *            The description of the step in a natural language.
     * @param runner
     *            The behavior of this step.
     * @return The current BddTypeScenario.
     */
    @Override
    public TypeSteps<T> and(final String description, final Runnable runner)
    {

        addStep("And", description, runnableToConsumer(runner));
        return this;
    }

    /**
     * Adds all steps of the specified scenario to the steps of this
     * BddTypeScenario.
     * 
     * @param scenario
     *            The {@link Steps} which steps should be added to the steps of this
     *            class.
     * @return The current BddTypeScenario.
     */
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
     * Adds all steps of the specified scenario to the steps of this
     * BddTypeScenario.<br>
     * Note, that the generic type of the parameter has to fit the generic type of
     * this class.
     * 
     * @param scenario
     *            The BddTypeScenario which steps should be added to the steps of
     *            this class.
     * @return The current BddTypeScenario.
     */
    public TypeSteps<T> given(final TypeSteps<T> scenario)
    {
        addAllSteps(scenario);
        return this;
    }

    /**
     * Adds the step specified by the description and consumer to this scenario.<br>
     * The {@link GherkinKeyword} of the step is "And".
     * 
     * @param description
     *            The description of the step in a natural language.
     * @param consumer
     *            The behavior of this step.
     * @return The current BddTypeScenario.
     */
    public TypeSteps<T> given(final String description, final Consumer<T> consumer)
    {
        addStep("Given", description, consumer);
        return this;
    }

    /**
     * Adds the step specified by the description and runner to this scenario.<br>
     * The {@link GherkinKeyword} of the step is "Given".
     * 
     * @param description
     *            The description of the step in a natural language.
     * @param runner
     *            The behavior of this step.
     * @return The current BddTypeScenario.
     */
    @Override
    public TypeSteps<T> given(final String description, final Runnable runner)
    {
        addStep("Given", description, runnableToConsumer(runner));

        return this;
    }

    /**
     * Adds all steps of the specified scenario to the steps of this
     * BddTypeScenario.
     * 
     * @param scenario
     *            The {@link Steps} which steps should be added to the steps of this
     *            class.
     * @return The current BddTypeScenario.
     */
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
     * Adds all steps of the specified scenario to the steps of this
     * BddTypeScenario.<br>
     * Note, that the generic type of the parameter has to fit the generic type of
     * this class.
     * 
     * @param scenario
     *            The BddTypeScenario which steps should be added to the steps of
     *            this class.
     * @return The current BddTypeScenario.
     */
    public TypeSteps<T> then(final TypeSteps<T> scenario)
    {
        addAllSteps(scenario);
        return this;
    }

    /**
     * Adds the step specified by the description and consumer to this scenario.<br>
     * The {@link GherkinKeyword} of the step is "And".
     * 
     * @param description
     *            The description of the step in a natural language.
     * @param consumer
     *            The behavior of this step.
     * @return The current BddTypeScenario.
     */
    public TypeSteps<T> then(final String description, final Consumer<T> consumer)
    {
        addStep("Then", description, consumer);
        return this;
    }

    /**
     * Adds the step specified by the description and runner to this scenario.<br>
     * The {@link GherkinKeyword} of the step is "Then".
     * 
     * @param description
     *            The description of the step in a natural language.
     * @param runner
     *            The behavior of this step.
     * @return The current BddTypeScenario.
     */
    @Override
    public TypeSteps<T> then(final String description, final Runnable runner)
    {
        addStep("Then", description, runnableToConsumer(runner));
        return this;
    }

    /**
     * Adds all steps of the specified scenario to the steps of this
     * BddTypeScenario.
     * 
     * @param scenario
     *            The {@link Steps} which steps should be added to the steps of this
     *            class.
     * @return The current BddTypeScenario.
     */
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
     * Adds all steps of the specified scenario to the steps of this
     * BddTypeScenario.<br>
     * Note, that the generic type of the parameter has to fit the generic type of
     * this class.
     * 
     * @param scenario
     *            The BddTypeScenario which steps should be added to the steps of
     *            this class.
     * @return The current BddTypeScenario.
     */
    public TypeSteps<T> when(final TypeSteps<T> scenario)
    {
        addAllSteps(scenario);
        return this;
    }

    /**
     * Adds the step specified by the description and consumer to this scenario.<br>
     * The {@link GherkinKeyword} of the step is "And".
     * 
     * @param description
     *            The description of the step in a natural language.
     * @param consumer
     *            The behavior of this step.
     * @return The current BddTypeScenario.
     */
    public TypeSteps<T> when(final String description, final Consumer<T> consumer)
    {
        addStep("When", description, consumer);
        return this;
    }

    /**
     * Adds the step specified by the description and runner to this scenario.<br>
     * The {@link GherkinKeyword} of the step is "When".
     * 
     * @param description
     *            The description of the step in a natural language.
     * @param runner
     *            The behavior of this step.
     * @return The current BddTypeScenario.
     */
    @Override
    public TypeSteps<T> when(final String description, final Runnable runner)
    {
        addStep("When", description, runnableToConsumer(runner));
        return this;
    }

    /**
     * Specifies the data of the BddTypeScenario.
     * 
     * @param data
     *            The test data for the scenario.
     * @return The current BddTypeScenario.
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
     *         A Consumer that runs the Runnable otherwise.
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
