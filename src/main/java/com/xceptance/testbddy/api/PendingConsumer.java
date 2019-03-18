package com.xceptance.testbddy.api;

import java.util.function.Consumer;

import com.xceptance.testbddy.core.bdd.steps.AbstractStep;

/**
 * Shows that an {@link AbstractStep} should be treated as pending.
 * 
 * @author ckeiner
 *
 * @param <T>
 *            The type of the test datum.
 */
public class PendingConsumer<T> implements Consumer<T>
{

    @Override
    public void accept(T arg0)
    {
        throw new UnsupportedOperationException("Should not be executed");
    }

}
