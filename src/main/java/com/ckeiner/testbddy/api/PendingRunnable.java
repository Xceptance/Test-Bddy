package com.ckeiner.testbddy.api;

import com.ckeiner.testbddy.core.bdd.steps.AbstractStep;

/**
 * Shows that an {@link AbstractStep} should be treated as pending.
 * 
 * @author ckeiner
 *
 */
public class PendingRunnable implements Runnable
{

    @Override
    public void run()
    {
        throw new UnsupportedOperationException("Should not be executed");
    }

}
