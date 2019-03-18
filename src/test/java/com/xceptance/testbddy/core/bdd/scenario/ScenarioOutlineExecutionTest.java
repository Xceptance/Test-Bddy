package com.xceptance.testbddy.core.bdd.scenario;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.aventstack.extentreports.GherkinKeyword;
import com.xceptance.testbddy.core.bdd.scenario.ScenarioOutline;
import com.xceptance.testbddy.core.bdd.steps.TypeSteps;
import com.xceptance.testbddy.core.throwables.errors.ScenarioError;
import com.xceptance.testbddy.core.throwables.exceptions.ScenarioException;
import com.xceptance.testbddy.util.ExecutionTest;

public class ScenarioOutlineExecutionTest extends ExecutionTest
{
    // TODO Null Testdata in between
    /**
     * Verifies that a {@link ScenarioOutline} does not execute without steps or
     * test data.
     */
    @Test(expected = ScenarioException.class)
    public void shouldNotExecuteWithoutNullStepsAndNullTestdata()
    {
        String scenarioDescription = "Scenario Description";
        new ScenarioOutline<Object>(scenarioDescription, null, null).test();
    }

    /**
     * Verifies that a {@link ScenarioOutline} does not execute without test data.
     */
    @Test(expected = ScenarioException.class)
    public void shouldNotExecuteWithoutNullTestdata()
    {
        String scenarioDescription = "Scenario Description";
        new ScenarioOutline<Object>(scenarioDescription, new TypeSteps<>(), null).test();
    }

    /**
     * Verifies that a {@link ScenarioOutline} does not execute without steps.
     */
    @Test(expected = ScenarioException.class)
    public void shouldNotExecuteWithoutNullSteps()
    {
        String scenarioDescription = "Scenario Description";
        List<Object> testdata = new ArrayList<>();
        testdata.add(new Object());
        new ScenarioOutline<Object>(scenarioDescription, null, testdata).test();
    }

    /**
     * Verifies that a {@link ScenarioOutline} executes its steps.
     * 
     * @throws ClassNotFoundException
     *             If the {@link GherkinKeyword} does not exist.
     */
    @Test
    public void shouldExecuteSteps() throws ClassNotFoundException
    {
        String scenarioDescription = "Scenario Description";
        String stepDescription = "Step Description";
        Object testdata = new Object();
        List<Object> testdataList = new ArrayList<>();
        testdataList.add(testdata);
        TypeSteps<Object> steps = new TypeSteps<Object>().given(stepDescription, (data) ->
            {
                Assert.assertSame(testdata, data);
                execution++;
            });
        new ScenarioOutline<Object>(scenarioDescription, steps, testdataList).test();
        Assert.assertEquals(1, execution);
    }

    /**
     * Verifies that a {@link ScenarioOutline} executes its steps once for each test
     * datum.
     * 
     * @throws ClassNotFoundException
     *             If the {@link GherkinKeyword} does not exist.
     */
    @Test
    public void shouldExecuteOnceForEachTestdatum() throws ClassNotFoundException
    {
        String scenarioDescription = "Scenario Description";
        String stepDescription = "Step Description";
        Object testdata = new Object();
        List<Object> testdataList = new ArrayList<>();
        testdataList.add(testdata);
        testdataList.add(testdata);
        testdataList.add(testdata);
        TypeSteps<Object> steps = new TypeSteps<Object>().given(stepDescription, (data) ->
            {
                Assert.assertSame(testdata, data);
                execution++;
            });
        new ScenarioOutline<Object>(scenarioDescription, steps, testdataList).test();
        Assert.assertEquals(3, execution);
    }

    /**
     * Verifies that a {@link ScenarioOutline} executes the steps for all test
     * datum, even if one fails.
     * 
     * @throws ClassNotFoundException
     *             If the {@link GherkinKeyword} does not exist.
     */
    @Test(expected = ScenarioError.class)
    public void shouldExecuteAllTestdatumsEvenIfOneThrowsError() throws ClassNotFoundException
    {
        String scenarioDescription = "Scenario Description";
        String stepDescription = "Step Description";
        Object testdata = new Object();
        List<Object> testdataList = new ArrayList<>();
        testdataList.add(testdata);
        testdataList.add(new Object());
        testdataList.add(testdata);
        TypeSteps<Object> steps = new TypeSteps<Object>().given(stepDescription, (data) ->
            {
                Assert.assertSame(testdata, data);
                execution++;
            });
        try
        {
            new ScenarioOutline<Object>(scenarioDescription, steps, testdataList).test();
        } catch (ScenarioError error)
        {
            Assert.assertEquals(2, execution);
            throw error;
        } catch (Throwable throwable)
        {
            Assert.fail("Unexpected Throwable thrown: " + throwable.getMessage());
        }
    }

    /**
     * Verifies that a {@link ScenarioOutline} executes the steps for all test
     * datum, even if one fails.
     * 
     * @throws ClassNotFoundException
     *             If the {@link GherkinKeyword} does not exist.
     */
    @Test(expected = ScenarioException.class)
    public void shouldExecuteAllTestdatumsEvenIfOneThrowsException() throws ClassNotFoundException
    {
        String scenarioDescription = "Scenario Description";
        String stepDescription = "Step Description";
        Object testdata = new Object();
        List<Object> testdataList = new ArrayList<>();
        testdataList.add(testdata);
        testdataList.add(new Object());
        testdataList.add(testdata);
        TypeSteps<Object> steps = new TypeSteps<Object>().given(stepDescription, (data) ->
            {
                if (data != testdata)
                {
                    throw new IllegalArgumentException("Intentional Failure");
                }
                execution++;
            });
        try
        {
            new ScenarioOutline<Object>(scenarioDescription, steps, testdataList).test();
        } catch (ScenarioException exception)
        {
            Assert.assertEquals(2, execution);
            throw exception;
        } catch (Throwable throwable)
        {
            Assert.fail("Unexpected Throwable thrown: " + throwable.getMessage());
        }
    }

}
