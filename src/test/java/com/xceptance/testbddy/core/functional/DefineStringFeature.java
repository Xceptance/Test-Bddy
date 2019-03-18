package com.xceptance.testbddy.core.functional;

import static com.xceptance.testbddy.api.BddSuite.feature;
import static com.xceptance.testbddy.api.BddSuite.scenario;
import static com.xceptance.testbddy.api.BddSuite.with;

import org.junit.Assert;
import org.junit.Test;

public class DefineStringFeature
{
    @Test
    public void ShouldBeAbleToDefineString()
    {
        String testString = "Teststring";
        String otherTestString = "Another String";

        feature("Some feature",

                () -> scenario("That a single string can easily be passed",
                        with(testString).given("The String is there", data ->
                            {
                                Assert.assertEquals(testString, data);
                            })),

                () -> scenario("That multiple Strings have two executions",
                        with(testString, otherTestString).given("Any of the Strings is there", data ->
                            {
                                if (!(data.equals(testString) || data.equals(otherTestString)))
                                {
                                    throw new AssertionError(data + " does not match any of the teststrings");
                                }
                            }))).withReporter(null).test();
    }

}