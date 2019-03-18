package com.xceptance.testbddy.dogtests.tests;

import static com.xceptance.testbddy.api.BddSuite.feature;
import static com.xceptance.testbddy.api.BddSuite.given;
import static com.xceptance.testbddy.api.BddSuite.scenario;

import org.junit.Test;

import com.xceptance.testbddy.core.bdd.Feature;

public class ReportingTest
{
    // TODO verify everything works without reporter
    // TODO nested features -> it's like backgrounds with data if it works

    /**
     * Verifies a feature can run without reporter.
     */
    @Test
    public void shouldRunWithoutReporter()
    {
        featureWithReporter().withReporter(null).test();
    }

    /**
     * Verifies a feature without reporter can run and doesn't affect a feature with
     * reporter.
     */
    @Test
    public void shouldRunWithAndWithoutReporter()
    {
        featureWithReporter().withReporter(null).test();
        featureWithReporter().test();
    }

    private Feature featureWithReporter()
    {
        //@formatter:off
        return feature("A feature with scenarios",
                    () -> scenario("A scenario with steps", given("A step with behavior", () -> {
                                })
                    )
                );
        //@formatter:on
    }
}
