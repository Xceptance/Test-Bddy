package bddtester.core.bdd.steps;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.aventstack.extentreports.GherkinKeyword;

import bddtester.core.bdd.background.Background;
import bddtester.core.bdd.background.PostStep;
import bddtester.core.bdd.status.Statusable;
import bddtester.core.reporting.ReportInterface;
import bddtester.core.throwables.errors.StepError;
import bddtester.core.throwables.exceptions.StepException;

/**
 * Describes a scenario with only one data set.<br>
 * Thus, it has the given, when, then, and methods.
 * 
 * @author ckeiner
 *
 * @param <T>
 *            The type of the Testdata.
 */
public class TypeSteps<T> implements Statusable
{
    /**
     * The class responsible for reporting.
     */
    private ReportInterface reporter;

    /**
     * The steps that happen before all other steps
     */
    private final List<Background> backgrounds;

    /**
     * The steps that happen after all other steps
     */
    private final List<PostStep> postSteps;

    /**
     * The list of steps to execute.
     */
    private final List<TypeStep<T>> steps;

    /**
     * The used test data.
     */
    private T data;

    /**
     * Creates a BddTypeScenario.
     */
    public TypeSteps()
    {
        this(new ArrayList<>());
    }

    /**
     * Creates a BddTypeScenario with the specified list of steps.
     *
     * @param steps
     *            The list of steps to execute.
     */
    public TypeSteps(final List<TypeStep<T>> steps)
    {
        this(steps, new ArrayList<>());
    }

    /**
     * Creates a BddTypeScenario with the specified reporter and list of
     * {@link Step}s and {@link Background}s.
     * 
     * @param steps
     *            The list of steps to execute.
     * @param backgrounds
     *            The list of {@link Background}s to execute before each other
     *            steps.
     */
    public TypeSteps(final List<TypeStep<T>> steps, final List<Background> backgrounds)
    {
        this(steps, backgrounds, new ArrayList<>());
    }

    /**
     * Creates a BddTypeScenario with the specified reporter and list of
     * {@link Step}s, {@link Background}s and {@link PostStep}s.
     * 
     * @param steps
     *            The list of steps to execute.
     * @param backgrounds
     *            The list of {@link Background}s to execute before the other steps.
     * @param postSteps
     *            The list of {@link PostSteps}s to execute after the other steps.
     */
    public TypeSteps(final List<TypeStep<T>> steps, final List<Background> backgrounds, final List<PostStep> postSteps)
    {
        this.steps = steps;
        this.backgrounds = backgrounds;
        this.postSteps = postSteps;
    }

    /**
     * Executes the scenario.<br>
     * If an exception or error occurs in the steps, the steps which weren't
     * executed yet, are set to be skipped. Afterwards, the exception or error is
     * re-thrown as {@link StepException} and {@link StepError} respectively.<br>
     * Should both, an exception and an error, occur, a StepException is thrown.
     */
    public void test()
    {
        StepException stepException = null;
        StepError stepError = null;
        addBackgroundToSteps();
        addPostStepsToSteps();
        for (final TypeStep<T> step : steps)
        {
            if (step.getReporter() == null && this.getReporter() != null)
            {
                step.setReporter(getReporter());
            }
            try
            {
                if (stepException != null || stepError != null)
                {
                    step.withData(data).skipStep();
                }
                // Test the step with the data
                else
                {
                    step.withData(data).test();
                }
            } catch (StepException e)
            {
                stepException = e;
            } catch (StepError e)
            {
                stepError = e;
            }
        }
        if (stepException != null)
        {
            throw stepException;
        }
        else if (stepError != null)
        {
            throw stepError;
        }
    }

    public void skipSteps()
    {
        for (final TypeStep<T> step : steps)
        {
            if (step.getReporter() == null && this.getReporter() != null)
            {
                step.setReporter(getReporter());
            }
            step.withData(data).skipStep();
        }
    }

    /**
     * Adds all {@link Background}s to the list of steps.
     */
    // TODO should I rather return a new list of steps, so if you look at this
    // class, the background is still separated from the list of steps?
    private void addBackgroundToSteps()
    {
        // Get the Steps from the background
        List<Steps> steps = new ArrayList<>();
        for (Background background : backgrounds)
        {
            steps.add(background.getSteps());
        }
        // The actual position to add steps depends on the number of steps from the last
        // added Steps
        int index = 0;
        for (Steps actualSteps : steps)
        {
            addAllSteps(index, actualSteps);
            // Increase the index by the number of steps added
            index += actualSteps.getSteps().size();
        }
    }

    /**
     * Adds all {@link PostStep}s to the list of steps.
     */
    private void addPostStepsToSteps()
    {
        for (PostStep postStep : postSteps)
        {
            addAllSteps(postStep.getSteps());
        }
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
        this.steps.add(new TypeStep<T>(keyword, description, consumer));
    }

    /**
     * Adds a step to the list of BddSteps.
     */
    private void addAllSteps(Steps steps)
    {
        for (Step step : steps.getSteps())
        {
            this.steps.add(
                    new TypeStep<T>(step.getKeyword(), step.getDescription(), runnableToConsumer(step.getBehavior())));
        }
    }

    /**
     * Adds a step to the list of BddSteps.
     * 
     * @param index
     *            Where the step should be added.
     * @param keyword
     *            The {@link GherkinKeyword} of the step.
     * @param description
     *            The description of the step in a natural language.
     * @param consumer
     *            The behavior of the step.
     */
    private void addStep(int index, GherkinKeyword keyword, String description, Consumer<T> consumer)
    {
        this.steps.add(index, new TypeStep<T>(keyword, description, consumer));
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
            this.steps.add(new TypeStep<T>(new GherkinKeyword(keyword), description, consumer));
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
     * Adds all steps of the specified scenario to the steps of this BddScenario.
     * 
     * @param index
     *            Where the Steps should be added.
     * @param steps
     *            The {@link Steps} which steps should be added to the steps of this
     *            class.
     */
    private void addAllSteps(int index, Steps steps)
    {
        for (Step step : steps.getSteps())
        {
            // Increase the index for each step
            addStep(index++, step.getKeyword(), step.getDescription(), runnableToConsumer(step.getBehavior()));
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
     * @return A Consumer, that executes the runner.
     */
    private Consumer<T> runnableToConsumer(final Runnable runner)
    {
        return new Consumer<T>()
            {
                @Override
                public void accept(final T t)
                {
                    runner.run();
                }
            };
    }

    public T getData()
    {
        return data;
    }

    public ReportInterface getReporter()
    {
        return reporter;
    }

    public void setReporter(ReportInterface reporter)
    {
        this.reporter = reporter;
    }

    public List<TypeStep<T>> getSteps()
    {
        return steps;
    }

    // public void addBackground(Background background)
    public void addBackground(List<Background> backgrounds)
    {
        this.backgrounds.addAll(backgrounds);
    }

    // public void addBackground(Background background)
    public void addPostSteps(List<PostStep> postSteps)
    {
        this.postSteps.addAll(postSteps);
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
