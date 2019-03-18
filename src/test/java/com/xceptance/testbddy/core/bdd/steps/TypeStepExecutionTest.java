package com.xceptance.testbddy.core.bdd.steps;

import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Test;

import com.aventstack.extentreports.GherkinKeyword;
import com.xceptance.testbddy.core.bdd.steps.Step;
import com.xceptance.testbddy.core.bdd.steps.TypeStep;
import com.xceptance.testbddy.core.throwables.exceptions.StepException;
import com.xceptance.testbddy.util.ExecutionTest;

public class TypeStepExecutionTest extends ExecutionTest
{

    /**
     * Verifies that a {@link TypeStep} can't execute without behavior but with test
     * data.
     * 
     * @throws ClassNotFoundException
     *             If the {@link GherkinKeyword} does not exist.
     */
    @Test(expected = StepException.class)
    public void shouldNotExecuteWithoutBehavior() throws ClassNotFoundException
    {
        // Create String containing the keyword
        String keywordString = "Given";
        // Create a GherkinKeyword for the step
        GherkinKeyword keyword = new GherkinKeyword(keywordString);
        // Create the description for a step
        String stepDescription = "Step Description";
        // Create Step with the keyword, description and no behavior
        new TypeStep<Object>(keyword, stepDescription, null).withData(new Object()).test();

    }

    /**
     * Verifies that a {@link Step} can't execute with behavior but missing test
     * data.
     * 
     * @throws ClassNotFoundException
     *             If the {@link GherkinKeyword} does not exist.
     */
    @Test(expected = StepException.class)
    public void shouldNotExecuteWithoutTestdata() throws ClassNotFoundException
    {
        String keywordString = "Given";
        // Create instance of steps
        GherkinKeyword keyword = new GherkinKeyword(keywordString);
        String stepDescription = "Step Description";
        Consumer<Object> consumer = (data) ->
            {
                execution++;
            };
        new TypeStep<Object>(keyword, stepDescription, consumer).test();
    }

    /**
     * Verifies that a {@link TypeStep} executes the behavior with the supplied test
     * data.
     * 
     * @throws ClassNotFoundException
     *             If the {@link GherkinKeyword} does not exist.
     */
    @Test
    public void shouldExecuteBehavior() throws ClassNotFoundException
    {
        // The testdata
        Object testdata = new Object();
        // Create String containing the keyword
        String keywordString = "Given";
        // Create a GherkinKeyword for the step
        GherkinKeyword keyword = new GherkinKeyword(keywordString);
        // Create the description for a step
        String stepDescription = "Step Description";
        // Create the consumer
        Consumer<Object> consumer = (data) ->
            {
                Assert.assertSame(testdata, data);
                execution++;
            };
        // Create Step with the keyword, description and no behavior
        new TypeStep<Object>(keyword, stepDescription, consumer).withData(testdata).test();
        Assert.assertEquals(1, execution);
    }
}
