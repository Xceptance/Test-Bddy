package com.xceptance.testbddy.core.bdd.steps;

import java.util.function.Consumer;

import com.aventstack.extentreports.GherkinKeyword;
import com.xceptance.testbddy.core.reporting.ReportElement;
import com.xceptance.testbddy.core.util.PlaceholderResolver;

/**
 * Describes a BDD Step with a test datum. <br>
 * To execute it, see the {@link #test()} method.
 *
 * @param <T>
 *            The type of the test data.
 * @author ckeiner
 */
public class TypeStep<T> extends AbstractStep<Consumer<T>>
{
    /**
     * The test datum for this step.
     */
    protected T testdata;

    /**
     * Creates a TypeStep with the specified keyword, description, behavior but
     * without test data.
     * 
     * @param keyword
     *            The {@link GherkinKeyword} of the step.
     * @param description
     *            A String describing what this step does.
     * @param behavior
     *            A consumer containing the behavior.
     * @see AbstractStep#AbstractStep(GherkinKeyword, String, Object)
     * @see GherkinKeyword
     */
    public TypeStep(final GherkinKeyword keyword, final String description, final Consumer<T> behavior)
    {
        this(keyword, description, behavior, null);
    }

    /**
     * Creates a TypeStep with the specified keyword, description, behavior but
     * without test data.
     * 
     * @param keyword
     *            The {@link GherkinKeyword} of the step.
     * @param description
     *            A String describing what this step does.
     * @param behavior
     *            A consumer containing the behavior.
     * @param testdata
     *            The test data for this step.
     * @see AbstractStep#AbstractStep(GherkinKeyword, String, Object)
     * @see GherkinKeyword
     */
    public TypeStep(final GherkinKeyword keyword, final String description, final Consumer<T> behavior,
            final T testdata)
    {
        super(keyword, description, behavior);
        this.testdata = testdata;
    }

    /**
     * Specifies the data used during the execution.
     * 
     * @param testdata
     *            The test datum for the scenario.
     * @return The current TypeStep.
     */
    public TypeStep<T> withData(T testdata)
    {
        this.testdata = testdata;
        return this;
    }

    /**
     * Sets the test data to the parameter and then calls {@link #test()}.
     * 
     * @param testdata
     *            The test data that should be used during the execution.
     */
    public void test(T testdata)
    {
        this.testdata = testdata;
        test();
    }

    /**
     * First, resolves the placeholders in the description, then sets up the
     * {@link ReportElement} for this component.<br>
     * Also assigns the step's status as the report element's category.
     * 
     * @return The {@link ReportElement} representing the step.
     */
    @Override
    protected ReportElement setUpReporter()
    {
        PlaceholderResolver<T> resolver = new PlaceholderResolver<>();
        setDescription(resolver.resolvePlaceholders(getDescription(), testdata));
        return super.setUpReporter(true, getDescription());
    }

    /**
     * Executes the consumer with {@link #testdata} as parameter.
     * 
     * @throws IllegalStateException
     *             If {@link #testdata} is <code>null</code>.
     */
    @Override
    protected void executeStep()
    {
        // Throw an error if the testdata is null
        if (testdata == null)
        {
            throw new IllegalStateException("Testdata cannot be null, please supply testdata");
        }
        // Execute the consumer
        getBehavior().accept(testdata);
    }

    public T getTestdata()
    {
        return testdata;
    }

}
